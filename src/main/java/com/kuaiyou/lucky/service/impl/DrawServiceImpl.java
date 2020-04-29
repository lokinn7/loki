package com.kuaiyou.lucky.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.Project;
import com.kuaiyou.lucky.compnent.MessageCompnent;
import com.kuaiyou.lucky.compnent.OpenMessageCompnent;
import com.kuaiyou.lucky.entity.Draw;
import com.kuaiyou.lucky.entity.Openuser;
import com.kuaiyou.lucky.entity.Prizesetting;
import com.kuaiyou.lucky.entity.Template;
import com.kuaiyou.lucky.entity.Userdraw;
import com.kuaiyou.lucky.entity.Wxuser;
import com.kuaiyou.lucky.enums.DrawTypeEnum;
import com.kuaiyou.lucky.enums.FakeTypeEnum;
import com.kuaiyou.lucky.enums.TemplateTypeEnum;
import com.kuaiyou.lucky.listenner.JedisExpiredListener;
import com.kuaiyou.lucky.mapper.DrawMapper;
import com.kuaiyou.lucky.req.DrawReq;
import com.kuaiyou.lucky.req.PrizeSettingReq;
import com.kuaiyou.lucky.res.AdminDrawRes;
import com.kuaiyou.lucky.res.DrawActiveRes;
import com.kuaiyou.lucky.res.DrawRes;
import com.kuaiyou.lucky.res.PrizeSettingRes;
import com.kuaiyou.lucky.res.UserdrawRes;
import com.kuaiyou.lucky.service.ActivityService;
import com.kuaiyou.lucky.service.DrawService;
import com.kuaiyou.lucky.service.FakeuserService;
import com.kuaiyou.lucky.service.OpenuserService;
import com.kuaiyou.lucky.service.PrizeService;
import com.kuaiyou.lucky.service.PrizesettingService;
import com.kuaiyou.lucky.service.TemplateService;
import com.kuaiyou.lucky.service.UserdrawService;
import com.kuaiyou.lucky.service.WxuserService;
import com.kuaiyou.lucky.thread.thread.TaskProvider;
import com.kuaiyou.lucky.utils.RandomUtil;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@Slf4j
@Service
public class DrawServiceImpl extends ServiceImpl<DrawMapper, Draw> implements DrawService {

	@Autowired
	PrizesettingService settingService;

	@Autowired
	PrizeService prizeService;

	@Autowired
	PrizesettingService prizesettingService;

	@Autowired
	StringRedisTemplate stringTemplate;

	@Autowired
	UserdrawService userdrawService;

	@Autowired
	TemplateService templateService;

	@Autowired
	ActivityService activityService;

	@Autowired
	TaskProvider taskProvider;

	@Autowired
	MessageCompnent messageCompnent;

	@Autowired
	OpenMessageCompnent openMessageCompnent;

	@Autowired
	WxuserService wxuserService;

	@Autowired
	FakeuserService fakeuserService;

	@Autowired
	OpenuserService openuserService;

	@Autowired
	Project project;

	@Override
	@Transactional(rollbackFor = IllegalArgumentException.class)
	public boolean admininsertWithActive(DrawReq draw) {
		Date now = new Date();
		Draw item = new Draw();
		item.setCtime(now);
		item.setIsrec(draw.getIsrec());
		item.setLevel(draw.getType());
		item.setName(draw.getName());
		item.setNotes(draw.getNotes());
		// 设置小程序id
		item.setMainurl(draw.getMainurl());
		item.setPubid(draw.getPubid());
		item.setPubtype(draw.getPubtype());
		item.setPubname(draw.getPubname());
		item.setStprize(draw.getStprize());
		item.setStatus(0);
		if (draw.getOnlinetime() != null) {
			item.setDeleted(1);
			stringTemplate.opsForValue().set(JedisExpiredListener.KEY_DEL_PATTERN + item.getId(), String.valueOf(0),
					draw.getOnlinetime().getTime() - now.getTime(), TimeUnit.MILLISECONDS);
		} else {
			item.setDeleted(0);
		}
		item.setType(draw.getType());
		item.setMencount(draw.getMencount());
		item.setOpentime(draw.getOpentime());
		item.setImg1(draw.getImg1());
		item.setImg2(draw.getImg2());
		item.setImg3(draw.getImg3());
		item.setAct(draw.getAct());

		// 开奖方式 1人满2时间到3手动
		Integer opentype = draw.getOpentype();
		item.setOpentype(opentype);
		boolean insert = insert(item);
		if (insert) {
			if (draw.getOnlinetime() != null && item.getDeleted().equals(1)) {
				item.setDeleted(1);
				stringTemplate.opsForValue().set(JedisExpiredListener.KEY_DEL_PATTERN + item.getId(), String.valueOf(0),
						draw.getOnlinetime().getTime() - now.getTime(), TimeUnit.MILLISECONDS);
			}
			if (opentype.equals(DrawTypeEnum.FULL.getCode())) {
				// 人满开奖 添加redis自增，人满后触发开奖条件
				stringTemplate.opsForValue().set(JedisExpiredListener.KEY_PATTERN + item.getId(), String.valueOf(0),
						3600 * 24 * 1000, TimeUnit.MILLISECONDS);
			} else if (opentype.equals(DrawTypeEnum.TIMEOVER.getCode())) {
				// 到时开奖 放入redis 配置key过期策略
				long open = draw.getOpentime().getTime();
				long time = now.getTime();
				if (open > time) {
					stringTemplate.opsForValue().set(JedisExpiredListener.KEY_PATTERN + item.getId(),
							String.valueOf(item.getId()), open - time, TimeUnit.MILLISECONDS);
				} else {
					throw new IllegalArgumentException();
				}
			} else {
				// 手动开奖 手动调起 .....
			}

			List<PrizeSettingReq> settings = draw.getPrizes();
			List<Prizesetting> prizesettings = new ArrayList<>();
			try {
				for (int i = 0; i < settings.size(); i++) {
					Prizesetting prizesetting = new Prizesetting();
					prizesetting.setPubtype(draw.getPubtype());
					prizesetting.setPubid(draw.getPubid());
					prizesetting.setAmount(settings.get(i).getAmount());
					prizesetting.setCtime(now);
					prizesetting.setDrawid(item.getId());
					prizesetting.setLevel(i + 1);
					prizesetting.setFake(settings.get(i).getFake());
					prizesetting.setPrizeurl(settings.get(i).getPrizeurl());
					prizesetting.setType(settings.get(i).getType());
					prizesetting.setIsbingo(0);
					prizesetting.setPrizename(settings.get(i).getPrizename());
					prizesetting.setLinkurl(settings.get(i).getLinkurl());
					prizesetting.setAppkey(settings.get(i).getAppkey());
					prizesettings.add(prizesetting);
				}
				boolean insertBatch = prizesettingService.insertBatch(prizesettings);
				if (insertBatch) {
					if (item.getAct() != null && item.getAct().equals(1)) {
						List<Wxuser> users = wxuserService.seletSubs();
						List<Userdraw> userdraws = new ArrayList<>();
						users.forEach(each -> {
							Userdraw userdraw = new Userdraw();
							userdraw.setUserid(each.getUserid());
							userdraw.setCtime(new Date());
							userdraw.setDrawid(item.getId());
							userdraw.setPrizelevel(0);
							userdraws.add(userdraw);
							// TODO 发送用户参与的服务消息提醒
							try {
								Openuser openuser = openuserService.selectbyUnionid(each.getUnionid());
								Template opentemp = new Template();
								opentemp.setCtime(new Date());
								opentemp.setDrawid(item.getId());
								opentemp.setOpenid(openuser.getOpenid());
								opentemp.setStatus(1);
								opentemp.setTemplateid(project.getModel4());
								opentemp.setKeyword1(item.getStprize());
								opentemp.setUserid(each.getUserid());
								opentemp.setType(TemplateTypeEnum.OPENACTIVE.getCode());
								templateService.insert(opentemp);
								opentemp.setOpenid(openuser.getOpenid());
								opentemp.setTemplateid(project.getModel3());
								opentemp.setKeyword1("成功");
								opentemp.setKeyword2(item.getStprize());
								opentemp.setKeyword3(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
								opentemp.setTitle(TemplateTypeEnum.ACTIVE.getTitle());
								opentemp.setRemark(TemplateTypeEnum.ACTIVE.getRemark());
								openMessageCompnent.send(opentemp);
							} catch (Exception e) {
								log.error("", "send notify exception :" + e);
							}
						});
						if (userdraws.size() > 0) {
							userdrawService.insertBatch(userdraws);
						}
					}
				} else {
					throw new IllegalArgumentException();
				}
				return insert;
			} catch (DuplicateKeyException e) {

			}
		}
		return false;
	}

	/**
	 * <pre>
	 * 	编辑一次抽奖
	 * 		0.如果已经有参与人则不得修改
	 * 		1.奖库中的记录删除
	 * 		2.
	 * </pre>
	 */
	@Override
	@Transactional(rollbackFor = IllegalArgumentException.class)
	public Integer updateWithSetting(DrawReq draw) {
		Date now = new Date();
		Draw item = selectById(draw.getId());
		item.setCtime(now);
		item.setLevel(draw.getType());
		item.setName(draw.getName());
		item.setNotes(draw.getNotes());
		// 设置小程序id
		item.setPubtype(draw.getPubtype());
		item.setOpentime(draw.getOpentime());
		item.setStprize(draw.getStprize());
		item.setMencount(draw.getMencount());
		// 开奖方式 1人满2时间到3手动
		item.setImg1(draw.getImg1());
		item.setImg2(draw.getImg2());
		item.setImg3(draw.getImg3());
		Integer opentype = draw.getOpentype();
		item.setOpentype(opentype);
		if (opentype.equals(DrawTypeEnum.FULL.getCode())) {
			// 人满开奖 添加redis自增，人满后触发开奖条件
			stringTemplate.opsForValue().set(JedisExpiredListener.KEY_PATTERN + item.getId(), String.valueOf(0),
					3600 * 24 * 1000, TimeUnit.MILLISECONDS);
		} else if (opentype.equals(DrawTypeEnum.TIMEOVER.getCode())) {
			// 到时开奖 放入redis 配置key过期策略
			long open = draw.getOpentime().getTime();
			long time = now.getTime();
			stringTemplate.opsForValue().set(JedisExpiredListener.KEY_PATTERN + item.getId(), String.valueOf(0),
					open - time, TimeUnit.MILLISECONDS);
		} else {
			// 手动开奖 手动调起 .....
		}
		boolean insert = updateById(item);
		// 先删除库存
		if (insert) {
			boolean delete = settingService.deleteBydrawid(item.getId());
			if (delete) {
				List<PrizeSettingReq> settings = draw.getPrizes();
				List<Prizesetting> prizesettings = new ArrayList<>();
				try {
					for (int i = 0; i < settings.size(); i++) {
						Prizesetting prizesetting = new Prizesetting();
						prizesetting.setPubtype(draw.getPubtype());
						prizesetting.setPubid(draw.getPubid());
						prizesetting.setAmount(settings.get(i).getAmount());
						prizesetting.setCtime(now);
						prizesetting.setDrawid(item.getId());
						prizesetting.setLevel(i + 1);
						prizesetting.setFake(settings.get(i).getFake());
						prizesetting.setPrizeurl(settings.get(i).getPrizeurl());
						prizesetting.setType(settings.get(i).getType());
						prizesetting.setIsbingo(0);
						prizesetting.setPrizename(settings.get(i).getPrizename());
						prizesetting.setLinkurl(settings.get(i).getLinkurl());
						prizesetting.setAppkey(settings.get(i).getAppkey());
						prizesettings.add(prizesetting);
					}
					boolean insertBatch = prizesettingService.insertBatch(prizesettings);
					if (!insertBatch) {
						throw new IllegalArgumentException();
					}
				} catch (DuplicateKeyException e) {

				}
			} else {
				throw new IllegalArgumentException();
			}
		}
		return item.getId();
	}

	/**
	 * <pre>
	 * 		1.查找该抽奖所有的用户
	 * 		2.查找该抽奖所有的奖品
	 * 		3.查看奖品是否含有伪抽奖
	 * </pre>
	 * 
	 * 开奖
	 */

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean openDraw(Draw cache) {
		cache = selectById(cache.getId());
		if (cache != null) {
			if (cache.getStatus().equals(1)) {
				return true;
			}
			Integer drawid = cache.getId();
			EntityWrapper<Prizesetting> pwrapper = new EntityWrapper<>();
			pwrapper.eq(Prizesetting.DRAWID, drawid);
			EntityWrapper<Userdraw> uwrapper = new EntityWrapper<>();
			uwrapper.eq(Userdraw.DRAWID, drawid).eq(Userdraw.BINGO, 0);
			/**
			 * <pre>
			 * 	1.根据抽奖将奖项分级
			 *  2.获取每个奖品集合的长度，并以此为seed进行开奖
			 *  3.入参为用户的id集合
			 *  4.当设置完抽奖时批量更新奖品及未中奖情况
			 * </pre>
			 */
			List<Userdraw> userdraws = userdrawService.selectJoinsList(drawid);
			List<Prizesetting> settings = prizesettingService.selectList(pwrapper);
			if (settings.size() > 0 && userdraws.size() > 0) {
				List<Integer> uids = userdraws.stream().map(e -> e.getId()).collect(Collectors.toList());
				List<Prizesetting> opens = settings.stream().collect(Collectors.groupingBy(Prizesetting::getFake))
						.get(FakeTypeEnum.NO.getCode());
				if (opens != null && opens.size() > 0) {
					List<Integer> updates = null;
					for (Prizesetting item : opens) {
						Integer prizetype = item.getType();
						Integer key = item.getLevel();
						Integer value = item.getAmount();
						if (updates == null) {
							updates = getLuckList(uids, value, key, prizetype);
						} else {
							updates = getLuckList(updates, value, key, prizetype);
						}
					}
				}
				sendMessage(cache);
			}
			cache.setStatus(1);
			cache.setOpentime(new Date());
			boolean flag = updateById(cache);
			log.info(String.format("open draw ,result is :%s, object is :%s", flag, JSON.toJSONString(cache)));
			if (flag) {
				stringTemplate.delete(JedisExpiredListener.KEY_PATTERN + cache.getId());
				return flag;
			} else {
				throw new IllegalArgumentException();
			}
		}
		return Boolean.FALSE;
	}

	// 抽奖 传参 1.总人数 2.几人中奖
	public List<Integer> getLuckList(List<Integer> idList, Integer n, Integer level, Integer prizetype) {
		ArrayList<Integer> a = null;
		if (idList.size() <= n) {
			a = RandomUtil.makeRandom(0, idList.size(), idList.size());
		} else {
			a = RandomUtil.makeRandom(0, idList.size(), n);
		}
		ArrayList<Integer> eidList = new ArrayList<Integer>();
		Object[] b = a.toArray();
		Arrays.sort(b);
		List<Integer> t = new ArrayList<>();
		for (int i = 0; i < b.length; i++) {
			eidList.add(idList.get((int) b[i]));
			t.add(idList.get((int) b[i]));
		}
		idList.removeAll(t);
		if (eidList.size() > 0) {
			userdrawService.updateBatchWithLevel(eidList, level, prizetype);
		}
		return idList;
	}

	public void sendMessage(Draw cache) {
		/**
		 * <pre>
		 * 		1发送模板消息
		 * 		2查找所有参与抽奖的人
		 * </pre>
		 * 
		 */
		// 同一个模板id 用户id 小程序id只可能有一个
		List<Template> templates = templateService.selectListByType(cache.getId(), TemplateTypeEnum.OPENMINI.getCode());
		List<Template> opentemplates = templateService.selectListByType(cache.getId(),
				TemplateTypeEnum.OPENACTIVE.getCode());

		try {
			for (Template template : templates) {
				template.setKeyword1(cache.getStprize());
				template.setKeyword2(cache.getPubname());
				template.setKeyword3(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
				if (StringUtils.isNotBlank(cache.getStprize()) || StringUtils.isNotBlank(cache.getPubname())) {
					template.setKeyword4(
							String.format("您参与的 %s 发起的 %s 已经开奖啦，快去看看吧！", cache.getPubname(), cache.getStprize()));
				} else {
					template.setKeyword4(String.format("您参与的抽奖已经开奖啦，快去看看吧！"));
				}
				template.setStatus(0);
				messageCompnent.sendMessage(template);
			}

			for (Template template : opentemplates) {
				template.setTitle(TemplateTypeEnum.OPENACTIVE.getTitle());
				template.setKeyword2(DateUtil.format(new Date(), "yyyyMMdd"));
				template.setKeyword3(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
				template.setRemark(TemplateTypeEnum.OPENACTIVE.getRemark());
				template.setStatus(0);
				openMessageCompnent.send(template);
			}
			templates.addAll(opentemplates);
			templateService.updateAllColumnBatchById(templates);
		} catch (Exception e) {
			log.error("send template exception happend :" + e);
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 *	1.若无奖品信息添加奖品 (个人用户天添加则无奖品信息)
	 *	2.批量添加奖品信息
	 *	3.添加抽奖信息
	 * </pre>
	 */
	@Override
	@Transactional(rollbackFor = IllegalArgumentException.class)
	public boolean admininsertWithSetting(DrawReq draw) {
		Date now = new Date();
		Draw item = new Draw();
		item.setCtime(now);
		item.setIsrec(draw.getIsrec());
		item.setLevel(draw.getType());
		item.setName(draw.getName());
		item.setNotes(draw.getNotes());
		// 设置小程序id
		item.setMainurl(draw.getMainurl());
		item.setPubid(draw.getPubid());
		item.setPubtype(1);
		item.setPubname(draw.getPubname());
		item.setStprize(draw.getStprize());
		item.setStatus(0);
		item.setDeleted(0);
		item.setType(draw.getType());
		item.setMencount(draw.getMencount());
		item.setOpentime(draw.getOpentime());
		item.setImg1(draw.getImg1());
		item.setImg2(draw.getImg2());
		item.setImg3(draw.getImg3());
		item.setAct(draw.getAct());

		// 开奖方式 1人满2时间到3手动
		Integer opentype = draw.getOpentype();
		item.setOpentype(opentype);
		boolean insert = insert(item);
		if (insert) {
			if (opentype.equals(DrawTypeEnum.FULL.getCode())) {
				// 人满开奖 添加redis自增，人满后触发开奖条件
				stringTemplate.opsForValue().set(JedisExpiredListener.KEY_PATTERN + item.getId(), String.valueOf(0),
						3600 * 24 * 1000, TimeUnit.MILLISECONDS);
			} else if (opentype.equals(DrawTypeEnum.TIMEOVER.getCode())) {
				// 到时开奖 放入redis 配置key过期策略
				long open = draw.getOpentime().getTime();
				long time = now.getTime();
				if (open > time) {
					stringTemplate.opsForValue().set(JedisExpiredListener.KEY_PATTERN + item.getId(),
							String.valueOf(item.getId()), open - time, TimeUnit.MILLISECONDS);
				} else {
					return false;
				}
			} else {
				// 手动开奖 手动调起 .....
			}

			List<PrizeSettingReq> settings = draw.getPrizes();
			List<Prizesetting> prizesettings = new ArrayList<>();
			try {
				for (int i = 0; i < settings.size(); i++) {
					Prizesetting prizesetting = new Prizesetting();
					prizesetting.setPubtype(draw.getPubtype());
					prizesetting.setPubid(draw.getPubid());
					prizesetting.setAmount(settings.get(i).getAmount());
					prizesetting.setCtime(now);
					prizesetting.setDrawid(item.getId());
					prizesetting.setLevel(i + 1);
					prizesetting.setFake(settings.get(i).getFake());
					prizesetting.setPrizeurl(settings.get(i).getPrizeurl());
					prizesetting.setType(settings.get(i).getType());
					prizesetting.setIsbingo(0);
					prizesetting.setPrizename(settings.get(i).getPrizename());
					prizesetting.setLinkurl(settings.get(i).getLinkurl());
					prizesetting.setAppkey(settings.get(i).getAppkey());
					prizesettings.add(prizesetting);
				}
				boolean insertBatch = prizesettingService.insertBatch(prizesettings);
				// 添加抽奖成功且抽奖为活动抽奖
				if (insertBatch && item.getAct().equals(1)) {
					List<Wxuser> users = wxuserService.seletSubs();
					List<Userdraw> userdraws = new ArrayList<>();
					users.forEach(each -> {
						Userdraw userdraw = new Userdraw();
						userdraw.setUserid(each.getUserid());
						userdraw.setCtime(new Date());
						userdraw.setDrawid(item.getId());
						userdraws.add(userdraw);
						// TODO 发送用户参与的服务消息提醒
					});
				} else {
					throw new IllegalArgumentException();
				}
				return insert;
			} catch (DuplicateKeyException e) {

			}
		}
		return false;
	}

	/**
	 * <pre>
	 *	1.若无奖品信息添加奖品 (个人用户天添加则无奖品信息)
	 *	2.批量添加奖品信息
	 *	3.添加抽奖信息
	 * </pre>
	 */
	@Override
	@Transactional(rollbackFor = IllegalArgumentException.class)
	public Integer insertWithSetting(DrawReq draw) {
		Wxuser wxuser = wxuserService.selectByUserid(draw.getUserid());
		Date now = new Date();
		Draw item = new Draw();
		item.setCtime(now);
		item.setIsrec(0);
		item.setLevel(draw.getType());
		item.setName(draw.getName());
		item.setNotes(draw.getNotes());
		item.setPubid(draw.getUserid());
		item.setPubtype(2);
		item.setPubname(wxuser.getNickname());
		item.setStprize(draw.getStprize());
		item.setStatus(0);
		item.setDeleted(0);
		item.setType(draw.getType());
		item.setMencount(draw.getMencount());
		item.setOpentime(draw.getOpentime());
		// 开奖方式 1人满2时间到3手动
		Integer opentype = draw.getOpentype();
		item.setOpentype(opentype);
		boolean insert = insert(item);
		if (insert) {
			log.info(String.format("isnert object : %s", JSON.toJSONString(item)));
			if (opentype.equals(DrawTypeEnum.FULL.getCode())) {
				// 人满开奖 添加redis自增，人满后触发开奖条件
				stringTemplate.opsForValue().set(JedisExpiredListener.KEY_PATTERN + item.getId(), String.valueOf(0),
						3600 * 24 * 1000, TimeUnit.MILLISECONDS);
			} else if (opentype.equals(DrawTypeEnum.TIMEOVER.getCode())) {
				// 到时开奖 放入redis 配置key过期策略
				long open = draw.getOpentime().getTime();
				long time = now.getTime();
				stringTemplate.opsForValue().set(JedisExpiredListener.KEY_PATTERN + item.getId(),
						String.valueOf(item.getId()), open - time, TimeUnit.MILLISECONDS);
			} else {
				// 手动开奖 手动调起 .....
			}
			templateService.insertBase(draw.getUserid(), draw.getFormid(), item.getId());
			List<PrizeSettingReq> settings = draw.getPrizes();
			List<Prizesetting> prizesettings = new ArrayList<>();
			try {
				for (int i = 0; i < settings.size(); i++) {
					// 组织settings
					Prizesetting prizesetting = new Prizesetting();
					prizesetting.setPubtype(2);
					prizesetting.setPubid(draw.getUserid());
					prizesetting.setAmount(settings.get(i).getAmount());
					prizesetting.setCtime(now);
					prizesetting.setDrawid(item.getId());
					prizesetting.setLevel(i + 1);
					prizesetting.setFake(settings.get(i).getFake());
					prizesetting.setPrizeurl(settings.get(i).getPrizeurl());
					prizesetting.setType(settings.get(i).getType());
					prizesetting.setIsbingo(0);
					prizesetting.setPrizename(settings.get(i).getPrizename());
					prizesetting.setAppkey(settings.get(i).getAppkey());
					prizesettings.add(prizesetting);
				}
				boolean insertBatch = prizesettingService.insertBatch(prizesettings);
				if (!insertBatch) {
					throw new IllegalArgumentException();
				}

			} catch (DuplicateKeyException e) {

			}
		}
		return item.getId();
	}

	@Override
	public DrawRes drawDetail(CommonReq req) {
		/**
		 * 查找所有参与的人并进行分组
		 */
		DrawRes drawDetail = baseMapper.drawDetail(req);
		if (drawDetail == null) {
			return null;
		}
		List<UserdrawRes> userdraws = userdrawService.selectByDrawId(req.getDrawid());
		drawDetail.setTotaljoin(userdraws.size());
		List<Userdraw> temps = userdrawService.selectDrawidWithUserid(Arrays.asList(Integer.valueOf(req.getDrawid())),
				req.getUserid());
		if (temps.size() > 0) {
			drawDetail.setIsjoin(1);
		}
		if (userdraws.size() > 0) {
			Optional<UserdrawRes> findFirst = userdraws.stream()
					.filter(each -> (each.getUserid().equals(req.getUserid()))).findFirst();

			if (findFirst.isPresent()) {
				drawDetail.setIsbingo(findFirst.get());
			}
			// 按中奖分组
			Map<Integer, List<UserdrawRes>> collect = userdraws.stream()
					.collect(Collectors.groupingBy(UserdrawRes::getPrizelevel));
			// for eache and limit
			collect.forEach(new BiConsumer<Integer, List<UserdrawRes>>() {
				@Override
				public void accept(Integer t, List<UserdrawRes> u) {
					u = u.subList(0, u.size() < 10 ? u.size() : 10);
				}

			});
			// 开奖后时执行,查不到新建
			if (drawDetail.getStatus().equals(1)) {
				List<PrizeSettingRes> fakes = drawDetail.getPrizes().stream().filter(each -> each.getFake().equals(1))
						.collect(Collectors.toList());
				fakes.forEach(e -> {
					List<UserdrawRes> fakeusers = fakeuserService.selectByDrawid(drawDetail.getId(), e.getLevel());

					Integer amount = e.getAmount();
					if (fakeusers.size() <= 0) {
						if (amount > 0) {
							fakeusers = wxuserService.randFake(amount, drawDetail.getId(), e.getLevel());
							fakeuserService.insertFakeBatch(fakeusers, drawDetail.getId(), e.getLevel());
						}
					}
					collect.put(e.getLevel(), fakeusers.subList(0, fakeusers.size() < 10 ? fakeusers.size() : 10));
					drawDetail.setTotaljoin(drawDetail.getTotaljoin() + fakeusers.size());
					userdraws.addAll(fakeusers);
				});
			}
			{
				List<UserdrawRes> fakeusers = fakeuserService.selectByDrawid(drawDetail.getId(), 0);
				drawDetail.setTotaljoin(drawDetail.getTotaljoin() + fakeusers.size());
				userdraws.addAll(fakeusers);
			}

			drawDetail.setJoins(userdraws.subList(0, userdraws.size() < 10 ? userdraws.size() : 10));
			drawDetail.setBingos(collect);
		}
		return drawDetail;
	}

	public void findJoin(List<DrawRes> drs, String userid) {
		List<Integer> collects = drs.stream().map(e -> e.getId()).collect(Collectors.toList());
		List<Userdraw> userdraws = userdrawService.selectDrawidWithUserid(collects, userid);
		if (userdraws.size() > 0 && collects.size() > 0) {
			Map<Integer, List<Userdraw>> collect = userdraws.stream()
					.collect(Collectors.groupingBy(Userdraw::getDrawid));
			for (DrawRes drawRes : drs) {
				List<Userdraw> list = collect.get(drawRes.getId());
				if (list != null) {
					if (list.size() > 0) {
						drawRes.setIsjoin(1);
					} else {
						drawRes.setIsjoin(0);
					}
				}
			}
		}
	}

	@Override
	public List<DrawRes> baseGrid(CommonReq req) {
		List<DrawRes> baseGrid = baseMapper.baseGrid(req);
		findJoin(baseGrid, req.getUserid());
		return baseGrid;
	}

	@Override
	public int baseGridCount(CommonReq req) {
		Integer count = baseMapper.baseGridCount(req);
		return count == null ? 0 : count;
	}

	@Override
	public List<DrawRes> recGrid(CommonReq req) {
		List<DrawRes> recGrid = baseMapper.recGrid(req);
		findJoin(recGrid, req.getUserid());
		return recGrid;
	}

	@Override
	public List<AdminDrawRes> adminGrid(CommonReq req) {
		return baseMapper.adminGrid(req);
	}

	@Override
	public int adminGridCount(CommonReq req) {
		Integer count = baseMapper.adminGridCount(req);
		return count == null ? 0 : count;
	}

	@Override
	public DrawRes adminDrawDetail(CommonReq req) {
		return baseMapper.adminDrawDetail(req);
	}

	@Override
	public Draw selectByWithStatus(Integer drawid) {
		EntityWrapper<Draw> wrapper = new EntityWrapper<>();
		wrapper.eq(Draw.ID, drawid).eq(Draw.DELETED, 0);
		return selectOne(wrapper);
	}

	@Override
	public List<DrawRes> activeGrid(CommonReq req) {
		return baseMapper.activeGrid(req);
	}

	@Override
	public int activeGridCount(CommonReq req) {
		Integer count = baseMapper.activeGridCount(req);
		return count == null ? 0 : count;
	}

	@Override
	public List<DrawActiveRes> selectWithActive() {
		return baseMapper.selectWithActive();
	}

}

package com.kuaiyou.lucky.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.Project;
import com.kuaiyou.lucky.compnent.OpenMessageCompnent;
import com.kuaiyou.lucky.entity.Draw;
import com.kuaiyou.lucky.entity.Openuser;
import com.kuaiyou.lucky.entity.Template;
import com.kuaiyou.lucky.entity.Userdraw;
import com.kuaiyou.lucky.entity.Wxuser;
import com.kuaiyou.lucky.enums.CoinTypeEnum;
import com.kuaiyou.lucky.enums.DrawTypeEnum;
import com.kuaiyou.lucky.enums.TemplateTypeEnum;
import com.kuaiyou.lucky.listenner.JedisExpiredListener;
import com.kuaiyou.lucky.mapper.UserdrawMapper;
import com.kuaiyou.lucky.req.ActiveReq;
import com.kuaiyou.lucky.req.UserdrawReq;
import com.kuaiyou.lucky.res.AdminUserdrawRes;
import com.kuaiyou.lucky.res.DaydataRes;
import com.kuaiyou.lucky.res.DrawRes;
import com.kuaiyou.lucky.res.UserdrawRes;
import com.kuaiyou.lucky.service.ActiveService;
import com.kuaiyou.lucky.service.BanlistService;
import com.kuaiyou.lucky.service.DrawService;
import com.kuaiyou.lucky.service.OpenuserService;
import com.kuaiyou.lucky.service.TemplateService;
import com.kuaiyou.lucky.service.UserdrawService;
import com.kuaiyou.lucky.service.WxuserService;
import com.kuaiyou.lucky.utils.DateUtil;

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
public class UserdrawServiceImpl extends ServiceImpl<UserdrawMapper, Userdraw> implements UserdrawService {

	@Autowired
	DrawService drawService;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	TemplateService templateService;

	@Autowired
	WxuserService wxuserService;

	@Autowired
	Project project;

	@Autowired
	ActiveService activeService;

	@Autowired
	BanlistService banlistService;

	@Autowired
	OpenuserService openuserService;

	@Autowired
	OpenMessageCompnent openMessageCompnent;

	@Override
	public boolean insertDraw(UserdrawReq item) {
		/**
		 * <pre>
		 * 		1.添加参与抽奖
		 * 		2.添加消息模板
		 * </pre>
		 */
		boolean flag = false;
		Integer drawid = item.getDrawid();
		Draw cachedraw = drawService.selectById(drawid);
		log.info("json is " + JSON.toJSONString(cachedraw));
		if (cachedraw != null) {
			Userdraw userdraw = new Userdraw();
			Integer opentype = cachedraw.getOpentype();
			userdraw.setDrawid(cachedraw.getId());
			userdraw.setPrizelevel(0);
			userdraw.setCtime(new Date());
			userdraw.setUserid(item.getUserid());
			// 添加消息模板
			flag = insert(userdraw);
			if (flag) {
				Wxuser user = wxuserService.selectByUserid(item.getUserid());
				{
					if (cachedraw.getAct().equals(1)) {
						try {
							// 添加公众号开奖通知
							Openuser openuser = openuserService.selectbyUnionid(user.getUnionid());
							Template opentemp = new Template();
							opentemp.setCtime(new Date());
							opentemp.setDrawid(item.getDrawid());
							opentemp.setOpenid(openuser.getOpenid());
							opentemp.setStatus(1);
							opentemp.setTemplateid(project.getModel4());
							opentemp.setUserid(item.getUserid());
							opentemp.setKeyword1(cachedraw.getStprize());
							opentemp.setType(TemplateTypeEnum.OPENACTIVE.getCode());
							templateService.insert(opentemp);
							// TODO 下发成功参与锦鲤的通知
							if (user.getSubac().equals(0)) {
								opentemp.setOpenid(openuser.getOpenid());
								opentemp.setTemplateid(project.getModel3());
								opentemp.setKeyword1("成功");
								opentemp.setKeyword2(cachedraw.getStprize());
								opentemp.setKeyword3(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
								opentemp.setTitle(TemplateTypeEnum.ACTIVE.getTitle());
								opentemp.setRemark(TemplateTypeEnum.ACTIVE.getRemark());
								openMessageCompnent.send(opentemp);
								user.setSubac(1);
								wxuserService.updateById(user);
							}
						} catch (Exception e) {
							log.error("{}", "send template error ,exception is :" + e);
						}
					} else {
						Template template = new Template();
						template.setCtime(new Date());
						template.setDrawid(item.getDrawid());
						template.setFormid(item.getFormid());
						template.setOpenid(user.getOpenid());
						template.setPage("/pages/prizedetail/prizedetail?source=notify&drawid=" + item.getDrawid());
						template.setStatus(1);
						template.setTemplateid(project.getModel1());
						template.setUserid(item.getUserid());
						template.setType(TemplateTypeEnum.OPENMINI.getCode());
						templateService.insert(template);
					}
				}

				try {
					if (opentype.equals(DrawTypeEnum.FULL.getCode())) {
						Long decrement = stringRedisTemplate.opsForValue()
								.increment(JedisExpiredListener.KEY_PATTERN + cachedraw.getId());
						log.info("send to redis : id is :" + JedisExpiredListener.KEY_PATTERN + cachedraw.getId());
						if (decrement.intValue() >= cachedraw.getMencount()) {
							drawService.openDraw(new Draw(cachedraw.getId()));
						}
					}
					try {
						ActiveReq dd = new ActiveReq();
						dd.setCtime(new Date());
						dd.setUserid(item.getUserid());
						dd.setCtype(CoinTypeEnum.JOINS.getCode());
						dd.setDrawid(cachedraw.getId());
						dd.setNotes(CoinTypeEnum.JOINS.getText());
						activeService.insertWithCoins(dd, CoinTypeEnum.JOINS.getCode());
					} catch (Exception e) {
						log.error("insert active and coins error");
					}
				} catch (DuplicateKeyException e) {
					log.error(String.format("insert add userdraw error :", e));
				}
			}
		}

		return flag;
	}

	@Override
	public boolean updateBatchWithLevel(ArrayList<Integer> eidList, int level, Integer prizetype) {
		return baseMapper.updateBatchWithLevel(eidList, level, prizetype) > 0;
	}

	@Override
	public List<UserdrawRes> selectByDrawId(String drawid) {
		return baseMapper.selectByDrawId(drawid);
	}

	@Override
	public List<Userdraw> selectDrawidWithUserid(List<Integer> collects, String userid) {
		EntityWrapper<Userdraw> wrapper = new EntityWrapper<>();
		wrapper.in(Userdraw.DRAWID, collects).eq(Userdraw.USERID, userid);
		return selectList(wrapper);
	}

	@Override
	public List<DrawRes> selectJoinList(CommonReq req) {
		return baseMapper.selectJoinList(req);
	}

	@Override
	public List<DrawRes> selectBingoList(CommonReq req) {
		return baseMapper.selectBingoList(req);
	}

	@Override
	public int selectJoinListCount(String userid) {
		Integer count = baseMapper.selectJoinListCount(userid);
		return count == null ? 0 : count;
	}

	@Override
	public int selectBingoListCount(String userid) {
		Integer count = baseMapper.selectBingoListCount(userid);
		return count == null ? 0 : count;
	}

	@Override
	public List<UserdrawRes> adminGrid(UserdrawReq item) {
		return baseMapper.adminGrid(item);
	}

	@Override
	public int adminGridCount(UserdrawReq item) {
		Integer count = baseMapper.adminGridCount(item);
		return count == null ? 0 : count;
	}

	@Override
	public List<UserdrawRes> selectByDrawId2(CommonReq req) {
		return baseMapper.selectByDrawId2(req);
	}

	@Override
	public int selectByDrawId2Count(CommonReq req) {
		Integer total = baseMapper.selectByDrawId2Count(req);
		return total == null ? 0 : total;
	}

	@Override
	public List<DrawRes> selectPubList(CommonReq req) {
		return baseMapper.selectPubList(req);
	}

	@Override
	public int selectPubListCount(String userid) {
		Integer total = baseMapper.selectPubListCount(userid);
		return total == null ? 0 : total;
	}

	@Override
	public List<DaydataRes> findUv(UserdrawReq item) {
		return baseMapper.findUv(item);
	}

	@Override
	public List<DaydataRes> findPubcount(UserdrawReq item) {
		return baseMapper.findPubcount(item);
	}

	@Override
	public List<DaydataRes> findOpencount(UserdrawReq item) {
		return baseMapper.findOpencount(item);
	}

	@Override
	public List<DaydataRes> findCountjoin(UserdrawReq item) {
		return baseMapper.findCountjoin(item);
	}

	@Override
	public List<AdminUserdrawRes> adminGridDetail(UserdrawReq item) {
		return baseMapper.adminGridDetail(item);
	}

	@Override
	public List<Userdraw> selectJoinsList(Integer uwrapper) {
		return baseMapper.selectJoinsList(uwrapper);
	}

}

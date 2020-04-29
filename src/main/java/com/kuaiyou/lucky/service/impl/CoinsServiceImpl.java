package com.kuaiyou.lucky.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.entity.Coins;
import com.kuaiyou.lucky.entity.Task;
import com.kuaiyou.lucky.entity.Wxuser;
import com.kuaiyou.lucky.enums.CoinTypeEnum;
import com.kuaiyou.lucky.mapper.CoinsMapper;
import com.kuaiyou.lucky.service.CoinsService;
import com.kuaiyou.lucky.service.TaskService;
import com.kuaiyou.lucky.service.WxuserService;
import com.kuaiyou.lucky.utils.DateUtil;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2019-09-09
 */
@Service
public class CoinsServiceImpl extends ServiceImpl<CoinsMapper, Coins> implements CoinsService {

	@Autowired
	TaskService taskService;

	@Autowired
	WxuserService wxuserService;

	/**
	 * <pre>
	 * 		1.按积分类型
	 * 		2.此接口为手动触发添加积分
	 * 		3.即为签到添加积分
	 * 		4.按不同类型调用
	 * </pre>
	 */
	@Override
	public boolean insertTemp(Coins req) {
		req.setCtime(new Date());
		req.setNotes(String.format(CoinTypeEnum.getDesc(req.getCtype()), req.getUserid()));
		// 两步操作，给用户添加积分，记录日志
		return insertWithUser(req);
	}

	@Override
	@Transactional(rollbackFor = IllegalArgumentException.class)
	public boolean insertTemp(Coins req, int dtype) {
		Wxuser user = wxuserService.selectByUserid(req.getUserid());
		user.setSubac(1);
		boolean flag = wxuserService.updateById(user);
		if (flag) {
			Task task = taskService.selectByCtype(req.getCtype(), dtype);
			if (task != null) {
				req.setAmount(task.getCoins());
				req.setCtime(new Date());
				req.setNotes(String.format(CoinTypeEnum.getDesc(req.getCtype()), req.getUserid()));
				// 两步操作，给用户添加积分，记录日志
				flag = insertWithUser(req);
			} else {
				throw new IllegalArgumentException("can not find task.");
			}
		}
		return flag;
	}

	@Override
	public boolean userSign(Coins req) {
		/**
		 * <pre>
		 *  0.查看用户今日是否参加过签到
		 *  1.查看当前用户是连续的第几天签到
		 *  2.按照天数*10的积分添加
		 *  3.查找连续的记录
		 *  	a.如果连续的记录到今天超过一天那么奖励的积分则为首日的签到积分
		 *  	b.如果连续的记录不足一个自然天那么视为连续签到
		 *  temp：
		 *  	暂时按照星期几来奖励金币
		 * </pre>
		 */
		int weekOfDay = DateUtil.getWeekOfDay();
		req.setAmount(10 * weekOfDay);
		return insertTemp(req);
	}

	@Override
	public boolean userAction(Coins req) {
		Task task = taskService.selectByCtype(CoinTypeEnum.SHARE.getCode(), 1);
		if (task != null) {
			EntityWrapper<Coins> wrapper = new EntityWrapper<>();
			wrapper.eq(Coins.USERID, req.getUserid()).eq(Coins.CTYPE, CoinTypeEnum.SHARE.getCode());
			int signs = selectCount(wrapper);
			/**
			 * 获取当日分享次数 1若等于任务完成数则
			 */
			if (task.getAmount().intValue() == signs) {
				return insertTemp(req);
			}
		}
		return insert(req);
	}

	@Override
	public Result selectWeekSigh(String userid) {
		Date end = DateUtil.getEndWeekOfDay();
		Date start = DateUtil.getStartWeekOfDay();
		EntityWrapper<Coins> wrapper = new EntityWrapper<>();
		wrapper.eq(Coins.USERID, userid).eq(Coins.CTYPE, CoinTypeEnum.SIGN.getCode()).between(Coins.CTIME, start, end);
		List<Coins> results = selectList(wrapper);
		List<Coins> weeks = Arrays.asList(null, null, null, null, null, null, null);
		for (Coins coins : results) {
			if (coins != null) {
				int index = DateUtil.getWeekOfDay(coins.getCtime());
				weeks.set(index - 1, coins);
			}
		}
		HashMap<String, Object> result = new HashMap<>();
		result.put("signs", weeks);
		result.put("signcount", results.size());
		return Result.ok(result);
	}

	@Override
	public boolean insertWithUser(Coins req) {
		return SqlHelper.retCount(baseMapper.insertWithUser(req)) > 0;
	}

	@Override
	public boolean updateTemp(Coins req) {
		req.setCtime(new Date());
		req.setNotes(String.format(CoinTypeEnum.getDesc(req.getCtype()), req.getUserid()));
		Integer count = baseMapper.minsWithUser(req);
		int i = count == null ? 0 : count;
		return i > 0;
	}

	@Override
	public Coins selectByType(String userid, int code) {
		EntityWrapper<Coins> wrapper = new EntityWrapper<>();
		wrapper.eq(Coins.USERID, userid).eq(Coins.CTYPE, code);
		return selectOne(wrapper);
	}

}

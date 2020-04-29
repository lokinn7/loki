package com.kuaiyou.lucky.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kuaiyou.lucky.entity.Active;
import com.kuaiyou.lucky.entity.Task;
import com.kuaiyou.lucky.mapper.ActiveMapper;
import com.kuaiyou.lucky.req.ActiveReq;
import com.kuaiyou.lucky.req.TaskReq;
import com.kuaiyou.lucky.res.ActiveFlagRes;
import com.kuaiyou.lucky.res.ActiveRes;
import com.kuaiyou.lucky.res.DailyreportRes;
import com.kuaiyou.lucky.res.TaskRes;
import com.kuaiyou.lucky.service.ActiveService;
import com.kuaiyou.lucky.service.TaskService;
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
public class ActiveServiceImpl extends ServiceImpl<ActiveMapper, Active> implements ActiveService {

	@Autowired
	TaskService taskService;

	@Override
	public boolean insertWithCoins(ActiveReq active, int cointype) {
		// 查找每日及每周任务
		Task task = taskService.selectByCtype(cointype, 2);
		Task taskweek = taskService.selectByCtype(cointype, 3);
		boolean flag = insert(initActive(active));
		EntityWrapper<Active> wrapper = new EntityWrapper<>();
		wrapper.eq(Active.USERID, active.getUserid()).eq(Active.CTYPE, active.getCtype()).between(Active.CTIME,
				DateUtil.getStartOfDay(), DateUtil.getEndOfDay());
		// 已经达到的次数
		int temp = selectCount(wrapper);
		EntityWrapper<Active> tempwrapper = new EntityWrapper<>();
		tempwrapper.eq(Active.USERID, active.getUserid()).eq(Active.CTYPE, active.getCtype()).between(Active.CTIME,
				DateUtil.getStartWeekOfDay(), DateUtil.getEndWeekOfDay());
		// 本周达到的次数
		int tempweek = selectCount(wrapper);
		if (task != null) {
			active.setCoins(task.getCoins());
			if (flag) {
				if (temp == task.getAmount().intValue()) {
					return baseMapper.insertWithCoins(active);
				}
			} else {
				throw new IllegalArgumentException(
						"Insert into active log occur a exception, please check your code and object");
			}
		}
		if (taskweek != null) {
			active.setCoins(taskweek.getCoins());
			if (flag) {
				if (tempweek == taskweek.getAmount().intValue()) {
					return baseMapper.insertWithCoins(active);
				}
			} else {
				throw new IllegalArgumentException(
						"Insert into active log occur a exception, please check your code and object");
			}
		}
		return flag;
	}

	public Active initActive(ActiveReq active) {
		Active temp = new Active();
		temp.setCtime(new Date());
		temp.setCtype(active.getCtype());
		temp.setDrawid(active.getDrawid());
		temp.setNotes(active.getNotes());
		temp.setUserid(active.getUserid());
		temp.setTuserid(active.getTuserid());
		return temp;
	}

	@Override
	public TaskRes dateTaskResult(TaskReq req) {
		return baseMapper.dateTaskResult(req);
	}

	@Override
	public TaskRes newTaskResult(TaskReq req) {
		return baseMapper.newTaskResult(req);
	}

	@Override
	public List<Active> selectByUserid(String userid, String invuserid, int code) {
		EntityWrapper<Active> wrapper = new EntityWrapper<>();
		wrapper.eq(Active.USERID, invuserid).eq(Active.USERID, userid).eq(Active.CTYPE, code);
		return selectList(wrapper);
	}

	@Override
	public List<ActiveRes> channelStat(ActiveRes req) {
		return baseMapper.channelStat(req);
	}

	@Override
	public int channelStatCount(ActiveRes req) {
		Integer count = baseMapper.channelStatCount(req);
		return count == null ? 0 : count;
	}

	@Override
	public List<DailyreportRes> dailyData(ActiveRes req) {
		return baseMapper.dailyData(req);
	}

	@Override
	public List<ActiveFlagRes> getDetailStatic(ActiveFlagRes req) {
		return baseMapper.getDetailStatic(req);
	}

}

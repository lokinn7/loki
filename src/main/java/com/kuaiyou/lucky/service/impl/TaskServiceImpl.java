package com.kuaiyou.lucky.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kuaiyou.lucky.entity.Task;
import com.kuaiyou.lucky.enums.CoinTypeEnum;
import com.kuaiyou.lucky.mapper.TaskMapper;
import com.kuaiyou.lucky.req.TaskReq;
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
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

	@Autowired
	ActiveService activeService;

	@Override
	public List<TaskRes> selectByDtype(int i) {
		List<TaskRes> list = baseMapper.selectByDtype(i);
		return list;
	}

	@Override
	public Task selectByCtype(int code, int dcode) {
		EntityWrapper<Task> wrapper = new EntityWrapper<>();
		wrapper.eq(Task.CTYPE, code).eq(Task.DTYPE, dcode);
		return selectOne(wrapper);
	}

	@Override
	public Map<String, Object> getTasks(String userid) {
		HashMap<String, Object> result = new HashMap<>();
		List<TaskRes> newtask = selectByDtype(1);
		List<TaskRes> daytask = selectByDtype(2);
		List<TaskRes> weektsk = selectByDtype(3);

		TaskReq req = new TaskReq();
		req.setStarttime(DateUtil.getStartOfDay());
		req.setEndtime(DateUtil.getEndOfDay());
		req.setUserid(userid);
		TaskRes newresult = activeService.newTaskResult(req);
		TaskRes dateresult = activeService.dateTaskResult(req);
		req.setStarttime(DateUtil.startWeekOfDay());
		req.setEndtime(DateUtil.endWeekOfDay());
		TaskRes weekresult = activeService.dateTaskResult(req);
		if (newresult != null) {
			for (TaskRes task : newtask) {
				Integer ctype = task.getCtype();
				if (ctype.equals(CoinTypeEnum.USERAUTH.getCode())) {
					task.setDone(newresult.getAudit());
				}
				if (ctype.equals(CoinTypeEnum.SUBACTIVE.getCode())) {
					task.setDone(newresult.getActcount() > 0 ? 1 : 0);
				}
				if (ctype.equals(CoinTypeEnum.SUBSIGN.getCode())) {
					task.setDone(newresult.getSubsign());
				}
			}
		}
		if (dateresult != null) {
			for (TaskRes task : daytask) {
				Integer ctype = task.getCtype();
				if (ctype.equals(CoinTypeEnum.JOINS.getCode())) {
					Integer joincount = dateresult.getJoincount();
					task.setJoincount(joincount);
					if (joincount.intValue() >= task.getAmount().intValue()) {
						task.setDone(1);
					}
				}
				if (ctype.equals(CoinTypeEnum.SHARE.getCode())) {
					Integer joincount = dateresult.getSharecount();
					task.setSharecount(joincount);
					if (joincount.intValue() >= task.getAmount().intValue()) {
						task.setDone(1);
					}
				}
				if (ctype.equals(CoinTypeEnum.INVITE.getCode())) {
					Integer joincount = dateresult.getInvitecount();
					task.setInvitecount(joincount);
					if (joincount.intValue() >= task.getAmount().intValue()) {
						task.setDone(1);
					}
				}
			}
		}
		if (weekresult != null) {
			for (TaskRes task : weektsk) {
				Integer ctype = task.getCtype();
				if (ctype.equals(CoinTypeEnum.JOINS.getCode())) {
					Integer joincount = weekresult.getJoincount();
					task.setJoincount(joincount);
					if (joincount.intValue() >= task.getAmount().intValue()) {
						task.setDone(1);
					}
				}
				if (ctype.equals(CoinTypeEnum.SHARE.getCode())) {
					Integer joincount = weekresult.getSharecount();
					task.setSharecount(joincount);
					if (joincount.intValue() >= task.getAmount().intValue()) {
						task.setDone(1);
					}
				}
				if (ctype.equals(CoinTypeEnum.INVITE.getCode())) {
					Integer joincount = weekresult.getInvitecount();
					task.setInvitecount(joincount);
					if (joincount.intValue() >= task.getAmount().intValue()) {
						task.setDone(1);
					}
				}
			}
		}

		result.put("newtask", newtask);
		result.put("daytask", daytask);
		result.put("weektsk", weektsk);
		return result;
	}

}

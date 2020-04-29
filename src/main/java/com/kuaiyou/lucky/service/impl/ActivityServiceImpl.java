package com.kuaiyou.lucky.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.entity.Activity;
import com.kuaiyou.lucky.mapper.ActivityMapper;
import com.kuaiyou.lucky.service.ActivityService;

/**
 * <p>
 * 发布任务 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

	@Override
	public List<Activity> activityList(CommonReq req) {
		return baseMapper.baseList(req);
	}

	@Override
	public int activityListCount(CommonReq req) {
		Integer count = baseMapper.baseListCount(req);
		return count == null ? 0 : count;
	}

}

package com.kuaiyou.lucky.service;

import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.entity.Activity;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 发布任务 服务类
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
public interface ActivityService extends IService<Activity> {

	List<Activity> activityList(CommonReq req);

	int activityListCount(CommonReq req);

}

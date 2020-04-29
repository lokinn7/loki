package com.kuaiyou.lucky.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.entity.Adviser;
import com.kuaiyou.lucky.res.AdviserRes;

/**
 * <p>
 * 发布任务 服务类
 * </p>
 *
 * @author yardney
 * @since 2019-11-04
 */
public interface AdviserService extends IService<Adviser> {

	List<AdviserRes> activityList(CommonReq req);

	int activityListCount(CommonReq req);

}

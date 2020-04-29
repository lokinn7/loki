package com.kuaiyou.lucky.mapper;

import java.util.List;

import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.SuperMapper;
import com.kuaiyou.lucky.entity.Activity;

/**
 * <p>
 * 发布任务 Mapper 接口
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
public interface ActivityMapper extends SuperMapper<Activity> {

	List<Activity> baseList(CommonReq req);

	Integer baseListCount(CommonReq req);

}

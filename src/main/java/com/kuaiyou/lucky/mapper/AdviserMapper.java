package com.kuaiyou.lucky.mapper;

import java.util.List;

import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.SuperMapper;
import com.kuaiyou.lucky.entity.Adviser;
import com.kuaiyou.lucky.res.AdviserRes;

/**
 * <p>
 * 发布任务 Mapper 接口
 * </p>
 *
 * @author yardney
 * @since 2019-11-04
 */
public interface AdviserMapper extends SuperMapper<Adviser> {

	Integer activityListCount(CommonReq req);

	List<AdviserRes> activityList(CommonReq req);

}

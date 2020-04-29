package com.kuaiyou.lucky.mapper;

import java.util.List;

import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.SuperMapper;
import com.kuaiyou.lucky.entity.Roundlog;
import com.kuaiyou.lucky.res.RoundlogRes;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yardney
 * @since 2019-10-23
 */
public interface RoundlogMapper extends SuperMapper<Roundlog> {

	List<RoundlogRes> selectByUserid(CommonReq roundlog);

	Integer selectByUseridCount(CommonReq roundlog);

	List<RoundlogRes> baseList(CommonReq req);

	Integer baseListCount(CommonReq req);

}

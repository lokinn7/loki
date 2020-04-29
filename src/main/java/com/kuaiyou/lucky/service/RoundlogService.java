package com.kuaiyou.lucky.service;

import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.entity.Roundlog;
import com.kuaiyou.lucky.res.RoundlogRes;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yardney
 * @since 2019-10-23
 */
public interface RoundlogService extends IService<Roundlog> {

	List<RoundlogRes> selectByUserid(CommonReq roundlog);

	int selectByUseridCount(CommonReq roundlog);

	List<RoundlogRes> baseList(CommonReq req);

	int baseListCount(CommonReq req);

}

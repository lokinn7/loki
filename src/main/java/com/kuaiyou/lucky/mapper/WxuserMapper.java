package com.kuaiyou.lucky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.SuperMapper;
import com.kuaiyou.lucky.entity.Wxuser;
import com.kuaiyou.lucky.res.UserdrawRes;

/**
 * <p>
 * 微信用户表 Mapper 接口
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
public interface WxuserMapper extends SuperMapper<Wxuser> {

	List<Wxuser> baseList(CommonReq req);

	Integer baseListCount(CommonReq req);

	List<UserdrawRes> randFake(@Param("amount") int amount);

}

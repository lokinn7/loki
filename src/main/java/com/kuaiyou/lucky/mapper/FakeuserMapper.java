package com.kuaiyou.lucky.mapper;

import com.kuaiyou.lucky.entity.Fakeuser;
import com.kuaiyou.lucky.res.UserdrawRes;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.SuperMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yardney
 * @since 2019-08-26
 */
public interface FakeuserMapper extends SuperMapper<Fakeuser> {

	List<UserdrawRes> selectByDrawid(@Param("drawid") Integer id, @Param("prizelevel") Integer level);

	List<UserdrawRes> selectFakeList(CommonReq req);

	Integer selectFakeListCount(CommonReq req);

}

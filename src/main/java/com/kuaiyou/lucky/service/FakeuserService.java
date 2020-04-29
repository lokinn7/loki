package com.kuaiyou.lucky.service;

import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.entity.Fakeuser;
import com.kuaiyou.lucky.res.UserdrawRes;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yardney
 * @since 2019-08-26
 */
public interface FakeuserService extends IService<Fakeuser> {

	List<UserdrawRes> selectByDrawid(Integer id, Integer level);

	void insertFakeBatch(List<UserdrawRes> fakeusers, Integer drawid, Integer level);

	List<UserdrawRes> selectFakeList(CommonReq req);

	int selectFakeListCount(CommonReq req);

}

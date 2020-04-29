package com.kuaiyou.lucky.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.entity.Wxuser;
import com.kuaiyou.lucky.res.UserdrawRes;

/**
 * <p>
 * 微信用户表 服务类
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
public interface WxuserService extends IService<Wxuser> {

	Wxuser selectByUserid(String userid);

	Wxuser selectByOpenid(String openid);

	List<Wxuser> baseList(CommonReq req);

	int baseListCount(CommonReq req);

	List<UserdrawRes> randFake(int amount, Integer drawid, Integer level);

	List<Wxuser> seletSubs();

}

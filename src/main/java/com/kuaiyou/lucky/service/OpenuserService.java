package com.kuaiyou.lucky.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.kuaiyou.lucky.entity.Openuser;

/**
 * <p>
 * 微信用户数据库 服务类
 * </p>
 *
 * @author yardney
 * @since 2019-09-25
 */
public interface OpenuserService extends IService<Openuser> {

	boolean unsubOpenUser(String fromUser);

	boolean subOpenUser(String fromUser);

	boolean isSub(String unionid);

	Openuser selectbyUnionid(String unionid);

	List<Openuser> selectBySubSign();

}

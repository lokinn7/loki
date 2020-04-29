package com.kuaiyou.lucky.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kuaiyou.lucky.entity.Openuser;
import com.kuaiyou.lucky.mapper.OpenuserMapper;
import com.kuaiyou.lucky.service.OpenuserService;
import com.riversoft.weixin.mp.user.Users;

/**
 * <p>
 * 微信用户数据库 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2019-09-25
 */
@Service
public class OpenuserServiceImpl extends ServiceImpl<OpenuserMapper, Openuser> implements OpenuserService {

	@Override
	public boolean subOpenUser(String openid) {
		boolean flag = Boolean.FALSE;
		Openuser user = selectOne(new EntityWrapper<Openuser>().eq(Openuser.OPENID, openid));
		if (user == null) {
			com.riversoft.weixin.mp.user.bean.User ut = Users.defaultUsers().get(openid);
			if (ut.isSubscribed()) {
				user = new Openuser();
				user.setCity(ut.getCity());
				user.setCountry(ut.getCountry());
				user.setAvatarurl(ut.getHeadImgUrl());
				user.setLanguage(ut.getLanguage());
				user.setNickname(ut.getNickName());
				user.setOpenid(openid);
				user.setProvince(ut.getProvince());
				user.setRemark(ut.getRemark());
				user.setGender(ut.getSex().getCode());
				user.setSub(1);
				user.setSubtime(ut.getSubscribedTime());
				user.setCtime(new Date());
				user.setUnionid(ut.getUnionId());
				flag = insert(user);
			}
		} else if (user.getSub().equals(0)) {
			Openuser upinfo = new Openuser();
			upinfo.setSub(1);
			upinfo.setSubtime(new Date());
			flag = update(upinfo, new EntityWrapper<Openuser>().eq(Openuser.ID, user.getId()));
		}
		return flag;
	}

	@Override
	public boolean unsubOpenUser(String fromUser) {
		Openuser user = selectOne(new EntityWrapper<Openuser>().eq(Openuser.OPENID, fromUser));
		if (user != null) {
			Openuser upInfo = new Openuser();
			upInfo.setSub(0);
			upInfo.setUnsubtime(new Date());
			return update(upInfo, new EntityWrapper<Openuser>().eq(Openuser.ID, user.getId()));
		}
		return false;
	}

	@Override
	public boolean isSub(String unionid) {
		EntityWrapper<Openuser> wrapper = new EntityWrapper<>();
		wrapper.eq(Openuser.UNIONID, unionid).eq(Openuser.SUB, 1);
		Openuser selectOne = selectOne(wrapper);
		return selectOne == null ? false : true;
	}

	@Override
	public Openuser selectbyUnionid(String unionid) {
		EntityWrapper<Openuser> wrapper = new EntityWrapper<>();
		wrapper.eq(Openuser.UNIONID, unionid);
		return selectOne(wrapper);
	}

	@Override
	public List<Openuser> selectBySubSign() {
		return baseMapper.selectBySubSign();
	}

}

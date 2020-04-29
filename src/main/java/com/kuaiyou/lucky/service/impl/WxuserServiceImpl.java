package com.kuaiyou.lucky.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.entity.Wxuser;
import com.kuaiyou.lucky.mapper.WxuserMapper;
import com.kuaiyou.lucky.res.UserdrawRes;
import com.kuaiyou.lucky.service.WxuserService;
import com.kuaiyou.lucky.utils.FakeNameUtil;

/**
 * <p>
 * 微信用户表 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@Service
public class WxuserServiceImpl extends ServiceImpl<WxuserMapper, Wxuser> implements WxuserService {

	@Override
	public Wxuser selectByUserid(String userid) {
		EntityWrapper<Wxuser> wrapper = new EntityWrapper<>();
		wrapper.eq(Wxuser.USERID, userid).eq(Wxuser.STATUS, 1);
		return selectOne(wrapper);
	}

	@Override
	public Wxuser selectByOpenid(String openid) {
		EntityWrapper<Wxuser> wrapper = new EntityWrapper<>();
		wrapper.eq(Wxuser.OPENID, openid).eq(Wxuser.STATUS, 1);
		return selectOne(wrapper);
	}

	@Override
	public List<Wxuser> baseList(CommonReq req) {
		return baseMapper.baseList(req);
	}

	@Override
	public int baseListCount(CommonReq req) {
		Integer count = baseMapper.baseListCount(req);
		return count == null ? 0 : count;
	}

	@Override
	public List<UserdrawRes> randFake(int amount, Integer drawid, Integer level) {
		List<UserdrawRes> randFake = new ArrayList<>();
		for (int i = 0; i < amount; i++) {
			String fakename = FakeNameUtil.getRandomName();
			String randomAvata = FakeNameUtil.getRandomAvata();
			UserdrawRes temp = new UserdrawRes(level, randomAvata, fakename, drawid);
			randFake.add(temp);
		}
		return randFake;
	}

	@Override
	public List<Wxuser> seletSubs() {
		EntityWrapper<Wxuser> wrapper = new EntityWrapper<>();
		wrapper.eq(Wxuser.SUBAC, 1).eq(Wxuser.STATUS, 1);
		return selectList(wrapper);
	}

}

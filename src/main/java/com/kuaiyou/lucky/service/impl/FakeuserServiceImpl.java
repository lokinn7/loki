package com.kuaiyou.lucky.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.entity.Fakeuser;
import com.kuaiyou.lucky.mapper.FakeuserMapper;
import com.kuaiyou.lucky.res.UserdrawRes;
import com.kuaiyou.lucky.service.FakeuserService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2019-08-26
 */
@Service
public class FakeuserServiceImpl extends ServiceImpl<FakeuserMapper, Fakeuser> implements FakeuserService {

	@Override
	public List<UserdrawRes> selectByDrawid(Integer id, Integer level) {
		return baseMapper.selectByDrawid(id, level);
	}

	@Override
	public void insertFakeBatch(List<UserdrawRes> fakeusers, Integer drawid, Integer level) {
		List<Fakeuser> inserts = new ArrayList<>();
		fakeusers.forEach(e -> {
			Fakeuser fakeuser = new Fakeuser();
			fakeuser.setAvatarurl(e.getAvatarurl());
			fakeuser.setDrawid(drawid);
			fakeuser.setNickname(e.getNickname());
			fakeuser.setPrizelevel(level);
			inserts.add(fakeuser);
		});
		if (inserts.size() > 0) {
			insertBatch(inserts, inserts.size());
		}
	}

	@Override
	public List<UserdrawRes> selectFakeList(CommonReq req) {
		return baseMapper.selectFakeList(req);
	}

	@Override
	public int selectFakeListCount(CommonReq req) {
		Integer count = baseMapper.selectFakeListCount(req);
		return count == null ? 0 : count;
	}

}

package com.kuaiyou.lucky.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.entity.Roundlog;
import com.kuaiyou.lucky.mapper.RoundlogMapper;
import com.kuaiyou.lucky.res.RoundlogRes;
import com.kuaiyou.lucky.service.RoundlogService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2019-10-23
 */
@Service
public class RoundlogServiceImpl extends ServiceImpl<RoundlogMapper, Roundlog> implements RoundlogService {

	@Override
	public List<RoundlogRes> selectByUserid(CommonReq roundlog) {
		return baseMapper.selectByUserid(roundlog);
	}

	@Override
	public int selectByUseridCount(CommonReq roundlog) {
		Integer count = baseMapper.selectByUseridCount(roundlog);
		return count == null ? 0 : count;
	}

	@Override
	public List<RoundlogRes> baseList(CommonReq req) {
		return baseMapper.baseList(req);
	}

	@Override
	public int baseListCount(CommonReq req) {
		Integer count = baseMapper.baseListCount(req);
		return count == null ? 0 : count;
	}

}

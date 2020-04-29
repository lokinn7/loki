package com.kuaiyou.lucky.service.impl;

import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.entity.Adviser;
import com.kuaiyou.lucky.mapper.AdviserMapper;
import com.kuaiyou.lucky.res.AdviserRes;
import com.kuaiyou.lucky.service.AdviserService;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 发布任务 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2019-11-04
 */
@Service
public class AdviserServiceImpl extends ServiceImpl<AdviserMapper, Adviser> implements AdviserService {

	@Override
	public List<AdviserRes> activityList(CommonReq req) {
		return baseMapper.activityList(req);
	}

	@Override
	public int activityListCount(CommonReq req) {
		Integer count = baseMapper.activityListCount(req);
		return SqlHelper.retCount(count);
	}

}

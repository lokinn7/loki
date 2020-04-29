package com.kuaiyou.lucky.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kuaiyou.lucky.entity.Goods;
import com.kuaiyou.lucky.mapper.GoodsMapper;
import com.kuaiyou.lucky.req.GoodsReq;
import com.kuaiyou.lucky.res.GoodsRes;
import com.kuaiyou.lucky.service.GoodsService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2019-10-17
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

	@Override
	public List<Long> selectByType(Integer type) {
		EntityWrapper<Goods> wrapper = new EntityWrapper<>();
		if (type != null) {
			wrapper.eq(Goods.TYPE, type);
		}
		List<Goods> selectList = selectList(wrapper);
		return selectList.stream().map(e -> e.getSkuid()).collect(Collectors.toList());
	}

	@Override
	public Goods selectBySkuid(Long skuid) {
		EntityWrapper<Goods> wrapper = new EntityWrapper<>();
		wrapper.eq(Goods.SKUID, skuid);
		return selectOne(wrapper);
	}

	@Override
	public List<GoodsRes> goodList(GoodsReq goods) {
		return baseMapper.goodList(goods);
	}

	@Override
	public int goodListCount(GoodsReq goods) {
		Integer count = baseMapper.goodListCount(goods);
		return count == null ? 0 : count;
	}

}

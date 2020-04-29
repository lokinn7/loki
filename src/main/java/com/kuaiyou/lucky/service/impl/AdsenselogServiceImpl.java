package com.kuaiyou.lucky.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kuaiyou.lucky.entity.Adsenselog;
import com.kuaiyou.lucky.mapper.AdsenseMapper;
import com.kuaiyou.lucky.mapper.AdsenselogMapper;
import com.kuaiyou.lucky.service.AdsenselogService;

/**
 * <p>
 * 广告位信息 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2019-10-31
 */
@Service
public class AdsenselogServiceImpl extends ServiceImpl<AdsenselogMapper, Adsenselog> implements AdsenselogService {

	@Autowired
	AdsenseMapper adsenseMapper;

	@Override
	public Adsenselog selectByAcAs(String acid, String asid) {
		Wrapper<Adsenselog> wrapper = new EntityWrapper<>();
		wrapper.eq(Adsenselog.ACID, acid).eq(Adsenselog.ASID, asid);
		return selectOne(wrapper);
	}

	@Override
	public List<Adsenselog> getAd(Integer asid) {
		return adsenseMapper.getAd(asid);
	}

}

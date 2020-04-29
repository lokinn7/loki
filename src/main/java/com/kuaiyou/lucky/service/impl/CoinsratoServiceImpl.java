package com.kuaiyou.lucky.service.impl;

import com.kuaiyou.lucky.entity.Coinsrato;
import com.kuaiyou.lucky.mapper.CoinsratoMapper;
import com.kuaiyou.lucky.service.CoinsratoService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2019-10-14
 */
@Service
public class CoinsratoServiceImpl extends ServiceImpl<CoinsratoMapper, Coinsrato> implements CoinsratoService {

	@Override
	public List<Coinsrato> getRatos() {
		EntityWrapper<Coinsrato> wrapper = new EntityWrapper<>();
		wrapper.gt(Coinsrato.RATO, 0);
		return selectList(wrapper);
	}

}

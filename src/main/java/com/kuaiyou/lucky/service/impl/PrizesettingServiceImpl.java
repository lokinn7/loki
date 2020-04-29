package com.kuaiyou.lucky.service.impl;

import com.kuaiyou.lucky.entity.Prizesetting;
import com.kuaiyou.lucky.mapper.PrizesettingMapper;
import com.kuaiyou.lucky.service.PrizesettingService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@Service
public class PrizesettingServiceImpl extends ServiceImpl<PrizesettingMapper, Prizesetting>
		implements PrizesettingService {

	@Override
	public boolean deleteBydrawid(Integer id) {
		EntityWrapper<Prizesetting> wrapper = new EntityWrapper<>();
		wrapper.eq(Prizesetting.DRAWID, id);
		return delete(wrapper);
	}

}

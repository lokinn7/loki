package com.kuaiyou.lucky.service.impl;

import com.kuaiyou.lucky.entity.Banlist;
import com.kuaiyou.lucky.mapper.BanlistMapper;
import com.kuaiyou.lucky.service.BanlistService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2019-09-24
 */
@Service
public class BanlistServiceImpl extends ServiceImpl<BanlistMapper, Banlist> implements BanlistService {

	@Override
	public boolean deleteByUserId(String userid) {
		EntityWrapper<Banlist> wrapper = new EntityWrapper<>();
		wrapper.eq(Banlist.USERID, userid);
		return delete(wrapper);
	}

}

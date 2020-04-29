package com.kuaiyou.lucky.service.impl;

import com.kuaiyou.lucky.entity.Address;
import com.kuaiyou.lucky.mapper.AddressMapper;
import com.kuaiyou.lucky.service.AddressService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yardney
 * @since 2019-08-16
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

	@Override
	public Address findByDrawid(String drawid, String userid) {
		EntityWrapper<Address> wrapper = new EntityWrapper<>();
		wrapper.eq(Address.DRAWID, drawid).eq(Address.USERID, userid);
		return selectOne(wrapper);
	}

}

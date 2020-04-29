package com.kuaiyou.lucky.service;

import com.kuaiyou.lucky.entity.Address;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yardney
 * @since 2019-08-16
 */
public interface AddressService extends IService<Address> {

	Address findByDrawid(String drawid, String userid);

}

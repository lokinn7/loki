package com.kuaiyou.lucky.api.open;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.entity.Address;
import com.kuaiyou.lucky.service.AddressService;
import com.kuaiyou.lucky.utils.SecurityUtil;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-08-16
 */
@RestController
@RequestMapping("/lucky/address")
public class AddressController {

	@Autowired
	AddressService addressService;

	@RequestMapping("add")
	public Result insert(@RequestBody Address address, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		Address cache = addressService.findByDrawid(address.getDrawid(), userid);
		boolean flag = false;
		if (cache != null) {
			cache.setAddress(address.getAddress());
			flag = addressService.updateById(cache);
		} else {
			address.setUserid(userid);
			address.setCtime(new Date());
			flag = addressService.insert(address);
		}
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

}

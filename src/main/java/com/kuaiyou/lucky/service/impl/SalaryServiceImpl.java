package com.kuaiyou.lucky.service.impl;

import com.kuaiyou.lucky.entity.Bind;
import com.kuaiyou.lucky.entity.Salary;
import com.kuaiyou.lucky.mapper.SalaryMapper;
import com.kuaiyou.lucky.service.BindService;
import com.kuaiyou.lucky.service.SalaryService;
import com.kuaiyou.lucky.utils.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2020-04-29
 */
@Service
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements SalaryService {

	@Autowired
	BindService bindService;

	@Override
	public Salary selectByOpenidAndMonth(Bind toUser) {

		String now = DateUtil.getNow();
		EntityWrapper<Salary> wrapper = new EntityWrapper<Salary>();
		wrapper.eq(Salary.IDCODE, toUser.getIdcode()).like(Salary.MONTH, now);
		return selectOne(wrapper);
	}

	@Override
	public Salary selectByMonth(String month, String toUser) {
		EntityWrapper<Bind> wrapper = new EntityWrapper<Bind>();
		wrapper.eq(Bind.OPENID, toUser);
		Bind bindcache = bindService.selectOne(wrapper);
		if (bindcache != null) {
			EntityWrapper<Salary> swrapper = new EntityWrapper<Salary>();
			swrapper.eq(Salary.IDCODE, bindcache.getIdcode()).like(Salary.MONTH, month);
			return selectOne(swrapper);
		}
		return null;
	}

}

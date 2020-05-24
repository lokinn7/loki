package com.kuaiyou.lucky.service;

import com.baomidou.mybatisplus.service.IService;
import com.kuaiyou.lucky.entity.Bind;
import com.kuaiyou.lucky.entity.Salary;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yardney
 * @since 2020-04-29
 */
public interface SalaryService extends IService<Salary> {

	Salary selectByOpenidAndMonth(Bind toUser);

	Salary selectByMonth(String content, String toUser);

}

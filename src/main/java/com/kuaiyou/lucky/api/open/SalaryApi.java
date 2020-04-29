package com.kuaiyou.lucky.api.open;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.entity.Salary;
import com.kuaiyou.lucky.service.SalaryService;

@RestController
@RequestMapping("/salary")
public class SalaryApi {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	SalaryService salaryService;

	/**
	 * 授权保存用户信息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("find")
	public Result init(@RequestBody CommonReq req) {
		String idcode = req.getIdcode();
		String nickname = req.getNickname();
		if (StringUtils.isBlank(idcode)) {
			return Result.error("请输入您的身份证号");
		}
		if (StringUtils.isBlank(req.getNickname())) {
			return Result.error("请输入您的姓名");
		}
		String code = idcode.trim();
		String name = nickname.trim();
		Salary cache = salaryService.selectOne(new EntityWrapper<Salary>().eq(Salary.IDCODE, code)
				.eq(Salary.NICKNAME, name).like(Salary.MONTH, req.getMonth()));
		if (cache == null) {
			return Result.error("未查到您当前月的工资信息");
		}
		return Result.ok(cache);
	}

}

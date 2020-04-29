package com.kuaiyou.lucky.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.entity.Dailyreport;
import com.kuaiyou.lucky.service.DailyreportService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-08-16
 */
@RestController
@RequestMapping("/admin/dailyreport")
public class AdminDailyController {

	@Autowired
	DailyreportService dailyreportService;

	@RequestMapping("grid")
	public Result grid(@RequestBody CommonReq req) {
		Page<Dailyreport> page = new Page<>();
		page.setCurrent(req.getPage());
		page.setSize(req.getSkip());
		Wrapper<Dailyreport> wrapper = new EntityWrapper<>();
		wrapper.between(Dailyreport.REPORTTIME, req.getStarttime(), req.getEndtime())
				.like(Dailyreport.ASNAME, req.getAsname()).like(Dailyreport.ACNAME, req.getAcname());
		Page<Dailyreport> grid = dailyreportService.selectPage(page, wrapper);
		return Result.ok(grid);
	}
}

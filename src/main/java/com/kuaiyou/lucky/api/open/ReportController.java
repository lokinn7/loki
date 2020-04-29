package com.kuaiyou.lucky.api.open;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.entity.Report;
import com.kuaiyou.lucky.service.ReportService;
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
@RequestMapping("/lucky/report")
public class ReportController {

	@Autowired
	ReportService reportService;

	@RequestMapping("add")
	public Result insert(@RequestBody Report report, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		{
			report.setCtime(new Date());
			report.setUserid(userid);
		}
		boolean insert = reportService.insert(report);
		if (insert) {
			return Result.ok();
		}
		return Result.error();
	}
}

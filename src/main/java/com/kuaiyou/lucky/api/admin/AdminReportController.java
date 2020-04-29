package com.kuaiyou.lucky.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuaiyou.lucky.common.PageUtils;
import com.kuaiyou.lucky.req.ReportReq;
import com.kuaiyou.lucky.res.ReportRes;
import com.kuaiyou.lucky.service.ReportService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-08-16
 */
@RestController
@RequestMapping("/admin/report")
public class AdminReportController {

	@Autowired
	ReportService reportService;

	@RequestMapping("grid")
	public PageUtils insert(@RequestBody ReportReq report) {
		List<ReportRes> grid = reportService.baseList(report);
		int total = reportService.baseListCount(report);
		return new PageUtils(grid, total, report.getPage(), report.getSkip());
	}
}

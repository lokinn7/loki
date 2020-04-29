package com.kuaiyou.lucky.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.PageUtils;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.entity.Activity;
import com.kuaiyou.lucky.service.ActivityService;

/**
 * <p>
 * 发布任务 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@RestController
@RequestMapping("/admin/activity")
public class AdminActivityController {

	@Autowired
	ActivityService activityService;

	@RequestMapping("grid")
	public PageUtils grid(@RequestBody CommonReq req) {
		List<Activity> grid = activityService.activityList(req);
		int total = activityService.activityListCount(req);
		return new PageUtils(grid, total, req.getPage(), req.getSkip());
	}

	@RequestMapping("select")
	public Result select(@RequestBody CommonReq req) {
		List<Activity> selectList = activityService.selectList(new EntityWrapper<>());
		return Result.ok(selectList);
	}

	@RequestMapping("insert")
	public Result insert(@RequestBody Activity req) {
		boolean flag = activityService.insert(req);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}
}

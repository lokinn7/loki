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
import com.kuaiyou.lucky.entity.Adviser;
import com.kuaiyou.lucky.res.AdviserRes;
import com.kuaiyou.lucky.service.ActivityService;
import com.kuaiyou.lucky.service.AdviserService;

/**
 * <p>
 * 发布任务 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-11-04
 */
@RestController
@RequestMapping("/admin/adviser")
public class AdminAdviserController {

	@Autowired
	AdviserService adviserService;

	@Autowired
	ActivityService activityService;

	@RequestMapping("grid")
	public PageUtils grid(@RequestBody CommonReq req) {
		List<AdviserRes> grid = adviserService.activityList(req);
		int total = adviserService.activityListCount(req);
		return new PageUtils(grid, total, req.getPage(), req.getSkip());
	}

	@RequestMapping("select")
	public Result select(@RequestBody CommonReq req) {
		List<Adviser> selectList = adviserService.selectList(new EntityWrapper<>());
		return Result.ok(selectList);
	}

	@RequestMapping("insert")
	public Result insert(@RequestBody Adviser req) {
		Activity cache = activityService.selectById(req.getAdvid());
		if (cache != null) {
			req.setAppkey(cache.getAppkey());
		}
		boolean flag = adviserService.insert(req);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequestMapping("edit")
	public Result edit(@RequestBody Adviser req) {
		boolean flag = adviserService.updateById(req);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}
}

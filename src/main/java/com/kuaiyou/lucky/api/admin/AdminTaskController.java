package com.kuaiyou.lucky.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.entity.Task;
import com.kuaiyou.lucky.service.TaskService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-09-09
 */
@RestController
@RequestMapping("/admin/task")
public class AdminTaskController {

	@Autowired
	TaskService taskService;

	@RequestMapping("add")
	public Result add(@RequestBody Task task) {
		if (task.getDtype() == null) {
			return Result.error("选择有效的任务区间");
		}
		boolean flag = taskService.insert(task);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequestMapping("grid")
	public Result grid(@RequestBody CommonReq req) {
		EntityWrapper<Task> wrapper = new EntityWrapper<>();
		List<Task> list = taskService.selectList(wrapper);
		return Result.ok(list);
	}

}

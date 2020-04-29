package com.kuaiyou.lucky.api.open;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.entity.Task;
import com.kuaiyou.lucky.service.TaskService;
import com.kuaiyou.lucky.utils.SecurityUtil;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-09-09
 */
@RestController
@RequestMapping("/lucky/task")
public class TaskController {

	@Autowired
	TaskService taskService;

	@RequestMapping("grid")
	public Result grid(@RequestBody Task task, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		Map<String, Object> tasks = taskService.getTasks(userid);
		return Result.ok(tasks);
	}

}

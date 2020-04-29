package com.kuaiyou.lucky.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.kuaiyou.lucky.entity.Task;
import com.kuaiyou.lucky.res.TaskRes;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yardney
 * @since 2019-09-09
 */
public interface TaskService extends IService<Task> {

	List<TaskRes> selectByDtype(int i);

	Task selectByCtype(int code, int dcode);

	Map<String, Object> getTasks(String userid);

}

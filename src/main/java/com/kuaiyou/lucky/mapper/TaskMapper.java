package com.kuaiyou.lucky.mapper;

import com.kuaiyou.lucky.entity.Task;
import com.kuaiyou.lucky.res.TaskRes;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kuaiyou.lucky.common.SuperMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yardney
 * @since 2019-09-09
 */
public interface TaskMapper extends SuperMapper<Task> {

	List<TaskRes> selectByDtype(@Param("dtype") int i);

}

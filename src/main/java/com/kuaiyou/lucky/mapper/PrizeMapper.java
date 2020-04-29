package com.kuaiyou.lucky.mapper;

import com.kuaiyou.lucky.entity.Prize;


import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kuaiyou.lucky.common.SuperMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
public interface PrizeMapper extends SuperMapper<Prize> {

	List<Prize> getRatos(@Param("userid") String userid, @Param("end") Date date, @Param("start") Date daysago);

}

package com.kuaiyou.lucky.mapper;

import com.kuaiyou.lucky.entity.Openuser;

import java.util.List;

import com.kuaiyou.lucky.common.SuperMapper;

/**
 * <p>
 * 微信用户数据库 Mapper 接口
 * </p>
 *
 * @author yardney
 * @since 2019-09-25
 */
public interface OpenuserMapper extends SuperMapper<Openuser> {

	List<Openuser> selectBySubSign();

}

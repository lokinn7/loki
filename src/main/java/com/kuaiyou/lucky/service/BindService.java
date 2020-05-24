package com.kuaiyou.lucky.service;

import com.kuaiyou.lucky.entity.Bind;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yardney
 * @since 2020-05-24
 */
public interface BindService extends IService<Bind> {

	Bind selectWithIDcode(String toUser);

	boolean bind(String content, String toUser);

}

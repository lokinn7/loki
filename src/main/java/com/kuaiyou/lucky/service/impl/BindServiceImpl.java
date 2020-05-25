package com.kuaiyou.lucky.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kuaiyou.lucky.entity.Bind;
import com.kuaiyou.lucky.mapper.BindMapper;
import com.kuaiyou.lucky.service.BindService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2020-05-24
 */
@Service
public class BindServiceImpl extends ServiceImpl<BindMapper, Bind> implements BindService {

	@Override
	public Bind selectWithIDcode(String toUser) {
		EntityWrapper<Bind> wrapper = new EntityWrapper<Bind>();
		wrapper.eq(Bind.OPENID, toUser);
		return selectOne(wrapper);
	}

	@Override
	public boolean bind(String content, String toUser) {

		Bind bind = selectOne(new EntityWrapper<Bind>().eq(Bind.IDCODE, content).eq(Bind.OPENID, toUser));
		if (bind == null) {
			Bind insert = new Bind();
			insert.setIdcode(content);
			insert.setOpenid(toUser);
			return insert(insert);
		}
		return true;
	}

	@Override
	public Bind selectByOpenid(String fromUser) {
		return selectOne(new EntityWrapper<Bind>().eq(Bind.OPENID, fromUser));
	}

}

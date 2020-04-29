package com.kuaiyou.lucky.service;

import com.kuaiyou.lucky.entity.Coinsrato;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yardney
 * @since 2019-10-14
 */
public interface CoinsratoService extends IService<Coinsrato> {

	List<Coinsrato> getRatos();

}

package com.kuaiyou.lucky.service;

import com.kuaiyou.lucky.entity.Convert;
import com.kuaiyou.lucky.entity.Goods;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yardney
 * @since 2019-10-17
 */
public interface ConvertService extends IService<Convert> {

	boolean saveItemWithUserCoins(Convert convert, Goods goods);

	List<Convert> selectByUserid(String userid);

	List<Convert> selectByUseridAndSkuid(Long skuid, String userid);

}

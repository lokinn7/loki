package com.kuaiyou.lucky.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.kuaiyou.lucky.entity.Goods;
import com.kuaiyou.lucky.req.GoodsReq;
import com.kuaiyou.lucky.res.GoodsRes;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yardney
 * @since 2019-10-17
 */
public interface GoodsService extends IService<Goods> {

	List<Long> selectByType(Integer type);

	Goods selectBySkuid(Long skuid);

	List<GoodsRes> goodList(GoodsReq goods);

	int goodListCount(GoodsReq goods);

}

package com.kuaiyou.lucky.mapper;

import java.util.List;

import com.kuaiyou.lucky.common.SuperMapper;
import com.kuaiyou.lucky.entity.Goods;
import com.kuaiyou.lucky.req.GoodsReq;
import com.kuaiyou.lucky.res.GoodsRes;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yardney
 * @since 2019-10-17
 */
public interface GoodsMapper extends SuperMapper<Goods> {

	List<GoodsRes> goodList(GoodsReq goods);

	Integer goodListCount(GoodsReq goods);

}

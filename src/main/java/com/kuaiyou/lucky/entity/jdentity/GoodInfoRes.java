package com.kuaiyou.lucky.entity.jdentity;

import jd.union.open.goods.query.response.GoodsResp;
import lombok.Data;

@Data
public class GoodInfoRes extends GoodsResp {

	private static final long serialVersionUID = 1L;
	private String puburl;
	private Integer aljoin = 0;

}

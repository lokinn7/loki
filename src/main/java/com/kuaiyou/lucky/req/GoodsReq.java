package com.kuaiyou.lucky.req;

import java.util.Date;
import java.util.List;

import com.kuaiyou.lucky.common.PageVo;
import com.kuaiyou.lucky.entity.Goods;

import lombok.Data;

@Data
public class GoodsReq extends PageVo {
	private Integer id;
	private Long skuid;
	private Date ctime;
	private Integer type;
	private Integer gtype;
	private Integer deleted;
	private Integer amount;
	private String userid;
	

	List<Goods> goods;
	List<Long> skuids;
}

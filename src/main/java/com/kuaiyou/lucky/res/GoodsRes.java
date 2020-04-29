package com.kuaiyou.lucky.res;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableLogic;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yardney
 * @since 2019-10-17
 */
@Data
public class GoodsRes {

	private Integer id;
	private Long skuid;
	private Integer deleted;
	private Integer type;
	private Integer amount;
	private String imgurl;
	private String skuname;
	private String brandname;
	private Double price;
	private Double lowestprice;
	private String shopname;
	private Integer isjdale;
	private Double discount;
	private Date ctime;
	private String couponurl;
	private Integer isget;
	private String shareimg;
	private Integer gtype;
	private String couponlink;

}

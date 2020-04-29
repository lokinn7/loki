package com.kuaiyou.lucky.entity;

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
@Accessors(chain = true)
@TableName("t_luck_goods")
public class Goods extends Model<Goods> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private Long skuid;
	@TableLogic
	private Integer deleted;
	/**
	 * 1超值特惠2爆款
	 */
	private Integer type;
	/**
	 * 需要兑换的金币
	 */
	private Integer amount;
	/**
	 * 图片
	 */
	private String imgurl;
	/**
	 * 商品名称
	 */
	private String skuname;
	/**
	 * 品牌名
	 */
	private String brandname;
	private Double price;
	/**
	 * 最低价格
	 */
	private Double lowestprice;
	private String shopname;
	/**
	 * 是否京东自营
	 */
	private Integer isjdale;
	/**
	 * 优惠券面额
	 */
	private Double discount;
	private Date ctime;
	/**
	 * 优惠券链接
	 */
	private String couponurl;
	private String shareimg;

	private Integer gtype;

	private String couponlink;

	public static final String ID = "id";

	public static final String SKUID = "skuid";

	public static final String DELETED = "deleted";

	public static final String TYPE = "type";

	public static final String AMOUNT = "amount";

	public static final String IMGURL = "imgurl";

	public static final String SKUNAME = "skuname";

	public static final String BRANDNAME = "brandName";

	public static final String PRICE = "price";

	public static final String LOWESTPRICE = "lowestprice";

	public static final String SHOPNAME = "shopname";

	public static final String ISJDALE = "isjdale";

	public static final String DISCOUNT = "discount";

	public static final String CTIME = "ctime";

	public static final String COUPONURL = "couponurl";

	public static final String GTYPE = "gtype";

	public static final String COUPONLINK = "couponlink";

	public static final String SHAREIMG = "shareimg";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}

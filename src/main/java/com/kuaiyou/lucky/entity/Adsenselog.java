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
 * 广告位信息
 * </p>
 *
 * @author yardney
 * @since 2019-10-31
 */
@Data
@Accessors(chain = true)
@TableName("t_luck_adsenselog")
public class Adsenselog extends Model<Adsenselog> {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 活动ID
	 */
	private Integer acid;

	private Integer asid;
	/**
	 * 活动名称
	 */
	private String acname;
	/**
	 * 广告位名称
	 */
	private String asname;
	/**
	 * 状态 1可以用 0不可用
	 */
	private Integer status;
	/**
	 * 广告位展示形式1 banner 2 insert
	 */
	private Integer astype;
	private Integer fill;
	/**
	 * 是否定投
	 */
	private Integer isdt;
	private String appkey;
	private String iconurl;
	private String inserturl;
	private String bannerurl;
	private String qrurl;
	private String linkurl;
	/**
	 * 1直跳2二维码跳转
	 */
	private Integer jumptype;
	/**
	 * 创建时间
	 */
	private Date ctime;

	public static final String ID = "id";

	public static final String ACID = "acid";

	public static final String ASID = "asid";

	public static final String ACNAME = "acname";

	public static final String ASNAME = "asname";

	public static final String STATUS = "status";

	public static final String ASTYPE = "astype";

	public static final String FILL = "fill";

	public static final String ISDT = "isdt";

	public static final String APPKEY = "appkey";

	public static final String ICONURL = "iconurl";

	public static final String INSERTURL = "inserturl";

	public static final String BANNERURL = "bannerurl";

	public static final String QRURL = "qrurl";

	public static final String LINKURL = "linkurl";

	public static final String JUMPTYPE = "jumptype";

	public static final String CTIME = "ctime";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}

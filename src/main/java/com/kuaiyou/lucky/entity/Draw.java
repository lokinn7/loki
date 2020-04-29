package com.kuaiyou.lucky.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@Data
@Accessors(chain = true)
@TableName("t_luck_draw")
public class Draw extends Model<Draw> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 抽奖名称
	 */
	private String name;
	/**
	 * 抽奖分级
	 */
	private Integer level;
	/**
	 * 开奖方式
	 */
	private Integer opentype;
	/**
	 * 抽奖说明
	 */
	private String notes;
	/**
	 * 奖品类型
	 */
	private Integer type;
	/**
	 * 抽奖状态
	 */
	private Integer status;
	/**
	 * 开奖时间
	 */
	private Date opentime;
	/**
	 * 更新时间
	 */
	private Date utime;
	/**
	 * 创建时间
	 */
	private Date ctime;
	/**
	 * 发布者
	 */
	private String pubid;
	/**
	 * 发布者类型1广告主2个人
	 */
	private Integer pubtype;
	/**
	 * 赞助商名称
	 */
	private String pubname;
	/**
	 * 一等奖名称
	 */
	private String stprize;
	/**
	 * 发布者赞助商
	 */
	private Integer adid;
	/**
	 * 是否推荐
	 */
	private Integer isrec;
	/**
	 * 开奖人数
	 */
	private Integer mencount;

	private String img1;
	private String img2;
	private String img3;

	private Integer deleted;

	private String mainurl;
	/**
	 * 是否是
	 */
	private Integer act;

	public static final String ID = "id";

	public static final String NAME = "name";

	public static final String LEVEL = "level";

	public static final String OPENTYPE = "opentype";

	public static final String NOTES = "notes";

	public static final String TYPE = "type";

	public static final String STATUS = "status";

	public static final String OPENTIME = "opentime";

	public static final String UTIME = "utime";

	public static final String CTIME = "ctime";

	public static final String PUBID = "pubid";

	public static final String PUBTYPE = "pubtype";

	public static final String ADID = "adid";

	public static final String ISREC = "isrec";

	public static final String MENCOUNT = "mencount";

	public static final String PUBNAME = "pubname";

	public static final String STPRIZE = "stprize";
	public static final String IMG1 = "img1";
	public static final String IMG2 = "img2";
	public static final String IMG3 = "img3";

	public static final String DELETED = "deleted";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public Draw(Integer id) {
		this.id = id;
	}

	public Draw() {
	}
}

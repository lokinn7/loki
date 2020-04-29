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
@TableName("t_luck_adsense")
public class Adsense extends Model<Adsense> {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
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
	private Integer atype;
	private Integer fill;
	/**
	 * 是否定投
	 */
	private Integer isdt;
	private Date ctime;

	public static final String ID = "id";

	public static final String ASNAME = "asname";

	public static final String STATUS = "status";

	public static final String ATYPE = "atype";

	public static final String FILL = "fill";

	public static final String ISDT = "isdt";

	public static final String CTIME = "ctime";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}

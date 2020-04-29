package com.kuaiyou.lucky.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
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
 * @since 2019-10-23
 */
@Data
@Accessors(chain = true)
@TableName("t_luck_roundlog")
public class Roundlog extends Model<Roundlog> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 奖品id
	 */
	private Integer prizeid;
	/**
	 * 用户id
	 */
	private String userid;
	/**
	 * 奖品兑奖状态
	 */
	private Integer status;

	private Date ctime;

	private Integer rtype;

	public static final String ID = "id";

	public static final String PRIZEID = "prizeid";

	public static final String USERID = "userid";

	public static final String STATUS = "status";

	public static final String CTIME = "ctime";

	public static final String RTYPE = "rtype";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}

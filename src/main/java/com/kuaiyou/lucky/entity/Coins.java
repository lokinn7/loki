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
 * @since 2019-09-09
 */
@Data
@Accessors(chain = true)
@TableName("t_luck_coins")
public class Coins extends Model<Coins> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 用户id
	 */
	private String userid;
	/**
	 * 积分类型
	 */
	private Integer ctype;
	/**
	 * 积分描述
	 */
	private String notes;

	private Date ctime;

	private Integer amount;

	private Integer acoin;

	private Integer bcoin;

	public static final String ID = "id";

	public static final String USERID = "userid";

	public static final String CTYPE = "ctype";

	public static final String NOTES = "notes";

	public static final String CTIME = "ctime";

	public static final String AMOUNT = "amount";
	
	public static final String ACOIN = "acoin";
	
	public static final String BCOIN = "bcoin";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public Coins(String userid, Integer ctype) {
		this.userid = userid;
		this.ctype = ctype;
	}

	public Coins() {

	}

}

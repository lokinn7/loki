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
@TableName("t_luck_userdraw")
public class Userdraw extends Model<Userdraw> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 抽奖id
	 */
	private Integer drawid;
	/**
	 * 用户id
	 */
	private String userid;
	/**
	 * 是否中奖
	 */
	private Integer bingo;
	/**
	 * 创建时间
	 */
	private Date ctime;
	/**
	 * 中奖等级
	 */
	private Integer prizelevel;
	/**
	 * 中奖时间
	 */
	private Date btime;

	private Integer prizetype;

	private String flag;

	public static final String ID = "id";

	public static final String DRAWID = "drawid";

	public static final String USERID = "userid";

	public static final String BINGO = "bingo";

	public static final String CTIME = "ctime";

	public static final String PRIZELEVEL = "prizelevel";

	public static final String BTIME = "btime";

	public static final String PRIZETYPE = "prizetype";

	public static final String FLAG = "flag";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}

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
 * @since 2019-09-09
 */
@Data
@Accessors(chain = true)
@TableName("t_luck_task")
public class Task extends Model<Task> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 任务名称
	 */
	private String name;
	/**
	 * 类型
	 */
	private Integer dtype;

	private Integer ctype;
	/**
	 * 任务描述
	 */
	private String desc;
	/**
	 * 达成任务数量
	 */
	private Integer coins;

	private Date ctime;

	private Integer amount;

	private Integer status;

	public static final String ID = "id";

	public static final String NAME = "name";

	public static final String DTYPE = "dtype";

	public static final String CTYPE = "ctype";

	public static final String DESC = "desc";

	public static final String COINS = "coins";

	public static final String AMOUNT = "amount";

	public static final String CTIME = "ctime";

	public static final String STATUS = "status";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}

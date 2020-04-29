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
@TableName("t_luck_active")
public class Active extends Model<Active> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private Integer ctype;
	private Date ctime;
	private Integer drawid;
	private String notes;
	private String userid;
	private String tuserid;
	private Long skuid;
	private String flag;
	private Integer asid;
	private Integer acid;

	public static final String ID = "id";

	public static final String NAME = "name";

	public static final String CTYPE = "ctype";

	public static final String DESC = "desc";

	public static final String CTIME = "ctime";

	public static final String DRAWID = "drawid";

	public static final String USERID = "userid";

	public static final String TUSERID = "tuserid";

	public static final String SKUID = "skuid";

	public static final String FLAG = "flag";
	public static final String ASID = "asid";
	public static final String ACID = "acid";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}

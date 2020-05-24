package com.kuaiyou.lucky.entity;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2020-05-24
 */
@Data
@Accessors(chain = true)
@TableName("t_hxy_user")
public class User extends Model<User> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private String name;
	private String idcode;
	private String department;
	private String gender;
	private Integer type;

	public static final String ID = "id";

	public static final String NAME = "name";

	public static final String IDCODE = "idcode";

	public static final String DEPARTMENT = "department";

	public static final String GENDER = "gender";

	public static final String TYPE = "type";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}

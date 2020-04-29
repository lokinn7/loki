package com.kuaiyou.lucky.entity;

import java.io.Serializable;

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
 * @since 2019-08-26
 */
@Data
@Accessors(chain = true)
@TableName("t_luck_fakeuser")
public class Fakeuser extends Model<Fakeuser> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private String nickname;
	private String avatarurl;
	private Integer prizelevel;
	private Integer drawid;

	public static final String ID = "id";

	public static final String NICKNAME = "nickname";

	public static final String AVATARURL = "avatarurl";

	public static final String PRIZELEVEL = "prizelevel";

	public static final String DRAWID = "drawid";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}

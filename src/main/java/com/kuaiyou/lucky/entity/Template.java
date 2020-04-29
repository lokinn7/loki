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
 * @since 2019-08-14
 */
@Data
@Accessors(chain = true)
@TableName("t_luck_template")
public class Template extends Model<Template> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private String openid;
	private String userid;
	private String page;
	private String formid;
	private String emphasiskeyword;
	private String keyword1;
	private String keyword2;
	private String keyword3;
	private String keyword4;
	private String keyword5;
	private String keyword6;
	private Date ctime;
	private String templateid;
	private Integer drawid;
	private Integer status;
	private Integer type;
	private String title;
	private String remark;

	public static final String ID = "id";

	public static final String OPENID = "openid";

	public static final String USERID = "userid";

	public static final String PAGE = "page";

	public static final String FORMID = "formid";

	public static final String EMPHASISKEYWORD = "emphasiskeyword";

	public static final String KEYWORD1 = "keyword1";

	public static final String KEYWORD2 = "keyword2";

	public static final String KEYWORD3 = "keyword3";

	public static final String KEYWORD4 = "keyword4";

	public static final String KEYWORD5 = "keyword5";

	public static final String KEYWORD6 = "keyword6";

	public static final String CTIME = "ctime";

	public static final String TEMPLATEID = "templateid";

	public static final String DRAWID = "drawid";

	public static final String STATUS = "status";

	public static final String TYPE = "type";

	public static final String TITLE = "title";

	public static final String REMARK = "remark";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}

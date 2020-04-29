package com.kuaiyou.lucky.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 微信用户表
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@Data
@Accessors(chain = true)
@TableName("t_luck_wxuser")
public class Wxuser extends Model<Wxuser> {

	private static final long serialVersionUID = 1L;

	/**
	 * 客户id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 微信昵称
	 */
	private String nickname;
	/**
	 * 1 有效 0无效
	 */
	private Integer status;
	/**
	 * 自己生成的用户ud
	 */
	private String userid;
	/**
	 * 微信的唯一id
	 */
	private String openid;
	/**
	 * 用户是否取消关注 1关注0取消关注
	 */
	@TableLogic
	private Integer deleted;
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 更新时间
	 */
	private Date updatetime;
	/**
	 * 性别1男
	 */
	private Integer gender;
	private String language;
	private String city;
	private String province;
	private String country;
	private String avatarurl;
	/**
	 * 游戏标识
	 */
	private String flag;
	/**
	 * 渠道标识
	 */
	private String channel;

	private Long coins;

	private Integer audit;

	private String unionid;

	private Integer subac;

	private Integer subsign;

	public static final String ID = "id";

	public static final String NICKNAME = "nickname";

	public static final String STATUS = "status";

	public static final String USERID = "userid";

	public static final String OPENID = "openid";

	public static final String DELETED = "deleted";

	public static final String CREATETIME = "createtime";

	public static final String UPDATETIME = "updatetime";

	public static final String GENDER = "gender";

	public static final String LANGUAGE = "language";

	public static final String CITY = "city";

	public static final String PROVINCE = "province";

	public static final String COUNTRY = "country";

	public static final String AVATARURL = "avatarurl";

	public static final String FLAG = "flag";

	public static final String CHANNEL = "channel";

	public static final String COINS = "coins";

	public static final String AUDIT = "audit";

	public static final String UNIONID = "unionid";

	public static final String SUBAC = "subac";

	public static final String SUBSIGN = "subsign";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}

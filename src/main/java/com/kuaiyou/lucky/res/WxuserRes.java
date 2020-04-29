package com.kuaiyou.lucky.res;

import java.util.Date;

import lombok.Data;

/**
 * <p>
 * 微信用户表
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@Data
public class WxuserRes {

	private Integer id;
	private String nickname;
	private Integer status;
	private String userid;
	private String openid;
	private Integer deleted;
	private Date createtime;
	private Date updatetime;
	private Integer gender;
	private String language;
	private String city;
	private String province;
	private String country;
	private String avatarurl;
	private String flag;
	private String channel;

	private long pubcount;
	private long joincount;
	private long bingocount;
	private String address;
	private Integer ban;

}

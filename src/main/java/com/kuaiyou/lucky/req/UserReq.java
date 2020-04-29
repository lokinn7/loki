package com.kuaiyou.lucky.req;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class UserReq {

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
	/**
	 * 语言
	 */
	private String language;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 国家
	 */
	private String country;
	private String avatarurl;
	private String name;
	private String phone;
	private String company;
	private String job;
	private String wechat;
	private int read;
	private String note;
	private String formid;
}

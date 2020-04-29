package com.kuaiyou.lucky.res;

import java.util.Date;

import lombok.Data;

/**
 * <p>
 * 发布任务
 * </p>
 *
 * @author yardney
 * @since 2019-11-04
 */
@Data
public class AdviserRes {


	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 所属id(userid)busnessid
	 */
	private Integer advid;
	/**
	 * 状态0无效1有效
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date ctime;
	/**
	 * 接入单价(分)
	 */
	private Long price;
	/**
	 * 任务简介
	 */
	private String desc;
	/**
	 * 更新时间
	 */
	private Date utime;
	/**
	 * 跳转类型 直接跳转:1 二维码跳转:2
	 */
	private Integer jumptype;
	/**
	 * 推广期开始
	 */
	private Date starttime;
	/**
	 * 推广期结束
	 */
	private Date endtime;
	/**
	 * 小程序所属类型1.渠道2.小游戏3.广告主
	 */
	private Integer type;
	/**
	 * appkey
	 */
	private String appkey;
	/**
	 * appsecret
	 */
	private String appsecret;
	private String iconurl;
	private String inserturl;
	private String bannerurl;
	private String qrurl;
	private String proname;

}

package com.kuaiyou.lucky.res;

import java.util.Date;

import lombok.Data;

@Data
public class PrizeSettingRes {
	private Integer id;
	private Integer drawid;
	private Integer amount;
	private Date ctime;
	private Integer isbingo;
	private Integer level;
	private String prizeurl;
	private String prizename;
	private String linkurl;
	private Integer type;
	private Integer fake;
	private String appkey;
	private String appsecret;

}

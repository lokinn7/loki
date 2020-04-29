package com.kuaiyou.lucky.req;

import lombok.Data;

@Data
public class PrizeSettingReq {
	private String name;
	private String prizeurl;
	private String mark;
	private int amount;
	private Integer level; // 1st,2sd,3rd
	private Integer type;
	private Integer fake;
	private String prizename;
	private String linkurl;
	private String appkey;
}

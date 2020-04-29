package com.kuaiyou.lucky.res;

import java.util.Date;

import lombok.Data;

@Data
public class UserdrawRes {
	private Integer id;
	private Integer drawid;
	private String userid;
	private Integer bingo;
	private Date ctime;
	private String prizename;
	private Integer prizelevel;
	private Date btime;
	private String avatarurl;
	private String address;
	private String nickname;
	private Integer type;
	private Integer prizetype;
	private String flag;

	public UserdrawRes(Integer prizelevel, String avatarurl, String nickname) {
		this.prizelevel = prizelevel;
		this.avatarurl = avatarurl;
		this.nickname = nickname;
	}

	public UserdrawRes(Integer prizelevel, String avatarurl, String nickname, Integer drawid) {
		this.drawid = drawid;
		this.prizelevel = prizelevel;
		this.avatarurl = avatarurl;
		this.nickname = nickname;
	}

	public UserdrawRes() {
	}

}

package com.kuaiyou.lucky.res;

import java.util.Date;

import lombok.Data;

@Data
public class BingoWithNoRes {
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
}

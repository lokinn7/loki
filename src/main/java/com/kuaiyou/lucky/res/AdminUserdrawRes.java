package com.kuaiyou.lucky.res;

import lombok.Data;

@Data
public class AdminUserdrawRes extends UserdrawRes {
	private String drawname;
	private long bingocount;
	private long joincount;
	private String stprize;
	private String pubname;
	private String nickname;
	private String address;
	private String flag;
}

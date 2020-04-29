package com.kuaiyou.lucky.res;

import java.util.Date;

import lombok.Data;

@Data
public class DaydataRes {

	// 统计时间
	private String ctime;
	// uv
	private long uv;
	// 新增uv
	private long newuv;
	// 总参与人数
	private long joincount;
	// 总发布的抽奖数
	private long pubcount;
	// 总开奖数
	private long opencount;

}

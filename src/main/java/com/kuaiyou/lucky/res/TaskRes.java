package com.kuaiyou.lucky.res;

import java.util.Date;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author yardney
 * @since 2019-09-09
 */
@Data
public class TaskRes {

	private Integer id;
	private String name;
	private Integer dtype;

	private Integer ctype;
	private String desc;
	private Integer coins;

	private Date ctime;

	// 用户完成数量
	private Integer amount;

	private Integer status;
	// 用户审核
	private Integer audit;
	private Integer subsign;
	// 当前是否参加锦鲤活动
	private Integer act;
	// 是否完成 0 未完成1 完成
	private int done;
	// 分享的次数
	private int sharecount;
	// 邀请别人的次数
	private int invitecount;
	// 参与的抽奖次数
	private int joincount;
	// 是否参与锦鲤活动
	private int actcount;

}

package com.kuaiyou.lucky.enums;

public enum TemplateTypeEnum {

	// 小程序通知开始
	OPENMINI("用户参与抽奖", 1, "抽奖开奖"),
	// 公众号通知 start
	/**
	 * <pre>
	 * 	预约成功
		恭喜你，签到提醒预约已成功！
		预约内容/预约时间
		终于等到你，感谢关注和支持。
	 * </pre>
	 */
	SIGN("预约签到提醒", 2, "恭喜你，签到提醒预约已成功！", "终于等到你，感谢关注和支持。"),

	/**
	 * <pre>
	 * 	报名成功
		恭喜你！成功参与【幸运锦鲤】会员专属活动！
		报名名称/报名时间
		专属锦鲤活动，只为特别的你！
	 * </pre>
	 */
	ACTIVE("锦鲤活动", 3, "恭喜你！成功参与【幸运锦鲤】会员专属活动！", "专属锦鲤活动，只为特别的你！"),

	/**
	 * <pre>
	 * 	开奖结果
		亲，你参与的【幸运锦鲤】会员专属活动已开奖！
		本期期号/开奖时间
		查看中奖名单！
	 * </pre>
	 */
	OPENACTIVE("锦鲤活动开奖通知", 4, "亲，你参与的【幸运锦鲤】会员专属活动已开奖！", "查看中奖名单！"),

	/**
	 * <pre>
	 * 	新预约提醒
		亲，预约签到时间已到，请注意！
		客户姓名/预约内容
		生活处处都有小惊喜，我在这里等你！
	 * </pre>
	 */
	SIGNPRE("预约签到", 5, "亲，预约签到时间已到，请注意！", "生活处处都有小惊喜，我在这里等你！");

	private String text;
	private int code;
	private String title;
	private String remark;

	private TemplateTypeEnum(String text, int code, String title, String remark) {
		this.title = title;
		this.remark = remark;
		this.text = text;
		this.code = code;
	}

	private TemplateTypeEnum(String text, int code, String title) {
		this.title = title;
		this.text = text;
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}

package com.kuaiyou.lucky.enums;

public enum CoinTypeEnum {
	/**
	 * 金币的类型即为任务的类型
	 */

	SIGN("用户签到,%s", 1),

	USERAUTH("用户授权,%s", 2),

	SUBACTIVE("用户%s参与一次幸运锦鲤", 3),

	PUBJOIN("用户%s发布的抽奖被参与", 4),

	SHARE("用户%s分享抽奖", 5),

	INVITE("用户%s邀请参与抽奖", 6),

	JOINS("用户%s参与的抽奖", 7),

	CLICK("用户%s点击", 8),

	SUBSIGN("用户预约签到,%s", 9),

	RATOCOIN("用户%s参与抽奖送金币", 10),

	NEWUSER("用户%s新用户注册送金币", 11),

	GOODSFEE("用户%s领取优惠券", 12),

	GOODSCLICK("用户%s商品点击", 13),

	ROUNDFEE("用户%s参与转盘", 14),

	CONVROUNDFEE("用户%s兑换转盘获奖金币", 15),

	ADCLICK("用户%s点击广告", 16),

	ADJUMP("用户%s点击广告跳转", 17),

	ADSHOW("用户%s广告展示", 18),

	DETAILCLICK("推广详情%s点击", 19),

	DETAILSHOW("推广详情%s展示", 20),

	DETAILHOME("推广详情%s点击home键", 21);

	private String text;
	private int code;
	private int amount;

	private CoinTypeEnum(String text, int code) {
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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public static String getDesc(int code) {
		CoinTypeEnum[] values = values();
		for (CoinTypeEnum coinTypeEnum : values) {
			if (coinTypeEnum.getCode() == code) {
				return coinTypeEnum.getText();
			}
		}
		return null;
	}

}

package com.kuaiyou.lucky.enums;

public enum DataTypeEnum {

	CLICK("用户点击", 1),

	SHARE("用户分享", 2);


	private String text;
	private int code;

	private DataTypeEnum(String text, int code) {
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

}

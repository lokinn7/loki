package com.kuaiyou.lucky.enums;

public enum FakeTypeEnum {

	YES("虚伪的开奖", 1), NO("真实的开奖", 0);

	private String text;
	private int code;

	private FakeTypeEnum(String text, int code) {
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

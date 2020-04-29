package com.kuaiyou.lucky.enums;

public enum DrawTypeEnum {

	FULL("人满开奖", 1), TIMEOVER("到时开奖", 2), MUNUL("手动开奖", 3);

	private String text;
	private int code;

	private DrawTypeEnum(String text, int code) {
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

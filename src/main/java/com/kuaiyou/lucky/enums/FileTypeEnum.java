package com.kuaiyou.lucky.enums;

/**
 * 文件对应的业务类型
 * 
 * @author lake.zhang
 *
 */
public enum FileTypeEnum {
	// 二维码图片上传restfull
	DRAWIMGURL,
	// 小程序log上传restfull
	TUWENJIESHAO;

	private FileTypeEnum() {
	}

	public static FileTypeEnum getTypeByName(String name) {
		FileTypeEnum type = null;
		for (FileTypeEnum item : values()) {
			if (item.name().equalsIgnoreCase(name)) {
				type = item;
				break;
			}
		}
		return type;
	}
}
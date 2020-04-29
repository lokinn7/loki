package com.kuaiyou.lucky.utils;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

public class Generator {

	/**
	 * 记录ID
	 * 
	 * @return
	 */
	public static String id() {
		return new ObjectId().toHexString();
	}

	public static String token() {
		String uuid = UUID.randomUUID().toString();
		return StringUtils.replaceAll(uuid, "-", "");
	}

	public static void main(String[] args) {
		System.out.println(token());
	}

}

package com.kuaiyou.lucky.common;

public interface CommonRedisKey {

	public final static String USEROPENID = "_game_notify:";
	public final static String USER_SESSION = "_lucky_session:";

	public static String getUserOpenidKey(String key) {
		return USEROPENID + key;
	}

}

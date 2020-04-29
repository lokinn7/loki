package com.kuaiyou.lucky.utils;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.http.HttpRequest;

public class SecurityUtil {
	public final static String USERID = "exchangeuserid";
	public final static String AUTHPREFIX = "_exchange_tinypro";
	public final static String WECHATCODE = "_wechatcode";

	public static void setUserid(HttpRequest request) {
		HttpServletRequest req = (HttpServletRequest) request;
		req.setAttribute(USERID, req);
	}

	public static String getUserid(HttpServletRequest request) {
		return request.getHeader(USERID);
	}
}

package com.kuaiyou.lucky.utils;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.kuaiyou.lucky.common.Project;
import com.kuaiyou.lucky.res.Token;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExpressUtil {

	// 获取access_token 限2000（次/天）
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	public final static String ADD_ORDER = "https://api.weixin.qq.com/cgi-bin/express/business/order/add?access_token=%s";
	public final static String GET_ORDER = "https://api.weixin.qq.com/cgi-bin/express/business/order/get?access_token=%s";
	public final static String GET_PATH = "https://api.weixin.qq.com/cgi-bin/express/business/path/get?access_token=access_token=%s";
	public final static String GETALLDELIVER = "https://api.weixin.qq.com/cgi-bin/express/business/delivery/getall?access_token=%s";
	public final static String Token_Redis = "_luck:access_token";

	@Autowired
	Project project;

	@Autowired
	StringRedisTemplate redisTemplate;

	public static String initToken(String appid, String appsecret) {
		String requestUrl = String.format(ACCESS_TOKEN_URL, appid, appsecret);
		Token token = HttpClientUtil2.invokeGet_0(requestUrl);
		return token.getAccessToken();
	}

	public String getToken() {
		String cache = redisTemplate.opsForValue().get(Token_Redis);
		if (StringUtils.isBlank(cache)) {
			String requestUrl = String.format(ACCESS_TOKEN_URL, project.getAppkey(), project.getAppsecret());
			Token token = HttpClientUtil2.invokeGet_0(requestUrl);
			log.info(String.format("get access_token is: %s", JSON.toJSONString(token)));
			if (token != null) {
				String value = token.getAccessToken();
				redisTemplate.opsForValue().set(Token_Redis, value, 360, TimeUnit.SECONDS);
				return value;
			}
		}
		return cache;
	}

}
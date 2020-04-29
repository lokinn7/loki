package com.kuaiyou.lucky.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kuaiyou.lucky.entity.Activity;
import com.kuaiyou.lucky.entity.Prize;
import com.kuaiyou.lucky.req.DrawReq;

public class JsonUtil {

	public static String json2String(Object obj) {
		return JSON.toJSONString(obj, SerializerFeature.WRITE_MAP_NULL_FEATURES, SerializerFeature.QuoteFieldNames);
	}

	public static void main(String[] args) {
		System.out.println(json2String(new Prize()));
	}
}

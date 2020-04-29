package com.kuaiyou.lucky.common;

import java.util.HashMap;
import java.util.Map;

import com.kuaiyou.lucky.entity.Wxuser;

public class Result extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public static final String SUCCESS = "Success";
	public static final String FAILED = "Failed";

	public Result() {
		put("code", 200);
		put("msg", "ok");
	}

	public static Result error() {
		return error("500", "系统错误，请联系管理员");
	}

	public static Result error(String msg) {
		return error("500", msg);
	}

	public static Result error(String code, String msg) {
		Result r = new Result();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static Result error(ApiResultEnum resultEnum) {
		Result r = new Result();
		r.put("code", resultEnum.getStatus());
		r.put("msg", resultEnum.getMessage());
		return r;
	}

	public static Result ok(Map<String, Object> map) {
		Result r = new Result();
		r.putAll(map);
		return r;
	}

	public static Result ok(Object data) {
		Result r = new Result();
		r.put("data", data);
		return r;
	}

	public static Result ok_bot(Object data, boolean goods, boolean sign) {
		Result r = new Result();
		r.put("data", data);
		r.put("enough", goods);
		r.put("sign", sign);
		return r;
	}

	public static Result ok_turn(Object data, Wxuser wxuser) {
		Result r = new Result();
		r.put("data", data);
		r.put("user", wxuser);
		return r;
	}

	public static Result ok_new(Object data, Integer isnew) {
		Result r = new Result();
		r.put("data", data);
		r.put("isnew", isnew);
		return r;
	}

	public static Result ok() {
		return new Result();
	}

	@Override
	public Result put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
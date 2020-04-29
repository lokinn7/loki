package com.kuaiyou.lucky.common;

import java.util.HashMap;
import java.util.Map;

public class RetMap {

	private String msg;
	private Integer code;
	private Object grid;

	public static final int SUCCESS_CODE = 200;
	public static final int FAILED_CODE = 500;
	public static final String SUCCESS_MSG = "SUCCESS";

	public static RetMap SUCCESS(Object data) {
		RetMap ret = new RetMap();
		ret.setCode(SUCCESS_CODE);
		ret.setGrid(data);
		ret.setMsg(SUCCESS_MSG);
		return ret;
	}

	public static RetMap SUCCESS() {
		RetMap ret = new RetMap();
		ret.setCode(SUCCESS_CODE);
		ret.setMsg(SUCCESS_MSG);
		return ret;
	}

	public static RetMap FAILED(String msg) {
		RetMap ret = new RetMap();
		ret.setCode(FAILED_CODE);
		ret.setMsg(msg);
		return ret;
	}

	public static Map<String, Object> success(Object data) {
		Map<String, Object> result = new HashMap<>();
		result.put("data", data);
		result.put("code", SUCCESS_CODE);
		return result;
	}

	public static Map<String, Object> success(Object data, String username) {
		Map<String, Object> result = new HashMap<>();
		result.put("data", data);
		result.put("code", SUCCESS_CODE);
		result.put("username", username);
		return result;
	}

	public static Map<String, Object> success() {
		Map<String, Object> result = new HashMap<>();
		result.put("code", SUCCESS_CODE);
		return result;
	}

	public static Map<String, Object> failed(String msg) {
		Map<String, Object> result = new HashMap<>();
		result.put("code", FAILED_CODE);
		result.put("msg", msg);
		return result;
	}

	public static Map<String, Object> failed(String msg, Integer code) {
		Map<String, Object> result = new HashMap<>();
		result.put("code", code);
		result.put("msg", msg);
		return result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Object getGrid() {
		return grid;
	}

	public void setGrid(Object grid) {
		this.grid = grid;
	}

}

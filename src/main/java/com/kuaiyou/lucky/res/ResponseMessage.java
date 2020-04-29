package com.kuaiyou.lucky.res;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;

/**
 * 响应消息。controller中处理后，返回此对象，响应请求结果给客户端。
 * @author liuyijie
 */
public class ResponseMessage<T> implements Serializable {
    /**
     * 返回成功默认消息"OK"
     */
    public static final String OK_MESSAGE = "OK";
    /**
     * 成功code=200
     */
    public static final int OK_CODE = 200;
    /**
     * 失败code=500
     */
    public static final int ERROR_CODE = 500;
    /**
     * 未认证code=401
     */
    public static final int ERROR_UNAUTH = 401;

    private static final long serialVersionUID = 8992436576262574064L;

    protected String msg;

    protected T data;

    protected int code;

    private Long timestamp;

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public static <T> ResponseMessage<T> error(String message) {
        return error(ERROR_CODE, message);
    }

    public static <T> ResponseMessage<T> error(int code, String message) {
        ResponseMessage<T> msg = new ResponseMessage<>();
        msg.msg = message;
        msg.code(code);
        return msg.putTimeStamp();
    }

    public static <T> ResponseMessage<T> okMessage(String message) {
        ResponseMessage<T> msg = new ResponseMessage<>();
        msg.msg = message;
        msg.code(OK_CODE);
        return msg.putTimeStamp();
    }

    public static <T> ResponseMessage<T> ok() {
        ResponseMessage<T> msg = new ResponseMessage<>();
        msg.msg = OK_MESSAGE;
        msg.code(OK_CODE);
        return msg.putTimeStamp();
    }

    private ResponseMessage<T> putTimeStamp() {
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public static <T> ResponseMessage<T> ok(T result) {
        return new ResponseMessage<T>()
                .data(result)
                .putTimeStamp()
                .withMessage(OK_MESSAGE)
                .code(OK_CODE);
    }

    public static <T> ResponseMessage<T> ok(String message, T result) {
        return new ResponseMessage<T>()
                .data(result)
                .putTimeStamp()
                .withMessage(message)
                .code(OK_CODE);
    }

    public ResponseMessage<T> data(T data) {
        this.data = data;
        return this;
    }

    public ResponseMessage<T> withMessage(String msg){
        this.msg = msg;
        return this;
    }

    public ResponseMessage() {

    }

    @SuppressWarnings("rawtypes")
	protected Set<String> getStringListFromMap(Map<Class<?>, Set<String>> map, Class type) {
        return map.computeIfAbsent(type, k -> new HashSet<>());
    }

    @Override
    public String toString() {
        return JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss");
    }

    public ResponseMessage<T> code(int code) {
        this.code = code;
        return this;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 判断是否成功
     * @return
     */
    public boolean success(){
        return this.code == OK_CODE;
    }
}
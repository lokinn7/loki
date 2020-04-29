package com.kuaiyou.lucky.req;

import lombok.Data;

@Data
public class OpenidReq {
	private String code;
	private String userid;
	private String encryptedData;
	private String iv;
	private String session_key;
	private InitUserReq userInfo;
}

package com.kuaiyou.lucky.req;

import lombok.Data;

@Data
public class InitUserReq {

	private String nickName, language, city, province, country, avatarUrl, userid;
	private int gender;

}

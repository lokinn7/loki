package com.kuaiyou.lucky.api.req;

import com.kuaiyou.lucky.common.PageVo;

import lombok.Data;

@Data
public class CommonReq extends PageVo {

	private String idcode;
	private String nickname;
	private String month;
}

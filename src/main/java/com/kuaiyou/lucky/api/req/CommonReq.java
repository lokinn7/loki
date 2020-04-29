package com.kuaiyou.lucky.api.req;

import java.util.List;

import com.kuaiyou.lucky.common.PageVo;
import com.kuaiyou.lucky.entity.Prize;

import lombok.Data;

@Data
public class CommonReq extends PageVo {

	private String drawid;
	private String userid;
	private int minetype;
	private Integer isrec;
	private Integer prizelevel;
	private Integer prizetype;
	private Integer pubid;
	private String nickname;
	private Integer type;
	private Integer pubtype;
	private Integer opentype;
	private Integer status;
	private String name;
	private Integer day;
	private List<Prize> prizeratos;
	private String asname;
	private String acname;
}

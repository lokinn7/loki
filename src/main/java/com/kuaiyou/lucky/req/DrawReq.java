package com.kuaiyou.lucky.req;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class DrawReq {

	private String id;
	private String name;
	private Integer level; // 4 2 1
	private Integer opentype;
	private String notes;
	private Integer type;
	private Integer status;
	private Integer pubtype;
	private String userid;
	private int isrec;
	private String pubid;
	private Date opentime;
	private int mencount;
	private String stprize;
	private String pubname;
	private String formid;
	private String img1;
	private String img2;
	private String img3;
	private Integer deleted;
	private List<PrizeSettingReq> prizes;
	private String mainurl;
	private Integer act;
	private Date onlinetime;

	public DrawReq(String id) {
		this.id = id;
	}

	public DrawReq() {
	}

}

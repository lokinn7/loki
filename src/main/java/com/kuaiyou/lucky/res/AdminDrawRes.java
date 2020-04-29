package com.kuaiyou.lucky.res;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class AdminDrawRes {
	private Integer id;
	private String name;
	private Integer level;
	private Integer opentype;
	private String notes;
	private Integer type;
	private Integer status;
	private Date opentime;
	private Date utime;
	private Date ctime;
	private String pubid;
	private Integer pubtype;
	private Integer adid;
	private Integer isrec;
	private String appkey;
	private String bannerurl;
	private String iconurl;
	private String qrurl;
	private String inserturl;
	private String acname;
	private String pubname;
	private String stprize;
	private String stprizeurl;
	private Integer mencount;
	private Integer isjoin;
	private UserdrawRes isbingo;
	private String img1;
	private String img2;
	private String img3;
	private Integer deleted;
	private int totaljoin;
	private int joincount;
	private int bingocount;
	private String mainurl;
	private Integer act;
	private List<UserdrawRes> joins;
	private Map<Integer, List<UserdrawRes>> bingos;
	private List<PrizeSettingRes> prizes;
}

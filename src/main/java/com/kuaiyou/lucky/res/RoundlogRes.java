package com.kuaiyou.lucky.res;

import java.util.Date;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author yardney
 * @since 2019-10-23
 */
@Data
public class RoundlogRes {

	private Integer id;
	private Integer prizeid;
	private String userid;
	private Integer status;
	private Date ctime;
	private String prizeurl;
	private String linkurl;
	private String name;
	private String address;
	private Integer type;
	private Integer amount;
	private String appkey;
}

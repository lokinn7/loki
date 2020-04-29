package com.kuaiyou.lucky.req;

import java.util.Date;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author yardney
 * @since 2019-09-09
 */
@Data
public class ActiveReq {

	private Integer id;
	private String name;
	private Integer ctype;
	private Integer dtype;
	private String notes;
	private Date ctime;
	private Integer drawid;
	private String userid;
	private Integer coins;
	private Integer amount;
	private Integer status;
	private String tuserid;
}

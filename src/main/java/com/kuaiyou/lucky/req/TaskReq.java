package com.kuaiyou.lucky.req;

import java.util.Date;

import com.kuaiyou.lucky.common.PageVo;

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
public class TaskReq extends PageVo {

	private Integer id;
	private String name;
	private Integer dtype;
	private Integer ctype;
	private String desc;
	private Integer coins;
	private Date ctime;
	private Integer amount;
	private Integer usercount;
	private String userid;
}

package com.kuaiyou.lucky.res;

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
public class ActiveRes extends PageVo {

	private Integer id;
	private Integer ctype;
	private Date ctime;
	private Integer drawid;
	private String notes;
	private String userid;
	private String tuserid;
	private Long skuid;
	private String flag;
	private String skuname;
	private long click;
	private long uclick;
	private String proname;
}

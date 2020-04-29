package com.kuaiyou.lucky.res;

import java.util.Date;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author yardney
 * @since 2019-08-16
 */
@Data
public class ReportRes {

	private Integer id;
	private Integer drawid;
	private String userid;
	private String reason;
	private Date ctime;
	private String nickname;
	private String pubname;

}

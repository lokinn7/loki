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
 * @since 2019-08-12
 */
@Data
public class UserdrawReq extends PageVo {

	private Integer id;
	private Integer drawid;
	private String userid;
	private Integer bingo;
	private Date ctime;
	private Integer prizelevel;
	private Date btime;
	private String formid;
	private String name;
	private String flag;

}

package com.kuaiyou.lucky.req;

import java.util.Date;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author yardney
 * @since 2019-10-17
 */
@Data
public class ConvertReq {

	private Integer id;
	private String userid;
	private Long skuid;
	private Date ctime;
	private Integer amount;

}

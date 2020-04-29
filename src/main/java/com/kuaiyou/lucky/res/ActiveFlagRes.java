package com.kuaiyou.lucky.res;

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
public class ActiveFlagRes extends PageVo {

	private Integer ctype;
	private String flagname;
	private String flag;
	private Integer deshow;
	private Integer dehome;
	private Integer joins;

}

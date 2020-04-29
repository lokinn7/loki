package com.kuaiyou.lucky.res;

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
public class CoinsRes implements Comparable<CoinsRes> {
	private Integer id;
	/**
	 * 用户id
	 */
	private String userid;
	/**
	 * 积分类型
	 */
	private Integer ctype;
	/**
	 * 积分描述
	 */
	private String notes;

	private Date ctime;

	private Integer amount;

	private int diff;

	@Override
	public int compareTo(CoinsRes o) {
		return diff - o.diff;
	}

}

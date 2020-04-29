package com.kuaiyou.lucky.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yardney
 * @since 2019-10-14
 */
@Data
@Accessors(chain = true)
@TableName("t_luck_coinsrato")
public class Coinsrato extends Model<Coinsrato> implements Comparable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer rato;
	private Integer amount;

	public static final String ID = "id";

	public static final String RATO = "rato";

	public static final String AMOUNT = "amount";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public int compareTo(Object o) {
		Integer temp = ((Coinsrato) o).getAmount();
		return this.amount.compareTo(temp);
	}

}

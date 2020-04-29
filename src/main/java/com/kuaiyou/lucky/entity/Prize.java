package com.kuaiyou.lucky.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@Data
@Accessors(chain = true)
@TableName("t_luck_prize")
public class Prize extends Model<Prize> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 奖品名称
	 */
	private String name;
	/**
	 * 奖品数量
	 */
	private Integer status;
	/**
	 * 奖品图文介绍URL
	 */
	private String prizeurl;
	/**
	 * 抽奖说明
	 */
	private String mark;
	/**
	 * 奖品类型1实物2虚拟
	 */
	private Integer type;
	/**
	 * 创建时间
	 */
	private Date ctime;
	/**
	 * 更新时间
	 */
	private Date utime;

	private String pubid;

	private Integer pubtype;

	private String linkurl;

	private String appkey;

	private String appsecret;

	private Integer amount;

	/**
	 * 1.普通商品2.转盘商品
	 */
	private Integer atype;

	private Integer rato;

	private Integer pub;

	public static final String ID = "id";

	public static final String NAME = "name";

	public static final String STATUS = "status";

	public static final String PRIZEURL = "prizeurl";

	public static final String MARK = "mark";

	public static final String TYPE = "type";

	public static final String CTIME = "ctime";

	public static final String UTIME = "utime";

	public static final String PUBID = "pubid";

	public static final String PUBTYPE = "pubtype";

	public static final String LINKURL = "linkurl";

	public static final String APPKEY = "appkey";

	public static final String APPSECRET = "appsecret";

	public static final String ATYPE = "atype";

	public static final String RATO = "rato";

	public static final String AMOUNT = "amount";

	public static final String PUB = "pub";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prize other = (Prize) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((appkey == null) ? 0 : appkey.hashCode());
		result = prime * result + ((appsecret == null) ? 0 : appsecret.hashCode());
		result = prime * result + ((atype == null) ? 0 : atype.hashCode());
		result = prime * result + ((ctime == null) ? 0 : ctime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((linkurl == null) ? 0 : linkurl.hashCode());
		result = prime * result + ((mark == null) ? 0 : mark.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((prizeurl == null) ? 0 : prizeurl.hashCode());
		result = prime * result + ((pub == null) ? 0 : pub.hashCode());
		result = prime * result + ((pubid == null) ? 0 : pubid.hashCode());
		result = prime * result + ((pubtype == null) ? 0 : pubtype.hashCode());
		result = prime * result + ((rato == null) ? 0 : rato.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((utime == null) ? 0 : utime.hashCode());
		return result;
	}

}

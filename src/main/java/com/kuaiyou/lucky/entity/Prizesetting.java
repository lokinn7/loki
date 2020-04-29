package com.kuaiyou.lucky.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
@TableName("t_luck_prizesetting")
public class Prizesetting extends Model<Prizesetting> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 抽奖
	 */
	private Integer drawid;
	/**
	 * 奖品
	 */
	private Integer prizeid;
	/**
	 * 数量
	 */
	private Integer amount;
	/**
	 * 创建时间
	 */
	private Date ctime;
	/**
	 * 是否中奖
	 */
	private Integer isbingo;

	private Integer level;

	private Integer fake;

	private String pubid;
	// 1广告主2用户
	private Integer pubtype;

	private String prizeurl;
	// 1实物2虚拟
	private Integer type;

	private String prizename;

	private String linkurl;

	private String appkey;

	private String appsecret;

	public static final String ID = "id";

	public static final String DRAWID = "drawid";

	public static final String PRIZEID = "prizeid";

	public static final String AMOUNT = "amount";

	public static final String CTIME = "ctime";

	public static final String ISBINGO = "isbingo";

	public static final String LEVEL = "level";

	public static final String FAKE = "fake";

	public static final String PUBID = "pubid";

	public static final String PUBTYPE = "pubtype";

	public static final String PRIZEURL = "prizeurl";

	public static final String TYPE = "type";

	public static final String PRIZENAME = "prizename";

	public static final String LINKURL = "linkurl";

	public static final String APPKEY = "appkey";

	public static final String APPSECRET = "appsecret";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}

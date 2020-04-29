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
 * 小程序任务报表
 * </p>
 *
 * @author yardney
 * @since 2019-11-01
 */
@Data
@Accessors(chain = true)
@TableName("t_luck_dailyreport")
public class Dailyreport extends Model<Dailyreport> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 报表日期
	 */
	private String reporttime;
	/**
	 * 小程序id
	 */
	private String acid;
	/**
	 * 广告位id
	 */
	private String asid;
	/**
	 * 任务名称
	 */
	private String acname;
	/**
	 * 广告位名称
	 */
	private String asname;
	/**
	 * 未去重的展示数
	 */
	private Integer show;
	/**
	 * 去重后的展示数
	 */
	private Integer ushow;
	/**
	 * 未去重后的点击数
	 */
	private Integer click;
	/**
	 * 去重后的点击数
	 */
	private Integer uclick;
	private Integer jump;
	/**
	 * 去重后的跳转数
	 */
	private Integer ujump;
	private String linkurl;
	private Date ctime;

	public static final String ID = "id";

	public static final String REPORTTIME = "reporttime";

	public static final String ACID = "acid";

	public static final String ASID = "asid";

	public static final String ACNAME = "acname";

	public static final String ASNAME = "asname";

	public static final String SHOW = "show";

	public static final String USHOW = "ushow";

	public static final String CLICK = "click";

	public static final String UCLICK = "uclick";

	public static final String JUMP = "jump";

	public static final String UJUMP = "ujump";

	public static final String LINKURL = "linkurl";

	public static final String CTIME = "ctime";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public Dailyreport(String reporttime, String acid, String asid, String acname, String asname, Integer show,
			Integer ushow, Integer click, Integer uclick, Date ctime) {
		this.reporttime = reporttime;
		this.acid = acid;
		this.asid = asid;
		this.acname = acname;
		this.asname = asname;
		this.show = show;
		this.ushow = ushow;
		this.click = click;
		this.uclick = uclick;
		this.ctime = ctime;
	}

	public Dailyreport() {
		super();
	}

}

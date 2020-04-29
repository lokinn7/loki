package com.kuaiyou.lucky.res;

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
public class DailyreportRes {
	private String reporttime;
	private String acid;
	private String asid;
	private String acname;
	private String asname;
	private Integer show;
	private Integer ushow;
	private Integer click;
	private Integer uclick;
	private Integer jump;
	private Integer ujump;
	private String linkurl;
	private Date ctime;
	private Integer uamount;
	private Integer amount;

}

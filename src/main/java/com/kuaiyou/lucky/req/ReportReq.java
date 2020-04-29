package com.kuaiyou.lucky.req;

import com.baomidou.mybatisplus.enums.IdType;
import com.kuaiyou.lucky.common.PageVo;

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
 * @since 2019-08-16
 */
@Data
public class ReportReq extends PageVo {

	private Integer id;
	private Integer drawid;
	private String userid;
	private String reason;
	private Date ctime;
	private String nickname;
	private String pubname;

}

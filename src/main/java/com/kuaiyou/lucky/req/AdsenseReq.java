package com.kuaiyou.lucky.req;

import java.util.Date;

import com.kuaiyou.lucky.common.PageVo;

import lombok.Data;

/**
 * <p>
 * 广告位信息
 * </p>
 *
 * @author yardney
 * @since 2019-10-31
 */
@Data
public class AdsenseReq extends PageVo {
	private Integer id;
	private Integer acid;

	private Integer asid;
	private String acname;
	private String asname;
	private Integer status;
	private Integer astype;
	private Integer fill;
	private Integer isdt;
	private String appkey;
	private String iconurl;
	private String inserturl;
	private String bannerurl;
	private String qrurl;
	private String linkurl;
	private Integer jumptype;
	private Date ctime;

}

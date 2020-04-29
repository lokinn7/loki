package com.kuaiyou.lucky.req;

import lombok.Data;

@Data
public class GenQrReq {

	private String scene;
	private String page;
	private Integer width = 430;
	private boolean auto_color = true;

	/**
	 * <pre>
	 * {
			"scene":"shareimg_156_flag_CAGYV",
			"page":"pages/prizedetail/prizedetail",
			"width":430,
			"auto_color":true
		}
	 * </pre>
	 */
}

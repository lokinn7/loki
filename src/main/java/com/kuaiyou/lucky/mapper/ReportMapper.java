package com.kuaiyou.lucky.mapper;

import com.kuaiyou.lucky.entity.Report;
import com.kuaiyou.lucky.req.ReportReq;
import com.kuaiyou.lucky.res.ReportRes;

import java.util.List;

import com.kuaiyou.lucky.common.SuperMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yardney
 * @since 2019-08-16
 */
public interface ReportMapper extends SuperMapper<Report> {
	List<ReportRes> baseList(ReportReq report);

	Integer baseListCount(ReportReq report);
}

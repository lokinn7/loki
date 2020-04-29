package com.kuaiyou.lucky.service;

import com.kuaiyou.lucky.entity.Report;
import com.kuaiyou.lucky.req.ReportReq;
import com.kuaiyou.lucky.res.ReportRes;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yardney
 * @since 2019-08-16
 */
public interface ReportService extends IService<Report> {

	List<ReportRes> baseList(ReportReq report);

	int baseListCount(ReportReq report);

}

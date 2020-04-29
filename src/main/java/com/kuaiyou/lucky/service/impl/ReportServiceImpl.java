package com.kuaiyou.lucky.service.impl;

import com.kuaiyou.lucky.entity.Report;
import com.kuaiyou.lucky.mapper.ReportMapper;
import com.kuaiyou.lucky.req.ReportReq;
import com.kuaiyou.lucky.res.ReportRes;
import com.kuaiyou.lucky.service.ReportService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2019-08-16
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {

	@Override
	public List<ReportRes> baseList(ReportReq report) {
		return baseMapper.baseList(report);
	}

	@Override
	public int baseListCount(ReportReq report) {
		Integer count = baseMapper.baseListCount(report);
		return count == null ? 0 : count;
	}

}

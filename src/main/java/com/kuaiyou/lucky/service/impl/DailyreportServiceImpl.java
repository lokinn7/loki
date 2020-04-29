package com.kuaiyou.lucky.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kuaiyou.lucky.entity.Dailyreport;
import com.kuaiyou.lucky.mapper.DailyreportMapper;
import com.kuaiyou.lucky.service.DailyreportService;

/**
 * <p>
 * 小程序任务报表 服务实现类
 * </p>
 *
 * @author yardney
 * @since 2019-11-01
 */
@Service
public class DailyreportServiceImpl extends ServiceImpl<DailyreportMapper, Dailyreport> implements DailyreportService {

	@Override
	public Dailyreport selectByProfile(String today, String asid, String acid) {
		Wrapper<Dailyreport> wrapper = new EntityWrapper<>();
		wrapper.eq(Dailyreport.REPORTTIME, today).eq(Dailyreport.ASID, asid).eq(Dailyreport.ACID, acid);
		return selectOne(wrapper);
	}

}

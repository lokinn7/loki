package com.kuaiyou.lucky.service;

import com.kuaiyou.lucky.entity.Dailyreport;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 小程序任务报表 服务类
 * </p>
 *
 * @author yardney
 * @since 2019-11-01
 */
public interface DailyreportService extends IService<Dailyreport> {

	Dailyreport selectByProfile(String today, String asid, String acid);

}

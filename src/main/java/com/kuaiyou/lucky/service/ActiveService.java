package com.kuaiyou.lucky.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.kuaiyou.lucky.entity.Active;
import com.kuaiyou.lucky.req.ActiveReq;
import com.kuaiyou.lucky.req.TaskReq;
import com.kuaiyou.lucky.res.ActiveFlagRes;
import com.kuaiyou.lucky.res.ActiveRes;
import com.kuaiyou.lucky.res.DailyreportRes;
import com.kuaiyou.lucky.res.TaskRes;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yardney
 * @since 2019-09-09
 */
public interface ActiveService extends IService<Active> {

	boolean insertWithCoins(ActiveReq active, int i);

	TaskRes dateTaskResult(TaskReq req);

	TaskRes newTaskResult(TaskReq req);

	List<Active> selectByUserid(String userid, String invuserid, int code);

	List<ActiveRes> channelStat(ActiveRes req);

	int channelStatCount(ActiveRes req);

	List<DailyreportRes> dailyData(ActiveRes req);

	List<ActiveFlagRes> getDetailStatic(ActiveFlagRes req);
}

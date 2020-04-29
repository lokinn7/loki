package com.kuaiyou.lucky.mapper;

import java.util.List;

import com.kuaiyou.lucky.common.SuperMapper;
import com.kuaiyou.lucky.entity.Active;
import com.kuaiyou.lucky.req.ActiveReq;
import com.kuaiyou.lucky.req.TaskReq;
import com.kuaiyou.lucky.res.ActiveFlagRes;
import com.kuaiyou.lucky.res.ActiveRes;
import com.kuaiyou.lucky.res.DailyreportRes;
import com.kuaiyou.lucky.res.TaskRes;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yardney
 * @since 2019-09-09
 */
public interface ActiveMapper extends SuperMapper<Active> {

	boolean insertWithCoins(ActiveReq active);

	TaskRes dateTaskResult(TaskReq req);

	TaskRes newTaskResult(TaskReq req);

	List<ActiveRes> channelStat(ActiveRes req);

	Integer channelStatCount(ActiveRes req);

	List<DailyreportRes> dailyData(ActiveRes req);

	List<ActiveFlagRes> getDetailStatic(ActiveFlagRes req);
}

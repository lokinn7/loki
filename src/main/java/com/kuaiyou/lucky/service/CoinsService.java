package com.kuaiyou.lucky.service;

import com.baomidou.mybatisplus.service.IService;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.entity.Coins;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yardney
 * @since 2019-09-09
 */
public interface CoinsService extends IService<Coins> {

	boolean insertTemp(Coins req);

	boolean insertTemp(Coins req, int dtype);

	boolean userSign(Coins req);

	boolean userAction(Coins req);

	boolean insertWithUser(Coins req);
	
	Result selectWeekSigh(String userid);

	boolean updateTemp(Coins coins);

	Coins selectByType(String userid, int code);

}

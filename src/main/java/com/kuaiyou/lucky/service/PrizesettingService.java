package com.kuaiyou.lucky.service;

import com.baomidou.mybatisplus.service.IService;
import com.kuaiyou.lucky.entity.Prizesetting;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
public interface PrizesettingService extends IService<Prizesetting> {

	boolean deleteBydrawid(Integer id);

}

package com.kuaiyou.lucky.service;

import com.kuaiyou.lucky.entity.Banlist;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yardney
 * @since 2019-09-24
 */
public interface BanlistService extends IService<Banlist> {

	boolean deleteByUserId(String userid);

}

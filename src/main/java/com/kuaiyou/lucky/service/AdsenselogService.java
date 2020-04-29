package com.kuaiyou.lucky.service;

import com.kuaiyou.lucky.entity.Adsenselog;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 广告位信息 服务类
 * </p>
 *
 * @author yardney
 * @since 2019-10-31
 */
public interface AdsenselogService extends IService<Adsenselog> {

	Adsenselog selectByAcAs(String acid, String asid);

	List<Adsenselog> getAd(Integer asid);

}

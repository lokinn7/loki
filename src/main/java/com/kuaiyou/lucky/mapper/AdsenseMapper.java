package com.kuaiyou.lucky.mapper;

import com.kuaiyou.lucky.entity.Adsense;
import com.kuaiyou.lucky.entity.Adsenselog;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

import com.kuaiyou.lucky.common.SuperMapper;

/**
 * <p>
 * 广告位信息 Mapper 接口
 * </p>
 *
 * @author yardney
 * @since 2019-10-31
 */
public interface AdsenseMapper extends SuperMapper<Adsense> {
	List<Adsenselog> getAd(@Param("asid") Integer asid);
}

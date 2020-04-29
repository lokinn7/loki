package com.kuaiyou.lucky.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kuaiyou.lucky.common.SuperMapper;
import com.kuaiyou.lucky.entity.Coins;
import com.kuaiyou.lucky.res.CoinsRes;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yardney
 * @since 2019-09-09
 */
public interface CoinsMapper extends SuperMapper<Coins> {

	Integer insertWithUser(Coins req);

	List<CoinsRes> selectDateDiff(@Param("start")Date start, @Param("end")Date end);

	Integer minsWithUser(Coins req);

}

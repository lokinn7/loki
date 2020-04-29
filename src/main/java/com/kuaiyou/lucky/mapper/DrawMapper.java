package com.kuaiyou.lucky.mapper;

import java.util.List;

import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.SuperMapper;
import com.kuaiyou.lucky.entity.Draw;
import com.kuaiyou.lucky.res.AdminDrawRes;
import com.kuaiyou.lucky.res.DrawActiveRes;
import com.kuaiyou.lucky.res.DrawRes;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
public interface DrawMapper extends SuperMapper<Draw> {
	List<DrawRes> recGrid(CommonReq req);

	List<DrawRes> baseGrid(CommonReq req);

	Integer baseGridCount(CommonReq req);

	DrawRes drawDetail(CommonReq req);

	DrawRes adminDrawDetail(CommonReq req);

	List<AdminDrawRes> adminGrid(CommonReq req);

	Integer adminGridCount(CommonReq req);

	List<DrawRes> activeGrid(CommonReq req);

	Integer activeGridCount(CommonReq req);

	List<DrawActiveRes> selectWithActive();
}

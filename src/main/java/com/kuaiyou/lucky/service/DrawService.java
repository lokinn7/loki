package com.kuaiyou.lucky.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.entity.Draw;
import com.kuaiyou.lucky.req.DrawReq;
import com.kuaiyou.lucky.res.AdminDrawRes;
import com.kuaiyou.lucky.res.DrawActiveRes;
import com.kuaiyou.lucky.res.DrawRes;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
public interface DrawService extends IService<Draw> {

	List<DrawRes> baseGrid(CommonReq req);

	int baseGridCount(CommonReq req);

	DrawRes drawDetail(CommonReq req);

	DrawRes adminDrawDetail(CommonReq req);

	List<DrawRes> recGrid(CommonReq req);

	Integer insertWithSetting(DrawReq draw);

	boolean admininsertWithSetting(DrawReq draw);

	boolean openDraw(Draw drawReq);

	Integer updateWithSetting(DrawReq draw);

	List<AdminDrawRes> adminGrid(CommonReq req);

	int adminGridCount(CommonReq req);

	Draw selectByWithStatus(Integer drawid);
	
	List<DrawRes> activeGrid(CommonReq req);

	int activeGridCount(CommonReq req);

	List<DrawActiveRes> selectWithActive();

	boolean admininsertWithActive(DrawReq draw);

}

package com.kuaiyou.lucky.api.open;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.entity.Adsenselog;
import com.kuaiyou.lucky.req.AdsenseReq;
import com.kuaiyou.lucky.service.ActivityService;
import com.kuaiyou.lucky.service.AdsenseService;
import com.kuaiyou.lucky.service.AdsenselogService;

/**
 * <p>
 * 广告位信息 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-10-31
 */
@RestController
@RequestMapping("/lucky/adsense")
public class AdsenseController {

	@Autowired
	ActivityService activityService;

	@Autowired
	AdsenseService adsenseService;

	@Autowired
	AdsenselogService adsenselogService;

	@RequestMapping("grid")
	public Result grid(@RequestBody AdsenseReq adsense) {
		List<Adsenselog> grid = adsenselogService.getAd(adsense.getAsid());
		return Result.ok(grid);
	}
	
}

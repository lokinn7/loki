package com.kuaiyou.lucky.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.PageUtils;
import com.kuaiyou.lucky.res.RoundlogRes;
import com.kuaiyou.lucky.service.RoundlogService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@RestController
@RequestMapping("/admin/round")
public class AdminRoundController {

	@Autowired
	RoundlogService roundlogService;

	@RequestMapping("grid")
	public PageUtils grid(@RequestBody CommonReq req) {
		List<RoundlogRes> grid = roundlogService.baseList(req);
		int total = roundlogService.baseListCount(req);
		return new PageUtils(grid, total, req.getPage(), req.getSkip());
	}
}

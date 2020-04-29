package com.kuaiyou.lucky.api.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.entity.Adsense;
import com.kuaiyou.lucky.req.AdsenseReq;
import com.kuaiyou.lucky.service.AdsenseService;

/**
 * <p>
 * 广告位信息 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-10-31
 */
@RestController
@RequestMapping("/admin/adsense")
public class AdminAdsenseController {

	@Autowired
	AdsenseService adsenseService;

	@RequestMapping("select")
	public Result select(@RequestBody AdsenseReq adsense) {
		List<Adsense> grid = adsenseService.selectList(new EntityWrapper<>());
		return Result.ok(grid);
	}

	@RequestMapping("grid")
	public Result grid(@RequestBody AdsenseReq adsense) {
		Page<Adsense> page = new Page<>();
		page.setCurrent(adsense.getPage());
		page.setSize(adsense.getSkip());
		EntityWrapper<Adsense> wrapper = new EntityWrapper<>();
		Page<Adsense> grid = adsenseService.selectPage(page, wrapper);
		return Result.ok(grid);
	}

	@RequestMapping("add")
	public Result insert(@RequestBody Adsense adsense) {
		if (StringUtils.isBlank(adsense.getAsname())) {
			return Result.error("广告位名称为空");
		}
		adsense.setStatus(1);
		adsense.setCtime(new Date());
		boolean flag = adsenseService.insert(adsense);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequestMapping("edit")
	public Result edit(@RequestBody Adsense adsense) {
		boolean flag = adsenseService.updateById(adsense);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}
}

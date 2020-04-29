package com.kuaiyou.lucky.api.admin;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.entity.Adsense;
import com.kuaiyou.lucky.entity.Adsenselog;
import com.kuaiyou.lucky.entity.Adviser;
import com.kuaiyou.lucky.req.AdsenseReq;
import com.kuaiyou.lucky.service.AdsenseService;
import com.kuaiyou.lucky.service.AdsenselogService;
import com.kuaiyou.lucky.service.AdviserService;

/**
 * <p>
 * 广告位信息 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-10-31
 */
@RestController
@RequestMapping("/admin/adsenselog")
public class AdminAdsenseLogController {

	@Autowired
	AdviserService activityService;

	@Autowired
	AdsenseService adsenseService;

	@Autowired
	AdsenselogService adsenselogService;

	@RequestMapping("grid")
	public Result grid(@RequestBody AdsenseReq adsense) {
		Page<Adsenselog> page = new Page<>();
		page.setCurrent(adsense.getPage());
		page.setSize(adsense.getSkip());
		EntityWrapper<Adsenselog> wrapper = new EntityWrapper<>();
		if (adsense.getAcid() != null) {
			wrapper.eq(Adsenselog.ACID, adsense.getAcid());
		}
		if (StringUtils.isNotBlank(adsense.getAcname())) {
			wrapper.like(Adsenselog.ACNAME, adsense.getAcname());
		}
		if (StringUtils.isNotBlank(adsense.getAsname())) {
			wrapper.like(Adsenselog.ASNAME, adsense.getAsname());
		}
		Page<Adsenselog> grid = adsenselogService.selectPage(page, wrapper);
		return Result.ok(grid);
	}

	@RequestMapping("add")
	public Result insert(@RequestBody Adsenselog adsenselog) {
		if (StringUtils.isBlank(adsenselog.getLinkurl())) {
			return Result.error("跳转链接为空");
		}
		Adsense adcache = adsenseService.selectById(adsenselog.getAsid());
		Adviser activity = activityService.selectById(adsenselog.getAcid());
		if (adcache == null || activity == null) {
			return Result.error("广告位不存在或广告不存在");
		}
		{
			adsenselog.setCtime(new Date());
			adsenselog.setAcname(activity.getName());
			adsenselog.setAppkey(activity.getAppkey());
			adsenselog.setAsname(adcache.getAsname());
			adsenselog.setBannerurl(activity.getBannerurl());
			adsenselog.setInserturl(activity.getInserturl());
			adsenselog.setStatus(1);
			boolean flag = adsenselogService.insert(adsenselog);
			if (flag) {
				return Result.ok();
			}
		}
		return Result.error();
	}

	@RequestMapping("edit")
	public Result edit(@RequestBody Adsenselog adsense) {
		boolean flag = adsenselogService.updateById(adsense);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}
}

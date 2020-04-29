package com.kuaiyou.lucky.api.admin;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuaiyou.lucky.common.PageUtils;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.req.UserdrawReq;
import com.kuaiyou.lucky.res.ActiveFlagRes;
import com.kuaiyou.lucky.res.ActiveRes;
import com.kuaiyou.lucky.res.DaydataRes;
import com.kuaiyou.lucky.service.ActiveService;
import com.kuaiyou.lucky.service.UserdrawService;
import com.kuaiyou.lucky.service.WxuserService;

/**
 * <p>
 * 微信用户表 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@RestController
@RequestMapping("/admin/statis")
public class AdminStatisController {

	@Autowired
	WxuserService wxuserService;

	@Autowired
	UserdrawService userdrawService;

	@Autowired
	ActiveService activeService;

	@RequestMapping("destatis")
	public Result detailStatic(@RequestBody ActiveFlagRes req) {
		List<ActiveFlagRes> grid = activeService.getDetailStatic(req);
		return Result.ok(grid);
	}

	@RequestMapping("singstat")
	public PageUtils channelStat(@RequestBody ActiveRes req) {
		List<ActiveRes> grid = activeService.channelStat(req);
		int count = activeService.channelStatCount(req);
		return new PageUtils(grid, count, req.getPage(), req.getSkip());
	}

	/**
	 * <pre>
	 * 用户参与抽奖
	 * </pre>
	 */
	@RequestMapping("grid")
	public Result statisGrid(@RequestBody UserdrawReq item, HttpServletRequest request) {

		HashMap<String, DaydataRes> result = new HashMap<>();
		userdrawService.findCountjoin(item).forEach(new Consumer<DaydataRes>() {
			@Override
			public void accept(DaydataRes t) {
				String ctime = t.getCtime();
				DaydataRes res = result.get(ctime);
				if (res == null) {
					result.put(ctime, t);
				} else {
					res.setJoincount(t.getJoincount());
				}
			}
		});
		userdrawService.findOpencount(item).forEach(new Consumer<DaydataRes>() {
			@Override
			public void accept(DaydataRes t) {
				String ctime = t.getCtime();
				DaydataRes res = result.get(ctime);
				if (res == null) {
					result.put(ctime, t);
				} else {
					res.setOpencount(t.getOpencount());
				}
			}
		});
		userdrawService.findPubcount(item).forEach(new Consumer<DaydataRes>() {
			@Override
			public void accept(DaydataRes t) {
				String ctime = t.getCtime();
				DaydataRes res = result.get(ctime);
				if (res == null) {
					result.put(ctime, t);
				} else {
					res.setPubcount(t.getPubcount());
				}
			}
		});
		userdrawService.findUv(item).forEach(new Consumer<DaydataRes>() {
			@Override
			public void accept(DaydataRes t) {
				String ctime = t.getCtime();
				DaydataRes res = result.get(ctime);
				if (res == null) {
					result.put(ctime, t);
				} else {
					res.setNewuv(t.getNewuv());
				}
			}
		});
		return Result.ok(result.values());
	}
}

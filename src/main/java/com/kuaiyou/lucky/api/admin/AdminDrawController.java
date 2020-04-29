package com.kuaiyou.lucky.api.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.RecPageUtils;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.entity.Draw;
import com.kuaiyou.lucky.req.DrawReq;
import com.kuaiyou.lucky.res.AdminDrawRes;
import com.kuaiyou.lucky.res.DrawRes;
import com.kuaiyou.lucky.res.UserdrawRes;
import com.kuaiyou.lucky.service.DrawService;
import com.kuaiyou.lucky.service.UserdrawService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@RestController
@RequestMapping("/admin/draw")
public class AdminDrawController {

	@Autowired
	DrawService drawService;

	@Autowired
	UserdrawService userdrawService;

	/**
	 * <pre>
	 * 开奖
	 * 		1.根据抽奖id开奖
	 * 		2.返回中奖的用户集合
	 * </pre>
	 * 
	 */
	@RequestMapping("open")
	public Result openDraw(@RequestBody Draw drawReq, HttpServletRequest request) {
		Draw draw = drawService.selectById(drawReq.getId());
		if (draw != null && draw.getStatus() == 1) {
			return Result.ok("已开奖");
		}
		boolean openDraw = drawService.openDraw(draw);
		if (openDraw) {
			return Result.ok();
		}
		return Result.error();
	}

	/**
	 * <pre>
	 * 发起一次抽奖
	 * </pre>
	 * 
	 * @param req
	 * @return
	 */
	@PostMapping("add")
	public Result insert(@RequestBody DrawReq draw, HttpServletRequest request) {
		int size = draw.getPrizes().size();
		if (size <= 0) {
			return Result.error("请添加奖品");
		}
		boolean flag = drawService.admininsertWithActive(draw);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

	@PostMapping("edit")
	public Result edit(@RequestBody DrawReq draw) {
		Draw cache = drawService.selectById(draw.getId());
		if (cache == null) {
			return Result.error("无此记录");
		}
		List<UserdrawRes> uds = userdrawService.selectByDrawId(draw.getId());
		if (uds.size() > 0) {
			return Result.error("已有参与该抽奖的用户，请勿修改");
		}
		int size = draw.getPrizes().size();
		if (size <= 0) {
			return Result.error("请添加奖品");
		}
		Integer flag = drawService.updateWithSetting(draw);
		return Result.ok(flag);
	}

	@PostMapping("change")
	public Result change(@RequestBody DrawReq draw) {
		Draw cache = drawService.selectById(draw.getId());
		if (cache == null) {
			return Result.error("无此记录");
		}
		cache.setDeleted(draw.getDeleted());
		cache.setIsrec(draw.getIsrec());
		boolean flag = drawService.updateById(cache);
		return Result.ok(flag);
	}

	/**
	 * <pre>
	 * 按drawid获取抽奖详情
	 * </pre>
	 * 
	 * @param req
	 * @return
	 */
	@PostMapping("detail")
	public Result detail(@RequestBody CommonReq req) {
		DrawRes drawDetail = drawService.adminDrawDetail(req);
		return Result.ok(drawDetail);
	}

	/**
	 * <pre>
	 * 	1.首页
	 * 		a.推荐位的列表
	 * 			a)获取推荐序号大于1的按照序号排列，后台可以更改抽奖顺序
	 * 			b)抽取为公共方法
	 * 		b.普通抽奖的列表
	 * 
	 * </pre>
	 * 
	 * @param req
	 * @return
	 */
	@PostMapping("grid")
	public RecPageUtils grid(@RequestBody CommonReq req) {
		List<AdminDrawRes> grid = drawService.adminGrid(req);
		int total = drawService.adminGridCount(req);
		return new RecPageUtils(grid, total, req.getPage(), req.getSkip());
	}
}

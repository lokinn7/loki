package com.kuaiyou.lucky.api.open;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.PageUtils;
import com.kuaiyou.lucky.common.RecPageUtils;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.config.UploadFilePropertyConfig;
import com.kuaiyou.lucky.entity.Draw;
import com.kuaiyou.lucky.entity.Qrurl;
import com.kuaiyou.lucky.req.DrawReq;
import com.kuaiyou.lucky.req.QrReq;
import com.kuaiyou.lucky.res.DrawRes;
import com.kuaiyou.lucky.res.UserdrawRes;
import com.kuaiyou.lucky.service.DrawService;
import com.kuaiyou.lucky.service.FakeuserService;
import com.kuaiyou.lucky.service.QrurlService;
import com.kuaiyou.lucky.service.UserdrawService;
import com.kuaiyou.lucky.utils.ExpressUtil;
import com.kuaiyou.lucky.utils.GenQrImgUtil;
import com.kuaiyou.lucky.utils.SecurityUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@Slf4j
@RestController
@RequestMapping("/lucky/draw")
public class DrawController {

	@Autowired
	DrawService drawService;

	@Autowired
	UserdrawService userdrawService;

	@Autowired
	QrurlService qrurlService;

	@Autowired
	UploadFilePropertyConfig uploadFilePropertyConfig;

	@Autowired
	FakeuserService fakeuserService;

	@Autowired
	ExpressUtil expressUtil;

	/**
	 * <pre>
	 * 		1.根据抽奖id开奖
	 * 		2.返回中奖的用户集合
	 * </pre>
	 * 
	 * 开奖
	 */
	@RequestMapping("open")
	public Result openDraw(@RequestBody DrawReq drawReq, HttpServletRequest request) {
		Draw cache = drawService.selectById(drawReq.getId());
		if (cache != null && cache.getStatus() == 1) {
			return Result.ok("已开奖");
		}
		boolean openDraw = drawService.openDraw(cache);
		if (openDraw) {
			return Result.ok();
		}
		return Result.error();
	}

	/**
	 * <pre>
	 * 按drawid获取抽奖详情
	 * </pre>
	 * 
	 * @param req
	 * @return
	 */
	@PostMapping("add")
	public Result insert(@RequestBody DrawReq draw, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		draw.setUserid(userid);
		int size = draw.getPrizes().size();
		if (size <= 0) {
			return Result.error("请添加奖品");
		}
		Integer flag = drawService.insertWithSetting(draw);
		return Result.ok(flag);
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

	/**
	 * <pre>
	 * 按drawid获取抽奖详情
	 * </pre>
	 * 
	 * @param req
	 * @return
	 */
	@PostMapping("detail")
	public Result detail(@RequestBody CommonReq req, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		req.setUserid(userid);
		// limit20个已参与的
		DrawRes drawDetail = drawService.drawDetail(req);
		return Result.ok(drawDetail);
	}

	/**
	 * <pre>
	 * 按drawid 及prizelevel获取抽奖用户
	 * </pre>
	 * 
	 * @param req
	 * @return
	 */
	@PostMapping("bingodetail")
	public PageUtils bingoDetial(@RequestBody CommonReq req) {
		List<UserdrawRes> drawDetail = userdrawService.selectByDrawId2(req);
		int total = userdrawService.selectByDrawId2Count(req);
		if (total <= 0) {
			drawDetail = fakeuserService.selectFakeList(req);
			total = fakeuserService.selectFakeListCount(req);
		} else {
			drawDetail.addAll(fakeuserService.selectFakeList(req));
			total += fakeuserService.selectFakeListCount(req);
		}
		return new PageUtils(drawDetail, total, req.getPage(), req.getSkip());
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
	public RecPageUtils grid(@RequestBody CommonReq req, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		req.setUserid(userid);
		List<DrawRes> grid = drawService.baseGrid(req);
		List<DrawRes> recgrid = drawService.recGrid(req);
		Collections.shuffle(recgrid);
		int total = drawService.baseGridCount(req);
		return new RecPageUtils(grid, recgrid, total, req.getPage(), req.getSkip());
	}

	@PostMapping("activegrid")
	public PageUtils grid(@RequestBody CommonReq req) {
		List<DrawRes> grid = drawService.activeGrid(req);
		Integer count = drawService.activeGridCount(req);
		return new PageUtils(grid, count, req.getPage(), req.getSkip());
	}

	@RequestMapping("genqr")
	public Result genQr(@RequestBody QrReq req) {
		EntityWrapper<Qrurl> wrapper = new EntityWrapper<>();
		Integer dInteger = req.getDrawid();
		wrapper.eq(Qrurl.DRAWID, dInteger);
		Qrurl cache = qrurlService.selectOne(wrapper);
		if (cache != null) {
			return Result.ok(cache);
		} else {
			String sceneStr = req.getScene();
			String token = expressUtil.getToken();
			String qrpath = uploadFilePropertyConfig.getQrpath();
			String uri = uploadFilePropertyConfig.getUri();
			String folder = uploadFilePropertyConfig.getFolder2();
			String filename = sceneStr + ".png";
			String fpath = qrpath + filename;
			log.info(String.format("generation qrimg , scenstr is:%s , token is:%s ,fpath is:%s", sceneStr, token,
					fpath));
			GenQrImgUtil.getminiqrQr(sceneStr, token, fpath);
			Qrurl qrurl = new Qrurl(uri + folder + "/" + filename, new Date(), sceneStr, req.getDrawid());
			boolean flag = qrurlService.insert(qrurl);
			if (flag) {
				return Result.ok(qrurl);
			}
			return Result.error("error");
		}
	}

	@RequestMapping("gengoodsqr")
	public Result gengoodsQr(@RequestBody QrReq req) {
		EntityWrapper<Qrurl> wrapper = new EntityWrapper<>();
		wrapper.eq(Qrurl.SCENE, req.getScene());
		Qrurl cache = qrurlService.selectOne(wrapper);
		if (cache != null) {
			return Result.ok(cache);
		} else {
			String sceneStr = req.getScene();
			String token = expressUtil.getToken();
			String qrpath = uploadFilePropertyConfig.getQrpath();
			String uri = uploadFilePropertyConfig.getUri();
			String folder = uploadFilePropertyConfig.getFolder2();
			String filename = sceneStr + ".png";
			String fpath = qrpath + filename;
			log.info(String.format("generation qrimg , scenstr is:%s , token is:%s ,fpath is:%s", sceneStr, token,
					fpath));
			GenQrImgUtil.getminiqrQr(sceneStr, token, fpath, req.getPage());
			Qrurl qrurl = new Qrurl(uri + folder + "/" + filename, new Date(), sceneStr, req.getDrawid(),
					req.getGoodsid());
			boolean flag = qrurlService.insert(qrurl);
			if (flag) {
				return Result.ok(qrurl);
			}
			return Result.error("error");
		}
	}

}

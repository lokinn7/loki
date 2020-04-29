package com.kuaiyou.lucky.api.open;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.entity.Coins;
import com.kuaiyou.lucky.entity.Goods;
import com.kuaiyou.lucky.entity.Wxuser;
import com.kuaiyou.lucky.enums.CoinTypeEnum;
import com.kuaiyou.lucky.res.DrawRes;
import com.kuaiyou.lucky.service.CoinsService;
import com.kuaiyou.lucky.service.DrawService;
import com.kuaiyou.lucky.service.GoodsService;
import com.kuaiyou.lucky.service.UserdrawService;
import com.kuaiyou.lucky.service.WxuserService;
import com.kuaiyou.lucky.utils.DateUtil;
import com.kuaiyou.lucky.utils.SecurityUtil;

/**
 * <p>
 * 微信用户表 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@RestController
@RequestMapping("/lucky/wxuser")
public class WxuserController {

	@Autowired
	DrawService drawService;

	@Autowired
	UserdrawService userdrawService;

	@Autowired
	WxuserService wxuserService;

	@Autowired
	GoodsService goodsService;

	@Autowired
	CoinsService coinsService;

	@RequestMapping("mine")
	public Result mine(@RequestBody CommonReq req, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		HashMap<String, Object> result = new HashMap<>();
		int joins = userdrawService.selectJoinListCount(userid);
		int bingos = userdrawService.selectBingoListCount(userid);
		int pubs = userdrawService.selectPubListCount(userid);
		result.put("publish", pubs);
		result.put("join", joins);
		result.put("bingo", bingos);
		return Result.ok(result);
	}

	@RequestMapping("info")
	public Result info(HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		Wxuser user = wxuserService.selectByUserid(userid);
		if (user != null) {
			Wrapper<Goods> wrapper = new EntityWrapper<>();
			wrapper.lt(Goods.AMOUNT, user.getCoins());
			int count = goodsService.selectCount(wrapper);
			Wrapper<Coins> coinswrapper = new EntityWrapper<>();
			coinswrapper.like(Coins.CTIME, DateUtil.format(new Date(), DateUtil.YMD))
					.eq(Coins.CTYPE, CoinTypeEnum.SIGN.getCode()).eq(Coins.USERID, userid);
			int signcount = coinsService.selectCount(coinswrapper);
			return Result.ok_bot(user, count > 0, signcount > 0);
		}
		return Result.error();
	}

	@RequestMapping("tab")
	public Result type(@RequestBody CommonReq req, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		int minetype = req.getMinetype();
		req.setUserid(userid);
		if (minetype == 0) {
			// 发布
			List<DrawRes> selectList = userdrawService.selectPubList(req);
			Map<String, Object> result = new HashMap<>();
			Map<Integer, List<DrawRes>> collect = selectList.stream()
					.collect(Collectors.groupingBy(DrawRes::getStatus));
			collect.forEach(new BiConsumer<Integer, List<DrawRes>>() {

				@Override
				public void accept(Integer t, List<DrawRes> u) {
					if (t > 0) {
						result.put("open", u);
					} else {
						result.put("close", u);
					}
				}

			});
			return Result.ok(result);
		} else if (minetype == 1) {
			// 参与
			List<DrawRes> joins = userdrawService.selectJoinList(req);
			Map<String, Object> result = new HashMap<>();
			Map<Integer, List<DrawRes>> collect = joins.stream().collect(Collectors.groupingBy(DrawRes::getStatus));
			collect.forEach(new BiConsumer<Integer, List<DrawRes>>() {

				@Override
				public void accept(Integer t, List<DrawRes> u) {
					if (t > 0) {
						result.put("open", u);
					} else {
						result.put("close", u);
					}
				}

			});
			return Result.ok(result);
		} else if (minetype == 2) {
			// 中奖的
			List<DrawRes> bingos = userdrawService.selectBingoList(req);
			Map<String, Object> result = new HashMap<>();
			Map<Integer, List<DrawRes>> collect = bingos.stream().collect(Collectors.groupingBy(DrawRes::getStatus));
			collect.forEach(new BiConsumer<Integer, List<DrawRes>>() {

				@Override
				public void accept(Integer t, List<DrawRes> u) {
					if (t > 0) {
						result.put("open", u);
					} else {
						result.put("close", u);
					}
				}

			});
			return Result.ok(result);
		}
		return Result.ok();
	}
}

package com.kuaiyou.lucky.api.open;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.PageUtils;
import com.kuaiyou.lucky.common.Project;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.compnent.CoinsratoCompnent;
import com.kuaiyou.lucky.entity.Coins;
import com.kuaiyou.lucky.entity.Prize;
import com.kuaiyou.lucky.entity.Roundlog;
import com.kuaiyou.lucky.entity.Wxuser;
import com.kuaiyou.lucky.enums.CoinTypeEnum;
import com.kuaiyou.lucky.listenner.JedisExpiredListener;
import com.kuaiyou.lucky.res.RoundlogRes;
import com.kuaiyou.lucky.service.CoinsService;
import com.kuaiyou.lucky.service.PrizeService;
import com.kuaiyou.lucky.service.RoundlogService;
import com.kuaiyou.lucky.service.WxuserService;
import com.kuaiyou.lucky.utils.DateUtil;
import com.kuaiyou.lucky.utils.FakeNameUtil;
import com.kuaiyou.lucky.utils.SecurityUtil;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-08-16
 */
@RestController
@RequestMapping("/lucky/round")
public class RoundController {

	@Autowired
	WxuserService wxuserService;

	@Autowired
	CoinsratoCompnent coinsratoCompnent;

	@Autowired
	Project project;

	@Autowired
	RoundlogService roundlogService;

	@Autowired
	PrizeService prizeService;

	@Autowired
	CoinsService coinsService;

	@Autowired
	StringRedisTemplate redisTemplate;

	/**
	 * 兑奖上报
	 * 
	 * @param coins
	 * @param request
	 * @return
	 */
	@RequestMapping("cvround")
	public Result inserTemp(@RequestBody Roundlog round, HttpServletRequest request) {
		Roundlog log = roundlogService.selectById(round.getId());
		if (log == null) {
			return Result.error("超过兑奖时间或已经兑换");
		}
		Date now = new Date();
		Prize prize = prizeService.selectById(log.getPrizeid());
		Date add = DateUtil.add(log.getCtime(), 3);
		if (now.getTime() > add.getTime()) {
			log.setStatus(1);
			roundlogService.updateById(log);
			return Result.ok("超过兑奖时间");
		}
		if (log.getRtype().equals(3)) {
			String userid = SecurityUtil.getUserid(request);
			Coins coin = new Coins();
			coin.setCtype(CoinTypeEnum.CONVROUNDFEE.getCode());
			coin.setCtime(now);
			coin.setNotes(String.format(CoinTypeEnum.CONVROUNDFEE.getText(), userid));
			coin.setUserid(userid);
			coin.setAmount(prize.getAmount());
			if (prize.getAmount() != null) {
				coinsService.insertTemp(coin);
			}
		}
		log.setStatus(1);
		boolean flag = roundlogService.updateById(log);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequestMapping("turn")
	public Result trun(@RequestBody Roundlog roundlog, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		String cache = redisTemplate.opsForValue().get(JedisExpiredListener.ROUND_LIMIT + userid);
		int tcount = 0;
		if (StringUtils.isNotBlank(cache)) {
			tcount = Integer.valueOf(cache);
		}
		if (tcount >= 10) {
			return Result.error("次数已用尽");
		}
		Wxuser user = wxuserService.selectByUserid(userid);
		Integer roundcoin = project.getRoundcoin();
		if (user.getCoins() >= roundcoin) {
			Roundlog prize = coinsratoCompnent.getPrize(userid);
			Wxuser uWxuser = wxuserService.selectByUserid(userid);
			return Result.ok_turn(prize, uWxuser);
		}
		return Result.error("金币不足");
	}

	@RequestMapping("prizelist")
	public Result prizelist() {
		HashMap<String, Object> result = new HashMap<>();
		Wrapper<Prize> wrapper = new EntityWrapper<>();
		wrapper.eq(Prize.ATYPE, 2).eq(Prize.PUB, 1);
		List<Prize> selectList = prizeService.selectList(wrapper);
		result.put("prizelist", selectList);
		result.put("roundcoin", project.getRoundcoin());
		return Result.ok(result);
	}

	@RequestMapping("roundlist")
	public PageUtils roundlist(@RequestBody CommonReq roundlog, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		roundlog.setUserid(userid);
		List<RoundlogRes> grid = roundlogService.selectByUserid(roundlog);
		int total = roundlogService.selectByUseridCount(roundlog);
		return new PageUtils(grid, total, roundlog.getPage(), roundlog.getSkip());
	}

	@RequestMapping("info")
	public Result roundinfo() {
		List<RoundInfo> grid = new ArrayList<>();
		Wrapper<Prize> wrapper = new EntityWrapper<>();
		wrapper.eq(Prize.ATYPE, 2).eq(Prize.PUB, 1).in(Prize.TYPE, new Integer[] { 1, 2 });
		List<Prize> selectList = prizeService.selectList(wrapper);
		for (int i = 0; i < 4; i++) {
			for (Prize prize : selectList) {
				String fakename = FakeNameUtil.getRandomName();
				RoundInfo roundInfo = new RoundInfo();
				roundInfo.name = fakename;
				roundInfo.prize = prize.getName();
				grid.add(roundInfo);
			}
		}
		return Result.ok(grid);
	}

	class RoundInfo {
		private String name;
		private String prize;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPrize() {
			return prize;
		}

		public void setPrize(String prize) {
			this.prize = prize;
		}

	}
}

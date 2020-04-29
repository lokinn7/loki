package com.kuaiyou.lucky.compnent;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kuaiyou.lucky.common.Project;
import com.kuaiyou.lucky.entity.Coins;
import com.kuaiyou.lucky.entity.Coinsrato;
import com.kuaiyou.lucky.entity.Prize;
import com.kuaiyou.lucky.entity.Roundlog;
import com.kuaiyou.lucky.enums.CoinTypeEnum;
import com.kuaiyou.lucky.listenner.JedisExpiredListener;
import com.kuaiyou.lucky.service.CoinsService;
import com.kuaiyou.lucky.service.CoinsratoService;
import com.kuaiyou.lucky.service.PrizeService;
import com.kuaiyou.lucky.service.RoundlogService;
import com.kuaiyou.lucky.utils.DateUtil;
import com.kuaiyou.lucky.utils.RatoUtil;

@Component
public class CoinsratoCompnent {

	@Autowired
	CoinsratoService coinsratoService;

	@Autowired
	CoinsService coinsService;

	@Autowired
	Project project;

	@Autowired
	StringRedisTemplate redisTemplate;

	@Autowired
	PrizeService prizeService;

	@Autowired
	RoundlogService roundlogService;

	/**
	 * <pre>
	 * 		0查找当前用户今日获取积分的情况
	 * 		1给当前用户生成一个金币数
	 * 		2加金币 over
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public Integer getCoins(String userid) {
		String cache = redisTemplate.opsForValue().get(JedisExpiredListener.COIN_LIMIT + userid);
		int tcount = 0;
		if (StringUtils.isNotBlank(cache)) {
			tcount = Integer.valueOf(cache);
		}
		if (tcount >= project.getCoinlimit()) {
			return null;
		} else {
			List<Coinsrato> list = coinsratoService.getRatos();
			Collections.sort(list);
			List<Integer> separates = list.stream().map(e -> e.getAmount()).collect(Collectors.toList());
			List<Integer> percents = list.stream().map(e -> e.getRato()).collect(Collectors.toList());
			int max = separates.get(separates.size() - 1);
			List<Integer> subList = separates.subList(0, separates.size() - 1);
			int coins = RatoUtil.produceRangeNumber(0, max, subList, percents);
			Date now = new Date();
			Coins coin = new Coins();
			coin.setCtype(CoinTypeEnum.RATOCOIN.getCode());
			coin.setCtime(now);
			coin.setNotes(String.format(CoinTypeEnum.RATOCOIN.getText(), userid));
			coin.setAmount(coins);
			coin.setUserid(userid);
			boolean flag = coinsService.insertTemp(coin);
			if (flag) {
				redisTemplate.opsForValue().increment(JedisExpiredListener.COIN_LIMIT + userid);
				redisTemplate.expire(JedisExpiredListener.COIN_LIMIT + userid,
						DateUtil.endOfDay().getTime() - now.getTime(), TimeUnit.MILLISECONDS);
			}
			return coins;
		}
	}

	/**
	 * <pre>
	 * 		1.获取奖品的概率，以概率定获取的物品
	 * </pre>
	 */
	@Transactional(rollbackFor = IllegalArgumentException.class)
	public Roundlog getPrize(String userid) {
		List<Prize> list = prizeService.getRatos(userid);
		List<Double> separates = list.stream().map(e -> (double) e.getRato() / 100).collect(Collectors.toList());
		int index = RatoUtil.lottery(separates);
		Prize prize = list.get(index);
		if (prize != null) {
			Roundlog roundlog = new Roundlog();
			Date now = new Date();
			roundlog.setCtime(now);
			roundlog.setPrizeid(prize.getId());
			roundlog.setStatus(0);
			roundlog.setUserid(userid);
			roundlog.setRtype(prize.getType());
			if (!prize.getType().equals(4)) {
				roundlogService.insert(roundlog);
			}
			// 扣除金币
			Coins coins = new Coins();
			coins.setAmount(project.getRoundcoin());
			coins.setUserid(userid);
			coins.setCtype(CoinTypeEnum.ROUNDFEE.getCode());
			boolean updateTemp = coinsService.updateTemp(coins);
			if (!updateTemp) {
				throw new IllegalArgumentException("update user coins happen exception.");
			}
			redisTemplate.opsForValue().increment(JedisExpiredListener.ROUND_LIMIT + userid);
			redisTemplate.expire(JedisExpiredListener.ROUND_LIMIT + userid,
					DateUtil.endOfDay().getTime() - now.getTime(), TimeUnit.MILLISECONDS);
			return roundlog;
		}
		Prize cache = prizeService.selectByCtype(4);
		Roundlog roundlog = new Roundlog();
		roundlog.setUserid(userid);
		roundlog.setPrizeid(cache.getId());
		roundlog.setRtype(2);
		return roundlog;
	}

}

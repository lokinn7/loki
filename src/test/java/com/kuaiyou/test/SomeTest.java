package com.kuaiyou.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.compnent.CoinsratoCompnent;
import com.kuaiyou.lucky.compnent.JDCompnent;
import com.kuaiyou.lucky.entity.Draw;
import com.kuaiyou.lucky.entity.Goods;
import com.kuaiyou.lucky.entity.Userdraw;
import com.kuaiyou.lucky.enums.CoinTypeEnum;
import com.kuaiyou.lucky.job.AdDataJob;
import com.kuaiyou.lucky.listenner.JedisExpiredListener;
import com.kuaiyou.lucky.res.DrawRes;
import com.kuaiyou.lucky.res.UserdrawRes;
import com.kuaiyou.lucky.service.DrawService;
import com.kuaiyou.lucky.service.FakeuserService;
import com.kuaiyou.lucky.service.UserdrawService;
import com.kuaiyou.lucky.service.WxuserService;

import jd.union.open.goods.query.response.GoodsResp;

public class SomeTest extends BaseTest {

	@Autowired
	DrawService drawService;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	WxuserService wxuserService;

	@Autowired
	FakeuserService fakeuserService;

	@Autowired
	UserdrawService userdrawService;

	@Autowired
	CoinsratoCompnent ratoCompnent;

	@Autowired
	JDCompnent JDCompnent;

	@Autowired
	AdDataJob adDataJob;

	@Test
	public void test_0() {
		List<DrawRes> list = drawService.baseGrid(null);
		System.out.println(list);

	}

	@Test
	public void test_1() {
		stringRedisTemplate.opsForValue().set(JedisExpiredListener.KEY_PATTERN + "12", "sdf", 20, TimeUnit.SECONDS);
	}

	@Test
	public void test_2() {
		for (;;) {
			List<UserdrawRes> randFake = wxuserService.randFake(15, 5, 1);
			fakeuserService.insertFakeBatch(randFake, 5, 1);
			if (randFake.size() > 18) {
				break;
			}
		}

	}

	@Test
	public void test_3() {
		Draw entity = new Draw(1);
		entity.setName("helloworld");
		boolean flag = drawService.insert(entity);
		System.out.println(flag);
	}

	@Test
	public void test_4() {
		List<Userdraw> selectJoinsList = userdrawService.selectJoinsList(6);
		System.out.println(selectJoinsList.size());
	}

	@Test
	public void test_5() {
		ratoCompnent.getCoins("5d5ca1f383c5a170d6f5d3cc");
	}

	@Test
	public void test_6() {
		String skuid = "";
		Goods goods = new Goods();
		goods.setSkuid(57756811962L);
		JDCompnent.insertGood(goods);
	}

	@Test
	public void test_7() {
		Long increment = stringRedisTemplate.opsForValue().increment(JedisExpiredListener.COIN_LIMIT + "3333");
		System.out.println(increment);
	}

	@Test
	public void test_8() {
		GoodsResp goodsResp = JDCompnent.getGoodsInfoBySkuid(7788763L);
		System.out.println(JSON.toJSONString(goodsResp));
	}

	@Test
	public void test_9() {
		List<Draw> statu1 = drawService.selectList(new EntityWrapper<Draw>().eq(Draw.STATUS, 0).eq(Draw.DELETED, 0));
		System.out.println(statu1.size());
		for (Draw draw : statu1) {
			CommonReq req = new CommonReq();
			req.setDrawid(draw.getId() + "");
			DrawRes drawDetail = drawService.drawDetail(req);
			List<UserdrawRes> fakeusers = new ArrayList<>();
			if (fakeusers.size() <= 0) {
				fakeusers = wxuserService.randFake(5000, drawDetail.getId(), 0);
				fakeuserService.insertFakeBatch(fakeusers, drawDetail.getId(), 0);
			}
		}
	}

	/**
	 * 添加假的用户,生成假用户的sql
	 */
	@Test
	public void test_10() {
		List<Draw> statu1 = drawService.selectList(new EntityWrapper<Draw>().in(Draw.ID,
				new Integer[] { 465, 466, 467, 468, 469, 470, 471, 472, 473, 474, 475, 476 }));
		// List<Draw> statu1 = drawService.selectList(new
		// EntityWrapper<Draw>().eq(Draw.STATUS, 0).eq(Draw.DELETED, 0));
		System.out.println(statu1.size());
		StringBuilder sb = new StringBuilder();
		for (Draw draw : statu1) {
			CommonReq req = new CommonReq();
			req.setDrawid(draw.getId() + "");
			DrawRes drawDetail = drawService.drawDetail(req);
			List<UserdrawRes> fakeusers = new ArrayList<>();
			if (fakeusers.size() <= 0) {
				// 生成的数量
				fakeusers = wxuserService.randFake(2000, drawDetail.getId(), 0);
				for (UserdrawRes res : fakeusers) {
					sb.append("('" + res.getNickname() + "','" + res.getAvatarurl() + "'," + "0," + res.getDrawid()
							+ "),");
				}
			}
		}
		// INSERT INTO t_luck_fakeuser (nickname,avatarurl,prizelevel,drawid) VALUES
		System.out.println(sb.toString());
	}

	@Test
	public void test_11() {
		String start = "2019-11-22";
		String end = "2019-11-22 23:59:59";
		String today = "2019-11-22";
		adDataJob.excuteData(CoinTypeEnum.ADCLICK.getCode(), start, end, today);
		adDataJob.excuteData(CoinTypeEnum.ADJUMP.getCode(), start, end, today);
		adDataJob.excuteData(CoinTypeEnum.ADSHOW.getCode(), start, end, today);
	}
}

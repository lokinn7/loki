package com.kuaiyou.lucky.api.open;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuaiyou.lucky.common.PageUtils;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.compnent.JDCompnent;
import com.kuaiyou.lucky.entity.Active;
import com.kuaiyou.lucky.entity.Convert;
import com.kuaiyou.lucky.entity.Goods;
import com.kuaiyou.lucky.entity.Wxuser;
import com.kuaiyou.lucky.entity.jdentity.GoodInfoRes;
import com.kuaiyou.lucky.enums.CoinTypeEnum;
import com.kuaiyou.lucky.req.GoodsReq;
import com.kuaiyou.lucky.res.GoodsRes;
import com.kuaiyou.lucky.service.ActiveService;
import com.kuaiyou.lucky.service.ConvertService;
import com.kuaiyou.lucky.service.GoodsService;
import com.kuaiyou.lucky.service.WxuserService;
import com.kuaiyou.lucky.utils.SecurityUtil;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-10-17
 */
@RestController
@RequestMapping("/lucky/goods")
public class GoodsController {
	@Autowired
	GoodsService goodsService;

	@Autowired
	WxuserService wxuserService;

	@Autowired
	ConvertService convertService;

	@Autowired
	JDCompnent jDCompnent;

	@Autowired
	ActiveService activeService;

	@RequestMapping("click")
	public Result click(@RequestBody Active active, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		active.setCtime(new Date());
		active.setCtype(CoinTypeEnum.GOODSCLICK.getCode());
		active.setNotes(String.format(CoinTypeEnum.GOODSCLICK.getText(), userid));
		active.setUserid(userid);
		boolean flag = activeService.insert(active);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequestMapping("grid_0")
	public Result grid(@RequestBody GoodsReq goods, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		List<GoodInfoRes> detail = jDCompnent.detail(goods.getType(), userid);
		return Result.ok(detail);
	}

	@RequestMapping("find")
	public Result findyId(@RequestBody GoodsReq goods) {
		Goods goods2 = goodsService.selectBySkuid(goods.getSkuid());
		return Result.ok(goods2);
	}

	@RequestMapping("grid")
	public PageUtils grid_0(@RequestBody GoodsReq goods, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		goods.setUserid(userid);
		List<GoodsRes> detail = goodsService.goodList(goods);
		int count = goodsService.goodListCount(goods);
		return new PageUtils(detail, count, goods.getPage(), goods.getSkip());
	}

	@RequestMapping("convert")
	public Result convert(@RequestBody Convert convert, HttpServletRequest request) {
		/**
		 * 1.添加兑换记录 2.添加兑换金币记录并扣除用户金币
		 */
		String userid = SecurityUtil.getUserid(request);
		Wxuser user = wxuserService.selectByUserid(userid);
		Goods goods = goodsService.selectBySkuid(convert.getSkuid());
		List<Convert> joins = convertService.selectByUseridAndSkuid(convert.getSkuid(), userid);
		if (joins.size() > 0) {
			return Result.ok("已领过当前购物券");
		}
		if (user != null && goods != null) {
			if (user.getCoins() < goods.getAmount()) {
				return Result.error("金币不足");
			}
			convert.setUserid(userid);
			boolean flag = convertService.saveItemWithUserCoins(convert, goods);
			if (flag) {
				return Result.ok();
			}
		}
		return Result.error("商品已经下架");
	}

}

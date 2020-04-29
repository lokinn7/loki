package com.kuaiyou.lucky.api.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.compnent.JDCompnent;
import com.kuaiyou.lucky.entity.Goods;
import com.kuaiyou.lucky.req.GoodsReq;
import com.kuaiyou.lucky.service.GoodsService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-10-17
 */
@RestController
@RequestMapping("/admin/goods")
public class AdminGoodsController {

	@Autowired
	GoodsService goodsService;

	@Autowired
	JDCompnent jDCompnent;

	@RequestMapping("grid")
	public Result grid(@RequestBody GoodsReq goods) {
		EntityWrapper<Goods> wrapper = new EntityWrapper<>();
		if (goods.getType() != null) {
			wrapper.eq(Goods.TYPE, goods.getType());
		}
		if (goods.getGtype() != null) {
			wrapper.eq(Goods.GTYPE, goods.getGtype());
		}
		Page<Goods> page = new Page<>(goods.getPage(), goods.getSkip());
		Page<Goods> result = goodsService.selectPage(page, wrapper);
		return Result.ok(result);
	}

	@RequestMapping("addbatch_0")
	public Result addbatch(@RequestBody GoodsReq goods) {
		List<Goods> adds = new ArrayList<>();
		List<Long> skuids = goods.getSkuids();
		if (skuids != null && skuids.size() > 0) {
			Date now = new Date();
			for (Long skuid : skuids) {
				Goods addgood = new Goods();
				addgood.setCtime(now);
				addgood.setSkuid(skuid);
				addgood.setDeleted(0);
				addgood.setType(goods.getType());
				adds.add(addgood);
			}
		}
		if (adds.size() > 0) {
			boolean flag = goodsService.insertOrUpdateBatch(adds);
			if (flag) {
				return Result.ok();
			}
		}
		return Result.error();
	}

	@RequestMapping("addbatch")
	public Result addbatch_0(@RequestBody GoodsReq goods) {
		List<Goods> temps = goods.getGoods();
		if (temps != null && temps.size() > 0) {
			boolean flag = jDCompnent.insertGoods(temps);
			if (flag) {
				return Result.ok();
			}
		}
		return Result.error();
	}

	@RequestMapping("add")
	public Result add(@RequestBody Goods goods) {
		goods.setCtime(new Date());
		boolean flag = jDCompnent.insertGood(goods);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequestMapping("edit")
	public Result edit(@RequestBody Goods goods) {
		Goods cache = goodsService.selectById(goods.getId());
		if (cache != null) {
			cache.setAmount(goods.getAmount());
			cache.setShareimg(goods.getShareimg());
			boolean flag = goodsService.updateById(cache);
			if (flag) {
				return Result.ok();
			}
		}
		return Result.error();
	}

	@RequestMapping("delbatch")
	public Result delBatch(@RequestBody GoodsReq goodsReq) {
		List<Long> skuids = goodsReq.getSkuids();
		if (skuids != null && skuids.size() > 0) {
			EntityWrapper<Goods> wrapper = new EntityWrapper<>();
			wrapper.in(Goods.SKUID, skuids);
			boolean flag = goodsService.delete(wrapper);
			if (flag) {
				return Result.ok();
			}
		}
		return Result.error();
	}
}

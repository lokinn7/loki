package com.kuaiyou.lucky.api.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.entity.Prize;
import com.kuaiyou.lucky.service.PrizeService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@RestController
@RequestMapping("/admin/prize")
public class AdminPrizeController {

	@Autowired
	PrizeService prizeService;

	@RequestMapping("grid")
	public Result grid(@RequestBody CommonReq req) {
		EntityWrapper<Prize> wrapper = new EntityWrapper<>();
		Page<Prize> page = new Page<>(req.getPage(), req.getSkip());
		Page<Prize> selectPage = prizeService.selectPage(page, wrapper);
		return Result.ok(selectPage);
	}

	@RequestMapping("roundlist")
	public Result roundlist(@RequestBody CommonReq req) {
		HashMap<String, Object> result = new HashMap<>();
		EntityWrapper<Prize> wrapper = new EntityWrapper<>();
		wrapper.eq(Prize.PUB, 1).eq(Prize.ATYPE, 2);
		List<Prize> nowlist = prizeService.selectList(wrapper);
		EntityWrapper<Prize> wrapper1 = new EntityWrapper<>();
		wrapper1.eq(Prize.ATYPE, 2).eq(Prize.STATUS, 1);
		List<Prize> prelist = prizeService.selectList(wrapper1);
		result.put("nowlist", nowlist);
		result.put("prelist", prelist);
		return Result.ok(result);
	}

	@RequestMapping("select")
	public Result select(@RequestBody CommonReq req) {
		EntityWrapper<Prize> wrapper = new EntityWrapper<>();
		List<Prize> select = prizeService.selectList(wrapper);
		return Result.ok(select);
	}

	@RequestMapping("add")
	public Result add(@RequestBody Prize req) {
		{
			req.setCtime(new Date());
		}
		boolean insert = prizeService.insert(req);
		if (insert) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequestMapping("addedit")
	public Result addBatch(@RequestBody CommonReq req) {
		List<Prize> prizeratos = req.getPrizeratos();
		if (checkRato(req.getPrizeratos())) {
			for (Prize prize : prizeratos) {
				prize.setAtype(2);
				prize.setPub(1);
				prize.setUtime(new Date());
			}
			prizeService.updateALL();
			prizeService.insertOrUpdateBatch(prizeratos);
			return Result.ok();
		}
		return Result.error("奖品概率之和不为100");
	}

	@RequestMapping("edit")
	public Result edit(@RequestBody Prize req) {
		boolean insert = prizeService.updateById(req);
		if (insert) {
			return Result.ok();
		}
		return Result.error();
	}

	public boolean checkRato() {
		EntityWrapper<Prize> wrapper = new EntityWrapper<>();
		wrapper.eq(Prize.STATUS, 1).eq(Prize.ATYPE, 2);
		List<Prize> selectList = prizeService.selectList(wrapper);
		int sum = 0;
		for (Prize prize : selectList) {
			sum += prize.getRato();
		}
		if (sum != 100) {
			return false;
		}
		return true;
	}

	public boolean checkRato(List<Prize> prizeratos) {
		int sum = 0;
		for (Prize prize : prizeratos) {
			sum += prize.getRato();
		}
		if (sum != 100) {
			return false;
		}
		return true;
	}
}

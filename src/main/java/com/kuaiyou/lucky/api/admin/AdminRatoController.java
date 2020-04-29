package com.kuaiyou.lucky.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.entity.Coinsrato;
import com.kuaiyou.lucky.req.RatoReq;
import com.kuaiyou.lucky.service.CoinsratoService;

/**
 * <p>
 * 微信用户表 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@RestController
@RequestMapping("/admin/rato")
public class AdminRatoController {

	@Autowired
	CoinsratoService ratoService;

	@RequestMapping("grid")
	public Result grid(@RequestBody CommonReq req) {
		EntityWrapper<Coinsrato> wrapper = new EntityWrapper<>();
		Page<Coinsrato> page = new Page<>(req.getPage(), req.getSkip());
		Page<Coinsrato> selectPage = ratoService.selectPage(page, wrapper);
		return Result.ok(selectPage);
	}

	@RequestMapping("edit")
	public Result edit(@RequestBody RatoReq req) {
		List<Coinsrato> coinsratos = req.getCoinsratos();
		int rato = 0;
		for (Coinsrato coinsrato : coinsratos) {
			rato += coinsrato.getRato();
		}
		if (rato != 100) {
			return Result.error("所有概率百分比加起来必须为100");
		}
		boolean flag = ratoService.insertOrUpdateBatch(coinsratos);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

}

package com.kuaiyou.lucky.api.open;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.entity.Active;
import com.kuaiyou.lucky.enums.CoinTypeEnum;
import com.kuaiyou.lucky.req.ActiveReq;
import com.kuaiyou.lucky.service.ActiveService;
import com.kuaiyou.lucky.utils.SecurityUtil;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-09-09
 */
@RestController
@RequestMapping("/lucky/active")
public class ActiveController {

	@Autowired
	ActiveService activeService;

	// ************** 详情页数据上报 start**********************
	@RequestMapping("declick")
	public Result detailclick(@RequestBody Active active, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		{
			active.setUserid(userid);
			active.setCtype(CoinTypeEnum.DETAILCLICK.getCode());
			active.setCtime(new Date());
			active.setNotes(String.format(CoinTypeEnum.DETAILCLICK.getText(), userid));
		}
		boolean flag = activeService.insert(active);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequestMapping("dehome")
	public Result detailjump(@RequestBody Active active, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		{
			active.setUserid(userid);
			active.setCtype(CoinTypeEnum.DETAILHOME.getCode());
			active.setCtime(new Date());
			active.setNotes(String.format(CoinTypeEnum.DETAILHOME.getText(), userid));
		}
		boolean flag = activeService.insert(active);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequestMapping("deshow")
	public Result detailshow(@RequestBody Active active, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		{
			active.setUserid(userid);
			active.setCtype(CoinTypeEnum.DETAILSHOW.getCode());
			active.setCtime(new Date());
			active.setNotes(String.format(CoinTypeEnum.DETAILSHOW.getText(), userid));
		}
		boolean flag = activeService.insert(active);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}
	// ************** 详情页数据上报 end **************************

	// ************** 广告数据上报 start**********************
	@RequestMapping("adclick")
	public Result adclick(@RequestBody Active active, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		{
			active.setUserid(userid);
			active.setCtype(CoinTypeEnum.ADCLICK.getCode());
			active.setCtime(new Date());
			active.setNotes(String.format(CoinTypeEnum.ADCLICK.getText(), userid));
		}
		boolean flag = activeService.insert(active);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequestMapping("adjump")
	public Result adjump(@RequestBody Active active, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		{
			active.setUserid(userid);
			active.setCtype(CoinTypeEnum.ADJUMP.getCode());
			active.setCtime(new Date());
			active.setNotes(String.format(CoinTypeEnum.ADJUMP.getText(), userid));
		}
		boolean flag = activeService.insert(active);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequestMapping("adshow")
	public Result adshow(@RequestBody Active active, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		{
			active.setUserid(userid);
			active.setCtype(CoinTypeEnum.ADSHOW.getCode());
			active.setCtime(new Date());
			active.setNotes(String.format(CoinTypeEnum.ADSHOW.getText(), userid));
		}
		boolean flag = activeService.insert(active);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}
	// ************** 广告数据上报 end **************************

	@RequestMapping("click")
	public Result add(@RequestBody Active active, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		{
			active.setUserid(userid);
			active.setCtype(CoinTypeEnum.CLICK.getCode());
			active.setCtime(new Date());
			active.setNotes(String.format(CoinTypeEnum.CLICK.getText(), userid));
		}
		boolean flag = activeService.insert(active);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

	/**
	 * 0.分享 1.更新用户积分 2.添加用户积分记录 3.添加用户行为
	 * 
	 * @param active
	 * @param request
	 * @return
	 */
	@RequestMapping("share")
	public Result share(@RequestBody ActiveReq active, HttpServletRequest request) {
		// TOOD 添加积分
		String userid = SecurityUtil.getUserid(request);
		String invuserid = active.getTuserid();
		List<Active> list = activeService.selectByUserid(userid, invuserid, CoinTypeEnum.SHARE.getCode());
		if (list.size() > 0) {
			return Result.ok("已经上报过的链接");
		}
		{
			active.setUserid(invuserid);
			active.setTuserid(userid);
			active.setCtype(CoinTypeEnum.SHARE.getCode());
			active.setCtime(new Date());
			active.setNotes(String.format(CoinTypeEnum.SHARE.getText(), userid));
		}
		boolean flag = activeService.insertWithCoins(active, CoinTypeEnum.SHARE.getCode());
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

	/**
	 * <pre>
	 * 0.邀请 1.更新用户积分 2.添加用户积分记录 3.添加用户行为
	 * </pre>
	 * 
	 * @param active
	 * @param request
	 * @return
	 */
	@RequestMapping("invite")
	public Result invite(@RequestBody ActiveReq active, HttpServletRequest request) {
		// TOOD 添加积分
		String userid = SecurityUtil.getUserid(request);
		String invuserid = active.getTuserid();
		List<Active> list = activeService.selectByUserid(userid, invuserid, CoinTypeEnum.SHARE.getCode());
		if (list.size() > 0) {
			return Result.ok("已经上报过的链接");
		}
		{
			active.setUserid(invuserid);
			active.setTuserid(userid);
			active.setCtype(CoinTypeEnum.INVITE.getCode());
			active.setCtime(new Date());
			active.setNotes(String.format(CoinTypeEnum.INVITE.getText(), userid));
		}
		boolean flag = activeService.insertWithCoins(active, CoinTypeEnum.INVITE.getCode());
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

}

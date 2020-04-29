package com.kuaiyou.lucky.api.open;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.Project;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.compnent.OpenMessageCompnent;
import com.kuaiyou.lucky.entity.Coins;
import com.kuaiyou.lucky.entity.Goods;
import com.kuaiyou.lucky.entity.Openuser;
import com.kuaiyou.lucky.entity.Task;
import com.kuaiyou.lucky.entity.Template;
import com.kuaiyou.lucky.entity.Wxuser;
import com.kuaiyou.lucky.enums.CoinTypeEnum;
import com.kuaiyou.lucky.enums.TemplateTypeEnum;
import com.kuaiyou.lucky.service.CoinsService;
import com.kuaiyou.lucky.service.GoodsService;
import com.kuaiyou.lucky.service.OpenuserService;
import com.kuaiyou.lucky.service.TaskService;
import com.kuaiyou.lucky.service.WxuserService;
import com.kuaiyou.lucky.utils.DateUtil;
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
@RequestMapping("/lucky/coin")
public class CoinController {

	@Autowired
	WxuserService wxuserService;

	@Autowired
	CoinsService coinsService;

	@Autowired
	OpenuserService openuserService;

	@Autowired
	OpenMessageCompnent messageCompnent;

	@Autowired
	Project project;

	@Autowired
	TaskService taskService;

	@Autowired
	GoodsService goodsService;

	/**
	 * 
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping("sign")
	public Result insert(@RequestBody Coins req, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		Wrapper<Coins> wrapper = new EntityWrapper<>();
		wrapper.like(Coins.CTIME, DateUtil.format(new Date(), DateUtil.YMD))
				.eq(Coins.CTYPE, CoinTypeEnum.SIGN.getCode()).eq(Coins.USERID, userid);
		List<Coins> signs = coinsService.selectList(wrapper);
		if (signs.size() >= 1) {
			return Result.ok("已签到过了");
		}
		{
			req.setUserid(userid);
			req.setCtype(CoinTypeEnum.SIGN.getCode());
		}
		boolean flag = coinsService.userSign(req);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequestMapping("subsign")
	public Result subsign(HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		/**
		 * 1.发送订阅通知 2.修改用户订阅状态
		 */

		Wxuser wxuser = wxuserService.selectByUserid(userid);
		Openuser openuser = openuserService.selectbyUnionid(wxuser.getUnionid());
		if (openuser == null || openuser.getSub().equals(0)) {
			return Result.error("5001", "您还未关注我们的公众号呢，关注后再来参与专属的锦鲤活动吧！");
		}
		if (wxuser.getSubsign().equals(1)) {
			return Result.ok("您已成功预约");
		}
		if (openuser != null) {
			Template template = new Template();
			template.setOpenid(openuser.getOpenid());
			template.setTemplateid(project.getModel2());
			template.setKeyword1(wxuser.getNickname());
			template.setKeyword2(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			template.setKeyword3("每日签到");
			template.setKeyword4("好物抽抽抽");
			template.setKeyword5("自动提醒");
			template.setTitle(TemplateTypeEnum.SIGN.getTitle());
			template.setRemark(TemplateTypeEnum.SIGN.getRemark());
			messageCompnent.send(template);
		}
		if (wxuser.getSubsign().equals(0)) {
			Coins coins = new Coins(wxuser.getUserid(), CoinTypeEnum.SUBSIGN.getCode());
			Task task = taskService.selectByCtype(CoinTypeEnum.SUBSIGN.getCode(), 1);
			if (task != null) {
				coins.setAcoin(wxuser.getCoins().intValue());
				coins.setAmount(task.getCoins());
				coins.setCtime(new Date());
				coins.setNotes(String.format(CoinTypeEnum.getDesc(coins.getCtype()), coins.getUserid()));
				Long value = wxuser.getCoins() + Math.abs(task.getCoins());
				wxuser.setCoins(value);
				coins.setBcoin(value.intValue());
				coinsService.insert(coins);
			}
		}
		wxuser.setSubsign(1);
		boolean flag = wxuserService.updateById(wxuser);
		if (flag) {
			return Result.ok();
		} else {
			return Result.error();
		}

	}

	@RequestMapping("subact")
	public Result subAct(@RequestBody Coins coins, HttpServletRequest request) {
		/**
		 * <pre>
		 * 		1.添加订阅记录 
		 * 		2.修改用户订阅活动状态 用户订阅活动
		 * </pre>
		 */
		String userid = SecurityUtil.getUserid(request);
		EntityWrapper<Coins> wrapper = new EntityWrapper<>();
		wrapper.eq(Coins.USERID, userid).eq(Coins.CTYPE, CoinTypeEnum.SUBACTIVE.getCode());
		Coins cache = coinsService.selectOne(wrapper);
		if (cache == null) {
			coins.setUserid(userid);
			coins.setCtime(new Date());
			coins.setCtype(CoinTypeEnum.SUBACTIVE.getCode());
			// 视为新手任务
			boolean flag = coinsService.insertTemp(coins, 1);
			if (flag) {
				return Result.ok();
			}
		} else {
			return Result.error("您已经参加过订阅了");
		}
		return Result.error();
	}

	@RequestMapping("notsubact")
	public Result notsubact(HttpServletRequest request) {
		/**
		 * 1.添加订阅记录 2.修改用户订阅活动状态 用户订阅活动
		 */
		String userid = SecurityUtil.getUserid(request);
		Wxuser wxuser = wxuserService.selectByUserid(userid);
		wxuser.setSubac(0);
		boolean flag = wxuserService.updateById(wxuser);
		if (flag) {
			return Result.ok("退订活动成功");
		}
		return Result.error();
	}

	/**
	 * 签到列表
	 * 
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping("grid")
	public Result grid(@RequestBody CommonReq req, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		return coinsService.selectWeekSigh(userid);
	}

	/**
	 * 积分列表
	 * 
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping("coins")
	public Result coins(@RequestBody CommonReq req, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		EntityWrapper<Coins> wrapper = new EntityWrapper<>();
		wrapper.eq(Coins.USERID, userid);
		wrapper.orderDesc(Arrays.asList(Coins.CTIME));
		Page<Coins> page = new Page<>();
		page.setCurrent(req.getPage());
		page.setSize(req.getSkip());
		Page<Coins> grid = coinsService.selectPage(page, wrapper);
		Wxuser user = wxuserService.selectByUserid(userid);
		Map<String, Object> result = new HashMap<>();
		result.put("grid", grid);
		result.put("user", user);
		return Result.ok(result);
	}

	/**
	 * <pre>
	 * 		1.控制获取金币的概率
	 * 			a.控制获取及未获取
	 * 				a).未获取
	 * 				b).获取到后得到具体多少金币的概率
	 * 			b.不控制获取和未获取
	 * 				a)设立每种档位及不获取金币的概率
	 * 		2.加金币
	 * </pre>
	 */

	@RequestMapping("newuser")
	public Result userNew(@RequestBody Coins coins, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		Coins cache = coinsService.selectByType(userid, CoinTypeEnum.NEWUSER.getCode());
		if (cache != null) {
			return Result.ok("已经领过了");
		}
		Date now = new Date();
		Coins coin = new Coins();
		coin.setCtype(CoinTypeEnum.NEWUSER.getCode());
		coin.setCtime(now);
		coin.setNotes(String.format(CoinTypeEnum.NEWUSER.getText(), userid));
		coin.setAmount(888);
		coin.setUserid(userid);
		boolean flag = coinsService.insertTemp(coin);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequestMapping("redbot")
	public Result redbot(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		String userid = SecurityUtil.getUserid(request);
		Wxuser user = wxuserService.selectByUserid(userid);
		Wrapper<Goods> wrapper = new EntityWrapper<>();
		wrapper.lt(Goods.AMOUNT, user.getCoins());
		int count = goodsService.selectCount(wrapper);

		Wrapper<Coins> coinswrapper = new EntityWrapper<>();
		coinswrapper.like(Coins.CTIME, DateUtil.format(new Date(), DateUtil.YMD))
				.eq(Coins.CTYPE, CoinTypeEnum.SIGN.getCode()).eq(Coins.USERID, userid);
		int signcount = coinsService.selectCount(coinswrapper);

		result.put("enough", count > 0);
		result.put("sign", signcount > 0);
		return Result.error();
	}

}

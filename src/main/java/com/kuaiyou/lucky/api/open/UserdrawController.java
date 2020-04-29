package com.kuaiyou.lucky.api.open;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.compnent.CoinsratoCompnent;
import com.kuaiyou.lucky.entity.Draw;
import com.kuaiyou.lucky.entity.Wxuser;
import com.kuaiyou.lucky.req.UserdrawReq;
import com.kuaiyou.lucky.service.DrawService;
import com.kuaiyou.lucky.service.OpenuserService;
import com.kuaiyou.lucky.service.UserdrawService;
import com.kuaiyou.lucky.service.WxuserService;
import com.kuaiyou.lucky.utils.SecurityUtil;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@Controller
@RequestMapping("/lucky/userdraw")
public class UserdrawController {

	@Autowired
	UserdrawService userdrawService;

	@Autowired
	DrawService drawService;

	@Autowired
	WxuserService wxuserService;

	@Autowired
	OpenuserService openuserService;

	@Autowired
	CoinsratoCompnent ratoCompnent;

	/**
	 * <pre>
	 * 用户参与抽奖
	 * </pre>
	 */
	@ResponseBody
	@RequestMapping("add")
	public Result insert(@RequestBody UserdrawReq item, HttpServletRequest request) {
		String userid = SecurityUtil.getUserid(request);
		Wxuser user = wxuserService.selectByUserid(userid);
		if (user.getAudit().equals(0)) {
			return Result.error("您还未授权");
		}
		if (Objects.isNull(item.getDrawid())) {
			return Result.error("没有参与的抽奖ID");
		}
		Draw cache = drawService.selectByWithStatus(item.getDrawid());
		if (cache == null) {
			return Result.error("暂时无法参与");
		}
		if (cache.getAct().equals(1) && !openuserService.isSub(user.getUnionid())) {
			return Result.error("5001", "您还未关注我们的公众号呢，关注后再来参与专属的锦鲤活动吧！");
		}
		item.setCtime(new Date());
		item.setUserid(userid);
		boolean flag = userdrawService.insertDraw(item);
		if (flag) {
			// 参与抽奖添加金币
			Integer coins = ratoCompnent.getCoins(userid);
			return Result.ok(coins);
		} else {
			return Result.error("参与失败");
		}
	}

	@RequestMapping("redic")
	public Result redicPath(@RequestParam("avata") String avata, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			URL url = new URL(avata);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3 * 1000);
			response.setContentType("application/force-download");// 设置强制下载不打开  
			response.addHeader("Content-Disposition", "attachment;fileName=" + "avatad.jpg");
			// 得到输入流
			InputStream inputStream = conn.getInputStream();

			OutputStream outputStream = response.getOutputStream();

			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Result.ok();
	}

}

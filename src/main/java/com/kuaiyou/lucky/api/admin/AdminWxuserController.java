package com.kuaiyou.lucky.api.admin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.kuaiyou.lucky.api.req.CommonReq;
import com.kuaiyou.lucky.common.PageUtils;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.entity.Banlist;
import com.kuaiyou.lucky.entity.Wxuser;
import com.kuaiyou.lucky.req.GenQrReq;
import com.kuaiyou.lucky.service.BanlistService;
import com.kuaiyou.lucky.service.WxuserService;
import com.kuaiyou.lucky.utils.ExpressUtil;
import com.kuaiyou.lucky.utils.GenQrImgUtil;

/**
 * <p>
 * 微信用户表 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@Controller
@RequestMapping("/admin/wxuser")
public class AdminWxuserController {

	@Autowired
	WxuserService wxuserService;

	@Autowired
	BanlistService banlistService;

	@Autowired
	ExpressUtil expressUtil;

	@ResponseBody
	@RequestMapping("mine")
	public PageUtils mine(@RequestBody CommonReq req, HttpServletRequest request) {
		List<Wxuser> grid = wxuserService.baseList(req);
		int total = wxuserService.baseListCount(req);
		return new PageUtils(grid, total, req.getPage(), req.getSkip());
	}

	@ResponseBody
	@RequestMapping("addban")
	public Result add(@RequestBody Banlist req) {
		boolean flag = banlistService.insert(req);
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

	@ResponseBody
	@RequestMapping("delban")
	public Result del(@RequestBody Banlist req) {
		boolean flag = banlistService.deleteByUserId(req.getUserid());
		if (flag) {
			return Result.ok();
		}
		return Result.error();
	}

	@RequestMapping("genqr")
	public String genqr(@RequestBody GenQrReq req, HttpServletRequest request, HttpServletResponse response) {
		try {
			URL url = new URL(GenQrImgUtil.QRURL + expressUtil.getToken());
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");// 提交模式
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
			JSONObject paramJson = new JSONObject();
			paramJson.put("scene", req.getScene());
			paramJson.put("page", req.getPage());
			paramJson.put("width", 430);
			paramJson.put("auto_color", true);
			printWriter.write(paramJson.toString());
			printWriter.flush();
			response.setContentType("application/force-download");// 设置强制下载不打开  
			response.addHeader("Content-Disposition", "attachment;fileName=" + req.getScene() + ".jpg");
			// 得到输入流
			InputStream inputStream = httpURLConnection.getInputStream();

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
		return null;
	}

}

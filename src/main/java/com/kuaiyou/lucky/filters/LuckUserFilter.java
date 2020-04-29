package com.kuaiyou.lucky.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.kuaiyou.lucky.common.Project;
import com.kuaiyou.lucky.entity.Wxuser;
import com.kuaiyou.lucky.service.WxuserService;
import com.kuaiyou.lucky.utils.SecurityUtil;

/**
 * 验证用户是否授权登录
 * 
 * @author yardney 2019年5月14日
 */
//@WebFilter(urlPatterns = "/lucky/*", filterName = "TinyproFilter")
public class LuckUserFilter implements Filter {

	@Autowired
	Project project;

	@Autowired
	WxuserService userService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String userid = SecurityUtil.getUserid(req);
		Wxuser user = userService.selectByUserid(userid);
		if (user != null) {
			chain.doFilter(request, response);
		} else {
			_no(req, res);
		}

	}

	@Override
	public void destroy() {
	}

	protected void _ok(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Response res = new Response(Response.ALREADYLOGIN, Response.ALREADYLOGINMSG);
		response.getWriter().write(JSON.toJSONString(res));
	}

	protected void _no(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Response res = new Response(Response.NOTLOGIN, Response.NOTLOGINMSG);
		response.getWriter().write(JSON.toJSONString(res));
	}

	class Response {

		final static int NOTLOGIN = 5000;
		final static String NOTLOGINMSG = "已经失效";
		final static int ALREADYLOGIN = 2000;
		final static String ALREADYLOGINMSG = "已经登录";

		private int code;
		private String msg;

		public Response(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

	}

}

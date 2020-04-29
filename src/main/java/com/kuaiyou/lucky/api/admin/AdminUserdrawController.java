package com.kuaiyou.lucky.api.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuaiyou.lucky.common.PageUtils;
import com.kuaiyou.lucky.common.Result;
import com.kuaiyou.lucky.req.UserdrawReq;
import com.kuaiyou.lucky.res.UserdrawRes;
import com.kuaiyou.lucky.service.UserdrawService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yardney
 * @since 2019-08-12
 */
@RestController
@RequestMapping("/admin/userdraw")
public class AdminUserdrawController {

	@Autowired
	UserdrawService userdrawService;

	/**
	 * <pre>
	 * 用户中奖列表
	 * </pre>
	 */
	@RequestMapping("grid")
	public PageUtils grid(@RequestBody UserdrawReq item, HttpServletRequest request) {
		List<UserdrawRes> grid = userdrawService.adminGrid(item);
		int total = userdrawService.adminGridCount(item);
		return new PageUtils(grid, total, item.getPage(), item.getSkip());
	}

	@RequestMapping("detail")
	public Result detail(@RequestBody UserdrawReq item) {
		return Result.ok(userdrawService.adminGridDetail(item));
	}

}

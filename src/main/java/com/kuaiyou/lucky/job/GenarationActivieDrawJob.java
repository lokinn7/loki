package com.kuaiyou.lucky.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kuaiyou.lucky.entity.Prizesetting;
import com.kuaiyou.lucky.entity.Userdraw;
import com.kuaiyou.lucky.res.DrawActiveRes;
import com.kuaiyou.lucky.service.DrawService;
import com.kuaiyou.lucky.service.PrizesettingService;
import com.kuaiyou.lucky.service.UserdrawService;
import com.kuaiyou.lucky.service.WxuserService;

@Component
@Transactional(rollbackFor = IllegalArgumentException.class)
public class GenarationActivieDrawJob {

	@Autowired
	DrawService drawService;

	@Autowired
	PrizesettingService prizesettingService;

	@Autowired
	WxuserService wxuserService;

	@Autowired
	UserdrawService userdrawService;

	// @Scheduled
	public void genarator() {
		/**
		 * <pre>
		 * 		1.查找上周的活动抽奖，并且本周还未生成，并且查找出参与这些活动的用户 
		 *	 	2.生成本周的活动并返回id 
		 * 		3.拼装id组装用户参与的记录
		 * 		4.消息通知 --》 服务号通知
		 * </pre>
		 */
		List<DrawActiveRes> list = drawService.selectWithActive();

		for (DrawActiveRes draw : list) {
			boolean add = drawService.insert(draw);
			if (add) {
				List<Prizesetting> prizes = draw.getPrizes();
				prizes.forEach(each -> each.setDrawid(draw.getId()));
				boolean setting = prizesettingService.insertBatch(prizes);
				if (setting) {
					List<Userdraw> userdraws = draw.getUserdraws();
					userdraws.forEach(each -> each.setDrawid(draw.getId()));
					boolean us = userdrawService.insertBatch(userdraws);
					if (us) {
						// TODO 发布模板消息
					} else {
						throw new IllegalArgumentException(
								"genarator wxuser exception ,flag is false, check your code ");

					}
				} else {
					throw new IllegalArgumentException(
							"genarator prizesetting exception ,flag is false, check your code ");
				}
			} else {
				throw new IllegalArgumentException("genarator draw exception ,flag is false, check your code");
			}
		}

	}
}

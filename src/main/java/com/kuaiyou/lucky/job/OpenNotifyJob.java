package com.kuaiyou.lucky.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kuaiyou.lucky.common.Project;
import com.kuaiyou.lucky.compnent.OpenMessageCompnent;
import com.kuaiyou.lucky.entity.Openuser;
import com.kuaiyou.lucky.entity.Template;
import com.kuaiyou.lucky.enums.TemplateTypeEnum;
import com.kuaiyou.lucky.service.OpenuserService;
import com.kuaiyou.lucky.service.WxuserService;
import com.kuaiyou.lucky.utils.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OpenNotifyJob {

	@Autowired
	OpenuserService openwxuserService;

	@Autowired
	WxuserService wxuserService;

	@Autowired
	Project project;

	@Autowired
	OpenMessageCompnent messageCompnent;

	@Scheduled(cron = "0 0 18 * * ?")
	public void notification() {
		/**
		 * <pre>
		 * 		1.查出所有订阅通知的且还在关注公众号的用户
		 * 		2.发送通知
		 * </pre>
		 * 
		 */
		try {
			List<Openuser> users = openwxuserService.selectBySubSign();
			users.forEach(openuser -> {
				Template template = new Template();
				template.setOpenid(openuser.getOpenid());
				template.setTemplateid(project.getModel5());
				template.setKeyword1(TemplateTypeEnum.SIGNPRE.getText());
				template.setKeyword2(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
				template.setTitle(TemplateTypeEnum.SIGNPRE.getTitle());
				template.setRemark(TemplateTypeEnum.SIGNPRE.getRemark());
				messageCompnent.send(template);
			});
		} catch (Exception e) {
			log.error("{}", "send to open notify happen a exception ,cause " + e);
		}

	}
}

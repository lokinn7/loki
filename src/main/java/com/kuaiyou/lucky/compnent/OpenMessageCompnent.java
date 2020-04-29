package com.kuaiyou.lucky.compnent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.kuaiyou.lucky.common.Project;
import com.kuaiyou.lucky.entity.Template;
import com.riversoft.weixin.mp.base.AppSetting;
import com.riversoft.weixin.mp.template.Data;
import com.riversoft.weixin.mp.template.MiniProgram;
import com.riversoft.weixin.mp.template.Templates;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OpenMessageCompnent {

	@Autowired
	Project project;

	// 签到通知
	@Async("sendtemplate")
	public void send(Template template) {
		AppSetting appSetting = new AppSetting(project.getOpenkey(), project.getOpensecret());
		Templates templates = Templates.with(appSetting);
		Map<String, Data> data = new HashMap<>();
		{
			if (StringUtils.isNotBlank(template.getKeyword1())) {
				data.put("keyword1", new Data(template.getKeyword1(), null));
			}
			if (StringUtils.isNotBlank(template.getKeyword2())) {
				data.put("keyword2", new Data(template.getKeyword2(), null));
			}
			if (StringUtils.isNotBlank(template.getKeyword3())) {
				data.put("keyword3", new Data(template.getKeyword3(), null));
			}
			if (StringUtils.isNotBlank(template.getKeyword4())) {
				data.put("keyword4", new Data(template.getKeyword4(), null));
			}
			if (StringUtils.isNotBlank(template.getKeyword5())) {
				data.put("keyword5", new Data(template.getKeyword5(), null));
			}
			if (StringUtils.isNotBlank(template.getTitle())) {
				data.put("first", new Data(template.getTitle(), null));
			}
			if (StringUtils.isNotBlank(template.getRemark())) {
				data.put("remark", new Data(template.getRemark(), null));
			} else {
				data.put("remark", new Data("感谢参与", null));
			}
		}
		MiniProgram minipro = new MiniProgram();
		minipro.setAppId(project.getAppkey());
		minipro.setPagePath("/pages/index/index");
		try {
			templates.send(template.getOpenid(), template.getTemplateid(), minipro, data);
		} catch (Exception e) {
			log.error("{}", e);
		}
	}

	public static void sendMessage() {
		AppSetting appSetting = new AppSetting("wx599ae3b5b1543a1a", "0e339d4fa7fbdb7ec083a406df9cc193");
		Templates templates = Templates.with(appSetting);
		HashMap<String, Data> data = new HashMap<>();
		{
			Data first = new Data();
			first.setColor("#00FF00");
			first.setValue("问题整理好了吗");
			Data data1 = new Data();
			data1.setColor("#00FF00");
			data1.setValue("问题整理好了吗");
			Data data2 = new Data();
			data2.setColor("#00FF00");
			data2.setValue("问题整理好了吗");
			Data data3 = new Data();
			data3.setColor("#00FF00");
			data3.setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			Data data4 = new Data();
			data4.setColor("#00FF00");
			Data remark = new Data();
			remark.setColor("#00FF00");
			remark.setValue("问题整理好了吗");
			data.put("first", first);
			data.put("keyword1", data1);
			// data.put("keyword2", data2);
			// data.put("keyword3", data3);
			data.put("keyword4", data4);
			data.put("remark", first);
		}
		MiniProgram minipro = new MiniProgram();
		minipro.setAppId("wxe49406e89dae807c");
		minipro.setPagePath("/pages/index/index");
		templates.send("o7Ffiv4EaFiNTGQflike0VFmPutY", "Yheq2VE_hmnTh78Q4txuKn7EQrv92rnPlEhcSIunl3I", minipro, data);
	}

	public static void main(String[] args) {
		sendMessage();
	}
}

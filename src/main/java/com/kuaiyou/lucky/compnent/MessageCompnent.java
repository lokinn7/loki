package com.kuaiyou.lucky.compnent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.kuaiyou.lucky.common.Project;
import com.kuaiyou.lucky.entity.Template;
import com.riversoft.weixin.app.base.AppSetting;
import com.riversoft.weixin.app.template.Message;
import com.riversoft.weixin.app.template.Message.Data;
import com.riversoft.weixin.app.template.Templates;

@Component
public class MessageCompnent {

	@Autowired
	Project project;

	@Async("sendtemplate")
	public void sendMessage(Template template) {
		AppSetting appSetting = new AppSetting(project.getAppkey(), project.getAppsecret());
		Templates templates = Templates.with(appSetting);
		Message message = new Message();

		message.setFormId(template.getFormid());
		message.setToUser(template.getOpenid());
		message.setTemplateId(template.getTemplateid());
		message.setPage(template.getPage());
		HashMap<String, Data> data = new HashMap<>();
		{
			Data data1 = new Data();
			data1.setValue(template.getKeyword1());
			Data data2 = new Data();
			data2.setValue(template.getKeyword2());
			Data data3 = new Data();
			data3.setValue(template.getKeyword3());
			Data data4 = new Data();
			data4.setValue(template.getKeyword4());
			data.put("keyword1", data1);
			data.put("keyword2", data2);
			data.put("keyword3", data3);
			data.put("keyword4", data4);
		}
		message.setData(data);
		templates.send(message);
	}

	public void sendMessage() {
		Templates templates = Templates.defaultTemplates();
		Message message = new Message();

		/**
		 * <pre>
			access_token		string		是	接口调用凭证
			touser				string		是	接收者（用户）的 openid
			template_id			string		是	所需下发的模板消息的id
			page				string		否	点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
			form_id				string		是	表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
			data				Object		否	模板内容，不填则下发空模板。具体格式请参考示例。
			emphasis_keyword	string		否	模板需要放大的关键词，不填则默认无放大
		 * </pre>
		 */
		message.setFormId("6091e770b0bc4b0e8ff991b7ae981145");
		message.setToUser("odqif4qRnP0y1J2K0O5KMDxJWxgg");
		message.setTemplateId("L0xereMfC73SXILAe_bCOkLSWClz-zBmX2Xvt4l4gUA");
		HashMap<String, Data> data = new HashMap<>();
		{

			Data first = new Data();
			first.setColor("#00FF00");
			first.setValue("来签到啊 ashome");
			Data data1 = new Data();
			data1.setColor("#00FF00");
			data1.setValue("签到通知");
			Data data2 = new Data();
			data2.setColor("#00FF00");
			data2.setValue("今天");
			Data data3 = new Data();
			data3.setColor("#00FF00");
			data3.setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			Data data4 = new Data();
			data4.setColor("#00FF00");
			data4.setValue("我是马德发");
			data.put("keyword1", data1);
			data.put("keyword2", data2);
			data.put("keyword3", data3);
			data.put("keyword4", data4);
		}
		message.setPage("pages/index/index");
		message.setData(data);
		templates.send(message);
	}

	public void getTemaplates() {

	}

}

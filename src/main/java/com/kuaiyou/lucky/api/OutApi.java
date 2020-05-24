package com.kuaiyou.lucky.api;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.kuaiyou.lucky.common.Project;
import com.kuaiyou.lucky.service.OpenuserService;
import com.kuaiyou.lucky.utils.IDCodeUtil;
import com.riversoft.weixin.common.decrypt.AesException;
import com.riversoft.weixin.common.decrypt.MessageDecryption;
import com.riversoft.weixin.common.decrypt.SHA1;
import com.riversoft.weixin.common.event.ClickEvent;
import com.riversoft.weixin.common.event.EventRequest;
import com.riversoft.weixin.common.event.EventType;
import com.riversoft.weixin.common.exception.WxRuntimeException;
import com.riversoft.weixin.common.message.MsgType;
import com.riversoft.weixin.common.message.Text;
import com.riversoft.weixin.common.message.XmlMessageHeader;
import com.riversoft.weixin.common.message.xml.TextXmlMessage;
import com.riversoft.weixin.common.request.TextRequest;
import com.riversoft.weixin.mp.base.AppSetting;
import com.riversoft.weixin.mp.message.MpXmlMessages;

/**
 * 用户init
 * 
 * @author yardney 2019年5月14日
 */
@RestController
public class OutApi {

	private static Logger logger = LoggerFactory.getLogger(OutApi.class);

	@Autowired
	StringRedisTemplate redisTemplate;

	@Autowired
	Project project;

	@Autowired
	OpenuserService openuserService;

	@RequestMapping("signature")
	public String signature(@RequestParam(name = "signature", required = false) String signature,
			@RequestParam(value = "msg_signature", required = false) String msg_signature,
			@RequestParam(name = "timestamp", required = false) String timestamp,
			@RequestParam(name = "nonce", required = false) String nonce,
			@RequestParam(name = "echostr", required = false) String echostr,
			@RequestParam(name = "encrypt_type", required = false) String encrypt_type,
			@RequestBody(required = false) String content, HttpServletRequest request) {
		logger.info("signature={}, msg_signature={}, timestamp={}, nonce={}, echostr={}, encrypt_type={}", signature,
				msg_signature, timestamp, nonce, echostr, encrypt_type);

		AppSetting appSetting = AppSetting.defaultSettings();
		try {
			if (!SHA1.getSHA1(appSetting.getToken(), timestamp, nonce).equals(signature)) {
				logger.warn("非法请求.");
				return "非法请求.";
			}
		} catch (AesException e) {
			logger.error("check signature failed:", e);
			return "非法请求.";
		}

		if (!StringUtils.isEmpty(echostr)) {
			return echostr;
		}

		XmlMessageHeader xmlRequest = null;
		if ("aes".equals(encrypt_type)) {
			try {
				MessageDecryption messageDecryption = new MessageDecryption(appSetting.getToken(),
						appSetting.getAesKey(), appSetting.getAppId());
				xmlRequest = MpXmlMessages.fromXml(content);
				XmlMessageHeader xmlResponse = mpDispatch(xmlRequest);
				logger.info("{}, {}", JSON.toJSONString(xmlRequest), JSON.toJSONString(xmlResponse));
				if (xmlResponse != null) {
					try {
						return MpXmlMessages.toXml(xmlResponse);
					} catch (WxRuntimeException e) {
						logger.error("Can not send mq cause: " + e.getMessage(), e);
					}
				}
			} catch (AesException e) {
				logger.error("Can not send mq cause: " + e.getMessage(), e);
			}
		} else {
			xmlRequest = MpXmlMessages.fromXml(content);
			XmlMessageHeader xmlResponse = mpDispatch(xmlRequest);
			logger.info("{}, {}", JSON.toJSONString(xmlRequest), JSON.toJSONString(xmlResponse));
			if (xmlResponse != null) {
				try {
					return MpXmlMessages.toXml(xmlResponse);
				} catch (WxRuntimeException e) {
					logger.error("Can not send mq cause: " + e.getMessage(), e);
				}
			}
		}

		return "";
	}

	private XmlMessageHeader mpDispatch(XmlMessageHeader xmlRequest) {
		String fromUser = xmlRequest.getFromUser();
		String toUser = xmlRequest.getToUser();
		if (xmlRequest instanceof EventRequest) {
			TextXmlMessage textXmlMessage = new TextXmlMessage();
			textXmlMessage.setFromUser(toUser);
			textXmlMessage.setToUser(fromUser);
			textXmlMessage.setCreateTime(new Date());
			EventRequest event = (EventRequest) xmlRequest;
			logger.info("{},{}", fromUser, JSON.toJSONString(event));
			EventType eventType = event.getEventType();

			// 接受事件
			logger.info("{}", JSON.toJSONString(eventType));
			switch (eventType) {
			case CLICK:
				ClickEvent clickEvent = (ClickEvent) xmlRequest;
				logger.info("{}", JSON.toJSONString(clickEvent));
//				if (clickEvent.getEventKey().equals("about_salary")) {
//					/**
//					 * <pre>
//					 * 1.菜单判断用户是否绑定
//					 * 2.身份证校验判断
//					 * </pre>
//					 */
//					openuserService.bindUser(text.getContent(), fromUser);
//				}
				textXmlMessage.setContent("回复身份证号与我们的公众号绑定后即可回复月份查询工资！");
				return textXmlMessage;
			case subscribe:
				// 添加用户 fromuser 即为openid
				openuserService.subOpenUser(fromUser);
				textXmlMessage.setContent("欢迎关注华鑫源快讯！");
				return textXmlMessage;
			case unsubscribe:
				openuserService.unsubOpenUser(fromUser);
				break;
			default:
				textXmlMessage.setContent("HELLO!");
				return textXmlMessage;
			}

			// 接受消息
			switch (xmlRequest.getMsgType()) {
			case text:

				logger.info(JSON.toJSONString(text));
				break;

			default:
				break;
			}
		}
		// 消息转发多客服中心
		xmlRequest.setToUser(fromUser);
		xmlRequest.setFromUser(toUser);
		xmlRequest.setMsgType(MsgType.transfer_customer_service);
		// 需要同步返回消息（被动回复）给用户则构造一个XmlMessageHeader类型，比较鸡肋，因为处理逻辑如果比较复杂响应太慢会影响用户感知，建议直接返回null；
		// 要发送消息给用户可以参考上面的例子使用客服消息接口进行异步发送
		return xmlRequest;
	}
}

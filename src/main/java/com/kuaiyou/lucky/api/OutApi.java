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
import com.kuaiyou.lucky.entity.Bind;
import com.kuaiyou.lucky.entity.Salary;
import com.kuaiyou.lucky.entity.User;
import com.kuaiyou.lucky.service.BindService;
import com.kuaiyou.lucky.service.OpenuserService;
import com.kuaiyou.lucky.service.SalaryService;
import com.kuaiyou.lucky.service.UserService;
import com.kuaiyou.lucky.utils.DateUtil;
import com.kuaiyou.lucky.utils.IDCodeUtil;
import com.riversoft.weixin.common.decrypt.AesException;
import com.riversoft.weixin.common.decrypt.MessageDecryption;
import com.riversoft.weixin.common.decrypt.SHA1;
import com.riversoft.weixin.common.event.ClickEvent;
import com.riversoft.weixin.common.event.EventRequest;
import com.riversoft.weixin.common.event.EventType;
import com.riversoft.weixin.common.exception.WxRuntimeException;
import com.riversoft.weixin.common.message.MsgType;
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

	@Autowired
	SalaryService salaryService;

	@Autowired
	BindService bindService;

	@Autowired
	UserService userService;

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
		/*
		 * 需要注意的是，在接收到微信推送的消息中tousername、fromusername的顺序与平台返回给微信（即要发送给微信用户）
		 * 的交互信息中tousername、fromusername的值是相反的
		 * 接受到的消息中：tousername=开发者公众号，fromusername=openid
		 * 发送给微信的消息中：tousername=openid,fromusername=开发者的公账号
		 */
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
			switch (eventType) {
			case CLICK:
				ClickEvent clickEvent = (ClickEvent) xmlRequest;
				logger.info("{}", JSON.toJSONString(clickEvent));
				/**
				 * 判断用户是否绑定，绑定了返回当月，没有绑定返回原消息
				 * 
				 */

				if (clickEvent.getEventKey().equals("about_salary")) {
					Bind bind = bindService.selectWithIDcode(fromUser);
					if (bind != null) {
//						Salary openuser = salaryService.selectByOpenidAndMonth(bind);
//						if (openuser != null) {
//							textXmlMessage.setContent("姓名：" + openuser.getNickname() + "\n身份证号：" + openuser.getIdcode()
//									+ "\n部门:" + openuser.getDepartment() + "\n岗位工资" + openuser.getPostSalary()
//									+ "\n基本工资" + openuser.getBaseSalary() + "\n岗位（技术）津贴" + openuser.getPostSubsidy()
//									+ "\n学历津贴" + openuser.getEduSubsidy() + "\n出勤" + openuser.getAttendance() + "\n加班"
//									+ openuser.getOvertime() + "\n本月工资" + openuser.getSalary() + "\n罚款"
//									+ openuser.getFine() + "\n收入合计" + openuser.getTotal() + "\n税费扣除"
//									+ openuser.getAddTaxes() + "\n扣借款" + openuser.getMines() + "\n实发"
//									+ openuser.getFactSalary());
//							return textXmlMessage;
//						}
						textXmlMessage.setContent("已将我们的公众号与您的个人身份绑定，回复月份，如：2020-01即可查询工资！");
						return textXmlMessage;
					}
				}
				textXmlMessage.setContent("回复身份证号与我们的公众号绑定，如已绑定则可回复月份，如：2020-01查询工资！");
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

				break;

			default:
				break;
			}
		}
		if (xmlRequest instanceof TextRequest) {
			TextXmlMessage textXmlMessage = new TextXmlMessage();
			textXmlMessage.setFromUser(toUser);
			textXmlMessage.setToUser(fromUser);
			textXmlMessage.setCreateTime(new Date());
			TextRequest event = (TextRequest) xmlRequest;
			logger.info("{},{}", fromUser, JSON.toJSONString(event));
			/**
			 * <pre>
			 * 	1.接受身份证号码
			 * 2.接受月份数字
			 * 3.接受普通消息
			 * </pre>
			 */
			switch (xmlRequest.getMsgType()) {
			case text:
				String content = event.getContent();
				if (new IDCodeUtil().validate(content)) {
					logger.info(event.getContent());
					// 绑定公众号和用户
					User user = userService.selectByIdCode(content);
					if (user == null) {
						textXmlMessage.setContent("系统暂未收录您的个人信息，请联系管理员");
						return textXmlMessage;
					}
					Bind cahBind = bindService.selectByOpenid(fromUser);
					if (cahBind != null) {
						textXmlMessage.setContent("您已绑定过了，请勿重复绑定");
						return textXmlMessage;
					} else {
						bindService.bind(content, fromUser);
						textXmlMessage.setContent("绑定成功，回复年份+月份即可查询工资，如：2020-01");
						return textXmlMessage;
					}
				}
				if (DateUtil.isValidDate(content)) {
					logger.info(event.getContent());
					// 先查询绑定
					Bind cacheBind = bindService.selectByOpenid(fromUser);
					if (cacheBind != null) {
						// 返回指定月份工资
						Salary openuser = salaryService.selectByMonth(content, fromUser);
						if (openuser != null) {
							textXmlMessage.setContent("姓名：" + openuser.getNickname() + "\n身份证号：" + openuser.getIdcode()
									+ "\n部门：" + openuser.getDepartment() + "\n岗位工资：" + openuser.getPostSalary()
									+ "\n基本工资：" + openuser.getBaseSalary() + "\n岗位（技术）津贴："
									+ (openuser.getPostSubsidy() == null ? "-" : openuser.getPostSubsidy()) + "\n学历津贴："
									+ (openuser.getEduSubsidy() == null ? "-" : openuser.getEduSubsidy()) + "\n出勤："
									+ (openuser.getAttendance() == null ? "-" : openuser.getAttendance()) + "\n加班："
									+ (openuser.getOvertime() == null ? "-" : openuser.getOvertime()) + "\n本月工资："
									+ (openuser.getMines() == null ? "-" : openuser.getMines()) + "\n奖金："
									+ (openuser.getBonus() == null ? "-" : openuser.getBonus()) + "\n其他补助："
									+ openuser.getSalary() + "\n罚款："
									+ (openuser.getFine() == null ? "-" : openuser.getFine()) + "\n税费扣除："
									+ (openuser.getAddTaxes() == null ? "-" : openuser.getAddTaxes()) + "\n扣借款："
									+ (openuser.getOtherSubsidy() == null ? "-" : openuser.getOtherSubsidy())
									+ "\n收入合计：" + openuser.getTotal() + "\n实发：" + openuser.getFactSalary());
							return textXmlMessage;
						} else {
							textXmlMessage.setContent("系统暂未收录该月工资信息，请日后再试");
							return textXmlMessage;
						}
					} else {
						textXmlMessage.setContent("您还未绑定个人信息，输入身份证号码绑定");
						return textXmlMessage;
					}
				}
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

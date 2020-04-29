//package com.kuaiyou.lucky.api;
//
//import java.util.Date;
//import java.util.concurrent.TimeUnit;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.alibaba.fastjson.JSON;
//import com.kuaiyou.lucky.common.CommonRedisKey;
//import com.kuaiyou.lucky.common.Project;
//import com.kuaiyou.lucky.common.Result;
//import com.kuaiyou.lucky.entity.Coins;
//import com.kuaiyou.lucky.entity.Task;
//import com.kuaiyou.lucky.entity.Wxuser;
//import com.kuaiyou.lucky.enums.CoinTypeEnum;
//import com.kuaiyou.lucky.req.InitUserReq;
//import com.kuaiyou.lucky.req.OpenidReq;
//import com.kuaiyou.lucky.service.CoinsService;
//import com.kuaiyou.lucky.service.OpenuserService;
//import com.kuaiyou.lucky.service.TaskService;
//import com.kuaiyou.lucky.service.WxuserService;
//import com.kuaiyou.lucky.utils.Generator;
//import com.kuaiyou.lucky.utils.SecurityUtil;
//import com.riversoft.weixin.app.MAppUtils;
//import com.riversoft.weixin.app.user.MAppuserinfo;
//import com.riversoft.weixin.app.user.SessionKey;
//import com.riversoft.weixin.common.decrypt.AesException;
//import com.riversoft.weixin.common.decrypt.MessageDecryption;
//import com.riversoft.weixin.common.decrypt.SHA1;
//import com.riversoft.weixin.common.event.EventRequest;
//import com.riversoft.weixin.common.event.EventType;
//import com.riversoft.weixin.common.exception.WxRuntimeException;
//import com.riversoft.weixin.common.message.MsgType;
//import com.riversoft.weixin.common.message.XmlMessageHeader;
//import com.riversoft.weixin.common.message.xml.TextXmlMessage;
//import com.riversoft.weixin.mp.base.AppSetting;
//import com.riversoft.weixin.mp.message.MpXmlMessages;
//
///**
// * 用户init
// * 
// * @author yardney 2019年5月14日
// */
//@RestController
//public class OutApi {
//
//	private static Logger logger = LoggerFactory.getLogger(OutApi.class);
//
//	@Autowired
//	StringRedisTemplate redisTemplate;
//
//	@Autowired
//	WxuserService userService;
//
//	@Autowired
//	Project project;
//
//	@Autowired
//	CoinsService coinsService;
//
//	@Autowired
//	TaskService taskService;
//
//	@Autowired
//	OpenuserService openuserService;
//
//	/**
//	 * 授权保存用户信息
//	 * 
//	 * @param user
//	 * @return
//	 */
//	@RequestMapping("init")
//	public Result init(@RequestBody OpenidReq openidReq) {
//		String code = openidReq.getCode();
//		SessionKey sessionKey = com.riversoft.weixin.app.user.Users
//				.defaultUsers(project.getAppkey(), project.getAppsecret()).code2Session(code);
//		String openid = sessionKey.getOpenId();
//		String unionid = sessionKey.getUnionid();
//		Wxuser cache = userService.selectByOpenid(openid);
//		if (cache != null) {
//			setToRedis(cache.getUserid(), sessionKey.getSessionKey());
//			userService.updateById(cache.setUnionid(unionid));
//			logger.info("init body :" + JSON.toJSONString(cache));
//			return Result.ok_new(cache, 0);
//		}
//		Wxuser user = new Wxuser();
//		String id = Generator.id();
//		Date now = new Date();
//		user.setUserid(id);
//		user.setStatus(1);
//		user.setDeleted(0);
//		user.setUnionid(unionid);
//		user.setCreatetime(now);
//		user.setOpenid(openid);
//		user.setAudit(0);
//		user.setCoins(0L);
//		logger.debug("id is :" + id);
//		boolean flag = userService.insert(user);
//		if (flag) {
//			// 保存session_key
//			setToRedis(id, sessionKey.getSessionKey());
//			return Result.ok_new(user, 1);
//		}
//		return Result.error("服务器开小差了...");
//	}
//
//	@RequestMapping("userauth")
//	public Result authuser(@RequestBody OpenidReq openidReq, HttpServletRequest request) {
//		String userid = SecurityUtil.getUserid(request);
//		Wxuser user = userService.selectByUserid(userid);
//		if (user != null) {
//			try {
//				String session_key = redisTemplate.opsForValue().get(CommonRedisKey.USER_SESSION + userid);
//				MAppuserinfo info = MAppUtils.getUserInfo(openidReq.getEncryptedData(), session_key, openidReq.getIv());
//				if (info != null) {
//					user.setUnionid(info.getUnionId());
//				}
//			} catch (Exception e) {
//				logger.error("user auth exception :{}", e);
//				e.printStackTrace();
//			}
//			if (user.getAudit().equals(0)) {
//				Coins coins = new Coins(user.getUserid(), CoinTypeEnum.USERAUTH.getCode());
//				Task task = taskService.selectByCtype(CoinTypeEnum.USERAUTH.getCode(), 1);
//				if (task != null) {
//					coins.setAmount(task.getCoins());
//					coins.setCtime(new Date());
//					coins.setNotes(String.format(CoinTypeEnum.getDesc(coins.getCtype()), coins.getUserid()));
//					Long value = user.getCoins() + Math.abs(task.getCoins());
//					user.setCoins(value);
//					coins.setBcoin(value.intValue());
//					coinsService.insert(coins);
//				}
//			}
//			InitUserReq initUserReq = openidReq.getUserInfo();
//			user.setNickname(initUserReq.getNickName());
//			user.setGender(initUserReq.getGender());
//			user.setAvatarurl(initUserReq.getAvatarUrl());
//			user.setLanguage(initUserReq.getLanguage());
//			user.setCity(initUserReq.getCity());
//			user.setProvince(initUserReq.getProvince());
//			user.setCountry(initUserReq.getCountry());
//			user.setAudit(1);
//			if (user.getUpdatetime() == null) {
//				user.setUpdatetime(new Date());
//			}
//			userService.updateById(user);
//			return Result.ok();
//		} else {
//			return Result.error("请稍后再试....");
//		}
//	}
//
//	private void setToRedis(String userid, String sessionKey) {
//		redisTemplate.opsForValue().set(CommonRedisKey.USER_SESSION + userid, sessionKey, 2, TimeUnit.HOURS);
//	}
//
//	@RequestMapping("signature")
//	public String signature(@RequestParam(name = "signature", required = false) String signature,
//			@RequestParam(value = "msg_signature", required = false) String msg_signature,
//			@RequestParam(name = "timestamp", required = false) String timestamp,
//			@RequestParam(name = "nonce", required = false) String nonce,
//			@RequestParam(name = "echostr", required = false) String echostr,
//			@RequestParam(name = "encrypt_type", required = false) String encrypt_type,
//			@RequestBody(required = false) String content, HttpServletRequest request) {
//		logger.info("signature={}, msg_signature={}, timestamp={}, nonce={}, echostr={}, encrypt_type={}", signature,
//				msg_signature, timestamp, nonce, echostr, encrypt_type);
//
//		AppSetting appSetting = AppSetting.defaultSettings();
//		try {
//			if (!SHA1.getSHA1(appSetting.getToken(), timestamp, nonce).equals(signature)) {
//				logger.warn("非法请求.");
//				return "非法请求.";
//			}
//		} catch (AesException e) {
//			logger.error("check signature failed:", e);
//			return "非法请求.";
//		}
//
//		if (!StringUtils.isEmpty(echostr)) {
//			return echostr;
//		}
//
//		XmlMessageHeader xmlRequest = null;
//		if ("aes".equals(encrypt_type)) {
//			try {
//				MessageDecryption messageDecryption = new MessageDecryption(appSetting.getToken(),
//						appSetting.getAesKey(), appSetting.getAppId());
//				xmlRequest = MpXmlMessages.fromXml(content);
//				XmlMessageHeader xmlResponse = mpDispatch(xmlRequest);
//				logger.info("{}, {}", JSON.toJSONString(xmlRequest), JSON.toJSONString(xmlResponse));
//				if (xmlResponse != null) {
//					try {
//						return MpXmlMessages.toXml(xmlResponse);
//					} catch (WxRuntimeException e) {
//						logger.error("Can not send mq cause: " + e.getMessage(), e);
//					}
//				}
//			} catch (AesException e) {
//				logger.error("Can not send mq cause: " + e.getMessage(), e);
//			}
//		} else {
//			xmlRequest = MpXmlMessages.fromXml(content);
//			XmlMessageHeader xmlResponse = mpDispatch(xmlRequest);
//			logger.info("{}, {}", JSON.toJSONString(xmlRequest), JSON.toJSONString(xmlResponse));
//			if (xmlResponse != null) {
//				try {
//					return MpXmlMessages.toXml(xmlResponse);
//				} catch (WxRuntimeException e) {
//					logger.error("Can not send mq cause: " + e.getMessage(), e);
//				}
//			}
//		}
//
//		return "";
//	}
//
//	private XmlMessageHeader mpDispatch(XmlMessageHeader xmlRequest) {
//		String fromUser = xmlRequest.getFromUser();
//		String toUser = xmlRequest.getToUser();
//		if (xmlRequest instanceof EventRequest) {
//			TextXmlMessage textXmlMessage = new TextXmlMessage();
//			textXmlMessage.setFromUser(toUser);
//			textXmlMessage.setToUser(fromUser);
//			textXmlMessage.setCreateTime(new Date());
//			EventRequest event = (EventRequest) xmlRequest;
//			logger.info("{},{}", fromUser, JSON.toJSONString(event));
//			EventType eventType = event.getEventType();
//			switch (eventType) {
//			case CLICK:
//				break;
//			case subscribe:
//				// 添加用户 fromuser 即为openid
//				openuserService.subOpenUser(fromUser);
//				textXmlMessage.setContent("欢迎关注好物抽抽抽！");
//				return textXmlMessage;
//			case unsubscribe:
//				openuserService.unsubOpenUser(fromUser);
//				break;
//			default:
//				textXmlMessage.setContent("HELLO!");
//				return textXmlMessage;
//			}
//		}
//		// 消息转发多客服中心
//		xmlRequest.setToUser(fromUser);
//		xmlRequest.setFromUser(toUser);
//		xmlRequest.setMsgType(MsgType.transfer_customer_service);
//		// 需要同步返回消息（被动回复）给用户则构造一个XmlMessageHeader类型，比较鸡肋，因为处理逻辑如果比较复杂响应太慢会影响用户感知，建议直接返回null；
//		// 要发送消息给用户可以参考上面的例子使用客服消息接口进行异步发送
//		return xmlRequest;
//	}
//}

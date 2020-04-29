package com.kuaiyou.lucky.listenner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.kuaiyou.lucky.entity.Draw;
import com.kuaiyou.lucky.service.DrawService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JedisExpiredListener implements MessageListener {

	public final static String LISTENER_PATTERN = "__keyevent@2__:expired";
	public final static String KEY_PATTERN = "_lucky:key:";
	public final static String KEY_DEL_PATTERN = "_lucky:del:";
	public final static String COIN_LIMIT = "_lucky:coin:limit:";
	public final static String ROUND_LIMIT = "_lucky:round:limit:";

	@Autowired
	DrawService drawService;

	@Override
	public void onMessage(Message message, byte[] bytes) {
		byte[] body = message.getBody();// 建议使用: valueSerializer
		byte[] channel = message.getChannel();
		String bodys = new String(body);
		if (!bodys.startsWith(KEY_PATTERN) && !bodys.startsWith(KEY_DEL_PATTERN)) {
			return;
		}

		log.info("get from redis expire >> " + String.format("channel: %s, body: %s, bytes: %s", new String(channel),
				new String(body), new String(bytes)));

		if (bodys.startsWith(KEY_PATTERN)) {
			String[] split = bodys.split("\\:");
			if (split.length > 0 && split.length == 3) {
				String id = split[2];
				Draw req = drawService.selectById(id);
				boolean openDraw = drawService.openDraw(req);
				log.info(String.format("open draw success obj is :%s , result is %s:", JSON.toJSONString(req),
						openDraw));
			}
		}
		if (bodys.startsWith(KEY_DEL_PATTERN)) {
			String[] split = bodys.split("\\:");
			if (split.length > 0 && split.length == 3) {
				String id = split[2];
				Draw req = drawService.selectById(id);
				if (req != null) {
					req.setDeleted(0);
					drawService.updateById(req);
				}
				log.info(String.format("open draw success obj is :%s ", JSON.toJSONString(req)));
			}
		}
	}

}
package com.kuaiyou.lucky;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuaiyou.lucky.listenner.JedisExpiredListener;

@SpringBootApplication(scanBasePackages = { "com.kuaiyou.lucky" })
@EnableScheduling
@ServletComponentScan("com.kuaiyou.lucky.filters")
@EnableCaching
public class LuckyApplication {

	@Autowired
	JedisExpiredListener jedisExpiredListener;

	public static void main(String[] args) throws Exception {
		System.setProperty("spring.profiles.default", "test");
		SpringApplication.run(LuckyApplication.class, args);
	}

	@Bean
	public ObjectMapper initObjectMapper() {
		// 解决接口参数map映射不当
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
		return objectMapper;
	}

	@Bean
	public RedisMessageListenerContainer listenerContainer(RedisConnectionFactory redisConnection) {
		ChannelTopic channelTopic = new ChannelTopic(JedisExpiredListener.LISTENER_PATTERN);
		RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
		redisMessageListenerContainer.setConnectionFactory(redisConnection);
		redisMessageListenerContainer.addMessageListener(jedisExpiredListener, channelTopic);
		return redisMessageListenerContainer;
	}

}

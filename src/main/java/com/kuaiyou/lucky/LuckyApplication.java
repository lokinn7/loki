package com.kuaiyou.lucky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication(scanBasePackages = { "com.kuaiyou.lucky" })
@EnableScheduling
@ServletComponentScan("com.kuaiyou.lucky.filters")
@EnableCaching
public class LuckyApplication {


	public static void main(String[] args) throws Exception {
		System.setProperty("spring.profiles.default", "product");
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

}

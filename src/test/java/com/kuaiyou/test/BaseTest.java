package com.kuaiyou.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kuaiyou.lucky.LuckyApplication;

@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LuckyApplication.class, properties = "application-test.properties")

public class BaseTest {

	static {
		System.setProperty("spring.profiles.default", "product");
	}
}

package com.kuaiyou.lucky.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;

/**
 * @author liuyijie
 */
@EnableTransactionManagement
@Configuration
@MapperScan({ "com.kuaiyou.lucky.mapper" })
public class MybatisPlusConf {

	/**
	 * mybatis-plus SQL执行效率插件【生产环境可以关闭】 @Profile({"dev","test"})// 设置 dev test
	 * 环境开启
	 */
	@Bean
	public PerformanceInterceptor performanceInterceptor() {
		PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
		performanceInterceptor.setWriteInLog(true);
		return performanceInterceptor;
	}

	/**
	 * mybatis-plus分页插件<br>
	 * 文档：http://mp.baomidou.com<br>
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		// 开启 PageHelper 的支持
		paginationInterceptor.setLocalPage(true);
		return paginationInterceptor;
	}

}
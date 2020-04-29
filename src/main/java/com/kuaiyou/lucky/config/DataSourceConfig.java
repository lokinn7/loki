package com.kuaiyou.lucky.config;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.fastjson.parser.ParserConfig;

@Configuration
public class DataSourceConfig {

	DataSourceConfig() {
		// 打开autotype功能
		ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
	}

	@Value("${jdbc.url}")
	private String url;
	@Value("${jdbc.username}")
	private String username;
	@Value("${jdbc.password}")
	private String password;

	/**
	 * 注册dataSource
	 * 
	 * @return
	 * @throws SQLException
	 */
	@Bean(name = "basisDataSource", initMethod = "init", destroyMethod = "close")
	public DruidDataSource dataSource() throws SQLException {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUrl(url);
		druidDataSource.setUsername(username);
		druidDataSource.setPassword(password);
		// 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：
		// 监控统计用的filter:stat
		// 日志用的filter:log4j
		// 防御sql注入的filter:wall
		druidDataSource.setFilters("stat,slf4j");
		// WallFilter wallFilter = new WallFilter();
		// WallConfig wallConfig = new WallConfig();
		// wallFilter.setConfig(wallConfig);
		// wallConfig.setMultiStatementAllow(Boolean.TRUE);
		// druidDataSource.setProxyFilters(Arrays.asList(wallFilter));
		// 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
		druidDataSource.setInitialSize(10);
		// 最大连接池数量
		druidDataSource.setMaxActive(128);
		// 最小连接池数量
		druidDataSource.setMinIdle(8);
		// 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
		druidDataSource.setMaxWait(1000);
		// 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
		druidDataSource.setTestWhileIdle(true);

		druidDataSource.init();
		return druidDataSource;
	}

	/**
	 *
	 * //白名单： //servletRegistrationBean.addInitParameter("allow","127.0.0.1");
	 * //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted
	 * to view this page.
	 * //servletRegistrationBean.addInitParameter("deny","192.168.1.73");
	 * //登录查看信息的账号密码.
	 * 
	 * @return
	 */
	@Bean
	public ServletRegistrationBean druidServlet() {

		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
				"/druid/*");

		// 添加初始化参数：initParams

		servletRegistrationBean.addInitParameter("loginUsername", "rtbkv");
		servletRegistrationBean.addInitParameter("loginPassword", "rtbkuaiyou123");
		// 是否能够重置数据.
		servletRegistrationBean.addInitParameter("resetEnable", "true");
		return servletRegistrationBean;

	}

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,/boardf");
		return filterRegistrationBean;
	}

}

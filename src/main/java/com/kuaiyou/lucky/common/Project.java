package com.kuaiyou.lucky.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "project")
public class Project {

	private String appkey;
	private String appsecret;
	private String openkey;
	private String opensecret;
	// --------------小程序------------------//
	// 开奖通知
	private String model1;
	// --------------公众号------------------//
	// 签到通知
	private String model2;
	// 报名锦鲤活动成功通知 (锦鲤活动通知)
	private String model3;
	// 开奖结果通知
	private String model4;
	// 预约成功通知(预约签到)
	private String model5;

	private Integer coinlimit;

	private String jdkey;
	private String jdopensecret;
	private String customerinfo;
	private Integer roundcoin;

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getModel1() {
		return model1;
	}

	public void setModel1(String model1) {
		this.model1 = model1;
	}

	public String getOpenkey() {
		return openkey;
	}

	public void setOpenkey(String openkey) {
		this.openkey = openkey;
	}

	public String getOpensecret() {
		return opensecret;
	}

	public void setOpensecret(String opensecret) {
		this.opensecret = opensecret;
	}

	public String getModel2() {
		return model2;
	}

	public void setModel2(String model2) {
		this.model2 = model2;
	}

	public String getModel3() {
		return model3;
	}

	public void setModel3(String model3) {
		this.model3 = model3;
	}

	public String getModel4() {
		return model4;
	}

	public void setModel4(String model4) {
		this.model4 = model4;
	}

	public String getModel5() {
		return model5;
	}

	public void setModel5(String model5) {
		this.model5 = model5;
	}

	public Integer getCoinlimit() {
		return coinlimit;
	}

	public void setCoinlimit(Integer coinlimit) {
		this.coinlimit = coinlimit;
	}

	public String getJdkey() {
		return jdkey;
	}

	public void setJdkey(String jdkey) {
		this.jdkey = jdkey;
	}

	public String getJdopensecret() {
		return jdopensecret;
	}

	public void setJdopensecret(String jdopensecret) {
		this.jdopensecret = jdopensecret;
	}

	public String getCustomerinfo() {
		return customerinfo;
	}

	public void setCustomerinfo(String customerinfo) {
		this.customerinfo = customerinfo;
	}

	public Integer getRoundcoin() {
		return roundcoin;
	}

	public void setRoundcoin(Integer roundcoin) {
		this.roundcoin = roundcoin;
	}

	public Project() {

	}

}

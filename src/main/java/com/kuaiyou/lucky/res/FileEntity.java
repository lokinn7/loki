package com.kuaiyou.lucky.res;

import com.kuaiyou.lucky.utils.RandomUtil;

/**
 * @author liuyijie
 * @date 2017/6/7.
 */
public class FileEntity {
	private String url, path, name, uniname;

	public FileEntity() {
	}

	public FileEntity(String uri, String path, String name) {
		this.url = uri;
		this.path = path;
		this.name = name;
		this.uniname = RandomUtil.randomChar(8) + "_" + name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniname() {
		return uniname;
	}

	public void setUniname(String uniname) {
		this.uniname = uniname;
	}

}

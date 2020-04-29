package com.kuaiyou.lucky.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyijie
 * @date 2017/6/7.
 */
@Configuration
@ConfigurationProperties(prefix = "file.root")
public class UploadFilePropertyConfig {
	private String directory, folder, folder2, uri, qrpath;

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getFolder2() {
		return folder2;
	}

	public void setFolder2(String folder2) {
		this.folder2 = folder2;
	}

	public String getQrpath() {
		return qrpath;
	}

	public void setQrpath(String qrpath) {
		this.qrpath = qrpath;
	}

}
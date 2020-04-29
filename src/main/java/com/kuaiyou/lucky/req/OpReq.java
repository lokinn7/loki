package com.kuaiyou.lucky.req;

public class OpReq {
	private String path;
	private int scene;
	private Object query, referrerInfo;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getScene() {
		return scene;
	}

	public void setScene(int scene) {
		this.scene = scene;
	}

	public Object getQuery() {
		return query;
	}

	public void setQuery(Object query) {
		this.query = query;
	}

	public Object getReferrerInfo() {
		return referrerInfo;
	}

	public void setReferrerInfo(Object referrerInfo) {
		this.referrerInfo = referrerInfo;
	}

}

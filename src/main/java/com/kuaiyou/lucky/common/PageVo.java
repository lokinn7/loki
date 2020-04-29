package com.kuaiyou.lucky.common;

public class PageVo {

	private int page;
	private int skip = 50;
	private String starttime;
	private String endtime;
	private String[] sorts;
	private int order = 0;

	public int getPage() {
		return (page - 1) <= 0 ? 0 : (page - 1) * skip;
	}

	public int getCurrPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String[] getSorts() {
		return sorts;
	}

	public void setSorts(String[] sorts) {
		this.sorts = sorts;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}

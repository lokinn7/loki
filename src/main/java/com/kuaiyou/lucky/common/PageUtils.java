package com.kuaiyou.lucky.common;

import java.io.Serializable;
import java.util.List;

public class PageUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	// 总记录数
	private int totalCount;
	// 每页记录数
	private int pageSize;
	// 总页数
	private int totalPage;
	// 当前页数
	private int currPage;
	// 列表数据
	private List<?> grid;
	// 数据状态
	private Integer code;

	/**
	 * 分页
	 * 
	 * @param list
	 *            列表数据
	 * @param totalCount
	 *            总记录数
	 * @param limit
	 *            每页记录数
	 * @param skip
	 *            当前页数
	 */
	public PageUtils(List<?> grid, int totalCount, int page, int skip, Integer code) {
		this.code = code;
		this.grid = grid;
		this.totalCount = totalCount;
		this.currPage = page;
		this.pageSize = skip;
		this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
	}

	public PageUtils(List<?> grid, int totalCount, int page, int skip) {
		this.code = RetMap.SUCCESS_CODE;
		this.grid = grid;
		this.totalCount = totalCount;
		this.currPage = page;
		this.pageSize = skip;
		this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
	}

	public PageUtils(List<?> grid, Integer code) {
		this.grid = grid;
		this.code = code;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public List<?> getGrid() {
		return grid;
	}

	public void setGrid(List<?> grid) {
		this.grid = grid;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}

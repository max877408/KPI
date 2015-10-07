package com.fantasia.base.bean;

public class PageData {
	/**
	 * 当前页
	 */
	public int page;
	
	/**
	 * 记录条数
	 */
	public int rows;
	
	/**
	 * 查询最大记录数
	 */
	public static Integer MAX_ROWS = Integer.MAX_VALUE;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}	
}

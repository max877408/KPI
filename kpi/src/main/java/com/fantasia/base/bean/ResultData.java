package com.fantasia.base.bean;

public class ResultData {
	/**
	 * 总记录数
	 */
	public int total;
		
	/**
	 * 行列表
	 */
	public Object rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}
	
	
}

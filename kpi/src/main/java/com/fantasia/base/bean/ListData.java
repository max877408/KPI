package com.fantasia.base.bean;

public class ListData {
	/**
	 * 新增数据
	 */
	public String inserted;

	/**
	 * 删除数据
	 */
	public String deleted;

	/**
	 * 修改数据
	 */
	public String updated;
	
	/**
	 * 参数数据
	 */
	public String data;

	public String getInserted() {
		return inserted;
	}

	public void setInserted(String inserted) {
		this.inserted = inserted;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}

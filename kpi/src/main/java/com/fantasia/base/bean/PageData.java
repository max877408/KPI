package com.fantasia.base.bean;

import java.util.Calendar;
import java.util.Date;

import com.sun.org.apache.bcel.internal.generic.NEW;

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
	 * 关键任务
	 */
	public String keyTask;
	
	/**
	 * 年份
	 */
	public int year ;
	
	/**
	 * 月份
	 */
	public int month;
	
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

	public String getKeyTask() {
		return keyTask;
	}

	public void setKeyTask(String keyTask) {
		this.keyTask = keyTask;
	}

	public int getYear() {
		if(year == 0){
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());
			year = now.get(Calendar.YEAR);
		}
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}	
}

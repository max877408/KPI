package com.fantasia.base.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PageData extends WorkFlowData {
	private static Logger _log = LoggerFactory.getLogger(PageData.class);
	/**
	 * 当前页
	 */
	public int page = 1;
	
	/**
	 * 记录条数
	 */
	public int rows = 15;
	
	/**
	 * 其实位置
	 */
	public int start = 0;
	
	/**
	 * 关键任务
	 */
	public String keyTask;
	
	/**
	 * 年份
	 */
	public int year ;
	
	/**
	 * 开始时间
	 */
	public String startTime;
	
	/**
	 * 结束时间
	 */
	public String endTime;
	
	/**
	 * 月份
	 */
	public int month;
	
	/**
	 * 部门id
	 */
	public String deptId;
	
	/**
	 * 用户id
	 */
	public String userId;
	
	public String id;
	
	public String modifyBy;
	
	public Date modifyTime;
	
	/**
	 * 状态
	 * 任务提交状态 (1 未 提交 2已提交)
	 */
	public String status;
	
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
		if(year == 0 ){
			Calendar startTime = Calendar.getInstance();
			startTime.setTime(new Date());
			return startTime.get(Calendar.YEAR);
		}
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		if(month == 0 ){
			Calendar startTime = Calendar.getInstance();
			startTime.setTime(new Date());
			return (startTime.get(Calendar.MONTH) + 1);
		}
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getStart() {
		return this.start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getStartTime() {
		/*SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime;
		try {
			startTime = sdf.parse(getYear() + "-01-01");			
		} catch (ParseException e) {
			_log.error("日期转换失败",e);
			return null;
		}
		return startTime ;*/
		
		String startTime = getYear() + "-01-01";
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		/*SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
		Date endTime;
		try {			
			endTime = sdf.parse(getYear() + "-12-31");			
		} catch (ParseException e) {
			_log.error("日期转换失败",e);
			return null;
		}
		return endTime;*/
		String endTime = getYear() + "-12-31";
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}		
}

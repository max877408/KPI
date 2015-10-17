package com.fantasia.service;

import java.util.List;

import com.fantasia.base.bean.ListData;
import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.bean.KpiEmployeeYear;
import com.fantasia.bean.KpiEmployeeYearBean;
import com.fantasia.exception.ServiceException;

public interface KpiEmployeeService {
	
	/**
	 * 保存员工绩效
	 * @param list
	 * @param deptKpi
	 */
	public ResultMsg SaveKpiEmployee(ListData listData);
	
	/**
	 * 查询员工绩效考核指标
	 * @param page
	 * @return
	 */
	public ResultData getKpiEmployee(PageData page);
	
	/**
	 * 根据id获取员工绩效
	 * @param id
	 * @return
	 */
	public KpiEmployeeYearBean getKpiEmployee(String id);
	
	/**
	 * 根据id获取员工绩效明细id
	 * @param id
	 * @return
	 */
	public List<KpiEmployeeYear> getKpiEmployeeDetail(String id);
	
	/**
	 * 员工年度计划任务下发
	 * @param year
	 * @return
	 * @throws ServiceException
	 */	
	public ResultMsg saveEmployTask(PageData page);
}

package com.fantasia.service;

import java.util.List;

import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.bean.KpiEmployeeYear;
import com.fantasia.bean.KpiEmployeeYearBean;

public interface KpiEmployeeService {
	
	/**
	 * 保存员工绩效
	 * @param list
	 * @param deptKpi
	 */
	public void SaveKpiEmployee(List<KpiEmployeeYear> list,String deptKpi);
	
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
}

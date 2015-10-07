package com.fantasia.service;

import java.util.List;

import com.fantasia.base.bean.ResultData;
import com.fantasia.bean.KpiEmployeeYear;
import com.fantasia.bean.kpiEmployeeMonth;

public interface KpiEmployeeMonthService {
	
	/**
	 * 新增员工月度考核指标
	 * @param list
	 */
	public void inertEmployeeMonthKpi(List<KpiEmployeeYear> list);
	
	/**
	 * 保存员工月度绩效
	 * @param list
	 * @param deptKpi
	 */
	public void InsertKpi(kpiEmployeeMonth kpiEmployeeMonth);
	
	/**
	 * 查询员工月度绩效考核指标
	 * @param page
	 * @return
	 */
	public ResultData getKpiEmployeeMonth(int page,int rows,int kpiYear,int kpiMonth);
	
}

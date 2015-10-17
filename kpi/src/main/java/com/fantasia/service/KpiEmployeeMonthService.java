package com.fantasia.service;

import java.util.List;

import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.bean.KpiEmployeeYear;
import com.fantasia.bean.kpiEmployeeMonth;
import com.fantasia.exception.ServiceException;

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
	 * 查询员工月度PBC
	 * @param page
	 * @return
	 * @throws ServiceException
	 */	
	public ResultData getKpiEmployeeMonthList(PageData page);
	
}

package com.fantasia.service;

import java.util.List;

import com.fantasia.base.bean.ResultData;
import com.fantasia.bean.KpiDeptYearDetail;


public interface KpiDeptMonthService {
	
	/**
	 * 新增部门月度考核指标
	 * @param list
	 */
	public void inertDeptMonthKpi(List<KpiDeptYearDetail> list);	
	
	/**
	 * 查询部门月度绩效考核指标
	 * @param page
	 * @return
	 */
	public ResultData getKpiDeptMonth(int page,int rows,int kpiYear,int kpiMonth);
}

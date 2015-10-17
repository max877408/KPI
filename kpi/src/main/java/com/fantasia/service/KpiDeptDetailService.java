package com.fantasia.service;

import java.util.List;

import com.fantasia.bean.KpiDeptYearDetail;

public interface KpiDeptDetailService {
	
	/**
	 * 保存部门明细信息
	 * @param list
	 * @param deptKpi
	 */
	public void SaveKpiDetail(List<KpiDeptYearDetail> list,String deptKpi);
	
	/**
	 * 根据id获取部门绩效明细考核
	 * @param id
	 * @return
	 */
	public List<KpiDeptYearDetail> getKpiDeptDetailById(String id);
	
	/**
	 * 批量删除部门年度计划
	 * @param list
	 */
	public void bachDeleDeptKpi(List<KpiDeptYearDetail> list);
}

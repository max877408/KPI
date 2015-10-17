package com.fantasia.dao;

import java.util.List;

import com.fantasia.bean.KpiDeptYearDetail;

public interface KpiDeptYearDetailMapper {

	public List<KpiDeptYearDetail> getKpiDeptDetailById(String id);
	
	public void insert(KpiDeptYearDetail record);

	public void update(KpiDeptYearDetail record);
	
	/**
	 * 删除部门年度计划
	 * @param id
	 * @return
	 */	
	public void delDeptKpi(String id);

}
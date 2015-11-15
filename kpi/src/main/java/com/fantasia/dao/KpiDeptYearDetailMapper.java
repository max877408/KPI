package com.fantasia.dao;

import java.util.List;

import com.fantasia.base.bean.PageData;
import com.fantasia.bean.KpiDeptYearDetail;

public interface KpiDeptYearDetailMapper {
	
	/**
	 * 获取部门明细列表
	 * @param page
	 * @return
	 */
	public List<KpiDeptYearDetail> getKpiDept(PageData page);

	public List<KpiDeptYearDetail> getKpiDeptDetailById(String id);
	
	/**
	 * 根据id获取部门关键节点
	 * @param id
	 * @return
	 */
	public KpiDeptYearDetail selectById(String id);
	
	public void insert(KpiDeptYearDetail record);

	public void update(KpiDeptYearDetail record);
	
	/**
	 * 删除部门年度计划
	 * @param id
	 * @return
	 */	
	public void delDeptKpi(String id);

}
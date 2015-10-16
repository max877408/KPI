package com.fantasia.dao;

import java.util.List;

import com.fantasia.base.bean.PageData;
import com.fantasia.bean.KpiDeptYear;
import com.fantasia.bean.KpiDeptYearBean;

public interface KpiDeptYearMapper {
	
	public List<KpiDeptYearBean> getKpiDept(PageData page);
	
	public KpiDeptYearBean getKpiDeptById(String id);	
	
	public void insert(KpiDeptYear record);

	public void update(KpiDeptYear record);
	
	/**
	 * 删除部门年度计划
	 * @param id
	 * @return
	 */	
	public void delDeptKpiGroup(String id);
	
	/**
	 * 任务下发，更新状态
	 * @param page
	 */
	public void updateTask(PageData page);
 
}
package com.fantasia.dao;

import java.util.List;

import com.fantasia.base.bean.PageData;
import com.fantasia.bean.KpiEmployeeYear;
import com.fantasia.bean.KpiEmployeeYearBean;

public interface KpiEmployeeYearMapper {
	
	public List<KpiEmployeeYearBean> getKpiEmployee(PageData page);
	
	public KpiEmployeeYearBean getKpiEmployeeById(String id);
	
	public List<KpiEmployeeYear> getKpiEmployeeDetail(String id);

	public void insert(KpiEmployeeYear record);

	public void update(KpiEmployeeYear record);
	
	public void delete(String id);
	
	/**
	 * 任务下发，更新状态
	 * @param page
	 */
	public void updateTask(PageData page);
}
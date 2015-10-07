package com.fantasia.dao;

import java.util.List;

import com.fantasia.bean.KpiEmployeeYear;
import com.fantasia.bean.KpiEmployeeYearBean;

public interface KpiEmployeeYearMapper {
	
	public List<KpiEmployeeYearBean> getKpiEmployee(int start, int rows);
	
	public KpiEmployeeYearBean getKpiEmployeeById(String id);
	
	public List<KpiEmployeeYear> getKpiEmployeeDetail(String id);

	public void insert(KpiEmployeeYear record);

	public void update(KpiEmployeeYear record);
}
package com.fantasia.dao;

import java.util.List;

import com.fantasia.bean.KpiDeptYear;
import com.fantasia.bean.KpiDeptYearBean;

public interface KpiDeptYearMapper {
	
	public List<KpiDeptYearBean> getKpiDept(int start,int rows);
	
	public KpiDeptYearBean getKpiDeptById(String id);	
	
	public void insert(KpiDeptYear record);

	public void update(KpiDeptYear record);
 
}
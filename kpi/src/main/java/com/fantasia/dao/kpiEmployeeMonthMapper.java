package com.fantasia.dao;

import java.util.List;

import com.fantasia.bean.KpiDeptMonthBean;
import com.fantasia.bean.kpiEmployeeMonth;

public interface kpiEmployeeMonthMapper { 

	public List<KpiDeptMonthBean> getKpiEmpoyeeMonth(int kpiYear,int kpiMonth, int start,int rows);
	
    int insert(kpiEmployeeMonth record);  

    int update(kpiEmployeeMonth record);
}
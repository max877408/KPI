package com.fantasia.dao;

import java.util.List;

import com.fantasia.base.bean.PageData;
import com.fantasia.bean.KpiDeptMonthBean;
import com.fantasia.bean.kpiEmployeeMonth;

public interface kpiEmployeeMonthMapper { 

	public List<KpiDeptMonthBean> getKpiEmpoyeeMonth(PageData page);
	
    int insert(kpiEmployeeMonth record);  

    int update(kpiEmployeeMonth record);
}
package com.fantasia.dao;

import java.util.List;

import com.fantasia.bean.KpiDeptMonthBean;
import com.fantasia.bean.kpiDeptMonth;

public interface kpiDeptMonthMapper {
	
   public List<KpiDeptMonthBean> getKpiDeptMonth(int kpiYear,int kpiMonth, int start,int rows);
  
   public int insert(kpiDeptMonth record); 

   public int update(kpiDeptMonth record);
}
package com.fantasia.service;

import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.bean.KpiDeptYearBean;

public interface KpiDeptService {
	
	/**
	 * 保存部门绩效信息
	 * @param kpiDeptYear
	 * @return
	 */
	public String SaveKpiDept(KpiDeptYearBean kpiDeptYear);
	
	/**
	 * 查询部门绩效考核指标
	 * @param page
	 * @return
	 */
	public ResultData getKpiDept(PageData page);
	
	/**
	 * 根据id获取部门绩效考核
	 * @param id
	 * @return
	 */
	public KpiDeptYearBean getKpiDeptById(String id);
		
}

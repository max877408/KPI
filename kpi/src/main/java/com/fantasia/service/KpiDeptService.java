package com.fantasia.service;

import com.fantasia.base.bean.ListData;
import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.bean.KpiDeptYearBean;
import com.fantasia.exception.ServiceException;

public interface KpiDeptService {
	
	/**
	 * 保存部门绩效信息
	 * @param kpiDeptYear
	 * @return
	 */
	public ResultMsg SaveKpiDept(ListData listData);
	
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
	
	/**
	 * 删除部门年度计划
	 * @param id
	 * @return
	 */
	public ResultMsg delDeptKpiGroup(String id);	
	
	/**
	 * 部门年度计划任务下发
	 * @param year
	 * @return
	 * @throws ServiceException
	 */	
	public ResultMsg saveDeptTask(PageData page);
}

package com.fantasia.service;

import java.util.List;

import com.fantasia.base.bean.ListData;
import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.bean.KpiDeptYearDetail;
import com.fantasia.exception.ServiceException;


public interface KpiDeptMonthService {
	
	/**
	 * 新增部门月度考核指标
	 * @param list
	 */
	public void inertDeptMonthKpi(List<KpiDeptYearDetail> list);	
	
	/**
	 * 查询部门月度绩效考核指标
	 * @param page
	 * @return
	 */
	public ResultData getKpiDeptMonth(PageData page);
	
	/**
	 * 删除部门月度PBC
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public ResultMsg delMonthDeptKpi(String id);
	
	/**
	 * 保存部门月度PBC
	 * @param listData
	 * @return
	 * @throws ServiceException
	 */	
	public ResultMsg saveDeptMonthKpi(ListData listData);
	
	/**
	 * 部门月度评价提交审批
	 * @param year
	 * @return
	 * @throws ServiceException
	 */	
	public ResultMsg saveDeptApprove(PageData page);
}

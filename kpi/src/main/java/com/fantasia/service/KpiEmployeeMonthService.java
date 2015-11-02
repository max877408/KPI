package com.fantasia.service;

import java.util.List;

import com.fantasia.base.bean.ListData;
import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.bean.KpiEmployeeYear;
import com.fantasia.bean.kpiEmployeeMonth;
import com.fantasia.exception.ServiceException;

public interface KpiEmployeeMonthService {
	
	/**
	 * 新增员工月度考核指标
	 * @param list
	 */
	public void inertEmployeeMonthKpi(List<KpiEmployeeYear> list);
	
	/**
	 * 新增员工月度考核指标
	 * @param list
	 */
	public void inertEmployeeMonthKpi(PageData page);
	
	/**
	 * 保存员工月度绩效
	 * @param list
	 * @param deptKpi
	 */
	public void InsertKpi(kpiEmployeeMonth kpiEmployeeMonth);
	
	/**
	 * 查询员工月度PBC
	 * @param page
	 * @return
	 * @throws ServiceException
	 */	
	public ResultData getKpiEmployeeMonthList(PageData page);
	
	/**
	 * 删除员工月度PBC
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public ResultMsg delMonthEmployeeKpi(String id);
	
	/**
	 * 保存员工月度PBC
	 * @param listData
	 * @return
	 * @throws ServiceException
	 */		
	public ResultMsg saveEmployeeMonthKpi(ListData listData);
	
	/**
	 * 员工月度PBC提交审批
	 * @param year
	 * @return
	 * @throws ServiceException
	 */
	public ResultMsg saveEmployeePbcApprove(PageData page);
	
	/**
	 * 员工 月度评价提交审批
	 * @param year
	 * @return
	 * @throws ServiceException
	 */	
	public ResultMsg saveEmployeeApprove(PageData page);
}

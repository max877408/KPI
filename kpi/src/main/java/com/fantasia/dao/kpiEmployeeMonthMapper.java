package com.fantasia.dao;

import java.util.List;

import com.fantasia.base.bean.PageData;
import com.fantasia.bean.KpiDeptMonthBean;
import com.fantasia.bean.kpiEmployeeMonth;
import com.fantasia.exception.ServiceException;

public interface kpiEmployeeMonthMapper { 

	public List<KpiDeptMonthBean> getKpiEmpoyeeMonth(PageData page);
	
    int insert(kpiEmployeeMonth record);  

    int update(kpiEmployeeMonth record);
    
    /**
	 * 删除员工月度PBC
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public void delMonthEmployeeKpi(String id);
	
	/**
	 * 员工 月度评价提交审批
	 * @param year
	 * @return
	 * @throws ServiceException
	 */	
	public void saveEmployeeApprove(PageData page);
	
	/**
	 * 工作流提交申请，更新状态
	 * @param page
	 */
	public void updateTask(PageData page);
}
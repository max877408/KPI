package com.fantasia.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fantasia.base.action.BaseAction;
import com.fantasia.base.bean.ListData;
import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.exception.ServiceException;
import com.fantasia.service.KpiDeptMonthService;
import com.fantasia.service.KpiEmployeeMonthService;

@Controller
@RequestMapping(value = "/kpiMonth")
public class KpiMonthAction extends BaseAction {
	
	@Autowired
	private KpiDeptMonthService kpiDeptMonthService;
	
	@Autowired
	private KpiEmployeeMonthService kpiEmployeeMonthService;
	
	/**
	 * 查询部门月度PBC
	 * @param page
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/getKpiDeptMonthtList")
	@ResponseBody
	public ResultData getKpiDeptMonthList(PageData page) throws ServiceException {
		return kpiDeptMonthService.getKpiDeptMonth(page);
	}
	
	/**
	 * 删除部门月度PBC
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/delMonthDeptKpi")
	@ResponseBody
	public ResultMsg delMonthDeptKpi(String id) throws ServiceException {
		return kpiDeptMonthService.delMonthDeptKpi(id);
	}
	
	/**
	 * 保存部门月度PBC
	 * @param listData
	 * @return
	 * @throws ServiceException
	 */	
	@RequestMapping(value = "/saveDeptMonthKpi")
	@ResponseBody
	public ResultMsg saveDeptMonthKpi(ListData listData) throws ServiceException {
		return kpiDeptMonthService.saveDeptMonthKpi(listData);				
	}
	
	/**
	 * 部门月度评价提交审批
	 * @param year
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/saveDeptApprove")
	@ResponseBody
	public ResultMsg saveDeptApprove(PageData page)  {
		return kpiDeptMonthService.saveDeptApprove(page);
	}
	
	/**
	 * 查询员工月度PBC
	 * @param page
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/getKpiEmployeeMonthList")
	@ResponseBody
	public ResultData getKpiEmployeeMonthList(PageData page) throws ServiceException {
		return kpiEmployeeMonthService.getKpiEmployeeMonthList(page);
	}
	
	/**
	 * 删除员工月度PBC
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/delMonthEmployeeKpi")
	@ResponseBody
	public ResultMsg delMonthEmployeeKpi(String id) throws ServiceException {
		return kpiEmployeeMonthService.delMonthEmployeeKpi(id);
	}
	
	/**
	 * 保存员工月度PBC
	 * @param listData
	 * @return
	 * @throws ServiceException
	 */	
	@RequestMapping(value = "/saveEmployeeMonthKpi")
	@ResponseBody
	public ResultMsg saveEmployeeMonthKpi(ListData listData) throws ServiceException {
		return kpiEmployeeMonthService.saveEmployeeMonthKpi(listData);				
	}
	
	/**
	 * 员工 月度评价提交审批
	 * @param year
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/saveEmployeeApprove")
	@ResponseBody
	public ResultMsg saveEmployeeApprove(PageData page)  {
		return kpiEmployeeMonthService.saveEmployeeApprove(page);
	}
}

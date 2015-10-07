package com.fantasia.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fantasia.base.action.BaseAction;
import com.fantasia.base.bean.ResultData;
import com.fantasia.exception.ServiceException;
import com.fantasia.service.KpiDeptMonthService;
import com.fantasia.service.KpiEmployeeMonthService;

@Controller
@RequestMapping(value = "/kpiMonth")
public class KpiMonthAction extends BaseAction {

	private static Logger _log = LoggerFactory.getLogger(KpiMonthAction.class);		
	
	@Autowired
	private KpiDeptMonthService kpiDeptMonthService;
	
	@Autowired
	private KpiEmployeeMonthService kpiEmployeeMonthService;
	
	/**
	 * 查询部门月度绩效考核指标
	 * @param page
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/getKpiDeptMonthtList")
	@ResponseBody
	public ResultData getKpiDeptMonthList(int page,int rows,int kpiYear,int kpiMonth) throws ServiceException {
		return kpiDeptMonthService.getKpiDeptMonth(page,rows,kpiYear,kpiMonth);
	}
	
	/**
	 * 查询员工月度绩效考核指标
	 * @param page
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/getKpiEmployeeMonthList")
	@ResponseBody
	public ResultData getKpiEmployeeMonthList(int page,int rows,int kpiYear,int kpiMonth) throws ServiceException {
		return kpiEmployeeMonthService.getKpiEmployeeMonth(page,rows,kpiYear,kpiMonth);
	}
}

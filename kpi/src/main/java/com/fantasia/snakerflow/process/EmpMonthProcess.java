package com.fantasia.snakerflow.process;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasia.base.bean.PageData;
import com.fantasia.core.DbcContext;
import com.fantasia.dao.KpiEmployeeYearMapper;
import com.fantasia.service.KpiEmployeeMonthService;
import com.fantasia.service.KpiEmployeeService;

/**
 * 员工月度评价
 * @author Administrator
 *
 */
@Service("EmpMonthProcess")
public class EmpMonthProcess extends WorkFlowBase implements SflowProcess {

	
	@Autowired
	private KpiEmployeeMonthService kpiEmployeeMonthService;
	
	@Autowired
	private KpiEmployeeYearMapper kpiEmployeeYearMapper;
	
	@Autowired
	private KpiEmployeeService kpiEmployeeService;
	
	@Override
	public void process(HttpServletRequest request) {	
		String processId = request.getParameter("processId");
		
		//员工年度计划
		if(processId.equalsIgnoreCase("72f868734ac84742b6897f6b63bafff2")){
			employeeYearProcess(request);
		}
	}
	
	/**
	 * 员工年度计划工作流提交
	 * @param processId
	 * @param orderId
	 * @param taskId
	 * @param methodStr
	 */
	private void employeeYearProcess(HttpServletRequest request){
		String orderId = request.getParameter("orderId");		
		String methodStr = request.getParameter("method");
		String taskName ="apply";	
		String curNode = request.getParameter("curNode");	
		
		//获取工作流节点,部门负责人提交审批		
		if(curNode.equals("1")){
			
			//不同意
			if(methodStr.equalsIgnoreCase("-1")){
				
			}
		}
		//分管领导提交审批
		if(curNode.equals("2")){
			
			//同意
			if(methodStr.equalsIgnoreCase("0")){
				
				//修改状态
				PageData page = new PageData();
				page.setYear(getKpiYear(orderId, taskName));
				page.setUserId(getUserId(orderId, taskName));				
				//审批通过
				page.setStatus("3");				
				page.setModifyBy(DbcContext.getUserId());
				page.setModifyTime(new Date());
				kpiEmployeeYearMapper.updateTask(page);
				
				//修改部门年度计划责任人
				kpiEmployeeService.updateResPerson(page);				
				
				//根据员工年度计划生成员工月度考核指标
				kpiEmployeeMonthService.inertEmployeeMonthKpi(page);
			}
		}		
	}
}

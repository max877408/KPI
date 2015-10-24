package com.fantasia.core;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasia.dao.KpiDeptYearMapper;
import com.fantasia.service.KpiDeptMonthService;
import com.fantasia.snakerflow.engine.SnakerEngineFacets;

/**
 * kpi工作流辅助类
 * 
 * @author Administrator
 * 
 */
@Service("KpiWorkFlow")
public class KpiWorkFlow {
	
	@Autowired
    private SnakerEngineFacets facets;
	
	@Autowired
	private KpiDeptMonthService kpiDeptMonthService;
	
	@Autowired
	private KpiDeptYearMapper kpiDeptYearMapper;
	
	/**
	 * 工作流提交
	 * 
	 * @param request
	 */
	public void process(HttpServletRequest request) {
		String processId = request.getParameter("processId");
		/*String orderId = request.getParameter("orderId");
		String taskId = request.getParameter("taskId");
		String methodStr = request.getParameter("method");
		String taskName = request.getParameter("taskName");	*/
		
		//部门年度计划
		if(processId.equalsIgnoreCase("86129c15f5a3475ab84da1eebb2fc844")){	
			deptYearProcess(request);
		}
		
		//员工年度计划
		if(processId.equalsIgnoreCase("")){
			employeeYearProcess(request);
		}
	}
	
	/**
	 * 获取工作流参数 年份
	 * @param orderId
	 * @param taskName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Integer getKpiYear(String orderId,String taskName){
		Integer year  = 0;
		
		//获取工作流保存参数数据	
		Map<String, Object> tasks = facets.flowData(orderId, taskName);
		if(tasks != null && tasks.size() > 0){
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("vars");
			if(list != null && list.size() > 0){
				year = (Integer) list.get(list.size() - 1).get("year") ;
			}			
		}
				
		return year;
	}
	
	/**
	 * 获取工作流部门
	 * @param orderId
	 * @param taskName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDept(String orderId,String taskName){
		String dept = "";
		
		//获取工作流保存参数数据	
		Map<String, Object> tasks = facets.flowData(orderId, taskName);
		if(tasks != null && tasks.size() > 0){
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("vars");
			if(list != null && list.size() > 0){
				dept = (String) list.get(list.size() - 1).get("dept") ;
			}			
		}
				
		return dept;
	}
	
	/**
	 * 部门年度计划工作流提交
	 * @param processId
	 * @param orderId
	 * @param taskId
	 * @param methodStr
	 */
	private void deptYearProcess(HttpServletRequest request){
		String orderId = request.getParameter("orderId");		
		String methodStr = request.getParameter("method");
		String taskName ="apply";	
		String curNode = request.getParameter("curNode");	
		
		//获取工作流节点,分管领导提交审批		
		if(curNode.equals("1")){
			
			//不同意
			if(methodStr.equalsIgnoreCase("-1")){
				/*PageData page = new PageData();
				page.setYear(getKpiYear(orderId, taskName));
				page.setStatus("1");
				
				page.setModifyBy(DbcContext.getUserId());
				page.setModifyTime(new Date());
				kpiDeptYearMapper.updateTask(page);*/
				
			}
		}
		//人力专员提交审批
		if(curNode.equals("2")){
			
			//同意
			if(methodStr.equalsIgnoreCase("0")){
				
				//根据部门时间段生成部门月度考核指标
				kpiDeptMonthService.inertDeptMonthKpi(null);
			}
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
		String methodStr = request.getParameter("method");
		//同意
		if(methodStr.equalsIgnoreCase("0")){
			
		}
		else if(methodStr.equalsIgnoreCase("-1")){
			
		}
	}
}

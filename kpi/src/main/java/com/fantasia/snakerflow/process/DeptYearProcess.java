package com.fantasia.snakerflow.process;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasia.base.bean.PageData;
import com.fantasia.core.DbcContext;
import com.fantasia.dao.KpiDeptYearMapper;
import com.fantasia.service.KpiDeptMonthService;

/**
 * 不能年度计划工作流处理
 * @author Administrator
 *
 */
@Service("DeptYearProcess")
public class DeptYearProcess  extends WorkFlowBase implements SflowProcess  {

	@Autowired
	private KpiDeptYearMapper kpiDeptYearMapper;
	
	@Autowired
	private KpiDeptMonthService kpiDeptMonthService;
	
	@Override
	public void process(HttpServletRequest request) {
		String processId = request.getParameter("processId");
		
		//部门年度计划
		if(processId.equalsIgnoreCase("34bd21ff94ea4e158ef8fc13ea49b2c4")){	
			deptYearProcess(request);
		}				
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
				
				PageData page = new PageData();
				page.setYear(getKpiYear(orderId, taskName));
				page.setDeptId(getDept(orderId, taskName));
				
				//审批通过
				page.setStatus("3");
				
				page.setModifyBy(DbcContext.getUserId());
				page.setModifyTime(new Date());
				kpiDeptYearMapper.updateTask(page);
				
				//根据部门年度计划生成部门月度考核指标
				kpiDeptMonthService.inertDeptMonthKpi(page);
			}
		}		
	}
}

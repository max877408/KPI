package com.fantasia.snakerflow.process;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasia.base.bean.PageData;
import com.fantasia.bean.KpiDeptMonthBean;
import com.fantasia.bean.kpiEmployeeMonth;
import com.fantasia.core.DbcContext;
import com.fantasia.dao.kpiEmployeeMonthMapper;
import com.fantasia.service.KpiEmployeeService;

/**
 * 员工月度PBC
 * @author Administrator
 *
 */
@Service("EmpMonthPbcProcess")
public class EmpMonthPbcProcess extends WorkFlowBase implements SflowProcess {
	
	@Autowired
	private KpiEmployeeService kpiEmployeeService;
	
	@Autowired
	private kpiEmployeeMonthMapper kpiEmployeeMonthMapper;
	
	@Override
	public void process(HttpServletRequest request) {	
		String processId = request.getParameter("processId");
		
		//员工月度pbc
		if(processId.equalsIgnoreCase("39a799627c834ea582ccaaac9980d727")){
			employeeMonthProcess(request);
		}
	}

	/**
	 * 员工月度pbc审批
	 * @param request
	 */
	private void employeeMonthProcess(HttpServletRequest request){
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
				page.setStart(0);
				page.setRows(PageData.MAX_ROWS);
				page.setYear(getKpiYear(orderId, taskName));				
				if(getKpiMonth(orderId, taskName) != null){
					page.setMonth(getKpiMonth(orderId, taskName));
				}				
				page.setUserId(getUserId(orderId, taskName));
			
				
				List<KpiDeptMonthBean> list = kpiEmployeeMonthMapper.getKpiEmpoyeeMonth(page);
				if(list != null && list.size() > 0){
					for (KpiDeptMonthBean kpiDeptMonthBean : list) {
						kpiEmployeeMonth record = new kpiEmployeeMonth();
						record.setId(kpiDeptMonthBean.getKpiId());
						record.setAuditStatus("3");
						record.setModifyBy(DbcContext.getUserId());
						record.setModifyTime(new Date());  
						kpiEmployeeMonthMapper.update(record);
					}
				}
				//kpiEmployeeYearMapper.updateTask(page);
				
				//修改部门年度计划责任人
				kpiEmployeeService.updateResPerson(page);				
			}
		}		
	}
}

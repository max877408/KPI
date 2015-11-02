package com.fantasia.snakerflow.process;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasia.base.bean.PageData;
import com.fantasia.bean.KpiDeptMonthBean;
import com.fantasia.bean.kpiDeptMonth;
import com.fantasia.core.DbcContext;
import com.fantasia.dao.kpiDeptMonthMapper;

/**
 * 部门月度PBC
 * @author Administrator
 *
 */
@Service("DeptMonthPbcProcess")
public class DeptMonthPbcProcess  extends WorkFlowBase implements SflowProcess  {

	@Autowired
	private kpiDeptMonthMapper kpiDeptMonthMapper;
	
	@Override
	public void process(HttpServletRequest request) {
		String processId = request.getParameter("processId");
		
		//部门月度PBC
		if(processId.equalsIgnoreCase("bce88d9731ed4ec0aab14d6adace6cc5")){	
			deptYearProcess(request);
		}				
	}
	
	/**
	 * 部门月度PBC工作流提交
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
				
			}
		}
		//人力专员提交审批
		if(curNode.equals("2")){
			
			//同意
			if(methodStr.equalsIgnoreCase("0")){
				
				PageData page = new PageData();
				page.setStart(0);
				page.setRows(PageData.MAX_ROWS);
				page.setYear(getKpiYear(orderId, taskName));
				page.setDeptId(getDept(orderId, taskName));
				page.setMonth(getKpiMonth(orderId, taskName));			
				
				List<KpiDeptMonthBean> list = kpiDeptMonthMapper.getKpiDeptMonth(page);		
				if(list != null && list.size() > 0){
					for (KpiDeptMonthBean kpiDeptMonthBean : list) {
						kpiDeptMonth record = new kpiDeptMonth();
						record.setId(kpiDeptMonthBean.getId());
						//审批通过
						record.setAuditStatus("3");				
						record.setModifyBy(DbcContext.getUserId());
						record.setModifyTime(new Date());
						kpiDeptMonthMapper.update(record);
					}
				}				
				//kpiDeptMonthMapper.updateTask(page);
			}
		}		
	}
}

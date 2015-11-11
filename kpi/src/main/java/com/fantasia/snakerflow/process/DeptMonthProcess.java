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
import com.fantasia.service.KpiDeptMonthService;

/**
 * 部门月度评价
 * @author Administrator
 *
 */
@Service("DeptMonthProcess")
public class DeptMonthProcess  extends WorkFlowBase implements SflowProcess  {

	@Autowired
	private kpiDeptMonthMapper kpiDeptMonthMapper;
	
	@Autowired
	private KpiDeptMonthService kpiDeptMonthService;
	
	@Override
	public void process(HttpServletRequest request) {
		String processId = request.getParameter("processId");
		
		//部门月度评价
		if(processId.equalsIgnoreCase("4f0a1cfec6954908b6d2271d0a870e35")){	
			deptYearProcess(request);
		}				
	}
	
	/**
	 * 部门月度评价工作流提交
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
			if(methodStr != null && methodStr.equalsIgnoreCase("-1")){
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
			if(methodStr != null && methodStr.equalsIgnoreCase("0")){
				
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
						record.setAuditStatus("6");				
						record.setModifyBy(DbcContext.getUserId());
						record.setModifyTime(new Date());
						kpiDeptMonthMapper.update(record);
					}
				}
				
				//根据部门年度计划生成部门月度考核指标
				kpiDeptMonthService.inertDeptMonthKpi(page);
			}
		}		
	}
}

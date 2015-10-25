package com.fantasia.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasia.base.bean.ListData;
import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.bean.KpiEmployeeYear;
import com.fantasia.bean.KpiEmployeeYearBean;
import com.fantasia.bean.PubUser;
import com.fantasia.core.DbcContext;
import com.fantasia.core.KpiWorkFlow;
import com.fantasia.core.Utils;
import com.fantasia.dao.KpiEmployeeYearMapper;
import com.fantasia.exception.ServiceException;
import com.fantasia.service.KpiEmployeeMonthService;
import com.fantasia.service.KpiEmployeeService;
import com.fantasia.service.PubUserService;
import com.fantasia.util.StringUtils;
import com.fantasia.workflow.KpiFlowService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("KpiEmployeeService")
public class KpiEmployeeServiceImpl implements KpiEmployeeService {

	private static Logger _log = LoggerFactory.getLogger(KpiEmployeeServiceImpl.class);
	
	@Autowired
	private KpiEmployeeYearMapper kpiEmployeeYearMapper;
	
	@Autowired
	private KpiEmployeeMonthService kpiEmployeeMonthService;
	
	@Autowired
	private PubUserService pubUserService;
	
	@Autowired
	private KpiFlowService kpiFlowService;
	
	@Autowired
	private KpiWorkFlow kpiWorkFlow;
	
	@Override
	public ResultData getKpiEmployee(PageData page) {
		
		ResultData data = new ResultData();
		page.setStart((page.getPage() -1) * page.getRows());
		if(!DbcContext.isAdmin()){
			if(StringUtils.isAnyoneEmpty(page.getUserId())){
				page.setUserId(DbcContext.getUser().getUserName());
			}
		}	
		
		List<KpiEmployeeYearBean> list = kpiEmployeeYearMapper.getKpiEmployee(page);
		data.setRows(list);
		
		//TotalRows 
		PageData totalPage = page;		
		totalPage.setStart(0);
		totalPage.setRows(PageData.MAX_ROWS);
		list = kpiEmployeeYearMapper.getKpiEmployee(totalPage);
		data.setTotal(list.size());
		return data;	
	}

	@Override
	public KpiEmployeeYearBean getKpiEmployee(String id) {
		return kpiEmployeeYearMapper.getKpiEmployeeById(id);
	}

	/**
	 * 保存员工年度计划
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultMsg SaveKpiEmployee(ListData listData) {		
		
		ResultMsg resultMsg = new ResultMsg();
		String deptKpi = listData.getData();
		ObjectMapper objectMapper = new ObjectMapper();
		List<KpiEmployeeYear> kpiEmployee = new ArrayList<KpiEmployeeYear>();

		try {
						
			/**
			 * 新增员工绩效明细信息
			 */
			if (!StringUtils.isEmpty(listData.getInserted())) {

				kpiEmployee = (List<KpiEmployeeYear>) objectMapper.readValue(
						listData.getInserted(),
						new TypeReference<List<KpiEmployeeYear>>() {
						});
				SaveKpiEmployee(kpiEmployee,deptKpi);
			}

			/**
			 * 修改员工绩效明细信息
			 */
			if (!StringUtils.isEmpty(listData.getUpdated())) {

				kpiEmployee = (List<KpiEmployeeYear>) objectMapper.readValue(listData.getUpdated(),
						new TypeReference<List<KpiEmployeeYear>>() {
						});
				SaveKpiEmployee(kpiEmployee,deptKpi);
			}

			/**
			 * 删除员工绩效明细信息
			 */
			if (!StringUtils.isEmpty(listData.getDeleted())) {

				kpiEmployee = (List<KpiEmployeeYear>) objectMapper.readValue(
						listData.getDeleted(),
						new TypeReference<List<KpiEmployeeYear>>() {
						});
				deleteKpiEmployee(kpiEmployee);
			}
		} catch (Exception e) {
			_log.error(e.getMessage());
			resultMsg.setCode("101");
			resultMsg.setErrorMsg(e.getMessage());			
		}	
		
		return resultMsg;
	}
	
	/**
	 * 保存员工年度计划
	 * @param kpiEmployee
	 * @param deptKpi
	 */
	private void SaveKpiEmployee(List<KpiEmployeeYear> list,String deptKpi){
		if(list != null && list.size() > 0){
			for (KpiEmployeeYear kpiEmployeeYear : list) {
				if(!org.springframework.util.StringUtils.isEmpty(kpiEmployeeYear.getId())){				
					kpiEmployeeYear.setModifyBy(DbcContext.getUserId());
					kpiEmployeeYear.setModifyTime(new Date());
					kpiEmployeeYearMapper.update(kpiEmployeeYear);
				}
				else{
					
					kpiEmployeeYear.setId(Utils.getGUID());	
					kpiEmployeeYear.setStatus("1");
					kpiEmployeeYear.setKpiId(deptKpi);
					kpiEmployeeYear.setCreateBy(DbcContext.getUserId());
					kpiEmployeeYear.setCreateTime(new Date());
					kpiEmployeeYearMapper.insert(kpiEmployeeYear);
				}				
			}
			
			//新增员工月度绩效考核指标
			kpiEmployeeMonthService.inertEmployeeMonthKpi(list);
		}
	}
	
	/**
	 * 删除员工年度计划
	 * @param list
	 */
	private void deleteKpiEmployee(List<KpiEmployeeYear> list){
		if(list != null && list.size() > 0){
			for (KpiEmployeeYear kpiEmployeeYear : list) {
				kpiEmployeeYearMapper.delete(kpiEmployeeYear.getId());			
			}
		}
	}

	@Override
	public List<KpiEmployeeYear> getKpiEmployeeDetail(String id) {		
		return kpiEmployeeYearMapper.getKpiEmployeeDetail(id);
	}
	
	/**
	 * 员工年度计划任务下发
	 * @param year
	 * @return
	 * @throws ServiceException
	 */	
	@Override
	public ResultMsg saveEmployTask(PageData page){
		ResultMsg msg = new ResultMsg();		
		
		//查看当前年度是否有员工年度计划
		page.setPage(1);
		ResultData data = getKpiEmployee(page);
		if(data != null && data.getTotal() == 0){			
			msg.setCode("100");
			msg.setErrorMsg("当前无员工年度计划任务!");
			return msg;
		}
		
		//检测是否配置部门负责人
		PubUser pubuser = pubUserService.getDeptChare(DbcContext.getUser().getDeptName());
		if(pubuser == null){
			msg.setCode("101");
			msg.setErrorMsg("当前部门("+DbcContext.getUser().getDeptName()+")部门负责人为空,请先配置部门负责人!");
			return msg;
		}
		
		//检测是否配置分管领导
		if(StringUtils.isEmpty(DbcContext.getUser().getChargeLeader())){
			msg.setCode("101");
			msg.setErrorMsg("当前部门("+DbcContext.getUser().getDeptName()+")分管领导为空,请先配置分管领导!");
			return msg;
		}
		
		page.setModifyBy(DbcContext.getUserId());
		page.setModifyTime(new Date());                              
		kpiEmployeeYearMapper.updateTask(page);	
		
		//启动工作流		
		 Map<String, Object> params = new HashMap<String, Object>();		
		 params.put("processId", "86a4c28073f045bf85404a7e012faf96");
		
		 if(!DbcContext.getRequest().getParameter("orderId").equals("null")){
			 params.put("orderId", DbcContext.getRequest().getParameter("orderId"));
		 }
		 else{
			 params.put("orderId", "");
		 }
		 if(!DbcContext.getRequest().getParameter("taskId").equals("null")){
			 params.put("taskId", DbcContext.getRequest().getParameter("taskId"));
		 }
		 else{
			 params.put("taskId", "");
		 }
		
		 if(!params.get("orderId").equals("") && ! params.get("taskId").equals("")){
			 params.put("year",kpiWorkFlow.getKpiYear(DbcContext.getRequest().getParameter("orderId"), DbcContext.getRequest().getParameter("taskName")));
		 }
		 else{
			 params.put("year", page.getYear());
		 }		 
		 params.put("userId", DbcContext.getUser().getUserName());	
		 
		 //判断是否部门负责人
		 if(pubuser.getId().equalsIgnoreCase(DbcContext.getUserId())){
			 params.put("charge", 1);	
		 }
		 else{
			 params.put("charge", 0);	
		 }
		 
		 //申请人
		 params.put("apply.operator", DbcContext.getUser().getUserName());
		 //部门负责人审批
		 params.put("approveDept.operator", pubuser.getUserName());
		 //分管领导审批
		 params.put("approveBoss.operator", DbcContext.getUser().getChargeLeader());		 
		 
		 kpiFlowService.process(params);
				 
		return msg;
	}
}

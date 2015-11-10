package com.fantasia.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fantasia.base.bean.ListData;
import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.bean.KpiDeptMonthBean;
import com.fantasia.bean.KpiEmployeeMonthBean;
import com.fantasia.bean.KpiEmployeeYear;
import com.fantasia.bean.KpiEmployeeYearBean;
import com.fantasia.bean.PubUser;
import com.fantasia.bean.kpiEmployeeMonth;
import com.fantasia.core.DbcContext;
import com.fantasia.core.Utils;
import com.fantasia.dao.KpiEmployeeYearMapper;
import com.fantasia.dao.kpiEmployeeMonthMapper;
import com.fantasia.exception.ServiceException;
import com.fantasia.service.KpiEmployeeMonthService;
import com.fantasia.service.PubUserService;
import com.fantasia.snakerflow.process.KpiWorkFlow;
import com.fantasia.workflow.KpiFlowService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("KpiEmployeeMonthService")
public class KpiEmployeeMonthServiceImpl implements KpiEmployeeMonthService {

	private static Logger _log = LoggerFactory.getLogger(KpiEmployeeMonthServiceImpl.class);
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private kpiEmployeeMonthMapper kpiEmployeeMonthMapper;
	
	@Autowired
	private KpiEmployeeYearMapper kpiEmployeeYearMapper;
	
	@Autowired
	private PubUserService pubUserService;
	
	@Autowired
	private KpiFlowService kpiFlowService;
	
	@Autowired
	private KpiWorkFlow kpiWorkFlow;

	@Override
	public void InsertKpi(kpiEmployeeMonth kpiEmployeeMonth) {
		kpiEmployeeMonthMapper.insert(kpiEmployeeMonth);		
	}
	
	/**
	 * 新增员工月度考核指标
	 * @param list
	 */
	public void inertEmployeeMonthKpi(List<KpiEmployeeYear> list){
		if(list != null && list.size() > 0){
			SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
			for (KpiEmployeeYear kpiEmployeeYear : list) {
				try {
					Date startDate = sdf.parse(kpiEmployeeYear.getStartTime()) ;
					Date endDate = sdf.parse(kpiEmployeeYear.getEndTime()) ;
					Calendar startTime = Calendar.getInstance();
					startTime.setTime(startDate);
					Calendar endTime = Calendar.getInstance();
					endTime.setTime(endDate);
					
					
					if(startTime.get(Calendar.YEAR)== endTime.get(Calendar.YEAR)){
						for(int month =startTime.get(Calendar.MONTH); month <= (endTime.get(Calendar.MONTH) + 1); month++){
							kpiEmployeeMonth record = new kpiEmployeeMonth();
							record.setId(Utils.getGUID());
							record.setKpiId(kpiEmployeeYear.getId());
							record.setKpiMonth(month);
							record.setKpiYear(startTime.get(Calendar.YEAR));
							record.setCreateBy(DbcContext.getAdminId());
							record.setCreateTime(new Date());
							record.setStatus("1");
							kpiEmployeeMonthMapper.insert(record);
						}
					}
					else{
						//跨年情况
						for(int month = startTime.get(Calendar.MONTH); month <= 12; month++){
							kpiEmployeeMonth record = new kpiEmployeeMonth();
							record.setId(Utils.getGUID());
							record.setKpiId(kpiEmployeeYear.getId());
							record.setKpiMonth(month);
							record.setKpiYear(startTime.get(Calendar.YEAR));
							record.setCreateBy(DbcContext.getAdminId());
							record.setCreateTime(new Date());
							record.setStatus("1");
							kpiEmployeeMonthMapper.insert(record);
						}
						
						for(int month = 1; month <=  endTime.get(Calendar.MONTH); month++){
							kpiEmployeeMonth record = new kpiEmployeeMonth();
							record.setId(Utils.getGUID());
							record.setKpiId(kpiEmployeeYear.getId());
							record.setKpiMonth(month);
							record.setKpiYear(endTime.get(Calendar.YEAR));
							record.setCreateBy(DbcContext.getAdminId());
							record.setCreateTime(new Date());
							record.setStatus("1");
							kpiEmployeeMonthMapper.insert(record);
						}
					}
					
				} catch (ParseException e) {
					_log.error("date convert error",e);
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 新增员工月度考核指标
	 * @param list
	 */
	public void inertEmployeeMonthKpi(PageData page){
		page.setStart(0);
		page.setRows(PageData.MAX_ROWS);
		
		//获取员工年度计划考核列表
		List<KpiEmployeeYearBean> list = kpiEmployeeYearMapper.getKpiEmployee(page);
		
		if(list != null && list.size() > 0){
			SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
			for (KpiEmployeeYearBean kpiEmployeeYear : list) {
				try {
					Date startDate = sdf.parse(kpiEmployeeYear.getStartTime()) ;
					Date endDate = sdf.parse(kpiEmployeeYear.getEndTime()) ;
					Calendar startTime = Calendar.getInstance();
					startTime.setTime(startDate);
					Calendar endTime = Calendar.getInstance();
					endTime.setTime(endDate);
					
					
					if(startTime.get(Calendar.YEAR)== endTime.get(Calendar.YEAR)){
						for(int month = (startTime.get(Calendar.MONTH)+1); month <= (endTime.get(Calendar.MONTH) + 1); month++){
							kpiEmployeeMonth record = new kpiEmployeeMonth();
							
							//获取年度计划明细
							 List<KpiEmployeeYear> lstDetail = kpiEmployeeYearMapper.getKpiEmployeeDetail(kpiEmployeeYear.getId());
							if(lstDetail != null && lstDetail.size() > 0){
								for (KpiEmployeeYear temp : lstDetail) {
									
									//任务分工
									if(!StringUtils.isEmpty(temp.getTaskDivision())){
										String[] taskDivision = temp.getTaskDivision().split(",");
										for(int i=0; i< taskDivision.length; i++){
											record.setKpiId(temp.getKpiId());
											record.setUserId(taskDivision[i]);
											record.setKpiMonth(month);
											record.setKpiYear(startTime.get(Calendar.YEAR));
											record.setId(Utils.getGUID());									
											record.setCreateBy(DbcContext.getUserId());
											record.setCreateTime(new Date());
											record.setStatus("1");
											kpiEmployeeMonthMapper.insert(record);
										}
									}									
								}
							}
							
						/*	String resPerson = kpiEmployeeYear.getResponsiblePerson();
							if(!StringUtils.isEmpty(resPerson)){
								String[] pers = resPerson.split(",");
								for(int i=0 ; i< pers.length ; i++){
									
									//月度绩效不存在则新增
									List<KpiDeptMonthBean> temp = kpiEmployeeMonthMapper.getKpiEmpoyeeMonth(page);
									if(temp == null || temp.size() == 0){
										record.setKpiId(kpiEmployeeYear.getId());
										record.setUserId(pers[i]);
										record.setKpiMonth(month);
										record.setKpiYear(startTime.get(Calendar.YEAR));
										record.setId(Utils.getGUID());									
										record.setCreateBy(DbcContext.getUserId());
										record.setCreateTime(new Date());
										record.setStatus("1");
										kpiEmployeeMonthMapper.insert(record);
									}
								}
							}*/								
						}
					}
				} catch (ParseException e) {
					_log.error("date convert error",e);
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 查询员工月度PBC
	 * @param page
	 * @return
	 * @throws ServiceException
	 */	
	public ResultData getKpiEmployeeMonthList(PageData page){
		
		ResultData data = new ResultData();
		page.setStart((page.getPage() -1) * page.getRows());	
		if(!DbcContext.isAdmin()){
			if(StringUtils.isEmpty(page.getUserId())){
				page.setUserId(DbcContext.getUser().getUserName());
			}
		}		
		
		List<KpiDeptMonthBean> list = kpiEmployeeMonthMapper.getKpiEmpoyeeMonth(page);
		data.setRows(list);
		
		//TotalRows 
		PageData totalPage = page;
	
		totalPage.setStart(0);
		totalPage.setRows(PageData.MAX_ROWS);
		list = kpiEmployeeMonthMapper.getKpiEmpoyeeMonth(totalPage);
		data.setTotal(list.size());
		return data;	
	}
	
	/**
	 * 删除员工月度PBC
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public ResultMsg delMonthEmployeeKpi(String id){
		ResultMsg resultMsg = new ResultMsg();
		kpiEmployeeMonthMapper.delMonthEmployeeKpi(id);
		return resultMsg;
	}
	
	/**
	 * 保存员工月度PBC
	 * @param listData
	 * @return
	 * @throws ServiceException
	 */		
	@SuppressWarnings("unchecked")
	public ResultMsg saveEmployeeMonthKpi(ListData listData){
		ResultMsg resultMsg = new ResultMsg();		
		
		List<KpiEmployeeMonthBean> kpiEmployeeDetail = new ArrayList<KpiEmployeeMonthBean>();

		try {			
			
			/**
			 * 新增员工绩效明细信息
			 */
			if (!StringUtils.isEmpty(listData.getInserted())) {

				kpiEmployeeDetail = (List<KpiEmployeeMonthBean>) objectMapper.readValue(
						listData.getInserted(),
						new TypeReference<List<KpiEmployeeMonthBean>>() {
						});
				saveEmployeeMonthKpi(kpiEmployeeDetail);
			}

			/**
			 * 修改员工绩效明细信息
			 */
			if (!StringUtils.isEmpty(listData.getUpdated())) {

				kpiEmployeeDetail = (List<KpiEmployeeMonthBean>) objectMapper.readValue(
						listData.getUpdated(),
						new TypeReference<List<KpiEmployeeMonthBean>>() {
						});
				saveEmployeeMonthKpi(kpiEmployeeDetail);
			}
			
		} catch (Exception e) {
			_log.error(e.getMessage());
			resultMsg.setCode("101");
			resultMsg.setErrorMsg(e.getMessage());			
		}
		
		return resultMsg;
	}
	
	/**
	 * 保存员工月度PBC
	 * @param list
	 */
	private void saveEmployeeMonthKpi(List<KpiEmployeeMonthBean> list){
		if(list != null && list.size() > 0){
			for (KpiEmployeeMonthBean kpiEmployeeMonth : list) {
				if(!StringUtils.isEmpty(kpiEmployeeMonth.getKpiId())){
					kpiEmployeeMonth record = new kpiEmployeeMonth();
					record.setId(kpiEmployeeMonth.getKpiId());
					record.setWeight(kpiEmployeeMonth.getWeight());
					record.setStandard(kpiEmployeeMonth.getStandard());
					record.setYearTarget(kpiEmployeeMonth.getYearTarget());
					record.setFinishValue(kpiEmployeeMonth.getFinishValue());
					record.setFinishDesc(kpiEmployeeMonth.getFinishDesc());
					record.setSeftScore(kpiEmployeeMonth.getSeftScore());
					
					//工作流审批，提交考核得分
					if(!StringUtils.isEmpty(kpiEmployeeMonth.getExamScore())){
						record.setExamScore(kpiEmployeeMonth.getExamScore());
						
						if(!kpiEmployeeMonth.getExamScore().equals(kpiEmployeeMonth.getSeftScore())){
							String remark = DbcContext.getUser().getUserName() + "修改考核得分为：" + kpiEmployeeMonth.getExamScore() + ";";
							record.setRemark(remark);
						}
					}
					
					record.setModifyBy(DbcContext.getUserId());
					record.setModifyTime(new Date());
					
					kpiEmployeeMonthMapper.update(record);
				}
				else{
					kpiEmployeeMonth record = new kpiEmployeeMonth();
					
					record.setId(Utils.getGUID());	
					record.setWeight(kpiEmployeeMonth.getWeight());
					record.setStandard(kpiEmployeeMonth.getStandard());
					record.setYearTarget(kpiEmployeeMonth.getYearTarget());
					record.setFinishValue(kpiEmployeeMonth.getFinishValue());
					record.setFinishDesc(kpiEmployeeMonth.getFinishDesc());
					record.setSeftScore(kpiEmployeeMonth.getSeftScore());
					
					record.setCreateBy(DbcContext.getUserId());
					record.setCreateTime(new Date());
					kpiEmployeeMonthMapper.insert(record);
				}
			}
		}
	}
	
	/**
	 * 员工月度PBC提交审批
	 * @param year
	 * @return
	 * @throws ServiceException
	 */	
	@SuppressWarnings({"unchecked" })
	public ResultMsg saveEmployeePbcApprove(PageData page){
		ResultMsg msg = new ResultMsg();	

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
		
		// 查看当前员工月度PBC
		page.setPage(1);
		ResultData data = getKpiEmployeeMonthList(page);
		if (data != null && data.getTotal() == 0) {
			msg.setCode("100");
			msg.setErrorMsg("当前无员工月度PBC!");
			return msg;
		}
		else{			
			//更新状态为提交申请
			List<KpiDeptMonthBean> list = (List<KpiDeptMonthBean>) data.getRows();
			if(list != null && list.size() > 0){
				for (KpiDeptMonthBean kpiDeptMonthBean : list) {
					kpiEmployeeMonth record = new kpiEmployeeMonth();
					record.setId(kpiDeptMonthBean.getKpiId());
					record.setAuditStatus("2");
					record.setModifyBy(DbcContext.getUserId());
					record.setModifyTime(new Date());  
					kpiEmployeeMonthMapper.update(record);
				}
			}
			//kpiEmployeeMonthMapper.saveEmployeeApprove(page);
			
		}
			
		
		//启动工作流		
		 Map<String, Object> params = new HashMap<String, Object>();		
		 params.put("processId", "39a799627c834ea582ccaaac9980d727");
		
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
			 params.put("month", page.getMonth());
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
	
	/**
	 * 员工 月度评价提交审批
	 * @param year
	 * @return
	 * @throws ServiceException
	 */	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResultMsg saveEmployeeApprove(PageData page){
		ResultMsg msg = new ResultMsg();	

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
		
		// 查看当前员工月度PBC
		page.setPage(1);
		ResultData data = getKpiEmployeeMonthList(page);
		if (data != null && data.getTotal() == 0) {
			msg.setCode("100");
			msg.setErrorMsg("当前无员工月度PBC!");
			return msg;
		}
		else{			
			//更新状态为提交申请
			List<KpiDeptMonthBean> list = (List<KpiDeptMonthBean>) data.getRows();
			if(list != null && list.size() > 0){
				for (KpiDeptMonthBean kpiDeptMonthBean : list) {
					kpiEmployeeMonth record = new kpiEmployeeMonth();
					record.setId(kpiDeptMonthBean.getKpiId());
					record.setAuditStatus("5");
					record.setModifyBy(DbcContext.getUserId());
					record.setModifyTime(new Date());  
					kpiEmployeeMonthMapper.update(record);
				}
			}
			//kpiEmployeeMonthMapper.saveEmployeeApprove(page);			
		}			
		
		//启动工作流		
		 Map<String, Object> params = new HashMap<String, Object>();		
		 params.put("processId", "92f7776b077243f6bc7c64e16525eb2d");
		
		 if(page.getOrderId() != null && !page.getOrderId().equals("null")){
			 params.put("orderId", page.getOrderId());
		 }
		 else{
			 params.put("orderId", "");
		 }
		 if(page.getTaskId() != null && !page.getTaskId().equals("null")){
			 params.put("taskId", page.getTaskId());
		 }
		 else{
			 params.put("taskId", "");
		 }
		
		 if(page.getTaskId() != null && !page.getTaskId().equals("")){
			 params.put("year",kpiWorkFlow.getKpiYear(page.getTaskId(), page.getTaskName()));
		 }
		 else{
			 params.put("year", page.getYear());
			 params.put("month", page.getMonth());
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

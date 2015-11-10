package com.fantasia.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fantasia.base.action.BaseAction;
import com.fantasia.base.bean.ListData;
import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.bean.KpiDeptYearBean;
import com.fantasia.bean.KpiDeptYearDetail;
import com.fantasia.bean.KpiEmployeeYear;
import com.fantasia.bean.KpiEmployeeYearBean;
import com.fantasia.bean.KpiGroupYear;
import com.fantasia.core.DbcContext;
import com.fantasia.exception.ServiceException;
import com.fantasia.service.KpiDeptDetailService;
import com.fantasia.service.KpiDeptService;
import com.fantasia.service.KpiEmployeeService;
import com.fantasia.service.KpiGroupService;
import com.fantasia.snakerflow.process.KpiWorkFlow;
import com.fantasia.util.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/kpiYear")
public class KpiYearAction extends BaseAction {

	private static Logger _log = LoggerFactory.getLogger(KpiYearAction.class);

	@Autowired
	private KpiGroupService kpiGroupService;
	
	@Autowired
	private KpiDeptService kpiDeptService; 
	
	@Autowired
	private KpiDeptDetailService  kpiDeptDetailService;
	
	@Autowired
	private KpiEmployeeService kpiEmployeeService;
	
	@Autowired
	private KpiWorkFlow kpiWorkFlow;

	/***************************** 年度关键任务部分 *********************************/
	
	/**
	 * 保存集团年度计划
	 * 
	 * @param kpiGroupYear
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SaveKpiGroup")
	@ResponseBody
	public ResultMsg SaveKpiGroup(ListData listData) throws ServiceException {

		ResultMsg resultMsg = new ResultMsg();
		ObjectMapper objectMapper = new ObjectMapper();
		List<KpiGroupYear> kpiGroups = new ArrayList<KpiGroupYear>();

		try {
			if (!StringUtils.isEmpty(listData.getInserted())) {

				kpiGroups = (List<KpiGroupYear>) objectMapper.readValue(
						listData.getInserted(),
						new TypeReference<List<KpiGroupYear>>() {
						});
				kpiGroupService.SaveKpiGroup(kpiGroups);
			}

			if (!StringUtils.isEmpty(listData.getUpdated())) {

				kpiGroups = (List<KpiGroupYear>) objectMapper.readValue(
						listData.getUpdated(),
						new TypeReference<List<KpiGroupYear>>() {
						});
				 kpiGroupService.SaveKpiGroup(kpiGroups);
			}

			if (!StringUtils.isEmpty(listData.getDeleted())) {

				kpiGroups = (List<KpiGroupYear>) objectMapper.readValue(
						listData.getDeleted(),
						new TypeReference<List<KpiGroupYear>>() {
						});
				kpiGroupService.batchDelete(kpiGroups);
			}
		} catch (Exception e) {
			_log.error(e.getMessage());
			resultMsg.setCode("101");
			resultMsg.setErrorMsg(e.getMessage());			
		}
		
		return resultMsg;		
	}
	
	/**
	 * 保存年度计划关键任务字段
	 * 
	 * @param kpiGroupYear
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/SaveKpiGroupTask")
	@ResponseBody
	public ResultMsg SaveKpiGroupTask(String id, String keyTask){
		
		KpiGroupYear kpi = new KpiGroupYear();	
		kpi.setId(id);
		kpi.setKeyTask(keyTask);
		
		return kpiGroupService.SaveKpiGroupTask(kpi);
	}
	
	/**
	 * 查询集团业务指标列表
	 * @param page
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/getKpiGroup")
	@ResponseBody
	public ResultData getKpiGroup(PageData page) throws ServiceException {
		return kpiGroupService.getKpiGroup(page);		
	}
	
	/**
	 * 获取关键任务列表
	 * @param keyTask
	 * @return
	 */
	@RequestMapping(value = "/getKpiGroupList")
	@ResponseBody
	public List<KpiGroupYear> getKpiGroupList(String year,String keyTask){
		return kpiGroupService.getKpiGroupList(year,keyTask);
	}
	
	/**
	 * 查询关键任务列表
	 * @param keyTask
	 * @param year
	 * @return
	 */
	@RequestMapping(value = "/searchKpiGroupList")
	@ResponseBody
	public List<KpiGroupYear> searchKpiGroupList(String keyTask,String year){
		return kpiGroupService.searchKpiGroupList(keyTask, year);
	}
	
	/**
	 * 删除集团绩效考核指标
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/delKpiGroup")
	@ResponseBody
	public ResultMsg delKpiGroup(String id) throws ServiceException {
		return kpiGroupService.DeleteKpiGroup(id);
	}
	
	/**
	 * 年度关键任务下发
	 * @param year
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/saveTask")
	@ResponseBody
	public ResultMsg saveTask(PageData page)  {
		return kpiGroupService.saveTask(page);
	}
	
	
	/***************************** 部门年度计划部分 *********************************/
	
	/**
	 * 查询部门绩效考核指标
	 * @param page
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/getKpiDeptList")
	@ResponseBody	
	public ResultData getKpiDeptList(PageData page,HttpServletRequest request) throws ServiceException {
		
		//获取工作流保存参数数据
		String orderId = request.getParameter("orderId");
		String taskName = request.getParameter("taskName");	
		if(!StringUtils.isAnyoneEmpty(orderId,taskName)){
			page.setYear(kpiWorkFlow.getKpiYear(orderId, taskName)) ;
			page.setDeptId(kpiWorkFlow.getDept(orderId, taskName)) ;	
		}
		else{
			if(!DbcContext.isDeptChare()){
				return null;
			}
		}
		
		return kpiDeptService.getKpiDept(page);
	}
	
	/**
	 * 根据id获取部门绩效
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/getKpiDept")
	@ResponseBody
	public KpiDeptYearBean getKpiDept(String id) throws ServiceException {
		return kpiDeptService.getKpiDeptById(id);
	}
	
	/**
	 * 根据id获取部门绩效明细信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/getKpiDeptDetail")
	@ResponseBody
	public List<KpiDeptYearDetail> getKpiDeptDetail(String id) throws ServiceException {
		return kpiDeptDetailService.getKpiDeptDetailById(id);
	}
	
	/**
	 * 保存部门绩效考核指标
	 * @param listData
	 * @return
	 * @throws ServiceException
	 */	
	@RequestMapping(value = "/SaveKpiDept")
	@ResponseBody
	public ResultMsg SaveKpiDept(ListData listData) throws ServiceException {
		return kpiDeptService.SaveKpiDept(listData);				
	}
	

	/**
	 * 删除部门年度计划
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/delDeptKpiGroup")
	@ResponseBody
	public ResultMsg delDeptKpiGroup(String id) throws ServiceException {
		return kpiDeptService.delDeptKpiGroup(id);
	}
	
	/**
	 * 部门年度计划任务下发
	 * @param year
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/saveDeptTask")
	@ResponseBody
	public ResultMsg saveDeptTask(PageData page)  {
		return kpiDeptService.saveDeptTask(page);
	}
	
	
	/***************************** 员工年度计划部分 *********************************/
	
	/**
	 * 查询员工绩效考核指标
	 * @param page
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/getKpiEmployeeList")
	@ResponseBody
	public ResultData getKpiEmployeeList(PageData page,HttpServletRequest request) throws ServiceException {	
		//获取工作流保存参数数据
		String orderId = request.getParameter("orderId");
		String taskName = request.getParameter("taskName");	
		if(!StringUtils.isAnyoneEmpty(orderId,taskName)){
			if(!DbcContext.isAdmin()){
				page.setYear(kpiWorkFlow.getKpiYear(orderId, taskName));				
				page.setUserId(kpiWorkFlow.getUserId(orderId, taskName));	
			}			
		}
		return kpiEmployeeService.getKpiEmployee(page);
	}
	
	/**
	 * 根据id获取员工绩效
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/getKpiEmployee")
	@ResponseBody
	public KpiEmployeeYearBean getKpiEmployee(String id) throws ServiceException {
		return kpiEmployeeService.getKpiEmployee(id);
	}
	
	/**
	 * 根据id获取员工绩效明细信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/getKpiEmployeeDetail")
	@ResponseBody
	public List<KpiEmployeeYear> getKpiEmployeeDetail(String id) throws ServiceException {
		return kpiEmployeeService.getKpiEmployeeDetail(id);
	}
	
	/**
	 * 保存员工绩效考核
	 * @param listData
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/SaveKpiEmployee")
	@ResponseBody
	public ResultMsg SaveKpiEmployee(ListData listData) throws ServiceException {		
		return kpiEmployeeService.SaveKpiEmployee(listData);		
	}
	
	/**
	 * 员工年度计划任务下发
	 * @param year
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/saveEmployTask")
	@ResponseBody
	public ResultMsg saveEmployTask(PageData page)  {
		return kpiEmployeeService.saveEmployTask(page);
	}
}

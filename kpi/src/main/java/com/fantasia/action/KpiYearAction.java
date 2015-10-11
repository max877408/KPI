package com.fantasia.action;

import java.util.ArrayList;
import java.util.List;

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
import com.fantasia.exception.ServiceException;
import com.fantasia.service.KpiDeptDetailService;
import com.fantasia.service.KpiDeptService;
import com.fantasia.service.KpiEmployeeService;
import com.fantasia.service.KpiGroupService;
import com.fantasia.util.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/kpiYear")
public class KpiYearAction extends BaseAction {

	private static Logger _log = LoggerFactory.getLogger(KpiYearAction.class);

	@Autowired
	private KpiGroupService kpiDeptGroupService;
	
	@Autowired
	private KpiDeptService kpiDeptService; 
	
	@Autowired
	private KpiDeptDetailService  kpiDeptDetailService;
	
	@Autowired
	private KpiEmployeeService kpiEmployeeService;

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
				kpiDeptGroupService.SaveKpiGroup(kpiGroups);
			}

			if (!StringUtils.isEmpty(listData.getUpdated())) {

				kpiGroups = (List<KpiGroupYear>) objectMapper.readValue(
						listData.getUpdated(),
						new TypeReference<List<KpiGroupYear>>() {
						});
				 kpiDeptGroupService.SaveKpiGroup(kpiGroups);
			}

			if (!StringUtils.isEmpty(listData.getDeleted())) {

				kpiGroups = (List<KpiGroupYear>) objectMapper.readValue(
						listData.getDeleted(),
						new TypeReference<List<KpiGroupYear>>() {
						});
				 //kpiDeptGroupService.DeleteKpiGroup(kpiGroups);
			}
		} catch (Exception e) {
			_log.error(e.getMessage());
			resultMsg.setCode("101");
			resultMsg.setErrorMsg(e.getMessage());			
		}
		
		return resultMsg;		
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
		return kpiDeptGroupService.getKpiGroup(page);		
	}
	
	/**
	 * 获取关键任务列表
	 * @param keyTask
	 * @return
	 */
	@RequestMapping(value = "/getKpiGroupList")
	@ResponseBody
	public List<KpiGroupYear> getKpiGroupList(String keyTask){
		return kpiDeptGroupService.getKpiGroupList(keyTask);
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
		return kpiDeptGroupService.searchKpiGroupList(keyTask, year);
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
		return kpiDeptGroupService.DeleteKpiGroup(id);
	}
	
	/**
	 * 查询部门绩效考核指标
	 * @param page
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/getKpiDeptList")
	@ResponseBody
	public ResultData getKpiDeptList(PageData page) throws ServiceException {
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SaveKpiDept")
	@ResponseBody
	public ResultMsg SaveKpiDept(ListData listData) throws ServiceException {

		ResultMsg resultMsg = new ResultMsg();
		String deptKpi = "";
		ObjectMapper objectMapper = new ObjectMapper();
		List<KpiDeptYearDetail> kpiDeptDetail = new ArrayList<KpiDeptYearDetail>();

		try {
			
			//保存部门绩效相关信息
			if (!StringUtils.isEmpty(listData.getData())) {

				KpiDeptYearBean kpiBean = objectMapper.readValue(listData.getData(),KpiDeptYearBean.class);
				deptKpi = kpiDeptService.SaveKpiDept(kpiBean);
			}
			
			/**
			 * 新增部门绩效明细信息
			 */
			if (!StringUtils.isEmpty(listData.getInserted())) {

				kpiDeptDetail = (List<KpiDeptYearDetail>) objectMapper.readValue(
						listData.getInserted(),
						new TypeReference<List<KpiDeptYearDetail>>() {
						});
				kpiDeptDetailService.SaveKpiDetail(kpiDeptDetail,deptKpi);
			}

			/**
			 * 修改部门绩效明细信息
			 */
			if (!StringUtils.isEmpty(listData.getUpdated())) {

				kpiDeptDetail = (List<KpiDeptYearDetail>) objectMapper.readValue(
						listData.getUpdated(),
						new TypeReference<List<KpiDeptYearDetail>>() {
						});
				kpiDeptDetailService.SaveKpiDetail(kpiDeptDetail,deptKpi);
			}

			/**
			 * 删除部门绩效明细信息
			 */
			if (!StringUtils.isEmpty(listData.getDeleted())) {

				kpiDeptDetail = (List<KpiDeptYearDetail>) objectMapper.readValue(
						listData.getDeleted(),
						new TypeReference<List<KpiDeptYearDetail>>() {
						});
				 //kpiDeptGroupService.DeleteKpiGroup(kpiGroups);
			}
		} catch (Exception e) {
			_log.error(e.getMessage());
			resultMsg.setCode("101");
			resultMsg.setErrorMsg(e.getMessage());			
		}
		
		return resultMsg;		
	}
	
	
	/**
	 * 查询员工绩效考核指标
	 * @param page
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/getKpiEmployeeList")
	@ResponseBody
	public ResultData getKpiEmployeeList(PageData page) throws ServiceException {		
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SaveKpiEmployee")
	@ResponseBody
	public ResultMsg SaveKpiEmployee(ListData listData) throws ServiceException {

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
				kpiEmployeeService.SaveKpiEmployee(kpiEmployee,deptKpi);
			}

			/**
			 * 修改员工绩效明细信息
			 */
			if (!StringUtils.isEmpty(listData.getUpdated())) {

				kpiEmployee = (List<KpiEmployeeYear>) objectMapper.readValue(
						listData.getUpdated(),
						new TypeReference<List<KpiEmployeeYear>>() {
						});
				kpiEmployeeService.SaveKpiEmployee(kpiEmployee,deptKpi);
			}

			/**
			 * 删除员工绩效明细信息
			 */
			if (!StringUtils.isEmpty(listData.getDeleted())) {

				kpiEmployee = (List<KpiEmployeeYear>) objectMapper.readValue(
						listData.getDeleted(),
						new TypeReference<List<KpiEmployeeYear>>() {
						});
				 //kpiEmployeeService.DeleteKpiGroup(kpiGroups);
			}
		} catch (Exception e) {
			_log.error(e.getMessage());
			resultMsg.setCode("101");
			resultMsg.setErrorMsg(e.getMessage());			
		}
		
		return resultMsg;		
	}
}

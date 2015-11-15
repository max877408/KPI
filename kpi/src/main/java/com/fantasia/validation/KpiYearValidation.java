package com.fantasia.validation;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.fantasia.bean.KpiDeptYearDetail;
import com.fantasia.bean.KpiEmployeeYear;
import com.fantasia.bean.KpiGroupYear;
import com.fantasia.core.Utils;
import com.fantasia.exception.ServiceException;
import com.fantasia.service.KpiDeptDetailService;
import com.fantasia.service.KpiGroupService;
import com.fantasia.util.DateTimeUtil;

public class KpiYearValidation {

	private static KpiGroupService kpiGroupService;

	private static KpiDeptDetailService kpiDeptDetailService;

	public static KpiGroupService getKpiGroupService() {
		if (kpiGroupService == null) {
			kpiGroupService =  Utils.webApplication.getBean(KpiGroupService.class);
		}
		return kpiGroupService;
	}

	public static KpiDeptDetailService getKpiDeptDetailService() {
		if (kpiDeptDetailService == null) {
			kpiDeptDetailService =  Utils.webApplication.getBean(KpiDeptDetailService.class);
		}
		return kpiDeptDetailService;
	}

	/**
	 * 验证年度关键任务
	 * 
	 * @param kpiGroups
	 * @throws ServiceException
	 */
	public static void validateYearKeyTask(List<KpiGroupYear> kpiGroups)
			throws ServiceException {
		if (kpiGroups != null && kpiGroups.size() > 0) {
			for (int i = 0; i < kpiGroups.size(); i++) {
				if (kpiGroups.get(i).getKeyTask().length() > 130) {
					throw new ServiceException("101", "关键任务长度超长！");
				}

				if (StringUtils.isEmpty(kpiGroups.get(i).getKeyItem())) {
					throw new ServiceException("101", "主要事项不能为空！");
				}

				if (StringUtils.isEmpty(kpiGroups.get(i).getDetailItem())) {
					// throw new ServiceException("101","具体事项不能为空！");
					kpiGroups.remove(i);
					continue;
				}

				if (StringUtils.isEmpty(kpiGroups.get(i).getDept())) {
					throw new ServiceException("101", "部门不能为空！");
				}

				if (StringUtils.isEmpty(kpiGroups.get(i).getStartTime())) {
					throw new ServiceException("101", "开始日期不能为空！");
				}

				if (StringUtils.isEmpty(kpiGroups.get(i).getEndTime())) {
					throw new ServiceException("101", "结束日期不能为空！");
				}

				DataTimeValidate.valiateTime(kpiGroups.get(i).getStartTime(),kpiGroups.get(i).getEndTime());

				validateYearKeyTaskStatus(kpiGroups.get(i).getStartTime(),kpiGroups.get(i).getEndTime());
			}
		}
	}

	/**
	 * 判断当前年度关键任务是否已任务下发
	 * 
	 * @param startTime
	 * @param endTime
	 * @throws ServiceException
	 */
	public static void validateYearKeyTaskStatus(String startTime,String endTime) throws ServiceException {
		Date startDate = DateTimeUtil.StrToDate(startTime);
		Date endDate = DateTimeUtil.StrToDate(endTime);

		Calendar cstartTime = Calendar.getInstance();
		cstartTime.setTime(startDate);
		Calendar cendTime = Calendar.getInstance();
		cendTime.setTime(endDate);

		if (cstartTime.get(Calendar.YEAR) != cendTime.get(Calendar.YEAR)) {
			throw new ServiceException("101", "时间不能跨年！");
		} else {
			List<KpiGroupYear> list = getKpiGroupService().getKpiGroupList("" + cstartTime.get(Calendar.YEAR),null );
			if (list != null && list.size() > 0) {
				String auditStatus = list.get(0).getAuditStatus();
				if (auditStatus.equalsIgnoreCase("1") == false) {
					throw new ServiceException("101", "当前年度:"+ cstartTime.get(Calendar.YEAR) + "任务已下发,不能添加！");
				}
			}
		}
	}
	
	/**
	 * 验证部门年度计划
	 * 
	 * @param kpiGroups
	 * @throws ServiceException
	 */
	public static void validateYearDept(KpiGroupYear kpiGroupYear,List<KpiDeptYearDetail> kpiDeptDetail)
			throws ServiceException {
		if (kpiDeptDetail != null && kpiDeptDetail.size() > 0) {
			
			//年度关键任务时间
			Date kstartDate = DateTimeUtil.StrToDate(kpiGroupYear.getStartTime());
			Date kendDate = DateTimeUtil.StrToDate(kpiGroupYear.getEndTime());
			
			for (int i = 0; i < kpiDeptDetail.size(); i++) {				
			   //部门年度计划关键节点时间
				Date dstartDate = DateTimeUtil.StrToDate(kpiDeptDetail.get(i).getStartTime());
				Date dendDate = DateTimeUtil.StrToDate(kpiDeptDetail.get(i).getEndTime());
				
				if (DateTimeUtil.compare_date(dstartDate, dendDate) == true) {
					throw new ServiceException("101", "关键节点开始时间不能大于结束日期！");
				}
				
				if (dstartDate.equals(kstartDate) == false && DateTimeUtil.compare_date(dstartDate, kstartDate) == false) {
					throw new ServiceException("101", "关键节点开始时间范围不正确！");
				}
				
				if (dstartDate.equals(kendDate) == false && DateTimeUtil.compare_date(dstartDate, kendDate) == true) {
					throw new ServiceException("101", "关键节点开始时间范围不正确！");
				}

				if (dendDate.equals(kstartDate) == false && DateTimeUtil.compare_date(dendDate, kstartDate) == false) {
					throw new ServiceException("101", "关键节点结束时间范围不正确！");
				}
				
				if (dendDate.equals(kendDate) == false && DateTimeUtil.compare_date(dendDate, kendDate) == true) {
					throw new ServiceException("101", "关键节点结束时间范围不正确！");
				}
			}
		}
	}
	
	/**
	 * 验证员工年度计划
	 * 
	 * @param kpiGroups
	 * @throws ServiceException
	 */
	public static void validateYearEmployee(String kpiDetailId,List<KpiEmployeeYear> kpiEmployees)
			throws ServiceException {
		if (kpiEmployees != null && kpiEmployees.size() > 0) {
			
			KpiDeptYearDetail deptDetail = getKpiDeptDetailService().selectById(kpiDetailId);
			if(deptDetail == null){
				return ;
			}
			
			//年度关键任务时间
			Date kstartDate = DateTimeUtil.StrToDate(deptDetail.getStartTime());
			Date kendDate = DateTimeUtil.StrToDate(deptDetail.getEndTime());
			
			for (int i = 0; i < kpiEmployees.size(); i++) {				
			   //部门年度计划关键节点时间
				Date dstartDate = DateTimeUtil.StrToDate(kpiEmployees.get(i).getStartTime());
				Date dendDate = DateTimeUtil.StrToDate(kpiEmployees.get(i).getEndTime());
				
				if (DateTimeUtil.compare_date(dstartDate, dendDate) == true) {
					throw new ServiceException("101", "工作阶段开始时间不能大于结束日期！");
				}
				
				if (dstartDate.equals(kstartDate) == false && DateTimeUtil.compare_date(dstartDate, kstartDate) == false) {
					throw new ServiceException("101", "工作阶段开始时间范围不正确！");
				}
				
				if (dstartDate.equals(kendDate) == false && DateTimeUtil.compare_date(dstartDate, kendDate) == true) {
					throw new ServiceException("101", "工作阶段开始时间范围不正确！");
				}

				if (dendDate.equals(kstartDate) == false && DateTimeUtil.compare_date(dendDate, kstartDate) == false) {
					throw new ServiceException("101", "工作阶段结束时间范围不正确！");
				}
				
				if (dendDate.equals(kendDate) == false && DateTimeUtil.compare_date(dendDate, kendDate) == true) {
					throw new ServiceException("101", "工作阶段结束时间范围不正确！");
				}
			}
		}
	}
}

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
import org.springframework.util.StringUtils;

import com.fantasia.base.bean.ListData;
import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.bean.KpiDeptMonthBean;
import com.fantasia.bean.KpiDeptYearBean;
import com.fantasia.bean.KpiDeptYearDetail;
import com.fantasia.bean.kpiDeptMonth;
import com.fantasia.core.DbcContext;
import com.fantasia.core.Utils;
import com.fantasia.dao.KpiDeptYearMapper;
import com.fantasia.dao.kpiDeptMonthMapper;
import com.fantasia.exception.ServiceException;
import com.fantasia.service.KpiDeptMonthService;
import com.fantasia.snakerflow.process.KpiWorkFlow;
import com.fantasia.workflow.KpiFlowService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("KpiDeptMonthService")
public class KpiDeptMonthServiceImpl implements KpiDeptMonthService {

	@Autowired
	private kpiDeptMonthMapper kpiDeptMonthMapper;

	@Autowired
	private KpiDeptYearMapper kpiDeptYearMapper;

	@Autowired
	private KpiWorkFlow kpiWorkFlow;
	
	@Autowired
	private KpiFlowService kpiFlowService;

	private ObjectMapper objectMapper = new ObjectMapper();

	private static Logger _log = LoggerFactory
			.getLogger(KpiDeptMonthServiceImpl.class);

	/**
	 * 新增部门月度考核指标
	 * 
	 * @param list
	 */
	public void inertDeptMonthKpi(List<KpiDeptYearDetail> list) {
		if (list != null && list.size() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (KpiDeptYearDetail kpiDeptYearDetail : list) {
				try {
					Date startDate = sdf
							.parse(kpiDeptYearDetail.getStartTime());
					Date endDate = sdf.parse(kpiDeptYearDetail.getEndTime());
					Calendar startTime = Calendar.getInstance();
					startTime.setTime(startDate);
					Calendar endTime = Calendar.getInstance();
					endTime.setTime(endDate);

					if (startTime.get(Calendar.YEAR) == endTime
							.get(Calendar.YEAR)) {
						for (int month = startTime.get(Calendar.MONTH) + 1; month <= endTime
								.get(Calendar.MONTH) + 1; month++) {
							kpiDeptMonth record = new kpiDeptMonth();
							record.setId(Utils.getGUID());
							record.setKpiId(kpiDeptYearDetail.getId());
							record.setKpiMonth(month);
							record.setKpiYear(startTime.get(Calendar.YEAR));
							record.setCreateBy(DbcContext.getAdminId());
							record.setCreateTime(new Date());
							record.setStatus("1");
							kpiDeptMonthMapper.insert(record);
						}
					} else {
						// 跨年情况
						for (int month = startTime.get(Calendar.MONTH) + 1; month <= 12; month++) {
							kpiDeptMonth record = new kpiDeptMonth();
							record.setId(Utils.getGUID());
							record.setKpiId(kpiDeptYearDetail.getId());
							record.setKpiMonth(++month);
							record.setKpiYear(startTime.get(Calendar.YEAR));
							record.setCreateBy(DbcContext.getAdminId());
							record.setCreateTime(new Date());
							record.setStatus("1");
							kpiDeptMonthMapper.insert(record);
						}

						for (int month = 1; month <= endTime
								.get(Calendar.MONTH) + 1; month++) {
							kpiDeptMonth record = new kpiDeptMonth();
							record.setId(Utils.getGUID());
							record.setKpiId(kpiDeptYearDetail.getId());
							record.setKpiMonth(month);
							record.setKpiYear(endTime.get(Calendar.YEAR));
							record.setResponsiblePerson(kpiDeptYearDetail
									.getLeadPerson());
							record.setCreateBy(DbcContext.getAdminId());
							record.setCreateTime(new Date());
							record.setStatus("1");
							kpiDeptMonthMapper.insert(record);
						}
					}

				} catch (ParseException e) {
					_log.error("date convert error", e);
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 新增部门月度考核指标
	 * 
	 * @param list
	 */
	public void inertDeptMonthKpi(PageData page) {

		page.setStart(0);
		page.setRows(PageData.MAX_ROWS);

		// 查询部门年度计划
		List<KpiDeptYearBean> list = kpiDeptYearMapper.getKpiDept(page);

		if (list != null && list.size() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (KpiDeptYearBean kpiDeptYearDetail : list) {
				try {
					Date startDate = sdf
							.parse(kpiDeptYearDetail.getStartTime());
					Date endDate = sdf.parse(kpiDeptYearDetail.getEndTime());
					Calendar startTime = Calendar.getInstance();
					startTime.setTime(startDate);
					Calendar endTime = Calendar.getInstance();
					endTime.setTime(endDate);

					if (startTime.get(Calendar.YEAR) == endTime
							.get(Calendar.YEAR)) {
						for (int month = startTime.get(Calendar.MONTH) + 1; month <= endTime
								.get(Calendar.MONTH) + 1; month++) {
							kpiDeptMonth record = new kpiDeptMonth();
							record.setId(Utils.getGUID());
							record.setKpiId(kpiDeptYearDetail.getId());
							record.setDeptId(page.getDeptId());
							record.setKpiMonth(month);
							record.setKpiYear(startTime.get(Calendar.YEAR));
							record.setCreateBy(DbcContext.getAdminId());
							record.setCreateTime(new Date());
							record.setStatus("1");
							kpiDeptMonthMapper.insert(record);
						}
					}

				} catch (ParseException e) {
					_log.error("date convert error", e);
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 查询部门月度PBC
	 */
	@Override
	public ResultData getKpiDeptMonth(PageData page) {

		ResultData data = new ResultData();
		page.setStart((page.getPage() - 1) * page.getRows());
		if(StringUtils.isEmpty(page.getDeptId())){
			page.setDeptId(DbcContext.getUser().getDeptName());
		}
		
		List<KpiDeptMonthBean> list = kpiDeptMonthMapper.getKpiDeptMonth(page);
		data.setRows(list);

		// TotalRows
		PageData totalPage = page;

		totalPage.setStart(0);
		totalPage.setRows(PageData.MAX_ROWS);
		list = kpiDeptMonthMapper.getKpiDeptMonth(totalPage);
		data.setTotal(list.size());
		return data;
	}
	
	

	/**
	 * 删除部门月度PBC
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResultMsg delMonthDeptKpi(String id) {
		ResultMsg resultMsg = new ResultMsg();
		kpiDeptMonthMapper.delMonthDeptKpi(id);
		return resultMsg;
	}

	/**
	 * 保存部门月度PBC
	 * 
	 * @param listData
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultMsg saveDeptMonthKpi(ListData listData) {
		ResultMsg resultMsg = new ResultMsg();

		List<KpiDeptMonthBean> kpiDeptDetail = new ArrayList<KpiDeptMonthBean>();

		try {

			/**
			 * 新增部门绩效明细信息
			 */
			if (!StringUtils.isEmpty(listData.getInserted())) {

				kpiDeptDetail = (List<KpiDeptMonthBean>) objectMapper
						.readValue(listData.getInserted(),
								new TypeReference<List<KpiDeptMonthBean>>() {
								});
				saveDeptMonthKpi(kpiDeptDetail);
			}

			/**
			 * 修改部门绩效明细信息
			 */
			if (!StringUtils.isEmpty(listData.getUpdated())) {

				kpiDeptDetail = (List<KpiDeptMonthBean>) objectMapper
						.readValue(listData.getUpdated(),
								new TypeReference<List<KpiDeptMonthBean>>() {
								});
				saveDeptMonthKpi(kpiDeptDetail);
			}

		} catch (Exception e) {
			_log.error(e.getMessage());
			resultMsg.setCode("101");
			resultMsg.setErrorMsg(e.getMessage());
		}

		return resultMsg;
	}

	/**
	 * 保存部门月度PBC
	 * 
	 * @param list
	 */
	private void saveDeptMonthKpi(List<KpiDeptMonthBean> list) {
		if (list != null && list.size() > 0) {
			for (KpiDeptMonthBean kpiDeptMonth : list) {
				if (!StringUtils.isEmpty(kpiDeptMonth.getId())) {
					kpiDeptMonth record = new kpiDeptMonth();
					record.setId(kpiDeptMonth.getId());
					record.setWeight(kpiDeptMonth.getWeight());
					record.setStandard(kpiDeptMonth.getStandard());
					record.setYearTarget(kpiDeptMonth.getYearTarget());
					record.setFinishValue(kpiDeptMonth.getFinishValue());
					record.setFinishDesc(kpiDeptMonth.getFinishDesc());
					record.setSeftScore(kpiDeptMonth.getSeftScore());
					
					//工作流审批，提交考核得分
					if(!StringUtils.isEmpty(kpiDeptMonth.getExamScore())){
						record.setExamScore(kpiDeptMonth.getExamScore());
						
						if(!kpiDeptMonth.getExamScore().equals(kpiDeptMonth.getSeftScore())){
							String remark = DbcContext.getUser().getUserName() + "修改考核得分为：" + kpiDeptMonth.getExamScore() + ";";
							record.setRemark(remark);
						}
					}

					record.setModifyBy(DbcContext.getUserId());
					record.setModifyTime(new Date());

					kpiDeptMonthMapper.update(record);
				} else {
					kpiDeptMonth record = new kpiDeptMonth();

					record.setId(Utils.getGUID());
					record.setWeight(kpiDeptMonth.getWeight());
					record.setStandard(kpiDeptMonth.getStandard());
					record.setYearTarget(kpiDeptMonth.getYearTarget());
					record.setFinishValue(kpiDeptMonth.getFinishValue());
					record.setFinishDesc(kpiDeptMonth.getFinishDesc());
					record.setSeftScore(kpiDeptMonth.getSeftScore());

					record.setCreateBy(DbcContext.getUserId());
					record.setCreateTime(new Date());
					kpiDeptMonthMapper.insert(record);
				}
			}
		}
	}
	
	/**
	 * 部门月度PBC提交审批
	 * @param year
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public ResultMsg saveDeptPbcApprove(PageData page){
		ResultMsg msg = new ResultMsg();	

		// 检测是否配置分管领导
		if (StringUtils.isEmpty(DbcContext.getUser().getChargeLeader())) {
			msg.setCode("101");
			msg.setErrorMsg("当前部门(" + DbcContext.getUser().getDeptName()
					+ ")分管领导为空,请先配置分管领导!");
			return msg;
		}
		
		// 查看当前部门月度PBC
		page.setPage(1);
		ResultData data = getKpiDeptMonth(page);
		if (data != null && data.getTotal() == 0) {
			msg.setCode("100");
			msg.setErrorMsg("当前无部门月度PBC!");
			return msg;
		}
		else{			
			//kpiDeptMonthMapper.saveDeptApprove(page);
			//更新状态为提交申请
			List<KpiDeptMonthBean> list = (List<KpiDeptMonthBean>) data.getRows();
			if(list != null && list.size() > 0){
				for (KpiDeptMonthBean kpiDeptMonth : list) {
					kpiDeptMonth record = new kpiDeptMonth();
					record.setId(kpiDeptMonth.getId());
					record.setAuditStatus("2");
					record.setModifyBy(DbcContext.getUserId());
					record.setModifyTime(new Date());  
					kpiDeptMonthMapper.update(record);
				}
			}
		}	

		// 启动工作流
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("processId", "bce88d9731ed4ec0aab14d6adace6cc5");

		if (!DbcContext.getRequest().getParameter("orderId").equals("null")) {
			params.put("orderId",DbcContext.getRequest().getParameter("orderId"));
		} else {
			params.put("orderId", "");
		}
		if (!DbcContext.getRequest().getParameter("taskId").equals("null")) {
			params.put("taskId", DbcContext.getRequest().getParameter("taskId"));
		} else {
			params.put("taskId", "");
		}

		if (!StringUtils.isEmpty(params.get("orderId"))	&& !StringUtils.isEmpty(params.get("taskId"))) {
			params.put("year", kpiWorkFlow.getKpiYear(DbcContext.getRequest().getParameter("orderId"), DbcContext.getRequest()
					.getParameter("taskName")));
			
			params.put("month", kpiWorkFlow.getKpiMonth(DbcContext.getRequest().getParameter("orderId"), DbcContext.getRequest()
					.getParameter("taskName")));
		} else {
			params.put("year", page.getYear());
			params.put("month", page.getMonth());
		}
		params.put("dept", DbcContext.getUser().getDeptName());

		// 申请人
		params.put("apply.operator", DbcContext.getUser().getUserName());
		// 分管领导审批人
		params.put("approveDept.operator", DbcContext.getUser().getChargeLeader());
		// 人力资源专员审批人
		params.put("approveHr.operator", "张玉真");

		kpiFlowService.process(params);
		return msg;
	}
	
	
	/**
	 * 查询部门月度评价
	 * @param page
	 * @return
	 * @throws ServiceException
	 */	
	public ResultData getKpiDeptMonthScore(PageData page){
		ResultData data = new ResultData();
		page.setStart((page.getPage() - 1) * page.getRows());
		if(StringUtils.isEmpty(page.getDeptId())){
			page.setDeptId(DbcContext.getUser().getDeptName());
		}
		
		List<KpiDeptMonthBean> list = kpiDeptMonthMapper.getKpiDeptMonthScore(page);
		data.setRows(list);

		// TotalRows
		PageData totalPage = page;

		totalPage.setStart(0);
		totalPage.setRows(PageData.MAX_ROWS);
		list = kpiDeptMonthMapper.getKpiDeptMonthScore(totalPage);
		data.setTotal(list.size());
		return data;
	}

	/**
	 * 部门月度评价提交审批
	 * 
	 * @param year
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public ResultMsg saveDeptApprove(PageData page) {
		ResultMsg msg = new ResultMsg();	

		// 查看当前年度是否有年度计划
		page.setPage(1);
		ResultData data = getKpiDeptMonth(page);
		if (data != null && data.getTotal() == 0) {
			msg.setCode("100");
			msg.setErrorMsg("当前无年度计划任务!");
			return msg;
		}

		// 检测是否配置分管领导
		if (StringUtils.isEmpty(DbcContext.getUser().getChargeLeader())) {
			msg.setCode("101");
			msg.setErrorMsg("当前部门(" + DbcContext.getUser().getDeptName()
					+ ")分管领导为空,请先配置分管领导!");
			return msg;
		}

		//修改部门月度评价已提交审批
		List<KpiDeptMonthBean> list = (List<KpiDeptMonthBean>) data.getRows();
		if(list != null && list.size() > 0){
			for (KpiDeptMonthBean kpiDeptMonthBean : list) {
				kpiDeptMonth record = new kpiDeptMonth();
				record.setId(kpiDeptMonthBean.getId());
				record.setAuditStatus("5");
				record.setModifyBy(DbcContext.getUserId());
				record.setModifyTime(new Date());
				kpiDeptMonthMapper.update(record);
			}			
		}		

		// 启动工作流
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("processId", "4f0a1cfec6954908b6d2271d0a870e35");

		if (!DbcContext.getRequest().getParameter("orderId").equals("null")) {
			params.put("orderId",DbcContext.getRequest().getParameter("orderId"));
		} else {
			params.put("orderId", "");
		}
		if (!DbcContext.getRequest().getParameter("taskId").equals("null")) {
			params.put("taskId", DbcContext.getRequest().getParameter("taskId"));
		} else {
			params.put("taskId", "");
		}

		if (!StringUtils.isEmpty(params.get("orderId"))	&& !StringUtils.isEmpty(params.get("taskId"))) {
			params.put("year", kpiWorkFlow.getKpiYear(DbcContext.getRequest().getParameter("orderId"), DbcContext.getRequest()
					.getParameter("taskName")));
			
			params.put("month", kpiWorkFlow.getKpiMonth(DbcContext.getRequest().getParameter("orderId"), DbcContext.getRequest()
					.getParameter("taskName")));
		} else {
			params.put("year", page.getYear());
			params.put("month", page.getMonth());
		}
		params.put("dept", DbcContext.getUser().getDeptName());

		// 申请人
		params.put("apply.operator", DbcContext.getUser().getUserName());
		// 分管领导审批人
		params.put("approveDept.operator", DbcContext.getUser().getChargeLeader());
		// 人力资源专员审批人
		params.put("approveHr.operator", "张玉真");

		kpiFlowService.process(params);
		return msg;
	}
}

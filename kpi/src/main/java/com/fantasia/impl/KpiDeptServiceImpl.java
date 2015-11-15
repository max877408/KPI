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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fantasia.base.bean.ListData;
import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.bean.KpiDeptYear;
import com.fantasia.bean.KpiDeptYearBean;
import com.fantasia.bean.KpiDeptYearDetail;
import com.fantasia.bean.KpiGroupYear;
import com.fantasia.core.DbcContext;
import com.fantasia.core.Utils;
import com.fantasia.dao.KpiDeptYearMapper;
import com.fantasia.dao.KpiGroupYearMapper;
import com.fantasia.exception.ServiceException;
import com.fantasia.service.KpiDeptDetailService;
import com.fantasia.service.KpiDeptService;
import com.fantasia.snakerflow.process.KpiWorkFlow;
import com.fantasia.util.DateTimeUtil;
import com.fantasia.validation.DataTimeValidate;
import com.fantasia.validation.KpiYearValidation;
import com.fantasia.workflow.KpiFlowService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("KpiDeptService")
public class KpiDeptServiceImpl implements KpiDeptService {

	@Autowired
	private KpiDeptYearMapper kpiDeptYearMapper;
	
	@Autowired
	private KpiGroupYearMapper kpiGroupYearMapper;
	
	@Autowired
	private KpiDeptDetailService  kpiDeptDetailService;
	
	@Autowired
	private KpiFlowService kpiFlowService;
	
	@Autowired
	private KpiWorkFlow kpiWorkFlow;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private static Logger _log = LoggerFactory.getLogger(KpiDeptServiceImpl.class);
	
	/**
	 * 保存部门绩效考核
	 * getGroupId 存在 则年度计划存在
	 * 需要 保存年度计划
	 * 部门年度计划
	 * 部门年度计划明细
	 * kpiBean.getStartTime() 为空 默认 
	 * 默认为部门月度PBC新增，默认时间为当前时间
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResultMsg SaveKpiDept(ListData listData) {		
		
		ResultMsg resultMsg = new ResultMsg();
		String deptKpi = "";
		
		//部门年度计划关键节点
		List<KpiDeptYearDetail> kpiDeptDetail = new ArrayList<KpiDeptYearDetail>();
		
		//年度关键任务	
		KpiGroupYear kpiGroupYear = null;

		try {
			
			//保存部门绩效相关信息
			if (!StringUtils.isEmpty(listData.getData())) {

				KpiDeptYearBean kpiBean = objectMapper.readValue(listData.getData(),KpiDeptYearBean.class);
								
				//年度关键任务			
				kpiGroupYear = new KpiGroupYear();
				kpiGroupYear.setId(kpiBean.getId());				
				kpiGroupYear.setKeyTask(kpiBean.getKeyTask());
				kpiGroupYear.setKeyItem(kpiBean.getKeyItem());
				kpiGroupYear.setDetailItem(kpiBean.getDetailItem());				
				kpiGroupYear.setDept(DbcContext.getUser().getDeptName());
				
				//设置默认时间
				if(!StringUtils.isEmpty(kpiBean.getStartTime())){
					kpiGroupYear.setStartTime(kpiBean.getStartTime());
				}
				else{		
					//添加部门月度绩效考核，默认审核通过
					kpiBean.setAuditStatus("2");
					kpiGroupYear.setStartTime(DateTimeUtil.shortDateString());
				}				
				if(!StringUtils.isEmpty(kpiBean.getEndTime())){
					kpiGroupYear.setEndTime(kpiBean.getEndTime());
				}
				else{					
					kpiGroupYear.setEndTime(DateTimeUtil.shortDateString());
				}
				
				DataTimeValidate.valiateTime(kpiGroupYear.getStartTime(),kpiGroupYear.getEndTime());
				//KpiYearValidation.validateYearKeyTaskStatus(kpiGroupYear.getStartTime(),kpiGroupYear.getStartTime());
						
				
				if(StringUtils.isEmpty(kpiBean.getGroupId())){					
					kpiGroupYear.setId(Utils.getGUID());				
					kpiGroupYear.setCreateBy(DbcContext.getUserId());
					kpiGroupYear.setCreateTime(new Date());
					//部门新增
					kpiGroupYear.setAuditStatus("2");
					kpiGroupYear.setOwer("2");
					
					kpiGroupYearMapper.insert(kpiGroupYear);
					
					kpiBean.setGroupId(kpiGroupYear.getId());
				}
				else{
					kpiGroupYear.setId(kpiBean.getGroupId());
					kpiGroupYear.setModifyBy(DbcContext.getUserId());
					kpiGroupYear.setModifyTime(new Date());
					
					//kpiGroupYearMapper.updateDeptId(kpiGroupYear);
				}
				
				deptKpi = SaveKpiDept(kpiBean);				
			}
			
			/**
			 * 新增部门绩效明细信息
			 */
			if (!StringUtils.isEmpty(listData.getInserted())) {

				kpiDeptDetail = (List<KpiDeptYearDetail>) objectMapper.readValue(
						listData.getInserted(),
						new TypeReference<List<KpiDeptYearDetail>>() {
						});
				KpiYearValidation.validateYearDept(kpiGroupYear, kpiDeptDetail);
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
				KpiYearValidation.validateYearDept(kpiGroupYear, kpiDeptDetail);
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
				KpiYearValidation.validateYearDept(kpiGroupYear, kpiDeptDetail);
				kpiDeptDetailService.bachDeleDeptKpi(kpiDeptDetail);
			}
		} 
		 catch (ServiceException e) {
				_log.error(e.getDescribe());
				resultMsg.setCode("101");
				resultMsg.setErrorMsg(e.getDescribe());
		}
		catch (Exception e) {
			_log.error(e.getMessage());
			resultMsg.setCode("101");
			resultMsg.setErrorMsg(e.getMessage());			
		}
		
		return resultMsg;
	}
	
	/**
	 * 新增部门年度计划
	 * @param KpiDeptYearBean
	 * @return
	 */
	public String SaveKpiDept(KpiDeptYearBean kpiDeptYear){
		KpiDeptYear record = new KpiDeptYear();
		
		if(!StringUtils.isEmpty(kpiDeptYear)){	
			record.setGroupId(kpiDeptYear.getGroupId());
			record.setDept(DbcContext.getUser().getDeptName());
			record.setWeight(kpiDeptYear.getWeight());
			record.setContent(kpiDeptYear.getContent());
			record.setYearTarget(kpiDeptYear.getYearTarget());
			record.setStandard(kpiDeptYear.getStandard());
			record.setProjectLevel(kpiDeptYear.getProjectLevel());
			record.setDifficulty(kpiDeptYear.getDifficulty());
			if(!StringUtils.isEmpty(kpiDeptYear.getAuditStatus())){
				record.setAuditStatus(kpiDeptYear.getAuditStatus());
			}
			//record.setResponsiblePerson(kpiDeptYear.getResponsiblePerson());
			
			if(!StringUtils.isEmpty(kpiDeptYear.getId())){	
				record.setId(kpiDeptYear.getId());
				record.setModifyBy(DbcContext.getUserId());
				record.setModifyTime(new Date());
				
				kpiDeptYearMapper.update(record);
			}
			else{
				record.setId(Utils.getGUID());
				record.setStatus("1");
				record.setCreateBy(DbcContext.getUserId());
				record.setCreateTime(new Date());
				
				kpiDeptYearMapper.insert(record);
			}			
		}
		return record.getId();
	}
	
	/**
	 * 查询部门年度计划
	 * 只有部门负责人，才可以看到部门年度计划
	 */
	@Override
	public ResultData getKpiDept(PageData page) {		
		
		ResultData data = new ResultData();	
		
		page.setStart((page.getPage() -1) * page.getRows());
		if(StringUtils.isEmpty(page.getDeptId())){
			page.setDeptId(DbcContext.getUser().getDeptName());
		}
		
		List<KpiDeptYearBean> list = kpiDeptYearMapper.getKpiDept(page);
		data.setRows(list);		
		
		page.setStart(0);
		page.setRows(PageData.MAX_ROWS);
		list = kpiDeptYearMapper.getKpiDept(page);
		data.setTotal(list.size());
		return data;
	}

	@Override
	public KpiDeptYearBean getKpiDeptById(String id) {
		return kpiDeptYearMapper.getKpiDeptById(id);
	}
	
	/**
	 * 删除部门年度计划
	 * @param id
	 * @return
	 */
	@Override
	public ResultMsg delDeptKpiGroup(String id){
		ResultMsg resultMsg = new ResultMsg();
		kpiDeptYearMapper.delDeptKpiGroup(id);
		return resultMsg;
	}
	
	/**
	 * 部门年度计划任务下发
	 * @param year
	 * @return
	 * @throws ServiceException
	 */	
	@Transactional	
	public ResultMsg saveDeptTask(PageData page){
		ResultMsg msg = new ResultMsg();
		
		//查看当前年度是否有年度计划
		page.setPage(1);
		ResultData data = getKpiDept(page);
		if(data != null && data.getTotal() == 0){			
			msg.setCode("100");
			msg.setErrorMsg("当前无年度计划任务!");
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
		kpiDeptYearMapper.updateTask(page);		
		
		//启动工作流		
		 Map<String, Object> params = new HashMap<String, Object>();		
		 params.put("processId", "34bd21ff94ea4e158ef8fc13ea49b2c4");
		
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
		
		 if(!StringUtils.isEmpty(params.get("orderId")) && !StringUtils.isEmpty(params.get("taskId"))){
			 params.put("year",kpiWorkFlow.getKpiYear(DbcContext.getRequest().getParameter("orderId"), DbcContext.getRequest().getParameter("taskName")));
		 }
		 else{
			 params.put("year", page.getYear());
		 }		 
		 params.put("dept", DbcContext.getUser().getDeptName());		
		 
		 //申请人
		 params.put("apply.operator", DbcContext.getUser().getUserName());
		 //分管领导审批人
		 params.put("approveDept.operator", DbcContext.getUser().getChargeLeader());
		 //人力资源专员审批人
		 params.put("approveHr.operator", "张玉真");		 
		 
		 kpiFlowService.process(params);
		
		return msg;
	}
}

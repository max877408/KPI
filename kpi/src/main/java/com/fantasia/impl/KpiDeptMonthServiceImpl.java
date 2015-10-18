package com.fantasia.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.fantasia.bean.KpiDeptYearDetail;
import com.fantasia.bean.kpiDeptMonth;
import com.fantasia.core.DbcContext;
import com.fantasia.core.Utils;
import com.fantasia.dao.kpiDeptMonthMapper;
import com.fantasia.exception.ServiceException;
import com.fantasia.service.KpiDeptMonthService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("KpiDeptMonthService")
public class KpiDeptMonthServiceImpl implements KpiDeptMonthService {

	@Autowired
	private kpiDeptMonthMapper kpiDeptMonthMapper;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private static Logger _log = LoggerFactory.getLogger(KpiDeptMonthServiceImpl.class);
	
	/**
	 * 新增部门月度考核指标
	 * @param list
	 */
	public void inertDeptMonthKpi(List<KpiDeptYearDetail> list){
		if(list != null && list.size() > 0){
			SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
			for (KpiDeptYearDetail kpiDeptYearDetail : list) {
				try {
					Date startDate = sdf.parse(kpiDeptYearDetail.getStartTime()) ;
					Date endDate = sdf.parse(kpiDeptYearDetail.getEndTime()) ;
					Calendar startTime = Calendar.getInstance();
					startTime.setTime(startDate);
					Calendar endTime = Calendar.getInstance();
					endTime.setTime(endDate);
					
					
					if(startTime.get(Calendar.YEAR)== endTime.get(Calendar.YEAR)){
						for(int month = startTime.get(Calendar.MONTH) + 1; month <= endTime.get(Calendar.MONTH)+1; month++){
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
					}
					else{
						//跨年情况
						for(int month = startTime.get(Calendar.MONTH) + 1; month <= 12; month++){
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
						
						for(int month = 1; month <=  endTime.get(Calendar.MONTH) + 1; month++){
							kpiDeptMonth record = new kpiDeptMonth();
							record.setId(Utils.getGUID());
							record.setKpiId(kpiDeptYearDetail.getId());
							record.setKpiMonth(month);
							record.setKpiYear(endTime.get(Calendar.YEAR));
							record.setResponsiblePerson(kpiDeptYearDetail.getLeadPerson());
							record.setCreateBy(DbcContext.getAdminId());
							record.setCreateTime(new Date());
							record.setStatus("1");
							kpiDeptMonthMapper.insert(record);
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
	 * 查询部门月度绩效考核指标
	 */
	@Override
	public ResultData getKpiDeptMonth(PageData page) {
		
		ResultData data = new ResultData();
		page.setStart((page.getPage() -1) * page.getRows());	
		page.setDeptId(DbcContext.getUser().getDeptName());
		
		List<KpiDeptMonthBean> list = kpiDeptMonthMapper.getKpiDeptMonth(page);
		data.setRows(list);
		
		//TotalRows 
		PageData totalPage = page;
	
		totalPage.setStart(0);
		totalPage.setRows(PageData.MAX_ROWS);
		list = kpiDeptMonthMapper.getKpiDeptMonth(totalPage);
		data.setTotal(list.size());
		return data;	
	}
	
	/**
	 * 删除部门月度PBC
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResultMsg delMonthDeptKpi(String id){
		ResultMsg resultMsg = new ResultMsg();
		kpiDeptMonthMapper.delMonthDeptKpi(id);
		return resultMsg;
	}
	
	/**
	 * 保存部门月度PBC
	 * @param listData
	 * @return
	 * @throws ServiceException
	 */	
	@SuppressWarnings("unchecked")
	@Override
	public ResultMsg saveDeptMonthKpi(ListData listData){
		ResultMsg resultMsg = new ResultMsg();		
		
		List<KpiDeptMonthBean> kpiDeptDetail = new ArrayList<KpiDeptMonthBean>();

		try {			
			
			/**
			 * 新增部门绩效明细信息
			 */
			if (!StringUtils.isEmpty(listData.getInserted())) {

				kpiDeptDetail = (List<KpiDeptMonthBean>) objectMapper.readValue(
						listData.getInserted(),
						new TypeReference<List<KpiDeptMonthBean>>() {
						});
				saveDeptMonthKpi(kpiDeptDetail);
			}

			/**
			 * 修改部门绩效明细信息
			 */
			if (!StringUtils.isEmpty(listData.getUpdated())) {

				kpiDeptDetail = (List<KpiDeptMonthBean>) objectMapper.readValue(
						listData.getUpdated(),
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
	 * @param list
	 */
	private void saveDeptMonthKpi(List<KpiDeptMonthBean> list){
		if(list != null && list.size() > 0){
			for (KpiDeptMonthBean kpiDeptMonth : list) {
				if(!StringUtils.isEmpty(kpiDeptMonth.getId())){
					kpiDeptMonth record = new kpiDeptMonth();
					record.setId(kpiDeptMonth.getId());
					record.setWeight(kpiDeptMonth.getWeight());
					record.setStandard(kpiDeptMonth.getStandard());
					record.setYearTarget(kpiDeptMonth.getYearTarget());
					record.setFinishValue(kpiDeptMonth.getFinishValue());
					record.setFinishDesc(kpiDeptMonth.getFinishDesc());
					record.setSeftScore(kpiDeptMonth.getSeftScore());
					
					record.setModifyBy(DbcContext.getUserId());
					record.setModifyTime(new Date());
					
					kpiDeptMonthMapper.update(record);
				}
				else{
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
	 * 部门月度评价提交审批
	 * @param year
	 * @return
	 * @throws ServiceException
	 */	
	public ResultMsg saveDeptApprove(PageData page){
		ResultMsg msg = new ResultMsg();
		if(!DbcContext.isAdmin()){
			page.setUserId(DbcContext.getUserId());
		}
		page.setModifyBy(DbcContext.getUserId());
		page.setModifyTime(new Date());
		kpiDeptMonthMapper.saveDeptApprove(page);		
		return msg;	
	}
}

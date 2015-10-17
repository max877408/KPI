package com.fantasia.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.fantasia.core.DbcContext;
import com.fantasia.core.Utils;
import com.fantasia.dao.KpiEmployeeYearMapper;
import com.fantasia.exception.ServiceException;
import com.fantasia.service.KpiEmployeeMonthService;
import com.fantasia.service.KpiEmployeeService;
import com.fantasia.util.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("KpiEmployeeService")
public class KpiEmployeeServiceImpl implements KpiEmployeeService {

	private static Logger _log = LoggerFactory.getLogger(KpiEmployeeServiceImpl.class);
	
	@Autowired
	private KpiEmployeeYearMapper kpiEmployeeYearMapper;
	
	@Autowired
	private KpiEmployeeMonthService kpiEmployeeMonthService;
	
	@Override
	public ResultData getKpiEmployee(PageData page) {
		
		ResultData data = new ResultData();
		page.setStart((page.getPage() -1) * page.getRows());
		if(!DbcContext.isAdmin()){
			page.setUserId(DbcContext.getUser().getUserName());
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
		
		page.setModifyBy(DbcContext.getUserId());
		page.setModifyTime(new Date());
		kpiEmployeeYearMapper.updateTask(page);		
		return msg;
	}
}

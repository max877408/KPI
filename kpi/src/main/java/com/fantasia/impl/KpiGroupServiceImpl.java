package com.fantasia.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.bean.KpiGroupYear;
import com.fantasia.core.DbcContext;
import com.fantasia.core.Utils;
import com.fantasia.dao.KpiGroupYearMapper;
import com.fantasia.exception.ServiceException;
import com.fantasia.service.KpiGroupService;
import com.fantasia.util.DateTimeUtil;
import com.fantasia.util.StringUtils;

@Service("KpiGroupService")
public class KpiGroupServiceImpl implements KpiGroupService {
	
	@Autowired
	private KpiGroupYearMapper kpiGroupYearMapper;


	@Override
	public ResultData getKpiGroup(PageData page){
		ResultData data = new ResultData();
		page.setStart((page.getPage() -1) * page.getRows());		
		
		List<KpiGroupYear> list = kpiGroupYearMapper.getKpiGroup(page);
		data.setRows(list);
		
		//TotalRows 
		PageData totalPage = page;		
		totalPage.setStart(0);		
		totalPage.setRows(PageData.MAX_ROWS);
		list = kpiGroupYearMapper.getKpiGroup(totalPage);	
		data.setTotal(list.size());
		return data;
	}
	
	/**
	 * 获取关键任务列表
	 * @param keyTask
	 * @return
	 */
	@Override
	public List<KpiGroupYear> getKpiGroupList(String year,String keyTask){
		PageData pageData = new PageData();
		pageData.setKeyTask(keyTask);
		pageData.setYear(Integer.parseInt(year));
		return kpiGroupYearMapper.getKpiGroupList(pageData);
	}
	
	/**
	 * 查询关键任务列表
	 * @param keyTask
	 * @param year
	 * @return
	 */
	@Override
	public List<KpiGroupYear> searchKpiGroupList(String keyTask,String year){
		return kpiGroupYearMapper.searchKpiGroupList(keyTask, year);
	}
	
	/**
	 * 保存年度关键任务
	 */
	@Override
	public List<KpiGroupYear> SaveKpiGroup(List<KpiGroupYear> list) {
		int i = 1;
		for (KpiGroupYear kpiGroupYear : list) {
			if(StringUtils.isNotEmpty(kpiGroupYear.getId())){			
				kpiGroupYear.setModifyBy(DbcContext.getUserId());
				kpiGroupYear.setModifyTime(new Date());
				kpiGroupYear.setStatus("1");

				kpiGroupYearMapper.update(kpiGroupYear);
			}
			else{
				kpiGroupYear.setId(Utils.getGUID());
				kpiGroupYear.setSort(i);
				kpiGroupYear.setCreateBy(DbcContext.getUserId());
				kpiGroupYear.setCreateTime(new Date());
				kpiGroupYear.setStatus("1");
				
				kpiGroupYearMapper.insert(kpiGroupYear);
				i++;
			}
			
		}
		return list;
	}
	
	/**
	 * 保存年度计划关键任务字段
	 * 
	 * @param kpiGroupYear
	 * @return
	 * @throws ServiceException
	 */	
	public ResultMsg SaveKpiGroupTask(KpiGroupYear kpiGroupYear){
		ResultMsg msg = new ResultMsg();
	
		KpiGroupYear kpiGroup = kpiGroupYearMapper.getKpiGroupById(kpiGroupYear.getId());
		if(kpiGroup != null ){
			String task = kpiGroup.getKeyTask();
			int year = 0;
			
			Calendar startTime = Calendar.getInstance();			
			startTime.setTime(DateTimeUtil.StrToDate(kpiGroup.getStartTime()));
			year = startTime.get(Calendar.YEAR);
			
			PageData page = new PageData();
			page.setYear(year);
			page.setKeyTask(task);
			page.setStart(0);
			page.setRows(PageData.MAX_ROWS);
			List<KpiGroupYear> list = kpiGroupYearMapper.searchKpiGroup(page);
		
			for (KpiGroupYear kpi : list) {
				KpiGroupYear record = new KpiGroupYear();
				record.setId(kpi.getId());
				record.setKeyTask(kpiGroupYear.getKeyTask());
				kpiGroupYearMapper.update(record);
			}
			
		}
		return msg;
	}

	@Override
	public void UpdateKpiGroup(List<KpiGroupYear> list) {		
		for (KpiGroupYear kpiGroupYear : list) {			
			kpiGroupYear.setModifyBy(DbcContext.getAdminId());
			kpiGroupYear.setModifyTime(new Date());

			kpiGroupYearMapper.update(kpiGroupYear);
		}		
	}

	@Override
	public ResultMsg DeleteKpiGroup(String id) {	
		ResultMsg msg = new ResultMsg();
		kpiGroupYearMapper.DeleteKpiGroup(id);
		return msg;
	}
	
	/**
	 * 批量删除
	 * @param list
	 */
	@Override
	public void batchDelete(List<KpiGroupYear> list){
		for (KpiGroupYear kpiGroupYear : list) {
			kpiGroupYearMapper.DeleteKpiGroup(kpiGroupYear.getId());
		}
	}
	
	/**
	 * 年度关键任务下发
	 * @param year
	 * @return	
	 */
	public ResultMsg saveTask(PageData page){
		ResultMsg msg = new ResultMsg();
		
		//查看当前年度是否有年度关键任务
		page.setPage(1);
		ResultData data = getKpiGroup(page);
		if(data != null && data.getTotal() == 0){			
			msg.setCode("100");
			msg.setErrorMsg("当前无年度关键任务!");
			return msg;
		}
		
		page.setModifyBy(DbcContext.getUserId());
		page.setModifyTime(new Date());
		kpiGroupYearMapper.updateTask(page);		
		return msg;
	}

	
}

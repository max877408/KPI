package com.fantasia.impl;

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
import com.fantasia.service.KpiGroupService;
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
		PageData totalPage = new PageData();
		totalPage.setKeyTask(page.getKeyTask());
		totalPage.setStartTime(page.getStartTime());
		totalPage.setEndTime(page.getEndTime());
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
	public List<KpiGroupYear> getKpiGroupList(String keyTask){
		return kpiGroupYearMapper.getKpiGroupList(keyTask);
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
	
	@Override
	public void SaveKpiGroup(List<KpiGroupYear> list) {
		for (KpiGroupYear kpiGroupYear : list) {
			if(StringUtils.isNotEmpty(kpiGroupYear.getId())){			
				kpiGroupYear.setModifyBy(DbcContext.getAdminId());
				kpiGroupYear.setModifyTime(new Date());

				kpiGroupYearMapper.update(kpiGroupYear);
			}
			else{
				kpiGroupYear.setId(Utils.getGUID());
				kpiGroupYear.setCreateBy(DbcContext.getAdminId());
				kpiGroupYear.setCreateTime(new Date());

				kpiGroupYearMapper.insert(kpiGroupYear);
			}
			
		}	
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
	 * 年度关键任务下发
	 * @param year
	 * @return	
	 */
	public ResultMsg saveTask(PageData page){
		ResultMsg msg = new ResultMsg();
		kpiGroupYearMapper.updateTask(page);		
		return msg;
	}

	
}

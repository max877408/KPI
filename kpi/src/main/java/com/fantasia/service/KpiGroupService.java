package com.fantasia.service;

import java.util.List;

import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.bean.KpiGroupYear;
import com.fantasia.exception.ServiceException;

public interface KpiGroupService {
	
	/**
	 * 查询集团绩效考核指标
	 * @param page
	 * @return
	 */
	public ResultData getKpiGroup(PageData page);
	
	/**
	 * 获取关键任务列表
	 * @param keyTask
	 * @return
	 */
	public List<KpiGroupYear> getKpiGroupList(String year,String keyTask);
	
	/**
	 * 查询关键任务列表
	 * @param keyTask
	 * @param year
	 * @return
	 */
	public List<KpiGroupYear> searchKpiGroupList(String keyTask,String year);
	
	/**
	 * 新增集团绩效考核指标
	 * @param list
	 */
	public List<KpiGroupYear> SaveKpiGroup(List<KpiGroupYear> list);
	
	/**
	 * 保存年度计划关键任务字段
	 * 
	 * @param kpiGroupYear
	 * @return
	 * @throws ServiceException
	 */
	public ResultMsg SaveKpiGroupTask(KpiGroupYear kpiGroupYear);
	
	/**
	 * 更新集团绩效考核指标
	 * @param list
	 */
	public void UpdateKpiGroup(List<KpiGroupYear> list);
	
	/**
	 * 删除集团绩效考核指标
	 * @param id
	 * @return
	 */
	public ResultMsg DeleteKpiGroup(String id);	
	
	/**
	 * 批量删除
	 * @param list
	 */
	public void batchDelete(List<KpiGroupYear> list);
	
	/**
	 * 年度关键任务下发
	 * @param year
	 * @return	
	 */
	public ResultMsg saveTask(PageData page);
	
}

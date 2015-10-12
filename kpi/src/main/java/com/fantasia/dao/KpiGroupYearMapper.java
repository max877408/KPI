package com.fantasia.dao;

import java.util.List;

import com.fantasia.base.bean.PageData;
import com.fantasia.bean.KpiGroupYear;

public interface KpiGroupYearMapper {
	
	public  List<KpiGroupYear> getKpiGroup(PageData page);
	
	/**
	 * 获取关键任务列表
	 * @param keyTask
	 * @return
	 */
	public List<KpiGroupYear> getKpiGroupList(String keyTask);
	
	/**
	 * 查询关键任务列表
	 * @param keyTask
	 * @param year
	 * @return
	 */
	public List<KpiGroupYear> searchKpiGroupList(String keyTask,String year);
	
	public void insert(KpiGroupYear record);

	public void update(KpiGroupYear record);
	
	/**
	 * 任务下发，更新状态
	 * @param page
	 */
	public void updateTask(PageData page);
	
	public void DeleteKpiGroup(String id);
}
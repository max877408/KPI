package com.fantasia.dao;

import java.util.List;

import com.fantasia.bean.KpiGroupYear;

public interface KpiGroupYearMapper {
	
	public  List<KpiGroupYear> getKpiGroup(int start,int rows);
	
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
	
	public void DeleteKpiGroup(String id);
}
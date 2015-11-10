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
	public List<KpiGroupYear> getKpiGroupList(PageData page);	
	
	/**
	 * 查找年度关键任务
	 * @param record
	 * @return
	 */
	public List<KpiGroupYear> searchKpiGroup(PageData page);
	
	/**
	 * 根据id查询年度关键任务
	 * @param id
	 * @return
	 */
	public KpiGroupYear getKpiGroupById(String id);

	
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
	 * 根据部门id更新
	 * @param record
	 */
	public void updateDeptId(KpiGroupYear record);
	
	/**
	 * 任务下发，更新状态
	 * @param page
	 */
	public void updateTask(PageData page);
	
	public void DeleteKpiGroup(String id);
}

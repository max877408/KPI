package com.fantasia.service;

import java.util.List;

import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.bean.KpiGroupYear;

public interface KpiGroupService {
	
	/**
	 * 查询集团绩效考核指标
	 * @param page
	 * @return
	 */
	public ResultData getKpiGroup(PageData page);
	
	/**
	 * 新增集团绩效考核指标
	 * @param list
	 */
	public void SaveKpiGroup(List<KpiGroupYear> list);
	
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
	
}
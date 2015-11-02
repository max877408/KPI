package com.fantasia.snakerflow.process;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fantasia.snakerflow.engine.SnakerEngineFacets;

public abstract class WorkFlowBase {
	
	@Autowired
    private SnakerEngineFacets facets;
	
	/**
	 * 获取工作流参数 年份
	 * @param orderId
	 * @param taskName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Integer getKpiYear(String orderId,String taskName){
		Integer year  = 0;
		
		//获取工作流保存参数数据	
		Map<String, Object> tasks = facets.flowData(orderId, taskName);
		if(tasks != null && tasks.size() > 0){
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("vars");
			if(list != null && list.size() > 0){
				year = (Integer) list.get(list.size() - 1).get("year") ;
			}			
		}
				
		return year;
	}
	
	/**
	 * 获取工作流参数月份
	 * @param orderId
	 * @param taskName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Integer getKpiMonth(String orderId,String taskName){
		Integer month  = 0;
		
		//获取工作流保存参数数据	
		Map<String, Object> tasks = facets.flowData(orderId, taskName);
		if(tasks != null && tasks.size() > 0){
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("vars");
			if(list != null && list.size() > 0){
				month = (Integer) list.get(list.size() - 1).get("month") ;
			}			
		}
				
		return month;
	}
	
	/**
	 * 获取工作流部门
	 * @param orderId
	 * @param taskName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDept(String orderId,String taskName){
		String dept = "";
		
		//获取工作流保存参数数据	
		Map<String, Object> tasks = facets.flowData(orderId, taskName);
		if(tasks != null && tasks.size() > 0){
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("vars");
			if(list != null && list.size() > 0){
				dept = (String) list.get(list.size() - 1).get("dept") ;
			}			
		}
				
		return dept;
	}
	
	/**
	 * 获取工作流用户
	 * @param orderId
	 * @param taskName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getUserId(String orderId,String taskName){
		String userId = "";
		
		//获取工作流保存参数数据	
		Map<String, Object> tasks = facets.flowData(orderId, taskName);
		if(tasks != null && tasks.size() > 0){
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("vars");
			if(list != null && list.size() > 0){
				userId = (String) list.get(list.size() - 1).get("userId") ;
			}			
		}
				
		return userId;
	}
}

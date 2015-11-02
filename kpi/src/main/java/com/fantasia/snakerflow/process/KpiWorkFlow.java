package com.fantasia.snakerflow.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.fantasia.core.Utils;

/**
 * kpi工作流辅助类
 * 
 * @author Administrator
 * 
 */
@Service("KpiWorkFlow")
public class KpiWorkFlow extends WorkFlowBase  {
		
	private static List<SflowProcess> wfServices;	
	
	private List<SflowProcess> getwfServices(){
		if (wfServices == null) {
			wfServices = new ArrayList<SflowProcess>();
			Map<String,SflowProcess> maps = Utils.webApplication.getBeansOfType(SflowProcess.class);	
			if(maps != null && maps.size() > 0){
				for (String key : maps.keySet()) {  
					wfServices.add(maps.get(key));		           
		        } 
			}		
		}
		return wfServices;
	}
	
	/**
	 * 工作流提交
	 * 
	 * @param request
	 */
	public void process(HttpServletRequest request) {
		
		List<SflowProcess> list = getwfServices();
		if(list != null && list.size() > 0){
			for (SflowProcess sflowProcess : list) {
				sflowProcess.process(request);
			}
		}		
	}	
	
}

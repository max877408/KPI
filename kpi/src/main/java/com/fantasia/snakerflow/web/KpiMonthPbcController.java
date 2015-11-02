package com.fantasia.snakerflow.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fantasia.snakerflow.engine.SnakerEngineFacets;

@Controller
@RequestMapping(value = "/flow/KpiMonthPbc")
public class KpiMonthPbcController {
	@Autowired
    private SnakerEngineFacets facets;
	
	/**
	 * 分管领导审批路由方法
	 */
	@RequestMapping(value = "approveDept", method= RequestMethod.GET)
	public String approveDept(Model model, String processId, String orderId, String taskId, String taskName) {
		model.addAttribute("processId", processId);
		model.addAttribute("orderId", orderId);
		model.addAttribute("taskId", taskId);
		if(StringUtils.isNotEmpty(taskId)) {
			return "flow/kpiMonthPbc/approveDept";
		} else {
			model.addAllAttributes(facets.flowData(orderId, taskName));
			return "flow/kpiMonthPbc/approveDeptView";
		}
	}
	
	/**
	 * 人力资源专员审批路由方法
	 */
	@RequestMapping(value = "approveHr", method= RequestMethod.GET)
	public String approveBoss(Model model, String processId, String orderId, String taskId, String taskName) {
		model.addAttribute("processId", processId);
		model.addAttribute("orderId", orderId);
		model.addAttribute("taskId", taskId);
		if(StringUtils.isNotEmpty(taskId)) {
			return "flow/kpiMonthPbc/approveHr";
		} else {
			model.addAllAttributes(facets.flowData(orderId, taskName));
			return "flow/kpiMonthPbc/approveHrView";
		}
	}
}

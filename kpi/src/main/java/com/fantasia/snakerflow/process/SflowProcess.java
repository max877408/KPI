package com.fantasia.snakerflow.process;

import javax.servlet.http.HttpServletRequest;

/**
 * 工作流完成处理
 * @author Administrator
 *
 */
public interface SflowProcess {

	/**
	 * 工作流提交处理
	 * @param request
	 */
	public void process(HttpServletRequest request);
}

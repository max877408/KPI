package com.fantasia.base.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fantasia.base.bean.ResultMsg;
import com.fantasia.exception.ServiceException;

public class BaseAction {

	private static Logger _log = LoggerFactory.getLogger(BaseAction.class);

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();

	}

	/** 基于@ExceptionHandler异常处理 */
	@ExceptionHandler
	@ResponseBody
	public ResultMsg exp(HttpServletRequest request, Exception ex) {
		ResultMsg rtnMsg = new ResultMsg();
		request.setAttribute("ex", ex);

		// 根据不同错误转向不同页面
		if (ex instanceof ServiceException) {
			rtnMsg.setCode(((ServiceException) ex).getCode());
			rtnMsg.setErrorMsg(((ServiceException) ex).getDescribe());
		} else {
			rtnMsg.setCode("500");
			rtnMsg.setErrorMsg(ex.getMessage());
		}

		_log.error("异常信息", ex);

		return rtnMsg;
	}
}

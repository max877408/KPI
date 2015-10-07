package com.fantasia.exception;

/***
 * 服务层 Action 异常
 * 
 * @author Administrator
 * 
 */
public class ServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * return msg code
	 */
	public String code;

	/**
	 * return msg describe
	 */
	public String describe;

	public ServiceException(String code, String describe) {
		this.code = code;
		this.describe = describe;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
}

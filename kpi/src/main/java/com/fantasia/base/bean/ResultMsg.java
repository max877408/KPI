package com.fantasia.base.bean;

public class ResultMsg {

	/**
	 * return msg code
	 */
	public String code;

	/**
	 * return msg describe
	 */
	public String errorMsg;

	/**
	 * return data;
	 */
	public Object data;
	

	public ResultMsg() {
		code = "000";
		errorMsg = "Operation succeed!";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}

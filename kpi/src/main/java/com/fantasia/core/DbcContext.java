package com.fantasia.core;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class DbcContext {
	private static Logger _log = LoggerFactory.getLogger(DbcContext.class);

	/**
	 * get current request
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	/**
	 * set token
	 * @param token
	 */
	public static void setToken(String token){
		HttpServletRequest request = getRequest();
		request.setAttribute("token", token);
	}
	
	/**
	 * 管理员ID
	 * @return
	 */
	public static String getAdminId(){
		return "EA7D34416D8343F0A92ECA70AA82AF7E";
	}
	
	/**
	 * 管理员名称
	 * @return
	 */
	public static String agetAdminName(){
		return "Admin";
	}

}

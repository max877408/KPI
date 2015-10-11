package com.fantasia.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.fantasia.bean.PubUser;

public class DbcContext {
	private static Logger _log = LoggerFactory.getLogger(DbcContext.class);

	/**
	 * get current request
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request;
	}

	/**
	 * get current response
	 * 
	 * @return
	 */
	public static HttpServletResponse getResponse() {
		HttpServletResponse response = ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
		return response;
	}

	/**
	 * set token
	 * 
	 * @param token
	 */
	public static void setToken(String token) {
		HttpServletRequest request = getRequest();
		request.setAttribute("token", token);
	}

	/**
	 * 获取当前用户 1.从 UserPermission.loginUsers取
	 * 
	 * @return
	 */
	public static PubUser getUser() {
		HttpServletRequest request = getRequest();
		String token = String.valueOf(request.getAttribute("token"));
		_log.info(token);
		PubUser user = UserPermission.getUser(token);

		if (user == null) {
			user = new PubUser();
		}
		return user;
	}

	/**
	 * 获取用户ID
	 * 
	 * @return
	 */
	public static String getUserId() {
		PubUser user = getUser();
		if (StringUtils.isEmpty(user.getId())) {
			return DbcContext.getAdminId();
		} else {
			return String.valueOf(user.getId());
		}
	}

	/**
	 * 管理员ID
	 * 
	 * @return
	 */
	public static String getAdminId() {
		return "EA7D34416D8343F0A92ECA70AA82AF7E";
	}

	/**
	 * 管理员名称
	 * 
	 * @return
	 */
	public static String agetAdminName() {
		return "Admin";
	}

}

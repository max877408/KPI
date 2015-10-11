package com.fantasia.core;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CommonInterceptor implements HandlerInterceptor {

	private static String[] ignoresUrls = null;

	private static Logger _log = LoggerFactory
			.getLogger(CommonInterceptor.class);

	public void setIgnoresUrl(String ignoresUrl) {
		ignoresUrls = ignoresUrl.split(",");
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	// 在业务处理器处理请求执行完成后,生成视图之前执行的动作
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mv)
			throws Exception {

		// 更新最后访问时间
		
	}

	/**
	 * 验证用户是否已登录
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) throws Exception {
		String requestUrl = request.getRequestURI();

		String rawRequstUrl = requestUrl + "?";
		
		Map<String, String[]> reqMap = request.getParameterMap();
		if (reqMap != null) {

			Set<Entry<String, String[]>> set = reqMap.entrySet();
			Iterator<Entry<String, String[]>> it = set.iterator();
			while (it.hasNext()) {
				Entry<String, String[]> entry = it.next();

				String strKey = entry.getKey();
				String strObj = "";

				for (String i : entry.getValue()) {
					strObj += i;
				}

				rawRequstUrl += strKey + "=" + strObj + "&";
			}

			_log.info(rawRequstUrl);
		}

		if (containsUrl(requestUrl)) {
			_log.info("当前访问URL：" + requestUrl + " 不需要登录认证!");
		} else {
		
			Cookie token = CookieTool.getCookieByName(request, "token");
			if(token != null){
				Boolean result = UserPermission.checkLogin(token.getValue());
				if(result == false){
					response.sendRedirect("login.html");
					return false;
				}
			}
			else{
				response.sendRedirect("/kpi/login.html");	
				response.flushBuffer();
				return false;
			}
			
		}		

		return true;
	}

	/**
	 * 当前请求URL 是否在 ignoresUrls 列表中
	 * 
	 * @param requestUrl
	 * @return
	 */
	private static boolean containsUrl(String requestUrl) {
		boolean result = false;
		for (String url : ignoresUrls) {
			if (requestUrl.lastIndexOf(url) != -1) {
				result = true;
				break;
			}
		}
		return result;
	}

}

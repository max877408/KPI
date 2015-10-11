package com.fantasia.core;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fantasia.bean.PubUser;
import com.fantasia.exception.ServiceException;

public class UserPermission {
	
	private static Logger _log = LoggerFactory.getLogger(UserPermission.class);	
	
	/**
	 * 在线用户列表
	 */
	public static ConcurrentMap<String, PubUser> loginUsers = new ConcurrentHashMap<String, PubUser>();


	/**
	 * 验证当前token用户是否登录
	 * 验证规则：
	 * @param token
	 * @throws ServiceException
	 * @throws IOException 
	 */
	public static Boolean checkLogin(String token) throws ServiceException, IOException {
		Boolean result = true;
		if (!StringUtils.isEmpty(token)) {
			DbcContext.setToken(token);
			PubUser user = DbcContext.getUser();
			if (user != null && !StringUtils.isEmpty(user.getToken())) {
				_log.info("validate token---------" + token);
			} else {
				result = false;
				//DbcContext.getResponse().sendRedirect("login.html");
				//throw new ServiceException("10000","parame token is invalid,please login");
			}
		} else {
			result = false;
			//DbcContext.getResponse().sendRedirect("login.html");
			//throw new ServiceException("101", "parame token is null");
		}
		return result;
	}
	
	/**
	 * 根据token获取用户消息
	 * @param token
	 * @return
	 */
	public static PubUser getUser(String token){
		PubUser pubUser  = loginUsers.get(token);
		if(pubUser != null){
			pubUser.setToken(token);
		}		
		return pubUser;
	}
	
	/**
	 * 生成token  暂用uuid
	 * @param user
	 * @return
	 */
	public  static String getToken(PubUser user){
		if(StringUtils.isEmpty(user.getToken())){
			return UUID.randomUUID().toString();
		}
		return user.getToken();
	}
}

package com.fantasia.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fantasia.base.action.BaseAction;
import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.bean.PubDept;
import com.fantasia.bean.PubUser;
import com.fantasia.core.CookieTool;
import com.fantasia.core.UserPermission;
import com.fantasia.dao.PubUserMapper;
import com.fantasia.exception.ServiceException;
import com.fantasia.service.PubDeptService;
import com.fantasia.service.PubUserService;



@Controller
@RequestMapping(value = "/user")
public class UserAction extends BaseAction {

	@Autowired
	private PubUserService userService;
	
	@Autowired
	private PubDeptService pubDeptService;
	
	@Autowired
	private PubUserMapper pubUserMapper;
	
	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public ResultMsg login(PubUser user){		
		
		PubUser pubUser  = pubUserMapper.queryUser(user.getToken());
		pubUser.setToken(user.getToken());
		UserPermission.loginUsers.put(user.getToken(), pubUser);
		
		ResultMsg  resultMsg = new ResultMsg();		
		CookieTool.addCookie(response, "token", user.getToken(), 3600);
		
		return resultMsg;
	}
	
	/**
	 * 获取部门消息
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/dept")
	@ResponseBody
	public List<PubDept> dept() throws ServiceException {		
		return pubDeptService.getDepts();		
	}
	
	/**
	 * 获取用户信息
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/queryUser")
	@ResponseBody
	public List<PubUser> queryUser() throws ServiceException {		
		return userService.queryUser();		
	}
	
	/**
	 * 获取用户相关信息
	 * 
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/getUser")
	@ResponseBody
	public ResultData getUserInfo(PageData page) throws ServiceException {
		return userService.getUsers(page);
	}
	
	/**
	 * 保存用户角色
	 * @param id 用户ID
	 * @param roleId
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/saveUserRole")
	@ResponseBody
	public ResultMsg saveUserRole(String id,String roleId)  {
		return userService.saveUserRole(id, roleId);
	}
	
}

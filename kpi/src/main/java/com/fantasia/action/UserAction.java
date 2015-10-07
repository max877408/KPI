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
	
	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public ResultMsg login() throws ServiceException {
		ResultMsg rtnMsg = new ResultMsg();

		return rtnMsg;
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
}

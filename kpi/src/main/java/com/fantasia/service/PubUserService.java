package com.fantasia.service;

import java.util.List;

import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.bean.PubUser;

public interface PubUserService {
	/**
	 * 取得所有用户
	 * 
	 * @return 用户列表
	 */
	public ResultData getUsers(PageData page);

	/**
	 * 查询用户信息
	 * 
	 * @return
	 */
	public List<PubUser> queryUser();

	/**
	 * 保存用户
	 * 
	 * @author JLC
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public ResultMsg saveUser(PubUser user);

	/**
	 * 更新用户
	 * 
	 * @param user
	 */
	public ResultMsg updateUser(PubUser user);

	/**
	 * 保存用户角色
	 * @param id
	 * @param roleId
	 * @return
	 */
	public ResultMsg saveUserRole(String id, String roleId);
	
	/**
	 * 更新用户部门负责人
	 * @param user
	 */	
	public ResultMsg updateDeptCharge(PubUser user);
	
	/**
	 * 获取部门负责人
	 * @param dept
	 * @return
	 */
	public PubUser getDeptChare(String dept);
}

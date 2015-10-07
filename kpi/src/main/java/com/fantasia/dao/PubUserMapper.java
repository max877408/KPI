package com.fantasia.dao;

import java.util.List;

import com.fantasia.bean.PubUser;

public interface PubUserMapper {

	/**
	 * 取得所有用户
	 * 
	 * @return 用户列表
	 */
	public List<PubUser> getUsers(int start,int rows);

	/**
	 * 保存用户
	 * 
	 * @author JLC
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public void saveUser(PubUser user);

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	public int deleteUser(Long id);
	
	/**
	 * 更新用户
	 * @param user
	 */
	public void updateUser(PubUser user);
 
}
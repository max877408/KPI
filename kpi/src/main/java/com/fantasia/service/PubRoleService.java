package com.fantasia.service;

import java.util.List;

import com.fantasia.bean.PubRole;

public interface PubRoleService {

	/**
	 * 新增角色
	 * @param pubRole
	 */
	public void insert(PubRole pubRole);
	
	/**
	 * 修改角色
	 * @param pubRole
	 */
	public void update(PubRole pubRole);
	
	/**
	 * 查询角色列表
	 * @return
	 */
	public List<PubRole> queryRoleList();
}

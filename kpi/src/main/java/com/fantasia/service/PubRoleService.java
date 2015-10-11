package com.fantasia.service;

import java.util.List;

import com.fantasia.bean.PubRole;
import com.fantasia.bean.PubRolePermission;

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
	public List<PubRole> queryRoleList(PubRole role);
	
	/**
	 * 删除角色信息
	 * @param id
	 */
	public void delRole(String id);
	
	/**
	 * 保存角色权限
	 * @param roleId 角色ID
	 * @param perId 权限ID
	 * @return
	 */
	public void saveRolePermission(String roleId,String perId);
	
	/**
	 * 查询角色权限
	 * @param roleId
	 * @return
	 */
	public List<PubRolePermission> getRolePermission(String roleId);
}

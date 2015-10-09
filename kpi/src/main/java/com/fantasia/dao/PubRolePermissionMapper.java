package com.fantasia.dao;

import java.util.List;

import com.fantasia.bean.PubRolePermission;

public interface PubRolePermissionMapper {

	/**
	 * 查询角色权限
	 * @param roleId
	 * @return
	 */
	public List<PubRolePermission> getRolePermission(String roleId);
	
    int delete(String roleId);

    int insert(PubRolePermission record);

    int update(PubRolePermission record);
}
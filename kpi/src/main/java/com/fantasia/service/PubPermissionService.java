package com.fantasia.service;

import com.fantasia.bean.PubPermission;

public interface PubPermissionService {
	/**
	 * 新增权限
	 * @param pubRole
	 */
	public void insert(PubPermission pubPermission);
	
	/**
	 * 修改权限
	 * @param pubRole
	 */
	public void update(PubPermission pubPermission);
}

package com.fantasia.dao;

import java.util.List;

import com.fantasia.bean.PubRole;

public interface PubRoleMapper {
   
    int deleteByPrimaryKey(String id);

    int insert(PubRole record);   

    int update(PubRole record);
    
    /**
	 * 查询角色列表
	 * @return
	 */
	public List<PubRole> queryRoleList();
}
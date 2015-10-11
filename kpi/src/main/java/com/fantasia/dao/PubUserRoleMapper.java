package com.fantasia.dao;

import com.fantasia.bean.PubUserRole;

public interface PubUserRoleMapper {

	PubUserRole selectByPrimaryKey(String id);   
	 
    int insert(PubUserRole record);

    int update(PubUserRole record);
    
    void delete(String roleId);
}
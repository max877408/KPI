package com.fantasia.dao;

import com.fantasia.bean.PubRolePermission;

public interface PubRolePermissionMapper {

    int deleteByPrimaryKey(String id);

    int insert(PubRolePermission record);

    int insertSelective(PubRolePermission record);
  
    PubRolePermission selectByPrimaryKey(String id);
  
    int updateByPrimaryKeySelective(PubRolePermission record);

    int updateByPrimaryKey(PubRolePermission record);
}
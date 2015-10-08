package com.fantasia.dao;

import com.fantasia.bean.PubPermission;

public interface PubPermissionMapper {
   
    int deleteByPrimaryKey(String id);

    int insert(PubPermission record);   

    int update(PubPermission record);
}
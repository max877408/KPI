package com.fantasia.dao;

import com.fantasia.bean.PubPermission;

public interface PubPermissionMapper {
   
    int deleteByPrimaryKey(String id);

    int insert(PubPermission record);

    int insertSelective(PubPermission record);   

    PubPermission selectByPrimaryKey(String id);

   
    int updateByPrimaryKeySelective(PubPermission record);

    int updateByPrimaryKey(PubPermission record);
}
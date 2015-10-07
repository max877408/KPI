package com.fantasia.dao;

import com.fantasia.bean.PubUserRole;

public interface PubUserRoleMapper {
   
    int deleteByPrimaryKey(String id);

    int insert(PubUserRole record);

    int insertSelective(PubUserRole record);
   
    PubUserRole selectByPrimaryKey(String id);
  
    int updateByPrimaryKeySelective(PubUserRole record);

    int updateByPrimaryKey(PubUserRole record);
}
package com.fantasia.dao;

import com.fantasia.bean.PubRole;

public interface PubRoleMapper {
   
    int deleteByPrimaryKey(String id);

    int insert(PubRole record);

    int insertSelective(PubRole record);
  
    PubRole selectByPrimaryKey(String id);
  
    int updateByPrimaryKeySelective(PubRole record);

    int updateByPrimaryKey(PubRole record);
}
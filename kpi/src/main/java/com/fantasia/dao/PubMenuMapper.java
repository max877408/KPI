package com.fantasia.dao;

import com.fantasia.bean.PubMenu;

public interface PubMenuMapper {  

    int deleteByPrimaryKey(String id);
    int insert(PubMenu record);
    int insertSelective(PubMenu record);

    PubMenu selectByPrimaryKey(String id);
 
    int updateByPrimaryKeySelective(PubMenu record);

    int updateByPrimaryKey(PubMenu record);
}
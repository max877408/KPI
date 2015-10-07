package com.fantasia.dao;

import java.util.List;

import com.fantasia.bean.PubDept;

public interface PubDeptMapper { 

    List<PubDept> getDepts();

    public void insertSelective(PubDept pubDept);
    
    public void updateByPrimaryKeySelective(PubDept pubDept);
}
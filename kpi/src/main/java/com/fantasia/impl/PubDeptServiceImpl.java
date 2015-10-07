package com.fantasia.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasia.bean.PubDept;
import com.fantasia.dao.PubDeptMapper;
import com.fantasia.service.PubDeptService;

@Service("deptService")
public class PubDeptServiceImpl implements PubDeptService {

	@Autowired
	private PubDeptMapper pubDeptMapper;
	
	@Override
	public List<PubDept> getDepts() {			
		List<PubDept> list = pubDeptMapper.getDepts();			
		return list;
	}

	@Override
	public void insertSelective(PubDept pubDept) {
		pubDeptMapper.insertSelective(pubDept);		
	}

	@Override
	public void updateByPrimaryKeySelective(PubDept pubDept) {
		pubDeptMapper.updateByPrimaryKeySelective(pubDept);		
	}	
}

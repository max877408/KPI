package com.fantasia.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasia.bean.PubRole;
import com.fantasia.core.DbcContext;
import com.fantasia.core.Utils;
import com.fantasia.dao.PubRoleMapper;
import com.fantasia.service.PubRoleService;

@Service("PubRoleService")
public class PubRoleServiceImpl implements PubRoleService{

	@Autowired
	private PubRoleMapper pubRoleMapper;
	
	@Override
	public void insert(PubRole pubRole) {
		pubRole.setId(Utils.getGUID());
		pubRole.setCreateBy(DbcContext.getAdminId());
		pubRole.setCreateTime(new Date());
		pubRoleMapper.insert(pubRole);		
	}

	@Override
	public void update(PubRole pubRole) {
		pubRoleMapper.update(pubRole);			
	}

	@Override
	public List<PubRole> queryRoleList() {
		return pubRoleMapper.queryRoleList();
	}	
}

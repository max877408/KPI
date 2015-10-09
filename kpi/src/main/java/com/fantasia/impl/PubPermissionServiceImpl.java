package com.fantasia.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasia.bean.PubPermission;
import com.fantasia.dao.PubPermissionMapper;
import com.fantasia.service.PubPermissionService;


@Service("PubPermissionService")
public class  PubPermissionServiceImpl implements PubPermissionService {

	@Autowired
	private PubPermissionMapper pubPermissionMapper;
	
	@Override
	public void insert(PubPermission pubPermission) {
		pubPermissionMapper.insert(pubPermission);
		
	}

	@Override
	public void update(PubPermission pubPermission) {
		pubPermissionMapper.update(pubPermission);
	}
}

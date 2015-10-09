package com.fantasia.dao;

import java.util.List;

import com.fantasia.bean.PubMenu;

public interface PubMenuMapper {

	int delete(String id);

	int insert(PubMenu record);

	int update(PubMenu record);
	
	public List<PubMenu> queryMenuList();
}
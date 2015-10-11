package com.fantasia.dao;

import java.util.List;

import com.fantasia.bean.PubMenu;

public interface PubMenuMapper {

	int delete(String id);

	int insert(PubMenu record);

	int update(PubMenu record);
	
	/**
	 * 获取所有的菜单列表
	 * @return
	 */
	public List<PubMenu> queryMenuList();
	
	/**
	 * 根据用户id获取所有的菜单
	 * @return
	 */
	public List<PubMenu> getMenuList(String userId);
}
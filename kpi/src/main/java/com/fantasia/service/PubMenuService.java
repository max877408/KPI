package com.fantasia.service;

import java.util.List;

import com.fantasia.bean.PubMenu;
import com.fantasia.bean.bus.MenuBean;
import com.fantasia.bean.bus.MenuRoot;

public interface PubMenuService {
	
	/**
	 * 获取权限菜单列表
	 * @return
	 */
	public List<MenuBean> queryMenuList();
	
	/**
	 * 获取导航菜单
	 * @return
	 */
	public MenuRoot queryMenu();
	
	/**
	 * 获取用户菜单列表
	 * @return
	 */
	public List<PubMenu> queryUserMenuList();
	
}

package com.fantasia.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fantasia.bean.PubMenu;
import com.fantasia.bean.bus.MenuBean;
import com.fantasia.bean.bus.MenuItem;
import com.fantasia.bean.bus.MenuNode;
import com.fantasia.bean.bus.MenuRoot;
import com.fantasia.core.DbcContext;
import com.fantasia.dao.PubMenuMapper;
import com.fantasia.service.PubMenuService;

@Service("PubMenuService")
public class PubMenuServiceImpl implements PubMenuService {

	@Autowired
	private PubMenuMapper pubMenuMapper;

	/**
	 * 获取菜单列表
	 */
	@Override
	public List<MenuBean> queryMenuList() {		
		List<MenuBean> menuList = new ArrayList<MenuBean>();
		List<PubMenu> list = pubMenuMapper.queryMenuList();
		
		//get root menu
		MenuBean menuBeanRoot = getMenuById("40715C35D8364F80AACF6A784895C6B0",list);
		List<MenuBean> temp= getChildMenu("40715C35D8364F80AACF6A784895C6B0",list);
		menuBeanRoot.setChildren(temp);		
		
		//get first level menu
		if (temp != null && temp.size() > 0) {
			for (MenuBean menuBean : temp) {
				List<MenuBean> childrens = getChildMenu(menuBean.getId(),list);
				menuBean.setChildren(childrens);
			}
		}
		
		menuList.add(menuBeanRoot);
		return menuList;
	}

	/**
	 * 根据ID获取子菜单
	 * @param id
	 * @param list
	 * @return
	 */
	private List<MenuBean> getChildMenu(String id, List<PubMenu> list) {
		List<MenuBean> menuList = new ArrayList<MenuBean>();
		if (list != null && list.size() > 0) {
			for (PubMenu menu : list) {
				if(!StringUtils.isEmpty(menu.getParentMenu()) && menu.getParentMenu().equals(id)){
					MenuBean menuBean = new MenuBean();
					menuBean.setId(menu.getId());
					menuBean.setText(menu.getMenuName());
					menuList.add(menuBean);
				}
			}
		}
		return menuList;
	}
	
	/**
	 * 根据ID获取菜单
	 * @param id
	 * @param list
	 * @return
	 */
	private MenuBean getMenuById(String id, List<PubMenu> list) {
		MenuBean menuBean = new MenuBean();
		if (list != null && list.size() > 0) {
			for (PubMenu menu : list) {
				if(menu.getId().equals(id)){					
					menuBean.setId(menu.getId());
					menuBean.setText(menu.getMenuName());					
				}
			}
		}
		return menuBean;
	}
	
	/**
	 * 获取导航菜单
	 * @return
	 */
	public MenuRoot queryMenu(){ 
		MenuRoot root = new MenuRoot();
		List<MenuItem> basic = new ArrayList<MenuItem>();
		List<PubMenu> list = pubMenuMapper.getMenuList(DbcContext.getUserId());
		
		//获取一级菜单节点
		List<MenuItem> items = getChildNavigation("40715C35D8364F80AACF6A784895C6B0",list);
		List<MenuBean> temp= getChildMenu("40715C35D8364F80AACF6A784895C6B0",list);
		basic.addAll(items);
		
	
		// 获取子菜单
		if (temp != null && temp.size() > 0) {	
			for (int i = 0; i < temp.size(); i++) {
				 List<MenuNode> milist = getChildNode(temp.get(i).getId(),list);
				 items.get(i).setMenus(milist);
			}
		}
		
		root.setMenus(basic);
		return root;
	}
	
	/**
	 * 获取导航子菜单
	 * @param id
	 * @param list
	 * @return
	 */
	private List<MenuItem> getChildNavigation(String id, List<PubMenu> list) {
		List<MenuItem> menuList = new ArrayList<MenuItem>();
		if (list != null && list.size() > 0) {
			for (PubMenu menu : list) {
				if(!StringUtils.isEmpty(menu.getParentMenu()) && menu.getParentMenu().equals(id)){
					MenuItem menuItem = new MenuItem();
					menuItem.setMenuid(menu.getId());
					menuItem.setMenuname(menu.getMenuName());				
					menuList.add(menuItem);
				}
			}
		}
		return menuList;
	}
	
	/**
	 * 根据ID获取子菜单
	 * @param id
	 * @param list
	 * @return
	 */
	private List<MenuNode> getChildNode(String id, List<PubMenu> list) {
		List<MenuNode> menuList = new ArrayList<MenuNode>();
		if (list != null && list.size() > 0) {
			for (PubMenu menu : list) {
				if(!StringUtils.isEmpty(menu.getParentMenu()) && menu.getParentMenu().equals(id)){
					MenuNode menuNode = new MenuNode();
					menuNode.setMenuid(menu.getId());
					menuNode.setMenuname(menu.getMenuName());
					menuNode.setUrl(menu.getMenuUrl());
					menuList.add(menuNode);
				}
			}
		}
		return menuList;
	}
	
	/**
	 * 获取用户菜单列表
	 * @return
	 */
	public List<PubMenu> queryUserMenuList(){
		List<PubMenu> list = pubMenuMapper.getMenuList(DbcContext.getUserId());
		return list;
	}
}

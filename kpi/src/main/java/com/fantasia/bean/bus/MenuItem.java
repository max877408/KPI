package com.fantasia.bean.bus;

import java.util.List;

public class MenuItem {

	private String menuid;
	
	private String icon = "icon-sys";
	
	private String menuname;
	
	private List<MenuNode> menus;

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public List<MenuNode> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuNode> menus) {
		this.menus = menus;
	}	
}

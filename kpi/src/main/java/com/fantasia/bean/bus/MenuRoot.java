package com.fantasia.bean.bus;

import java.util.List;

public class MenuRoot {
	private List<MenuItem> basic;

	public List<MenuItem> getMenus() {
		return basic;
	}

	public void setMenus(List<MenuItem> menus) {
		this.basic = menus;
	}
}

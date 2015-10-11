package com.fantasia.bean.bus;

import java.util.List;

import com.fantasia.bean.PubMenu;
import com.fantasia.bean.PubUser;
import com.fantasia.bean.PubUserRole;

public class UserContext {
	/**
	 * 用户信息
	 */
	private PubUser user;
	
	/**
	 * 用户菜单
	 */
	private List<PubMenu> userMenu;
	
	/**
	 * 用户角色
	 */
	private PubUserRole userRole;

	public PubUser getUser() {
		return user;
	}

	public void setUser(PubUser user) {
		this.user = user;
	}

	public List<PubMenu> getUserMenu() {
		return userMenu;
	}

	public void setUserMenu(List<PubMenu> userMenu) {
		this.userMenu = userMenu;
	}

	public PubUserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(PubUserRole userRole) {
		this.userRole = userRole;
	}	
}

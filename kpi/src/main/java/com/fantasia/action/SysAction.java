package com.fantasia.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fantasia.base.action.BaseAction;
import com.fantasia.bean.PubMenu;
import com.fantasia.bean.bus.MenuRoot;
import com.fantasia.bean.bus.UserContext;
import com.fantasia.core.DbcContext;
import com.fantasia.service.PubMenuService;

@Controller
@RequestMapping(value = "/sys")
public class SysAction extends BaseAction {

	@Autowired
	private PubMenuService pubMenuService;	

	/**
	 * 获取菜单列表
	 * @return
	 */
	@RequestMapping(value = "/getMenuList")
	@ResponseBody
	public MenuRoot getMenuList() {		
		MenuRoot root = pubMenuService.queryMenu();        
        return root;
	}	
	
	/**
	 * 获取用户context
	 * @return
	 */
	@RequestMapping(value = "/getUserContext")
	@ResponseBody
	public UserContext getUserContext() {	
		UserContext usercontext = new UserContext();
		List<PubMenu> list = pubMenuService.queryUserMenuList(); 
		usercontext.setUserMenu(list);  
		usercontext.setUser(DbcContext.getUser());
        return usercontext;
	}
}

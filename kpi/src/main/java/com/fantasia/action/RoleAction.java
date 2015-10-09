package com.fantasia.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fantasia.base.action.BaseAction;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.bean.PubRole;
import com.fantasia.bean.PubRolePermission;
import com.fantasia.bean.bus.MenuBean;
import com.fantasia.exception.ServiceException;
import com.fantasia.service.PubMenuService;
import com.fantasia.service.PubRoleService;



@Controller
@RequestMapping(value = "/role")
public class RoleAction extends BaseAction {

	@Autowired
	private PubRoleService pubRoleService;
	
	@Autowired
	private PubMenuService pubMenuService;	
	
    /**
     * 新增角色信息
     * @return
     * @throws ServiceException
     */
	@RequestMapping(value = "/saveRole")
	@ResponseBody
	public ResultMsg saveRole(PubRole pubRole) {
		ResultMsg rtnMsg = new ResultMsg();
		if(StringUtils.isEmpty(pubRole.getId())){
			pubRoleService.insert(pubRole);
		}
		else{
			pubRoleService.update(pubRole);
		}
		
		return rtnMsg;
	}
	
	/**
	 * 修改角色
	 * @param pubRole
	 * @return
	 */
	@RequestMapping(value = "/editRole")
	@ResponseBody
	public ResultMsg editRole(PubRole pubRole) {
		ResultMsg rtnMsg = new ResultMsg();
		pubRoleService.update(pubRole);
		return rtnMsg;
	}
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/delRole")
	@ResponseBody
	public ResultMsg delRole(String id) throws ServiceException {
		ResultMsg rtnMsg = new ResultMsg();
		pubRoleService.delRole(id);
		return rtnMsg;
	}
	
	 /**
     * 查询角色信息
     * @return
     * @throws ServiceException
     */
	@RequestMapping(value = "/queryRoleList")
	@ResponseBody
	public List<PubRole> queryRoleList() {		
		List<PubRole> list = pubRoleService.queryRoleList();		
		return list;
	}
	
	/*
	 * 获取菜单列表
	 */
	@RequestMapping(value = "/queryMenuList")
	@ResponseBody
	public List<MenuBean> queryMenuList() {		
		List<MenuBean> list = pubMenuService.queryMenuList();	
		return list;
	}
	
	/**
	 * 获取角色权限
	 * @return
	 */
	@RequestMapping(value = "/getRolePermission")
	@ResponseBody
	public List<PubRolePermission> getRolePermission(String roleId) {		
		return pubRoleService.getRolePermission(roleId);
	}
	
	/**
	 * 保存角色权限
	 * @return
	 */
	@RequestMapping(value = "/saveRolePermission")
	@ResponseBody
	public ResultMsg saveRolePermission(String id,String perId) {	
		ResultMsg rtnMsg = new ResultMsg();
		pubRoleService.saveRolePermission(id, perId);
		return rtnMsg;
	}
}

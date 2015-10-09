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
import com.fantasia.exception.ServiceException;
import com.fantasia.service.PubRoleService;



@Controller
@RequestMapping(value = "/sys")
public class SysAction extends BaseAction {

	@Autowired
	private PubRoleService pubRoleService;
	
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
}

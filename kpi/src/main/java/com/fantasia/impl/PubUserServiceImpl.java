package com.fantasia.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.bean.PubUser;
import com.fantasia.bean.PubUserRole;
import com.fantasia.core.DbcContext;
import com.fantasia.core.Utils;
import com.fantasia.dao.PubUserMapper;
import com.fantasia.dao.PubUserRoleMapper;
import com.fantasia.service.PubUserService;

@Service("userService")
public class PubUserServiceImpl implements PubUserService {

	@Autowired
	private PubUserMapper pubUserMapper;	
	
	@Autowired
	private PubUserRoleMapper pubUserRoleMapper;

	@Override
	public ResultData getUsers(PageData page) {
		ResultData data = new ResultData();
		page.setDeptId(DbcContext.getRequest().getParameter("dept"));
		page.setUserId(DbcContext.getRequest().getParameter("userName"));
		int start = (page.getPage() -1) * page.getRows();
		page.setStart(start);
		List<PubUser> list = pubUserMapper.getUsers(page);
		data.setRows(list);
		
		//TotalRows 
		PageData totalPage = new PageData();
		totalPage.setDeptId(DbcContext.getRequest().getParameter("dept"));
		totalPage.setUserId(DbcContext.getRequest().getParameter("userName"));
		totalPage.setStart(0);
		totalPage.setRows(PageData.MAX_ROWS);
		list = pubUserMapper.getUsers(totalPage);	
		data.setTotal(list.size());
		return data;
	}

	@Override
	public ResultMsg saveUser(PubUser user) {
		ResultMsg rtnMsg = new ResultMsg();
	    pubUserMapper.saveUser(user);
		return rtnMsg;
	}
	

	@Override
	public ResultMsg updateUser(PubUser user) {
		ResultMsg rtnMsg = new ResultMsg();
		pubUserMapper.updateUser(user);
		return rtnMsg;
	}

	@Override
	public List<PubUser> queryUser() {
		PageData page = new PageData();
		page.setStart(0);
		page.setRows(PageData.MAX_ROWS);
		List<PubUser> list = pubUserMapper.getUsers(page);
		return list;
	}
	
	/**
	 * 保存用户角色
	 * @param id
	 * @param roleId
	 * @return
	 */
	@Override
	@Transactional
	public ResultMsg saveUserRole(String id,String roleId){
		ResultMsg rtnMsg = new ResultMsg();
		
		//删除用户角色
		pubUserRoleMapper.delete(id);
		
		PubUserRole record = new PubUserRole();
		record.setUserId(id);
		record.setRoleId(roleId);
		record.setId(Utils.getGUID());
		record.setCreateBy(DbcContext.getAdminId());
		record.setCreateTime(new Date());
		pubUserRoleMapper.insert(record);
		return rtnMsg;
	}
	
	/**
	 * 更新用户部门负责人
	 * @param user
	 */	
	@Override
	public ResultMsg updateDeptCharge(PubUser user){
		ResultMsg rtnMsg = new ResultMsg();
		
		if(!StringUtils.isEmpty(user.getId()) && !user.getDeptId().equalsIgnoreCase("null")){
			//更新部门负责人为空
			PubUser deptUser = new PubUser();
			deptUser.setDeptId(user.getDeptId());
			deptUser.setIsCharge("0");
			deptUser.setModifyBy(DbcContext.getUserId());
			deptUser.setModifyTime(new Date());
			pubUserMapper.updateDeptCharge(deptUser);
			
			//更新当前用户为部门负责人
			PubUser curUser = new PubUser();
			curUser.setId(user.getId());
			curUser.setIsCharge("1");
			curUser.setModifyBy(DbcContext.getUserId());
			curUser.setModifyTime(new Date());
			pubUserMapper.updateDeptCharge(curUser);
		}		
		
		return rtnMsg;
	}
	
	/**
	 * 获取部门负责人
	 * @param dept
	 * @return
	 */
	@Override
	public PubUser getDeptChare(String dept){
		PubUser user = null;
		PageData page = new PageData();
		page.setStart(0);
		page.setRows(PageData.MAX_ROWS);
		List<PubUser> list = pubUserMapper.getUsers(page);
		if(list != null && list.size() > 0){
			for (PubUser pubUser : list) {
				if(pubUser.getDeptName() != null && pubUser.getDeptName().equalsIgnoreCase(dept)){
					if(pubUser.getIsCharge().equalsIgnoreCase("是")){
						user = pubUser;
					}
				}
				
			}
		}
		return user;
	}
	
}

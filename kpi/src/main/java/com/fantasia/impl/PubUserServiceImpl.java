package com.fantasia.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		int start = (page.getPage() -1) * page.getRows();
		List<PubUser> list = pubUserMapper.getUsers(start,page.getRows());
		data.setRows(list);
		
		//TotalRows 
		list = pubUserMapper.getUsers(0,PageData.MAX_ROWS);	
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
		List<PubUser> list = pubUserMapper.getUsers(0,PageData.MAX_ROWS);
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
}

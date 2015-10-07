package com.fantasia.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.bean.PubUser;
import com.fantasia.dao.PubUserMapper;
import com.fantasia.service.PubUserService;

@Service("userService")
public class PubUserServiceImpl implements PubUserService {

	@Autowired
	private PubUserMapper pubUserMapper;	

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
	
	
}

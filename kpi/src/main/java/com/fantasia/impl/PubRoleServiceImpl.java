package com.fantasia.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fantasia.bean.PubRole;
import com.fantasia.bean.PubRolePermission;
import com.fantasia.core.DbcContext;
import com.fantasia.core.Utils;
import com.fantasia.dao.PubRoleMapper;
import com.fantasia.dao.PubRolePermissionMapper;
import com.fantasia.service.PubRoleService;

@Service("PubRoleService")
public class PubRoleServiceImpl implements PubRoleService{

	@Autowired
	private PubRoleMapper pubRoleMapper;
	
	@Autowired
	private PubRolePermissionMapper pubRolePermissionMapper;
	
	@Override
	public void insert(PubRole pubRole) {
		pubRole.setId(Utils.getGUID());
		pubRole.setCreateBy(DbcContext.getAdminId());
		pubRole.setCreateTime(new Date());
		pubRoleMapper.insert(pubRole);		
	}

	@Override
	public void update(PubRole pubRole) {
		pubRoleMapper.update(pubRole);			
	}

	@Override
	public List<PubRole> queryRoleList() {
		return pubRoleMapper.queryRoleList();
	}	
	
	/**
	 * 删除角色信息
	 * @param id
	 */
	@Override
	public void delRole(String id){
		pubRoleMapper.delete(id);
	}
	
	/**
	 * 保存角色权限
	 * @param id
	 * @param perId
	 * @return
	 */
	@Transactional
	public void saveRolePermission(String roleId,String perId){
		pubRolePermissionMapper.delete(roleId);
		if(!StringUtils.isEmpty(perId)){
			String[] perIds = perId.split(",");
			
			if(perIds != null && perIds.length > 0 ){
				for (int i = 0; i < perIds.length; i++) {
					PubRolePermission record = new PubRolePermission();
					
					record.setRoleId(roleId);
					record.setObjectCode(perIds[i]);
					record.setId(Utils.getGUID());
					record.setCreateBy(DbcContext.getAdminId());
					record.setCreateTime(new Date());
					pubRolePermissionMapper.insert(record);
				}
			}
		}
	}
	
	/**
	 * 查询角色权限
	 * @param roleId
	 * @return
	 */
	public List<PubRolePermission> getRolePermission(String roleId){
		return pubRolePermissionMapper.getRolePermission(roleId);
	}
}

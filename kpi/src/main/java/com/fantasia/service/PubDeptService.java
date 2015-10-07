package com.fantasia.service;

import java.util.List;

import com.fantasia.bean.PubDept;

public interface PubDeptService {
	/**
	 * 获取所有部门
	 * @return
	 */
	List<PubDept> getDepts();

	/**
	 * 新增部门
	 * @param pubDept
	 */
	public void insertSelective(PubDept pubDept);

	/**
	 * 修改部门
	 * @param pubDept
	 */
	public void updateByPrimaryKeySelective(PubDept pubDept);
}

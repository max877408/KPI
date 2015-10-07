package com.fantasia.dao;

import java.util.List;

import com.fantasia.bean.KpiGroupYear;

public interface KpiGroupYearMapper {
	
	public  List<KpiGroupYear> getKpiGroup(int start,int rows);
	
	public void insert(KpiGroupYear record);

	public void update(KpiGroupYear record);
	
	public void DeleteKpiGroup(String id);
}
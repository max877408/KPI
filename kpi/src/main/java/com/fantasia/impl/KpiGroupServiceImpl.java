package com.fantasia.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.bean.KpiGroupYear;
import com.fantasia.core.DbcContext;
import com.fantasia.core.Utils;
import com.fantasia.dao.KpiGroupYearMapper;
import com.fantasia.service.KpiGroupService;

@Service("KpiGroupService")
public class KpiGroupServiceImpl implements KpiGroupService {

	@Autowired
	private KpiGroupYearMapper kpiGroupYearMapper;

	@Override
	public ResultData getKpiGroup(PageData page){
		ResultData data = new ResultData();
		int start = (page.getPage() -1) * page.getRows();
		List<KpiGroupYear> list = kpiGroupYearMapper.getKpiGroup(start,page.getRows());
		data.setRows(list);
		
		//TotalRows 
		list = kpiGroupYearMapper.getKpiGroup(0,PageData.MAX_ROWS);	
		data.setTotal(list.size());
		return data;
	}
	
	@Override
	public void SaveKpiGroup(List<KpiGroupYear> list) {
		for (KpiGroupYear kpiGroupYear : list) {
			kpiGroupYear.setId(Utils.getGUID());
			kpiGroupYear.setCreateBy(DbcContext.getAdminId());
			kpiGroupYear.setCreateTime(new Date());

			kpiGroupYearMapper.insert(kpiGroupYear);
		}	
	}

	@Override
	public void UpdateKpiGroup(List<KpiGroupYear> list) {		
		for (KpiGroupYear kpiGroupYear : list) {			
			kpiGroupYear.setModifyBy(DbcContext.getAdminId());
			kpiGroupYear.setModifyTime(new Date());

			kpiGroupYearMapper.update(kpiGroupYear);
		}		
	}

	@Override
	public ResultMsg DeleteKpiGroup(String id) {	
		ResultMsg msg = new ResultMsg();
		kpiGroupYearMapper.DeleteKpiGroup(id);
		return msg;
	}

	
}
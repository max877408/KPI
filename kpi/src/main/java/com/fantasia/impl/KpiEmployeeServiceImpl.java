package com.fantasia.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.bean.KpiEmployeeYear;
import com.fantasia.bean.KpiEmployeeYearBean;
import com.fantasia.core.DbcContext;
import com.fantasia.core.Utils;
import com.fantasia.dao.KpiEmployeeYearMapper;
import com.fantasia.service.KpiEmployeeMonthService;
import com.fantasia.service.KpiEmployeeService;

@Service("KpiEmployeeService")
public class KpiEmployeeServiceImpl implements KpiEmployeeService {

	@Autowired
	private KpiEmployeeYearMapper kpiEmployeeYearMapper;
	
	@Autowired
	private KpiEmployeeMonthService kpiEmployeeMonthService;
	
	@Override
	public ResultData getKpiEmployee(PageData page) {
		
		ResultData data = new ResultData();
		int start = (page.getPage() -1) * page.getRows();
		List<KpiEmployeeYearBean> list = kpiEmployeeYearMapper.getKpiEmployee(start,page.getRows());
		data.setRows(list);
		
		//TotalRows 
		list = kpiEmployeeYearMapper.getKpiEmployee(0,PageData.MAX_ROWS);	
		data.setTotal(list.size());
		return data;
	}

	@Override
	public KpiEmployeeYearBean getKpiEmployee(String id) {
		return kpiEmployeeYearMapper.getKpiEmployeeById(id);
	}

	@Override
	public void SaveKpiEmployee(List<KpiEmployeeYear> list, String deptKpi) {
		if(list != null && list.size() > 0){
			for (KpiEmployeeYear kpiEmployeeYear : list) {
				kpiEmployeeYear.setId(Utils.getGUID());	
				kpiEmployeeYear.setStatus("1");
				kpiEmployeeYear.setKpiId(deptKpi);
				kpiEmployeeYear.setCreateBy(DbcContext.getAdminId());
				kpiEmployeeYear.setCreateTime(new Date());
				kpiEmployeeYearMapper.insert(kpiEmployeeYear);
			}
			
			//新增员工月度绩效考核指标
			kpiEmployeeMonthService.inertEmployeeMonthKpi(list);
		}
	}

	@Override
	public List<KpiEmployeeYear> getKpiEmployeeDetail(String id) {		
		return kpiEmployeeYearMapper.getKpiEmployeeDetail(id);
	}
}

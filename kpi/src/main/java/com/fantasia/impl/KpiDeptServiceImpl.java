package com.fantasia.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.bean.KpiDeptYear;
import com.fantasia.bean.KpiDeptYearBean;
import com.fantasia.core.DbcContext;
import com.fantasia.core.Utils;
import com.fantasia.dao.KpiDeptYearMapper;
import com.fantasia.service.KpiDeptService;

@Service("KpiDeptService")
public class KpiDeptServiceImpl implements KpiDeptService {

	@Autowired
	private KpiDeptYearMapper kpiDeptYearMapper;
	
	@Override
	public String SaveKpiDept(KpiDeptYearBean kpiDeptYear) {		 
		KpiDeptYear record = new KpiDeptYear();
		record.setId(Utils.getGUID());		
		if(!StringUtils.isEmpty(kpiDeptYear)){				
			record.setGroupId(kpiDeptYear.getId());
			record.setWeight(kpiDeptYear.getWeight());
			record.setContent(kpiDeptYear.getContent());
			record.setYearTarget(kpiDeptYear.getYearTarget());
			record.setStandard(kpiDeptYear.getStandard());
			record.setProjectLevel(kpiDeptYear.getProjectLevel());
			record.setDifficulty(kpiDeptYear.getDifficulty());
			//record.setResponsiblePerson(kpiDeptYear.getResponsiblePerson());
			record.setStatus("1");
			record.setCreateBy(DbcContext.getAdminId());
			record.setCreateTime(new Date());
			
			kpiDeptYearMapper.insert(record);
		}
		return record.getId();
	}
	
	@Override
	public ResultData getKpiDept(PageData page) {
		ResultData data = new ResultData();
		int start = (page.getPage() -1) * page.getRows();
		List<KpiDeptYearBean> list = kpiDeptYearMapper.getKpiDept(start,page.getRows());
		data.setRows(list);
		
		//TotalRows 
		list = kpiDeptYearMapper.getKpiDept(0,PageData.MAX_ROWS);	
		data.setTotal(list.size());
		return data;
	}

	@Override
	public KpiDeptYearBean getKpiDeptById(String id) {
		return kpiDeptYearMapper.getKpiDeptById(id);
	}
}

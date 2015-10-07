package com.fantasia.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.bean.KpiDeptMonthBean;
import com.fantasia.bean.KpiEmployeeYear;
import com.fantasia.bean.kpiEmployeeMonth;
import com.fantasia.core.DbcContext;
import com.fantasia.core.Utils;
import com.fantasia.dao.kpiEmployeeMonthMapper;
import com.fantasia.service.KpiEmployeeMonthService;

@Service("KpiEmployeeMonthService")
public class KpiEmployeeMonthServiceImpl implements KpiEmployeeMonthService {

	private static Logger _log = LoggerFactory.getLogger(KpiEmployeeMonthServiceImpl.class);
	
	@Autowired
	private kpiEmployeeMonthMapper kpiEmployeeMonthMapper;

	@Override
	public void InsertKpi(kpiEmployeeMonth kpiEmployeeMonth) {
		kpiEmployeeMonthMapper.insert(kpiEmployeeMonth);		
	}
	
	/**
	 * 新增员工月度考核指标
	 * @param list
	 */
	public void inertEmployeeMonthKpi(List<KpiEmployeeYear> list){
		if(list != null && list.size() > 0){
			SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
			for (KpiEmployeeYear kpiEmployeeYear : list) {
				try {
					Date startDate = sdf.parse(kpiEmployeeYear.getStartTime()) ;
					Date endDate = sdf.parse(kpiEmployeeYear.getEndTime()) ;
					Calendar startTime = Calendar.getInstance();
					startTime.setTime(startDate);
					Calendar endTime = Calendar.getInstance();
					endTime.setTime(endDate);
					
					
					if(startTime.get(Calendar.YEAR)== endTime.get(Calendar.YEAR)){
						for(int month =startTime.get(Calendar.MONTH); month <= (endTime.get(Calendar.MONTH) + 1); month++){
							kpiEmployeeMonth record = new kpiEmployeeMonth();
							record.setId(Utils.getGUID());
							record.setKpiId(kpiEmployeeYear.getId());
							record.setKpiMonth(month);
							record.setKpiYear(startTime.get(Calendar.YEAR));
							record.setCreateBy(DbcContext.getAdminId());
							record.setCreateTime(new Date());
							record.setStatus("1");
							kpiEmployeeMonthMapper.insert(record);
						}
					}
					else{
						//跨年情况
						for(int month = startTime.get(Calendar.MONTH); month <= 12; month++){
							kpiEmployeeMonth record = new kpiEmployeeMonth();
							record.setId(Utils.getGUID());
							record.setKpiId(kpiEmployeeYear.getId());
							record.setKpiMonth(month);
							record.setKpiYear(startTime.get(Calendar.YEAR));
							record.setCreateBy(DbcContext.getAdminId());
							record.setCreateTime(new Date());
							record.setStatus("1");
							kpiEmployeeMonthMapper.insert(record);
						}
						
						for(int month = 1; month <=  endTime.get(Calendar.MONTH); month++){
							kpiEmployeeMonth record = new kpiEmployeeMonth();
							record.setId(Utils.getGUID());
							record.setKpiId(kpiEmployeeYear.getId());
							record.setKpiMonth(month);
							record.setKpiYear(endTime.get(Calendar.YEAR));
							record.setCreateBy(DbcContext.getAdminId());
							record.setCreateTime(new Date());
							record.setStatus("1");
							kpiEmployeeMonthMapper.insert(record);
						}
					}
					
				} catch (ParseException e) {
					_log.error("date convert error",e);
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public ResultData getKpiEmployeeMonth(int page, int rows, int kpiYear,int kpiMonth) {
		ResultData data = new ResultData();
		int start = (page -1) * rows;
		List<KpiDeptMonthBean> list = kpiEmployeeMonthMapper.getKpiEmpoyeeMonth(kpiYear,kpiMonth, start,rows);
		data.setRows(list);
		
		//TotalRows 
		list = kpiEmployeeMonthMapper.getKpiEmpoyeeMonth(kpiYear,kpiMonth,0,PageData.MAX_ROWS);	
		data.setTotal(list.size());
		return data;
	}	
}

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
import com.fantasia.bean.KpiDeptYearDetail;
import com.fantasia.bean.kpiDeptMonth;
import com.fantasia.core.DbcContext;
import com.fantasia.core.Utils;
import com.fantasia.dao.kpiDeptMonthMapper;
import com.fantasia.service.KpiDeptMonthService;

@Service("KpiDeptMonthService")
public class KpiDeptMonthServiceImpl implements KpiDeptMonthService {

	@Autowired
	private kpiDeptMonthMapper kpiDeptMonthMapper;
	
	private static Logger _log = LoggerFactory.getLogger(KpiDeptMonthServiceImpl.class);
	
	/**
	 * 新增部门月度考核指标
	 * @param list
	 */
	public void inertDeptMonthKpi(List<KpiDeptYearDetail> list){
		if(list != null && list.size() > 0){
			SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
			for (KpiDeptYearDetail kpiDeptYearDetail : list) {
				try {
					Date startDate = sdf.parse(kpiDeptYearDetail.getStartTime()) ;
					Date endDate = sdf.parse(kpiDeptYearDetail.getEndTime()) ;
					Calendar startTime = Calendar.getInstance();
					startTime.setTime(startDate);
					Calendar endTime = Calendar.getInstance();
					endTime.setTime(endDate);
					
					
					if(startTime.get(Calendar.YEAR)== endTime.get(Calendar.YEAR)){
						for(int month =startTime.get(Calendar.MONTH); month <= endTime.get(Calendar.MONTH); month++){
							kpiDeptMonth record = new kpiDeptMonth();
							record.setId(Utils.getGUID());
							record.setKpiId(kpiDeptYearDetail.getId());
							record.setKpiMonth(month);
							record.setKpiYear(startTime.get(Calendar.YEAR));
							record.setCreateBy(DbcContext.getAdminId());
							record.setCreateTime(new Date());
							record.setStatus("1");
							kpiDeptMonthMapper.insert(record);
						}
					}
					else{
						//跨年情况
						for(int month = startTime.get(Calendar.MONTH); month <= 12; month++){
							kpiDeptMonth record = new kpiDeptMonth();
							record.setId(Utils.getGUID());
							record.setKpiId(kpiDeptYearDetail.getId());
							record.setKpiMonth(month);
							record.setKpiYear(startTime.get(Calendar.YEAR));
							record.setCreateBy(DbcContext.getAdminId());
							record.setCreateTime(new Date());
							record.setStatus("1");
							kpiDeptMonthMapper.insert(record);
						}
						
						for(int month = 1; month <=  endTime.get(Calendar.MONTH); month++){
							kpiDeptMonth record = new kpiDeptMonth();
							record.setId(Utils.getGUID());
							record.setKpiId(kpiDeptYearDetail.getId());
							record.setKpiMonth(month);
							record.setKpiYear(endTime.get(Calendar.YEAR));
							record.setCreateBy(DbcContext.getAdminId());
							record.setCreateTime(new Date());
							record.setStatus("1");
							kpiDeptMonthMapper.insert(record);
						}
					}
					
				} catch (ParseException e) {
					_log.error("date convert error",e);
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 查询部门月度绩效考核指标
	 */
	@Override
	public ResultData getKpiDeptMonth(int page,int rows,int kpiYear,int kpiMonth) {
		ResultData data = new ResultData();
		int start = (page -1) * rows;
		List<KpiDeptMonthBean> list = kpiDeptMonthMapper.getKpiDeptMonth(kpiYear,kpiMonth, start,rows);
		data.setRows(list);
		
		//TotalRows 
		list = kpiDeptMonthMapper.getKpiDeptMonth(kpiYear,kpiMonth,0,PageData.MAX_ROWS);	
		data.setTotal(list.size());
		return data;
	}
}

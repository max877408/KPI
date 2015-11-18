package com.fantasia.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasia.bean.KpiDeptYearDetail;
import com.fantasia.core.DbcContext;
import com.fantasia.core.Utils;
import com.fantasia.dao.KpiDeptYearDetailMapper;
import com.fantasia.service.KpiDeptDetailService;
import com.fantasia.util.StringUtils;

@Service("KpiDeptDetailService")
public class KpiDeptDetailServiceImpl implements KpiDeptDetailService {

	@Autowired
	private KpiDeptYearDetailMapper kpiDeptYearDetailMapper;

	/**
	 * 保存部门年度绩效考核
	 */
	@Override
	public void SaveKpiDetail(List<KpiDeptYearDetail> list,String deptKpi) {
		if(list != null && list.size() > 0){
			int i = 0;
			for (KpiDeptYearDetail kpiDeptYearDetail : list) {
				if(!StringUtils.isAnyoneEmpty(kpiDeptYearDetail.getKeyPoint(),
											kpiDeptYearDetail.getLeadPerson(),
											kpiDeptYearDetail.getStartTime(),
											kpiDeptYearDetail.getEndTime())){
					
					kpiDeptYearDetail.setDeptKpiId(deptKpi);			
					
					if(!StringUtils.isAnyoneEmpty(kpiDeptYearDetail.getId())){	
						
						kpiDeptYearDetail.setModifyBy(DbcContext.getAdminId());
						kpiDeptYearDetail.setModifyTime(new Date());
						kpiDeptYearDetailMapper.update(kpiDeptYearDetail);
					}
					else{
						kpiDeptYearDetail.setId(Utils.getGUID());	
						kpiDeptYearDetail.setCreateBy(DbcContext.getUserId());
						kpiDeptYearDetail.setCreateTime(new Date());
						kpiDeptYearDetail.setStatus("1");
						kpiDeptYearDetail.setSort(i);						
						kpiDeptYearDetailMapper.insert(kpiDeptYearDetail);
						i++;
					}
				}
							
			}
		}
	}
	
	
	@Override
	public List<KpiDeptYearDetail> getKpiDeptDetailById(String id) {
		return kpiDeptYearDetailMapper.getKpiDeptDetailById(id);
	}
	
	/**
	 * 根据id获取部门关键节点
	 * @param id
	 * @return
	 */
	@Override
	public KpiDeptYearDetail selectById(String id){
		return kpiDeptYearDetailMapper.selectById(id);
	}
	
	/**
	 * 批量删除部门年度计划
	 * @param list
	 */
	public void bachDeleDeptKpi(List<KpiDeptYearDetail> list){
		for (KpiDeptYearDetail kpiDeptYearDetail : list) {
			kpiDeptYearDetailMapper.delDeptKpi(kpiDeptYearDetail.getId());
		}
	}
	
	
}

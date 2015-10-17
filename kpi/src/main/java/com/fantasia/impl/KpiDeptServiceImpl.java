package com.fantasia.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fantasia.base.bean.ListData;
import com.fantasia.base.bean.PageData;
import com.fantasia.base.bean.ResultData;
import com.fantasia.base.bean.ResultMsg;
import com.fantasia.bean.KpiDeptYear;
import com.fantasia.bean.KpiDeptYearBean;
import com.fantasia.bean.KpiDeptYearDetail;
import com.fantasia.bean.KpiGroupYear;
import com.fantasia.core.DbcContext;
import com.fantasia.core.Utils;
import com.fantasia.dao.KpiDeptYearMapper;
import com.fantasia.dao.KpiGroupYearMapper;
import com.fantasia.exception.ServiceException;
import com.fantasia.service.KpiDeptDetailService;
import com.fantasia.service.KpiDeptService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("KpiDeptService")
public class KpiDeptServiceImpl implements KpiDeptService {

	@Autowired
	private KpiDeptYearMapper kpiDeptYearMapper;
	
	@Autowired
	private KpiGroupYearMapper kpiGroupYearMapper;
	
	@Autowired
	private KpiDeptDetailService  kpiDeptDetailService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private static Logger _log = LoggerFactory.getLogger(KpiDeptServiceImpl.class);
	
	/**
	 * 保存部门绩效考核
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResultMsg SaveKpiDept(ListData listData) {		
		
		ResultMsg resultMsg = new ResultMsg();
		String deptKpi = "";
		
		List<KpiDeptYearDetail> kpiDeptDetail = new ArrayList<KpiDeptYearDetail>();

		try {
			
			//保存部门绩效相关信息
			if (!StringUtils.isEmpty(listData.getData())) {

				KpiDeptYearBean kpiBean = objectMapper.readValue(listData.getData(),KpiDeptYearBean.class);
								
				//年度关键任务			
				KpiGroupYear kpiGroupYear = new KpiGroupYear();
				kpiGroupYear.setId(kpiBean.getId());				
				kpiGroupYear.setKeyTask(kpiBean.getKeyTask());
				kpiGroupYear.setKeyItem(kpiBean.getKeyItem());
				kpiGroupYear.setDetailItem(kpiBean.getDetailItem());				
				kpiGroupYear.setDept(DbcContext.getUser().getDeptId());
				kpiGroupYear.setStartTime(kpiBean.getStartTime());
				kpiGroupYear.setEndTime(kpiBean.getEndTime());	
				
				//部门新增
				kpiGroupYear.setStatus("2");
				kpiGroupYear.setOwer("2");
							
				
				if(StringUtils.isEmpty(kpiGroupYear.getId())){
					kpiGroupYear.setId(Utils.getGUID());				
					kpiGroupYear.setCreateBy(DbcContext.getUserId());
					kpiGroupYear.setCreateTime(new Date());
					kpiGroupYearMapper.insert(kpiGroupYear);
					
					kpiBean.setGroupId(kpiGroupYear.getId());
				}
				else{
					kpiGroupYear.setModifyBy(DbcContext.getUserId());
					kpiGroupYear.setModifyTime(new Date());
					
					kpiGroupYearMapper.updateDeptId(kpiGroupYear);
				}
				
				deptKpi = SaveKpiDept(kpiBean);				
			}
			
			/**
			 * 新增部门绩效明细信息
			 */
			if (!StringUtils.isEmpty(listData.getInserted())) {

				kpiDeptDetail = (List<KpiDeptYearDetail>) objectMapper.readValue(
						listData.getInserted(),
						new TypeReference<List<KpiDeptYearDetail>>() {
						});
				kpiDeptDetailService.SaveKpiDetail(kpiDeptDetail,deptKpi);
			}

			/**
			 * 修改部门绩效明细信息
			 */
			if (!StringUtils.isEmpty(listData.getUpdated())) {

				kpiDeptDetail = (List<KpiDeptYearDetail>) objectMapper.readValue(
						listData.getUpdated(),
						new TypeReference<List<KpiDeptYearDetail>>() {
						});
				kpiDeptDetailService.SaveKpiDetail(kpiDeptDetail,deptKpi);
			}

			/**
			 * 删除部门绩效明细信息
			 */
			if (!StringUtils.isEmpty(listData.getDeleted())) {

				kpiDeptDetail = (List<KpiDeptYearDetail>) objectMapper.readValue(
						listData.getDeleted(),
						new TypeReference<List<KpiDeptYearDetail>>() {
						});
				 //kpiDeptGroupService.DeleteKpiGroup(kpiGroups);
			}
		} catch (Exception e) {
			_log.error(e.getMessage());
			resultMsg.setCode("101");
			resultMsg.setErrorMsg(e.getMessage());			
		}
		
		return resultMsg;
	}
	
	/**
	 * 新增部门年度计划
	 * @param KpiDeptYearBean
	 * @return
	 */
	public String SaveKpiDept(KpiDeptYearBean kpiDeptYear){
		KpiDeptYear record = new KpiDeptYear();
		
		if(!StringUtils.isEmpty(kpiDeptYear)){	
			record.setGroupId(kpiDeptYear.getGroupId());
			record.setWeight(kpiDeptYear.getWeight());
			record.setContent(kpiDeptYear.getContent());
			record.setYearTarget(kpiDeptYear.getYearTarget());
			record.setStandard(kpiDeptYear.getStandard());
			record.setProjectLevel(kpiDeptYear.getProjectLevel());
			record.setDifficulty(kpiDeptYear.getDifficulty());
			//record.setResponsiblePerson(kpiDeptYear.getResponsiblePerson());
			
			if(!StringUtils.isEmpty(kpiDeptYear.getId())){	
				record.setId(kpiDeptYear.getId());
				record.setModifyBy(DbcContext.getAdminId());
				record.setModifyTime(new Date());
				
				kpiDeptYearMapper.update(record);
			}
			else{
				record.setId(Utils.getGUID());
				record.setStatus("1");
				record.setCreateBy(DbcContext.getAdminId());
				record.setCreateTime(new Date());
				
				kpiDeptYearMapper.insert(record);
			}			
		}
		return record.getId();
	}
	
	/**
	 * 查询部门年度计划
	 */
	@Override
	public ResultData getKpiDept(PageData page) {		
		
		ResultData data = new ResultData();
		page.setStart((page.getPage() -1) * page.getRows());	
		page.setDeptId(DbcContext.getUser().getDeptName());
		
		List<KpiDeptYearBean> list = kpiDeptYearMapper.getKpiDept(page);
		data.setRows(list);
		
		//TotalRows 
		PageData totalPage = new PageData();
		totalPage.setKeyTask(page.getKeyTask());
		totalPage.setStartTime(page.getStartTime());
		totalPage.setEndTime(page.getEndTime());
		totalPage.setStart(0);
		totalPage.setRows(PageData.MAX_ROWS);
		list = kpiDeptYearMapper.getKpiDept(totalPage);
		data.setTotal(list.size());
		return data;
	}

	@Override
	public KpiDeptYearBean getKpiDeptById(String id) {
		return kpiDeptYearMapper.getKpiDeptById(id);
	}
	
	/**
	 * 删除部门年度计划
	 * @param id
	 * @return
	 */
	@Override
	public ResultMsg delDeptKpiGroup(String id){
		ResultMsg resultMsg = new ResultMsg();
		kpiDeptYearMapper.delDeptKpiGroup(id);
		return resultMsg;
	}
	
	/**
	 * 部门年度计划任务下发
	 * @param year
	 * @return
	 * @throws ServiceException
	 */	
	public ResultMsg saveDeptTask(PageData page){
		ResultMsg msg = new ResultMsg();
		page.setModifyBy(DbcContext.getUserId());
		page.setModifyTime(new Date());
		kpiDeptYearMapper.updateTask(page);		
		return msg;
	}
}

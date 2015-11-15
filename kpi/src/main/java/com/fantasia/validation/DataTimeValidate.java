package com.fantasia.validation;

import java.util.Date;

import com.fantasia.exception.ServiceException;
import com.fantasia.util.DateTimeUtil;

public class DataTimeValidate {

	/**
	 * 验证开始日期是否小于结束日期
	 * @param startTime
	 * @param endTime
	 * @throws ServiceException
	 */
	public static void valiateTime(String startTime,String endTime) throws ServiceException{
		Date startDate =  DateTimeUtil.StrToDate(startTime);
		Date endDate = DateTimeUtil.StrToDate(endTime);
		if(DateTimeUtil.compare_date(startDate, endDate)){
			throw new ServiceException("101", "开始日期不能大于结束日期!");
		}
	}
}

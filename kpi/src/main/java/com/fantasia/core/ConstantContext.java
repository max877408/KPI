package com.fantasia.core;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ConstantContext {

	/**
	 * 访问图片URL
	 */
	private static String imgUrl;
	
	/**
	 * 登录默认超时时间
	 */
	private static int timeout;
	
	/**
	 * 获取预留车位数
	 */
	private static int parking;
	
	/**
	 * 礼宾服务金额
	 */
	private static float concierge;

	static{
		ResourceBundle bundle = PropertyResourceBundle.getBundle("config");
		imgUrl = bundle.getString("imgUrl");
		timeout = Integer.parseInt(bundle.getString("timeout"));
		parking = Integer.parseInt(bundle.getString("parking"));
		concierge = Float.parseFloat(bundle.getString("concierge"));
	}
	
	public static String getImgUrl() {
		return imgUrl;
	}

	public static int getTimeout() {
		return timeout;
	}
	
	public static int getParking(){
		return parking;
	}

	public static float getConcierge() {
		return concierge;
	}

}

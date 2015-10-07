package com.fantasia.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 字符串工具类
 * 
 * @author Administrator
 * 
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {

	/**
	 * 数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern
				.compile("[0-9]*");
		java.util.regex.Matcher match = pattern.matcher(str);
		return match.matches();
//		if (match.matches() == false) {
//			return false;
//		}
//
//		return true;
	}
	
	/**
     * 获取count个随机数
     * @param count 随机数个数
     * @return
     */
    public static String randomNumber(int count){
        StringBuffer sb = new StringBuffer();
        String str = "0123456789";
        Random r = new Random();
        for(int i=0;i<count;i++){
            int num = r.nextInt(str.length());
            sb.append(str.charAt(num));
            str = str.replace((str.charAt(num)+""), "");
        }
        return sb.toString();
    }
	/*
	 * String转换List 
	 */
	public static List<String> StringToList(String listText, String regex) {

		if (listText == null || listText.equals("")) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		if (listText.contains(regex)) {	
			String[] text = listText.split(regex);
			for (String str : text) {
				list.add(str.replaceAll("\\s*", ""));
			}
			
		}else{
			list.add(listText.replaceAll("\\s*", ""));
		}
		return list;
	}
	
	/**
	 * 任何一个字符对象为空则返回true
	 * @param strs
	 * @return
	 */
	public static boolean isAnyoneEmpty(String... strs){
		for (String object : strs) {
			if(StringUtils.isEmpty(object)){
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
//		System.out.println(isNumber("12345678"));
//		System.out.println(randomNumber(6));
		@SuppressWarnings("unused")
		String testString = " 9014c5f6-0a8a-11e5-b693- 000c29a71491 :9014c5f6-0a8a-11e5-b693-000c29a71491:9014c5f6-0a8a-11e5-b693-000c29a71491:9014c5f6-0a8a-11e5-b693-000c29a71491"
				+ ": 9014c5f6-0a8a-11e5-b693-000c29a71491:9014c5f6-0a8a-11e5-b693-000c29a71491:9014c5f6-0a8a-11e5-b693-000c29a71491:9014c5f6-0a8a-11e5-b693-000c29a71491:9014c5f6-0a8a-11e5-b693-000c29a71491"
				+ ":9014c5f6-0a8a-11e5-b693-000c29a71491";
		String testStirng2 = "9014c5f6-0a8a-11e5-b693-000c29a71491";
		System.out.println(StringToList(testStirng2,":"));

	}
}

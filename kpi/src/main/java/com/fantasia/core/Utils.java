package com.fantasia.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.fantasia.util.HttpClientUtil;
import com.fantasia.util.StringUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	/**
	 * application context
	 */
	private static Logger _log = LoggerFactory.getLogger(Utils.class);

	public static WebApplicationContext webApplication = ContextLoader
			.getCurrentWebApplicationContext();

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static ExecutorService threadPool = Executors.newFixedThreadPool(20);

	/**
	 * json字符串转对象
	 * 
	 * @param t
	 * @param o
	 * @return
	 */
	public static <T> T readValue(Class<T> t, String o) {
		T data = null;
		try {
			data = objectMapper.readValue(o, t);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * get guid
	 * 
	 * @return
	 */
	public static String getGUID() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.toUpperCase();
		uuid = uuid.replaceAll("-", "");

		return uuid;
	}

	/**
	 * 查询图片URL 根据图片ID获取图片路径URL
	 * 
	 * @param picId
	 * @return
	 */
	public static String queryImg(String picId) {
		String body = "";
		try {
			String imgUrl = ConstantContext.getImgUrl()
					+ "api/Img/GetAppImgById?id=" + picId;
			body = HttpClientUtil.sendGetRequest(imgUrl, null);

			if (body != null) {
				Pattern p = Pattern.compile("\\s*|\t|\r|\n");
				Matcher m = p.matcher(body);
				body = m.replaceAll("");
			}
			body = body.replace("\"", "");
			body = ConstantContext.getImgUrl() + body;
		} catch (Exception e) {
			_log.error("获取图片失败！" + e.getMessage());
		}
		return body;
	}

	/**
	 * 汉字转换位汉语拼音首字母，英文字符不变
	 * 
	 * @param chines 汉字
	 *            
	 * @return 拼音
	 */
	public static String converterToFirstSpell(String chines) {
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0].charAt(0);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}

	/**
	 * 汉字转换位汉语拼音，英文字符不变
	 * 
	 * @param chines 汉字
	 *            
	 * @return 拼音
	 */
	public static String converterToSpell(String chines) {
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0];
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}

}

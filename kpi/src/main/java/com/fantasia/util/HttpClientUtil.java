package com.fantasia.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
	
	private static Logger _log = LoggerFactory.getLogger(HttpClientUtil.class);
	
	public static String sendRequest(String url, Map<String, String> params) {
		
		HttpClient client = new HttpClient();
		PostMethod method = null;

		try {
			method = new PostMethod(url);

			if (params != null) {
				for (String paramName : params.keySet()) {
					method.addParameter(paramName, params.get(paramName));
				}
				HttpMethodParams param = method.getParams();
				param.setContentCharset("UTF-8");
			}

			client.executeMethod(method);

			InputStream stream = method.getResponseBodyAsStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					stream, "UTF-8"));
			StringBuffer buf = new StringBuffer();
			String line;
			while (null != (line = br.readLine())) {
				buf.append(line).append("\n");
			}
			return buf.toString();
		} catch (Exception e) {
			_log.error(e.getMessage());			
		} finally {
			// 释放连接
			method.releaseConnection();
		}
		return null;
	}
	
   public static String sendGetRequest(String url, Map<String, String> params) {
		
		HttpClient client = new HttpClient();
		GetMethod method = null;

		try {
			method = new GetMethod(url);

			if (params != null) {
				/*for (String paramName : params.keySet()) {
					method.addParameter(paramName, params.get(paramName));
				}*/
				HttpMethodParams param = method.getParams();
				param.setContentCharset("UTF-8");
			}
			client.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
			client.getHttpConnectionManager().getParams().setSoTimeout(3000);
			client.executeMethod(method);

			InputStream stream = method.getResponseBodyAsStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					stream, "UTF-8"));
			StringBuffer buf = new StringBuffer();
			String line;
			while (null != (line = br.readLine())) {
				buf.append(line).append("\n");
			}
			return buf.toString();
		} catch (Exception e) {
			_log.error(e.getMessage());	
		} finally {
			// 释放连接
			method.releaseConnection();
		}
		return null;
	}
}

package com.jctl.cloud.utils.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.servlet.http.HttpServletRequest;

/**
 * 爬虫util
 *create by gent 2017年3月23日
 */
public class JsoupUtils {
	/**
	 * 通过地址得到document对象
	 * @param url 目标网站路径
	 * @return
	 */
	public static Document getDocument(String url, HttpServletRequest request){
		try {
			Document document = Jsoup.connect(url).timeout(60).get();
			if (document == null || document.toString().equals("")) {
				System.out.println("请求被拦截或者网络问题,重新请求...");
				HttpUtils.setProxyIp(request);
				getDocument(url,request);
			}
			return document;
		} catch (Exception e) {
			System.out.println("请求超时...");
			HttpUtils.setProxyIp(request);
			getDocument(url,request);
		}
		return getDocument(url,request);
	}
}

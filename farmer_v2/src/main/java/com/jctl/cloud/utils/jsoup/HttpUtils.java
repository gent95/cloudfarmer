package com.jctl.cloud.utils.jsoup;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * 反扒工具类
 *create by gent 2017年3月23日 E:\jctl\farmer_v1\src\main\resources\proxyip.txt
 */
public class HttpUtils {
	public static void setProxyIp(HttpServletRequest request){
		List<String> ipList = new ArrayList<String>();
		try {
			BufferedReader proxyIpReader = new BufferedReader(new InputStreamReader(new FileInputStream(request.getSession().getServletContext().getRealPath("/WEB-INF/proxyip.txt"))));
			String ip = "";
			while((ip = proxyIpReader.readLine())!=null){
				ipList.add(ip);
			}
			Random random = new Random();
			int randomInt = random.nextInt(ipList.size());
			String ipport = ipList.get(randomInt);
			String proxyIp = ipport.substring(0, ipport.lastIndexOf(":"));
			String proxyProt = ipport.substring(ipport.lastIndexOf(":"));	
			System.setProperty("http.maxRedirects", "50");
			System.getProperties().setProperty("http.proxyHost", proxyIp);
			System.getProperties().setProperty("http.proxyPort", proxyProt);
			System.out.println("代理 IP为:"+proxyIp+"端口号为:"+proxyProt);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("重新获取代理 IP");
			setProxyIp(request);
		}
		
	}
}

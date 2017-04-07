package com.jctl.cloud.mina.listener;

import com.jctl.cloud.mina.server.MinaLongConnServer;
import com.jctl.cloud.mina.server.MinaLongConnServerHandler;
import com.jctl.cloud.mina.thread.cache.RelayConnectionStatusThread;
import com.jctl.cloud.utils.QutarzUtil;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MinaServerListener implements ServletContextListener{

	private MinaLongConnServer ms = new MinaLongConnServer();
	private RelayConnectionStatusThread hs = new RelayConnectionStatusThread();

	// 作为日志打印用
	private final static org.slf4j.Logger log = LoggerFactory.getLogger(MinaLongConnServerHandler.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		log.info("项目关闭");
	}

	//项目启动后启动mini服务器
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ms.start();
		hs.start();
		//启动定时器
		QutarzUtil.start();
	}

}

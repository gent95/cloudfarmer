package com.jctl.cloud.mina.client;

import com.jctl.cloud.mina.main.Main;
import com.jctl.cloud.mina.entity.ClientSessions;
import com.jctl.cloud.mina.entity.OrdersQueueVO;
import com.jctl.cloud.mina.server.MinaLongConnServer;
import com.jctl.cloud.mina.thread.ServerSendThread;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class SocketClient {


	@Value("#{config['serverPort']}")
	private Integer ServerPort;

	public boolean send(char[] orders){
		IoConnector connector = new NioSocketConnector();
       connector.getSessionConfig().setReadBufferSize(2048);
       connector.getFilterChain().addLast("logger", new LoggingFilter());
       connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
       connector.setHandler(new MinaShortClientHandler());
        ConnectFuture future = connector.connect(new InetSocketAddress("localhost", ServerPort));
        future.awaitUninterruptibly();
        String str = new String(orders);
      //  str = str.trim()+"\r\n";
        str=str.trim();
        IoSession session =future.getSession();
        session.write(str);
        session.getCloseFuture().awaitUninterruptibly();
       System.out.println("result=" + session.getAttribute("result"));
    
	    connector.dispose();
		return true;
	}
	
	public boolean send_1(char[] orders){
		
		
		//接收命令,将命令转换为字符串
        String str = new String(orders);
        //str = str.trim()+"\r\n";
        System.out.println("str---------------------->"+str);

        IoConnector connector = new NioSocketConnector();
	    connector.getSessionConfig().setReadBufferSize(2048);
	    connector.getFilterChain().addLast("logger", new LoggingFilter());
	    connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
	    connector.setHandler(new MinaShortClientHandler());
	    ConnectFuture future = connector.connect(new InetSocketAddress("localhost", ServerPort));
	    future.awaitUninterruptibly();
        IoSession session=future.getSession();
        ClientSessions se=new ClientSessions();
        if(MinaLongConnServer.sessionList.size()!=0){
        for(ClientSessions sessions:MinaLongConnServer.sessionList){
        	if(sessions.getServerMac()!=null){
        			se.setServerMac(sessions.getServerMac());
        			se.setSession(session);
        			MinaLongConnServer.sessionList.remove(sessions);
        		}
        	}
        }
        
		//将命令添加到待发列表之中
		boolean flag = true;
		//防止瞬间重复的命令进入
		for(OrdersQueueVO ordersQueueVO : MinaLongConnServer.ordersQueueVOList){//便利命令队列数组
			if(str.trim().equals(ordersQueueVO.getOrder())){//判断接受的命令是否是orderQueueVo队列中的命令的指令
				long lastTime = ordersQueueVO.getTime();//收到需要执行的任务的时间戳
				lastTime += 3000;//给时间增加3000毫秒
				if(System.currentTimeMillis() >= lastTime){//获得当前时间的毫秒值，比较是否大于增加3000后的时间
					if(MinaLongConnServer.INDEX > ordersQueueVO.getIndex()){//如果MinaLongConnServer命令队列数组的长度大于命令的游标
						OrdersQueueVO oqVO = new OrdersQueueVO();
						oqVO.setIndex(MinaLongConnServer.INDEX);//将命令队列数组中命令游标添加到新的命令队列类中
						oqVO.setTime(System.currentTimeMillis());//将当前时间的毫秒值装换为收到需要执行的任务的时间戳
						oqVO.setOrder(str.trim());//将接收的命令添加到命令队列类中的命令的指令
						System.out.println(oqVO.toString()+"命令是已发：Oqvo对象的值：。。。。");
						//将命令队列类添加到命令队列集合中
						MinaLongConnServer.ordersQueueVOList.add(oqVO);
						//
						MinaLongConnServer.sessionList.add(se);
						//
						flag = false;//命令改为已发的
						MinaLongConnServer.INDEX++;//MinaLongConnServer中的index加1
					}
				}
			}
		}
		if (flag) {//如果命令是待发的
			OrdersQueueVO oqVO = new OrdersQueueVO();
			oqVO.setIndex(MinaLongConnServer.INDEX);//将MinaLongConnServer中的index添加到新的命令队列类中
			oqVO.setTime(System.currentTimeMillis());//将当前时间的毫秒值装换为收到需要执行的任务的时间戳
			oqVO.setOrder(str.trim());//将接收的命令添加到命令队列类中的命令的指令
			System.out.println(oqVO.toString()+"命令是待发：oqVo对象的值：、、、、");
			MinaLongConnServer.ordersQueueVOList.add(oqVO);//将命令队列类添加到命令队列集合中
			//
			MinaLongConnServer.sessionList.add(se);
			//
			MinaLongConnServer.INDEX++;//MinaLongConnServer中的index加1
		}
		
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%当前List之中的任务%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		for(OrdersQueueVO ordersQueueVO : MinaLongConnServer.ordersQueueVOList) {
			System.out.println("命令队列集合中的命令队列类："+ordersQueueVO.toString());
		}
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%当前List之中的任务-End%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		//调用发送的方法
		Thread thread = new ServerSendThread();
		thread.run();
       
		return true;
	}
	
	
	
//	private Socket socket = null;
//	private OutputStream os = null;
//	private InputStream is = null;
//
//	
//	public boolean send(char[] orders) {
//		try {
//			socket = new Socket("localhost", 41530);
//			os = socket.getOutputStream();
//			is = socket.getInputStream();
//			String str = new String(orders).trim();
//			str = str+"\r\n";
//			os.write(str.getBytes());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return true;
//	}
	
	
	public static void main(String[] args) {
		new SocketClient().send(Main.sendGatewayOrders());
//		new SocketClient().send(Main.sendCilentOrders("0C059001", "313633303037"));
//		new SocketClient().send(Main.sendCilentOrdersGetData("0C059001", "313633303037"));
	}

}

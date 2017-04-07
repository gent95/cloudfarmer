package com.jctl.cloud.mina.client;

import com.jctl.cloud.mina.main.Main;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class MinaShortClient {


    @Value("#{config['serverPort']}")
    private static Integer ServerPort;

    public static void main(String[] args) throws IOException, InterruptedException {
        IoConnector connector = new NioSocketConnector();
        connector.getSessionConfig().setReadBufferSize(2048);
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        connector.setHandler(new MinaShortClientHandler());
        ConnectFuture future = connector.connect(new InetSocketAddress("localhost", ServerPort));
        future.awaitUninterruptibly();
        String str = new String(Main.sendGatewayOrders());
        str = str.trim() + "\r\n";
        IoSession session = future.getSession();

        session.write(str);
        session.getCloseFuture().awaitUninterruptibly();
        System.out.println("result(结果)=" + session.getAttribute("result"));

        connector.dispose();

    }
}

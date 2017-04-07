package com.jctl.cloud.mina.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TcpKeepAliveClient {
	
	private String ip;
    private int port;
    private static Socket socket = null;
    private static int timeout = 50 * 1000;

 

    public TcpKeepAliveClient(String ip, int port) {
       this.ip = ip;
       this.port = port;
    }

 

    public void receiveAndSend() throws IOException {
       InputStream input = null;
       OutputStream output = null;
       try {
           if (socket == null ||socket.isClosed() || !socket.isConnected()) {
              socket = new Socket();
              InetSocketAddress addr = new InetSocketAddress(ip, port);
              socket.connect(addr, timeout);
              socket.setSoTimeout(timeout);
              System.out.println("TcpKeepAliveClientnew ");
           }
           input = socket.getInputStream();
           output = socket.getOutputStream();
           // read body
           byte[] receiveBytes = {};// 收到的包字节数组
           while (true) {
              if (input.available() > 0) {
                  receiveBytes = new byte[input.available()];
                  input.read(receiveBytes);
                  // send
                  System.out.println("TcpKeepAliveClientsend date :" +new String(receiveBytes));
                  output.write(receiveBytes, 0, receiveBytes.length);
                  output.flush();
              }
           }
       } catch (Exception e) {
           e.printStackTrace();
           System.out.println("TcpClientnew socket error");
       }
    }

 

    public static void main(String[] args) throws Exception {
       TcpKeepAliveClient client = new TcpKeepAliveClient("localhost", 9000);
       client.receiveAndSend();

    }
}

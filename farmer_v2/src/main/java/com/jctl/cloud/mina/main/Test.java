package com.jctl.cloud.mina.main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Test {
	
	public static void main(String[] args) {
		while (true) {
			Scanner scanner = new Scanner(System.in);
			int flag = scanner.nextInt();
			if(flag == 1){
//				Socket socket = SocketBank.socketMap.get("FFFEDD112233");
//				System.out.println(socket);
				//send(SocketBank.socketMap.get("FFFEDD112233"),new String(Main.sendGatewayOrders()));
			}else if (flag == 2) {
				//send(SocketBank.socketMap.get("FFFEDD112233"),new String(Main.sendCilentOrders("075BCD12", "FFFEDD112233")));
			}
		}
	}
	
	private static void send(Socket socket,String orders){
		try {
			OutputStream out = socket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(out);
			dataOutputStream.write(orders.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

package com.jctl.cloud.mina.client;

import com.jctl.cloud.mina.entity.ClientSessions;
import com.jctl.cloud.mina.server.MinaLongConnServer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class MinaShortClientHandler extends IoHandlerAdapter{
	
    public MinaShortClientHandler() {

    }


    @Override
    public void sessionOpened(IoSession session) {
    }
 
    @Override
    public void messageReceived(IoSession session, Object message) {
       System.out.println("Messagereceived in the client..");
       System.out.println("Message is: Client——>" + message);
       session.setAttribute("result", message);
       session.close(true);
    }
 
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
       session.close(true);
    }
    
    @Override
    public void sessionClosed(IoSession session) throws Exception {
    	for(ClientSessions cs : MinaLongConnServer.sessionList){
    		if(cs.getId() == String.valueOf(session.getId())){
    			MinaLongConnServer.sessionList.remove(cs);
    		}
    	}
    	super.sessionClosed(session);
    }

}

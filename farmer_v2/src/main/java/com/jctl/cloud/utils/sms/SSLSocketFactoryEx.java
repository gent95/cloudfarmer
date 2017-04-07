package com.jctl.cloud.utils.sms;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
//SSLSocketFactory不能限制SSL/TLS使用的协议,，因此扩展之。代码来源
//http://stackoverflow.com/questions/1037590/which-cipher-suites-to-enable-for-ssl-socket/23365536#23365536
/**
 * 
 * @used ssl加密
 * @author majiangtao
 * @date 2017年2月22日
 *
 */
public class SSLSocketFactoryEx extends SSLSocketFactory {
	private SSLContext m_ctx;
    private String[] m_ciphers;		//密码套件(没用上)
    private String[] m_protocols;		//允许使用协议
    
    public SSLSocketFactoryEx(KeyManager[] km, TrustManager[] tm, SecureRandom random) throws NoSuchAlgorithmException, KeyManagementException
    {
    	this.m_ctx = SSLContext.getInstance("TLS");
        this.m_ctx.init(km, tm, random);
        String[] preferredProtocols = { "TLSv1", "TLSv1.1", "TLSv1.2"};		//TLS 1.0-1.2均可
        //String[] preferredProtocols = {"TLSv1.2"};		//限制只支持TLSv1.2
        this.m_protocols = preferredProtocols;
    }
    //限定密码套件
    public void setEnabledProtocols(String[] protocols){
    	this.m_protocols = protocols;
    }
    
    @Override
	public String[] getDefaultCipherSuites() {
		// TODO Auto-generated method stub
    	return null;
	}

	@Override
	public String[] getSupportedCipherSuites() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
		// TODO Auto-generated method stub
		SSLSocketFactory factory = m_ctx.getSocketFactory();
        SSLSocket ss = (SSLSocket)factory.createSocket(s, host, port, autoClose);

        ss.setEnabledProtocols(m_protocols);
        //ss.setEnabledCipherSuites(m_ciphers);
        return ss;
	}

	

	@Override
	public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
		// TODO Auto-generated method stub
		SSLSocketFactory factory = m_ctx.getSocketFactory();
        SSLSocket ss = (SSLSocket)factory.createSocket(host, port);

        ss.setEnabledProtocols(m_protocols);
        //ss.setEnabledCipherSuites(m_ciphers);

        return ss;
	}

	@Override
	public Socket createSocket(InetAddress host, int port) throws IOException {
		// TODO Auto-generated method stub
        SSLSocketFactory factory = m_ctx.getSocketFactory();
        SSLSocket ss = (SSLSocket)factory.createSocket(host, port);

        ss.setEnabledProtocols(m_protocols);
        //ss.setEnabledCipherSuites(m_ciphers);

        return ss;
	}

	@Override
	public Socket createSocket(String host, int port, InetAddress localHost, int localPort)
			throws IOException, UnknownHostException {
		// TODO Auto-generated method stub
        SSLSocketFactory factory = m_ctx.getSocketFactory();
        SSLSocket ss = (SSLSocket)factory.createSocket(host, port, localHost, localPort);

        ss.setEnabledProtocols(m_protocols);
        //ss.setEnabledCipherSuites(m_ciphers);

        return ss;
	}

	@Override
	public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort)
			throws IOException {
		// TODO Auto-generated method stub
        SSLSocketFactory factory = m_ctx.getSocketFactory();
        SSLSocket ss = (SSLSocket)factory.createSocket(address, port, localAddress, localPort);

        ss.setEnabledProtocols(m_protocols);
        //ss.setEnabledCipherSuites(m_ciphers);

        return ss;
	}

}

package com.jctl.cloud.utils.server;

import com.jctl.cloud.mina.server.MinaLongConnServer;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 刘凯 on 2017/2/21 0021.
 * IO容器工具  受spring管理
 */
@Service
public class IoSessionContainerUtil {


    //活动session
    private static Map<String, IoSession> sessionMap;

    public IoSessionContainerUtil() {
        sessionMap = new HashMap<>(100);
    }


    /**
     * 通过设备的唯一ip获取活动session
     *
     * @param ip
     * @return
     */
    public static IoSession getIoSessionByIp(String ip) {
        if (ip == null || ip.equals("")) {
            return null;
        }
        return IoSessionContainerUtil.sessionMap.get(ip);
    }

    /**
     * 根据mac获取活动session
     * @param mac
     * @return
     */
    public static IoSession getIoSessionByMac(String mac) {
        if (mac == null || mac.equals("")) {
            return null;
        }
        return MinaLongConnServer.sessionMap.get(mac).getIoSession();
    }


}

package com.jctl.cloud.mina.utils.cache;

import com.jctl.cloud.common.utils.TimeUtils;
import com.jctl.cloud.exception.mina.ParameterTypeException;
import com.jctl.cloud.mina.entity.IoSessionEntity;
import com.jctl.cloud.mina.entity.ResultSet;
import com.jctl.cloud.mina.main.Main;
import com.jctl.cloud.mina.server.MinaLongConnServer;
import com.jctl.cloud.mina.server.MinaLongConnServerHandler;
import org.apache.mina.core.session.IoSession;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 设备连接操作管理工具
 * Created by LewKay on 2017/3/14.
 */
public class IoSessionCacheManager implements CacheManager {

    private final static org.slf4j.Logger log = LoggerFactory.getLogger(CacheManager.class);

    static {
        sessionMap = MinaLongConnServer.sessionMap;
        sessionMapBackups = MinaLongConnServer.sessionMapBackups;
        hashCodeMap = MinaLongConnServer.hashCodeMap;
    }

    public static Map<String, IoSessionEntity> sessionMap;//实际的存储iosession

    public static Map<Integer, IoSessionEntity> sessionMapBackups;//在内存中备份的 永不清除

    public static Map<Integer,IoSessionEntity> hashCodeMap;//依据连接信息保存的hashcode


    public static Map<String, IoSessionEntity> getSessionMap() {
        return sessionMap;
    }

    public static Map<Integer, IoSessionEntity> getSessionMapBackups() {
        return sessionMapBackups;
    }

    public static Map<Integer, IoSessionEntity> getHashCodeMap() {
        return hashCodeMap;
    }




    /**
     * 修改缓存线程操作对象
     * @param session
     */
    public static void changeHashCodeCacheMap(IoSession session){
        IoSessionEntity newEntity = MinaLongConnServer.sessionMapBackups.get(session.hashCode());
        if(newEntity == null){
            session.write(new String(Main.sendGatewayOrders()).trim());
            return;
        }
        log.info("更新缓存的存储时间，最新时间为"+ TimeUtils.toTimeString(System.currentTimeMillis()));
//        MinaLongConnServer.hashCodeMap.put(session.hashCode(), newEntity);
        //因为担心设备网络原因丢包  线程中误清除  如果仅仅是dt消息  就更新  在一起做了
        //  所以在这里放置进去备份的iosession
        newEntity.setAddTime(System.currentTimeMillis());

        MinaLongConnServer.sessionMap.put(newEntity.getRelayMac(), newEntity);
    }

    /**
     * 增加新的操作缓存对象
     * @param resultSet
     */

    public static void addIoSessionCache(ResultSet resultSet, IoSession session){
        log.info("-->将设备信息维护到缓存！");
        IoSessionEntity sessionEntity = new IoSessionEntity(
                System.currentTimeMillis(),
                session.hashCode(),
                session,
                resultSet.getGatewayResultSet().getServerMac());
        //维护到缓存
        MinaLongConnServer.sessionMap.put(resultSet.getGatewayResultSet().getServerMac(), sessionEntity);
        //备份
        MinaLongConnServer.sessionMapBackups.put(session.hashCode(), sessionEntity);
        //线程操作对象
//        MinaLongConnServer.hashCodeMap.put(session.hashCode(), sessionEntity);
    }


    public static void remove(IoSessionEntity var1)  {
//        if(var1 == null){
//            throw new ParameterTypeException("Parameter type must is IoSessionEntity or extend IoSessionEntity ");
//        }
        sessionMap.remove(var1.getRelayMac());
    }

//    public void remove(T var1) {
//
//    }

    /**
     * 测试
     * @param args
     */

    public static void main(String [] args){

        //初始化绝对值




//        System.out.println(IoSessionCacheManager.getSessionMap());
    }
}

package com.jctl.cloud.mina.entity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;

import java.io.Serializable;

/**
 * IoSession cache entity
 * Created by lewKay on 2017/3/10.
 */
public class IoSessionEntity
        implements Serializable {

    private static final long serialVersionUID = 1L;
    private Log log = LogFactory.getLog(this.getClass());

    public IoSessionEntity() {
    }

    public IoSessionEntity(Long addTime, Integer SessionCode, IoSession ioSession, String relayMac) {
        this.addTime = addTime;
        this.SessionCode = SessionCode;
        this.ioSession = ioSession;
        this.relayMac = relayMac;
    }


    private Long addTime;
    private IoSession ioSession;
    private Integer SessionCode;
    private String relayMac;

    public String getRelayMac() {
        return relayMac;
    }

    public void setRelayMac(String relayMac) {
        this.relayMac = relayMac;
    }

    public Integer getSessionCode() {
        return SessionCode;
    }

    public void setSessionCode(Integer sessionCode) {
        SessionCode = sessionCode;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public IoSession getIoSession() {
        return ioSession;
    }

    public void setIoSession(IoSession ioSession) {
        this.ioSession = ioSession;
    }

    public void clear() {
        log.debug("Nothing to do.");
        //
    }


}


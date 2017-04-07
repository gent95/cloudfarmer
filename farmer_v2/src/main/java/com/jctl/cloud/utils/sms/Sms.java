package com.jctl.cloud.utils.sms;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 短信接口工具
 * Created by Administrator on 2017/3/13.
 */
public interface Sms extends Serializable {


    /**
     * 通过每次请求获取SESSION中当前连接保存的验证码（或者通过保存时使用的key）
     * @param request
     * @return
     */
    String getVerification(HttpServletRequest request,String key);


    /**
     * 设置保存时长并保存验证码
     * @param code 验证码
     * @param keepTime  保存时间
     */
    void  setVerification(String key, String code, String keepTime);


    /**
     * 保存验证码 时长系统session默认存在时间
     * @param code
     */
    void setVerification(String code,String key);

}

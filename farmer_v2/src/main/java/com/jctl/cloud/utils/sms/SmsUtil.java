package com.jctl.cloud.utils.sms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by gent on 2017/3/13.
 */
public class SmsUtil{

    public static String getVerification(HttpServletRequest request, String phone) {
        System.out.println("====================发送短信验证码===================");
        HttpsRequest httpRequest = new HttpsRequest();
        HttpSession session = request.getSession();
        httpRequest.getBalance("POST",request);
        httpRequest.sendSms("POST",session,request,phone);
        String mesCheckCode = session.getAttribute(phone).toString();
        return mesCheckCode;
    }
}

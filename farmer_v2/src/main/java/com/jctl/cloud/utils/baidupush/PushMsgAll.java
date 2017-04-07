package com.jctl.cloud.utils.baidupush;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;
import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by gent on 2017/3/29.
 */
public class PushMsgAll {
    public static boolean push(String message) throws PushClientException, PushServerException {
        // 1. get apiKey and secretKey from developer console
        String apiKey = "CYSIWF53gz2pLENmWWL9P3Q0";
        String secretKey = "z3CLDpuG4kgMfSrwfhq5h6zR5nSnuIhO";
        PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

        // 2. build a BaidupushClient object to access released interfaces
        BaiduPushClient pushClient = new BaiduPushClient(pair, BaiduPushConstants.CHANNEL_REST_URL);

        // 3. register a YunLogHandler to get detail interacting information
        // in this request.
        pushClient.setChannelLogHandler(new YunLogHandler() {
            public void onHandle(YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });

        try {
            // 4. specify request arguments
            PushMsgToAllRequest request = new PushMsgToAllRequest().addMsgExpires(new Integer(3600)).addMessageType(0)
                    .addMessage(message)
                    // 设置定时推送时间，必需超过当前时间一分钟，单位秒.实例70秒后推送
                    .addSendTime(System.currentTimeMillis() / 1000 + 70).addDeviceType(3);
            // 5. http request
            PushMsgToAllResponse response = pushClient.pushMsgToAll(request);
            // Http请求返回值解析
            System.out.println("msgId: " + response.getMsgId() + ",sendTime: " + response.getSendTime() + ",timerId: "
                    + response.getTimerId());
            return true;
        } catch (PushClientException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                e.printStackTrace();
            }
            return false;
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                System.out.println(String.format("requestId: %d, errorCode: %d, errorMsg: %s", e.getRequestId(),
                        e.getErrorCode(), e.getErrorMsg()));
            }
            return false;
        }
    }
}

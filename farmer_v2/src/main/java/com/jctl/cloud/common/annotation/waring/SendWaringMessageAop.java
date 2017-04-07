package com.jctl.cloud.common.annotation.waring;

import com.jctl.cloud.manager.nodedatadetails.entity.NodeDataDetails;
import com.jctl.cloud.manager.relay.service.RelayService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by LewKay on 2017/3/7.
 * 切面类
 * 切入到报警信息如果有预警就在此处插入到数据库
 */
@Aspect
@Service
public class SendWaringMessageAop {

    //将报警信息保存到数据库
    @Autowired
    private RelayService relayService;

    //切入点
    @Pointcut("@annotation(com.jctl.cloud.common.annotation.waring.WaringMessagePush)")
    public void waringMessagePushAspect() {
    }

    /**
     * 前置通知  将报警信息保存到数据库
     */
    @Before("waringMessagePushAspect()")
    public void addWaringMessage(JoinPoint joinPoint) {
        try {
            for (Object obj : joinPoint.getArgs()) {
                if (obj instanceof NodeDataDetails) {
                    NodeDataDetails dataDetails = (NodeDataDetails) obj;
                    relayService.sendWaringMessage(dataDetails);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    @Pointcut("execution(* com.jctl.cloud.manager.relay.service.*.save*(..))")
//
//    @Pointcut("execution(* com.jctl.cloud.manager.*.web.*.*(..))")
//    public void addWringMessage(){
//        System.out.println("******************************");
//    }
//
//
//    @Before("addWringMessage()")
//    public void doBefore(JoinPoint point){
//        Object [] obj = point.getArgs();
//        System.out.println("我是是这个方法的参数"+obj.toString()+"来自spring aop");
//    }
//
//
//    @After("addWringMessage()")
//    public void doAfter(){
//        System.out.println(222);
//    }


}

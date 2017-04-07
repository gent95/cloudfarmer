package com.jctl.cloud.common.annotation.waring;

import java.lang.annotation.*;

/**
 *
 * 自定义注解  拦截service 设备状态信息 检测是否有超出阈值
 * Created by lewKay on 2017/3/8.
 */

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WaringMessagePush {
    String description() default "";
}

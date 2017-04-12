package com.jctl.cloud.common.entity;

/**
 * 登录结果
 *
 * @author Administrator
 * @version 1.0 Revise History:
 */
public enum LoginResult {
    登录成功("LOGIN_SUCCESS", "登录成功"), 登录失败("ERROR_USER_PASSWORD", "账号或密码错"), 用户被锁定("ERROR_LOCK", "用户被锁定");
    LoginResult(String login_success, String 登录成功) {
    }
}

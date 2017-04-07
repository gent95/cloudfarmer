package com.jctl.cloud.utils;

import com.jctl.cloud.common.entity.LoginResult;
import com.jctl.cloud.common.utils.SpringContextHolder;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.modules.sys.service.UserService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lewKay on 2017/3/23.
 */
public class FrontUserUtils {


    private static UserService userService = SpringContextHolder.getBean(UserService.class);
    private static SystemService systemService = SpringContextHolder.getBean(SystemService.class);


    public static LoginResult doLogin(User loginUser, HttpServletRequest request) {
        User user = userService.getByLoginName(loginUser);
        if (user == null) {
            return LoginResult.登录失败;
        }
        if (user.getLoginFlag() == null || user.getLoginFlag().equals("0")) {
            return LoginResult.用户被锁定;
        }
        Boolean pass = SystemService.validatePassword(loginUser.getPassword(), user.getPassword());
        if (pass) {
            request.getSession().setAttribute(request.getSession().getId(), user);
            return LoginResult.登录成功;

        }
        return LoginResult.登录失败;
    }


    public static User getUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return (User) request.getSession().getAttribute(request.getSession().getId());
    }
}

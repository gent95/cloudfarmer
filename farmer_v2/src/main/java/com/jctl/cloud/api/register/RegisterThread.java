package com.jctl.cloud.api.register;

import com.jctl.cloud.common.utils.SpringContextHolder;
import com.jctl.cloud.modules.sys.dao.UserDao;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.RoleService;
import com.jctl.cloud.modules.sys.service.UserService;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

/**
 * Created by LewKay on 2017/3/23.
 */
public class RegisterThread extends Thread {

    public RegisterThread(String eName,String loginName) {
        this.eName =eName;
        this.loginName =loginName;
    }

    private String eName;
    private String loginName;

   private RoleService roleService = SpringContextHolder.getBean(RoleService.class);
   private UserService userService = SpringContextHolder.getBean(UserService.class);


    @Override
    public void run(){
        try {
            Thread.sleep(2000);
            Role role = roleService.findByEname("farmerBoss");
            User user = UserUtils.getByLoginName(loginName);
            user.setRole(role);
            userService.insertUserAndRole(user);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

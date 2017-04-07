package com.jctl.cloud.api.user;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.modules.sys.service.UserService;
import com.jctl.cloud.utils.FrontUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 刘凯 on 2017/3/24.
 */
@Controller
@RequestMapping("user")
public class AUserServiceController {


    @Autowired
    private UserService userService;

    @Autowired
    private SystemService systemService;

    /**
     * 更新个人信息
     *
     * @param user
     * @return
     */
    @RequestMapping("update")
    public Map update(User user) {
        Map result = Maps.newHashMap();
        try {
            userService.save(user);
            result.put("flag", 1);
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取农场下的农户列表
     *
     * @return
     */
    @RequestMapping("farmers")
    @ResponseBody
    public Map getFarmers(HttpServletRequest request, HttpServletResponse response) {
        Map result = Maps.newHashMap();
        List<Map<String,String>> data = new ArrayList<>();
        try {
            User user = FrontUserUtils.getUser();
//            List<User> users  = systemService.findUser(new Page<User>(request, response), user).getList();
            List<User> users  = userService.getByCompany(user);
            users.remove(user);

            String [] propertys = new String[]{"id","name","phone"};

            for ( User user1: users) {
                Map map = new HashMap();
                for (String property: propertys) {
                    map.put(property, Reflections.invokeGetter(user1,property));
                }
                data.add(map);
            }

            result.put("flag", 1);
            result.put("data", data);
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }


}

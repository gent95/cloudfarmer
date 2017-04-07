package com.jctl.cloud.api.register;

import com.google.common.collect.Maps;
import com.jctl.cloud.modules.sys.entity.Area;
import com.jctl.cloud.modules.sys.entity.Office;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.OfficeService;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.modules.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LewKAY on 2017/3/23.
 */

@Controller
@RequestMapping
public class ARegisterController {

    @Autowired
    private UserService userService;


    @Autowired
    private OfficeService officeService;


    @RequestMapping("register")
    @ResponseBody
    public Map register(User user) {

        Map result = new HashMap();
        try {
            //创建赋予角色
          String res=  officeService.doRegister(user);
          if(res.equals("0")){
              result.put("flag", "0");
              result.put("msg", "该手机已被注册！");
              return result;
          }
            result.put("flag", 1);
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败！");
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 发送验证码
     * @param mobile
     * @return
     */
    @RequestMapping("/sendSmsCode")
    @ResponseBody
    public Map sendSmsCode(String mobile, HttpServletRequest request){
        Map result = Maps.newHashMap();
        try {
            result.put("flag", 1);
            result.put("code", 123456);
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败！");
            e.printStackTrace();
        }

        return result;
    }



}

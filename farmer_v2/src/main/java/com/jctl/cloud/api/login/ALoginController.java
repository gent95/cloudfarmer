package com.jctl.cloud.api.login;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.entity.LoginResult;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import com.jctl.cloud.utils.FrontUserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.apache.batik.svggen.font.table.Table.post;

/**
 * Created by Lew on 2017/3/23.
 */

@Controller
@RequestMapping("login")
public class ALoginController {

    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping(value = "doLogin",method = RequestMethod.POST)
    @ResponseBody
    public Map doLogin(User user, HttpServletRequest request) {
        Map result = Maps.newHashMap();
        try {
            LoginResult flag = FrontUserUtils.doLogin(user,request);
         if(flag == LoginResult.登录成功){
             result.put("flag",1);
             result.put("userId",FrontUserUtils.getUser().getId());
         }else if(flag == LoginResult.用户被锁定){
             result.put("flag",0);
             result.put("msg","用户被锁定，请联系管理员！");
         }else if(flag ==LoginResult.登录失败){
             result.put("flag",0);
             result.put("msg","账号或密码错误！");
         }
        } catch (Exception e) {
            result.put("flag",0);
            result.put("msg","操作失败！");
            e.printStackTrace();
        }
        return result;
    }



}

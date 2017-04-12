package com.jctl.cloud.manager.register;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.modules.sys.entity.Office;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.OfficeService;
import com.jctl.cloud.modules.sys.service.RoleService;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.modules.sys.service.UserService;
import com.jctl.cloud.utils.sms.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/13.
 */
@Controller
@RequestMapping("${adminPath}/reg")
public class RegisterController {
    @Autowired
    private SystemService systemService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    /**
     * 选择角色页面
     */
    @RequestMapping("/optionRole")
    public String optionRole(HttpServletRequest request){
        return "manager/register/optionRole";
    }
    /**
     * 跳转到注册页面
     * @param request
     * @return
     */
    @RequestMapping("/register")
    public String re(@RequestParam String eName, String farmerName, Model model, HttpServletRequest request){
        Office office=new Office();
        Office parent=new Office();
        parent.setId("0");
        office.setParent(parent);
        List<Office> list=officeService.findAllList(office);
        model.addAttribute("eName",eName);
        model.addAttribute("farmerName",farmerName);
        model.addAttribute("resultList",list);
            return "manager/register/register";
    }

    /**
     * 跳转到忘记密码页面
     * @param request
     * @return
     */
    @RequestMapping("/forgetpassword")
    public String forget(HttpServletRequest request){
        return "manager/register/forgetPassword";
    }

    /**
     * 验证注册的用户是否存在
     * @param request
     * @param name
     * @return
     */
    @RequestMapping("/checkUserName")
    public  @ResponseBody Map<String,Object> checkName(HttpServletRequest request, String name){
        Map<String,Object> maps= Maps.newHashMap();
        User user=systemService.getUserByLoginName(name);
        if(user!=null){
            maps.put("status","0");
        }
        if(user==null){
            maps.put("status","1");
        }
        return maps;
    }

    /**
     * 注册用户
     * @param request
     * @param user
     * @return
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public Map addUser(User user,String eName,String officeName,String farmerName, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
        Map result=Maps.newHashMap();
       try {
           user.setPhone(user.getLoginName());
           user.setMobile(user.getLoginName());
           if("farmerBoss".equals(eName)||eName=="farmerBoss") {
               user.setCompanyName(farmerName);
            officeService.doRegister(user);
           }
           if("farmerWorker".equals(eName)||eName=="farmerWorker"){
               Role role = roleService.findByEname("farmerWorker");
               user.setRole(role);
        if(officeName!=null&&officeName!=""){
            Office office=new Office();
            office.setName(officeName);
            Office company=officeService.getOfficeName(office);
           Office office1=new Office();
           office1.setParent(company);
           office1.setName("农户");
            Office offices=officeService.getOfficeName(office1);
        if(company!=null){
                user.setCompany(company);
                user.setOffice(offices);
                user.preInsert();
                user.setCreateDate(new Date());
           systemService.register(user);
          userService.insertUserAndRole(user);
            }
        }
           }
        result.put("flag",1);
           result.put("info",user);
       }catch (Exception e){
           e.printStackTrace();
        result.put("flag",0);

       }
            return result;
    }
    @RequestMapping("login")
    public String login(Model model,User user){
        model.addAttribute("user",user);
        return "modules/sys/sysLogin";
    }
    @RequestMapping("checkOfficeName")
    @ResponseBody
    public Map checkOfficeName(String officeName){
        Map result=Maps.newHashMap();
        try{
            Office off=new Office();
            off.setName(officeName);
        Office office=officeService.getOfficeName(off);
        if(office!=null){
            result.put("flag",0);
        }else{
            result.put("flag",1);
        }
        }catch (Exception e) {
            result.put("flag", 0);
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 发送验证码
     * @param request
     * @param name
     * @return
     */
    @RequestMapping("/validateMsg")
    public @ResponseBody Map<String,Object> validateMsg(HttpServletRequest request,String name){
        Map<String,Object> maps=Maps.newHashMap();
        String mesCheckCode=SmsUtil.getVerification(request,name);
        maps.put("msgCode",mesCheckCode);
        return maps;
    }
    /**
     * 跳转到修改密码页面
     */
    @RequestMapping("/modifyPassword")
    public String modifyPassword(HttpServletRequest request, Model model, String loginName){
        model.addAttribute("name",loginName);
        System.out.println(loginName+"ssssssssssss");
        return "manager/register/modifyPassword";
    }
    /**
     * 修改密码
     */
    @RequestMapping("/modify")
    public String modify(HttpServletRequest request,User user){
        systemService.updatePasswordByLoginName(user);
        return  "modules/sys/sysLogin";
    }
    @RequestMapping("officeData")
    @ResponseBody
    public Map officeData(String phone,HttpServletRequest request){
        Map result=Maps.newHashMap();
        Office office=new Office();
        Office parent=officeService.get("0");
        office.setParent(parent);
        List<Office> list=officeService.findList(office);
        String[] proper=new String[]{"id","name"};
        List resultList=new ArrayList<>();
        for(Office of:list){
            Map maps=Maps.newHashMap();
            for(String property:proper){
                maps.put(property, Reflections.invokeGetter(of,property));
            }
            resultList.add(maps);
        }
        result.put("info",resultList);
        return result;
    }
}

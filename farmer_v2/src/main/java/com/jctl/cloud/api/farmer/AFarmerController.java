package com.jctl.cloud.api.farmer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.common.utils.SpringContextHolder;
import com.jctl.cloud.manager.farmer.entity.Farmer;
import com.jctl.cloud.manager.farmer.service.FarmerService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/23.
 */
@Controller
@RequestMapping("farmer")
public class AFarmerController {
    @Autowired
    private FarmerService farmerService;
    @Autowired
    private SystemService systemService;

    @RequestMapping("list")
    @ResponseBody
    public Map listFarmer(String userId, Farmer farmer, HttpServletRequest request) {
        Map result = Maps.newHashMap();
        List list = new ArrayList();
        try {
                User user = systemService.getUser(userId);
                if (user != null) {
                    boolean AdminUser = User.isAdmin(userId);
                    if (!AdminUser) {
                        List<Role> rolse = UserUtils.getRoleList();
                        for (Role ro : rolse) {
                            if (ro.getEnname().equals("farmerBoss")) {
                                farmer.setUser(user);
                            }
                        }
                    }
                    farmer.setUser(user);
                   List<Farmer> lists=farmerService.findList(farmer);
                    String[] propertys = new String[]{"id", "name", "addr", "farmlandNumber","plantVariety"};
                    if (lists!= null || lists.size()> 0) {
                        for (Farmer fa:lists) {
                            Map map = new HashMap();
                            for (String property: propertys) {
                                map.put(property, Reflections.invokeGetter(fa,property));
                            }
                            list.add(map);
                        }
                        result.put("flag", 1);
                        result.put("info",list);
                    }else {
                        result.put("flag","0");
                        result.put("msg", "抱歉，没有该农场信息!");
                    }
                }


        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("save")
    @ResponseBody
    public Map addFarmer(Farmer farmer, HttpServletRequest request) {
        Map result = Maps.newHashMap();
        try {
            farmerService.save(farmer);
            result.put("flag", "1");
            result.put("msg", "添加成功");
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "添加失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("update")
    @ResponseBody
    public Map updateFarmer(Farmer farmer, HttpServletRequest request) {
        Map result = Maps.newHashMap();
        try {
            if (farmer.getId() != null&&farmer.getId()!="") {
                farmerService.save(farmer);
                result.put("flag", "1");
                result.put("msg", "修改成功");
            }
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "修改失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("delete")
    @ResponseBody
    public Map deleteFarmer(Farmer farmer, HttpServletRequest request) {
        Map result = Maps.newHashMap();
        try {
            farmerService.delete(farmer);
            result.put("flag", "1");
            result.put("msg", "删除成功");
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "删除失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("get")
    @ResponseBody
    public Map getFarmer(String id, HttpServletRequest request) {
        Map result = Maps.newHashMap();
        List list=new ArrayList();

        try {
            Farmer farmer = farmerService.get(id);
            String[] propert=new String[]{"id", "name","farmerNum", "addr","area","plantVariety","user.name","farmlandNumber","relayNumber"};
            Map maps=Maps.newHashMap();
            for(String pro:propert){
                maps.put(pro,Reflections.invokeGetter(farmer,pro));
            }
            list.add(maps);
            result.put("info", list);
            result.put("flag","1");
        }catch (Exception e){
            result.put("flag","0");
            result.put("msg","操作失败");
        }
        return result;
    }
}

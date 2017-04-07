package com.jctl.cloud.api.farmland;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.manager.farmer.service.FarmerService;
import com.jctl.cloud.manager.farmerland.entity.Farmland;
import com.jctl.cloud.manager.farmerland.service.FarmlandService;
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
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/23.
 */
@Controller
@RequestMapping("farmland")
public class AFarmlandController {
    @Autowired
    private FarmlandService farmlandService;
    @Autowired
    private SystemService systemService;

    @RequestMapping("list")
    @ResponseBody
    public Map listFarmland(String userId, Farmland farmland, HttpServletRequest request) {
        Map result = Maps.newHashMap();
        try {
            User user = systemService.getUser(userId);
            if(user!=null) {
                boolean AdminUser = User.isAdmin(userId);
                if (!AdminUser) {
                    List<Role> rolse = UserUtils.getRoleList();
                    for (Role ro : rolse) {
                        if (ro.getEnname().equals("farmerBoss")) {
                            farmland.setUser(user);
                        }
                        if (ro.getEnname().equals("farmerWork")) {
                            farmland.setUsedId(user.getId());
                        }
                    }
                }
                List<Farmland> farmlandList = farmlandService.findList(farmland);
                String[] propert = new String[]{"id", "alias", "area", "plantVaritety", "nodeNumber"};
                if (farmlandList != null||farmlandList.size()>0) {
                    List messages = new ArrayList();
                    for (Farmland fa : farmlandList) {
                        Map message = Maps.newHashMap();
                        for (String property : propert) {
                            message.put(property, Reflections.invokeGetter(fa, property));
                        }
                        messages.add(message);
                    }
                    result.put("flag", "1");
                    result.put("info", messages);
                } else {
                    result.put("flag", "0");
                    result.put("msg", "抱歉，没有到农田信息!");
                    return null;
                }
            }

        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("save")
    @ResponseBody
    public Map saveFarmland(Farmland farmland, HttpServletRequest request) {
        Map result = Maps.newHashMap();
        try {
            farmlandService.save(farmland);
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
    public Map updateFarmer(Farmland farmland, HttpServletRequest request) {
        Map result = Maps.newHashMap();
        try {
            if (farmland.getId() != null && farmland.getId() != "") {
                farmlandService.save(farmland);
                result.put("flag", "1");
                result.put("msg", "修改成功");
            }
        } catch (Exception e) {
            result.put("flat", "0");
            result.put("msg", "修改失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("delete")
    @ResponseBody
    public Map delteFarmland(Farmland farmland, HttpServletRequest request) {
        Map result = Maps.newHashMap();
        try {
            farmlandService.delete(farmland);
            result.put("flag", "1");
            result.put("msg", "删除成功");
        } catch (Exception e) {
            result.put("flag", "0");
            result.put("msg", "删除失败");
        }
        return result;
    }

    @RequestMapping("get")
    @ResponseBody
    public Map getFarmland(String id, HttpServletRequest request) {
        Map result = Maps.newHashMap();
        try {
            Farmland farmland = farmlandService.get(id);
            String[] propert = new String[]{"id", "alias", "farmlandNum", "user.name", "usedName", "landType", "plantVaritety", "assigneTime", "relay.relayNum", "nodeNumber", "area"};
            List messages = new ArrayList();
            Map message = Maps.newHashMap();
            for (String pro : propert) {
                message.put(pro, Reflections.invokeGetter(farmland, pro));
            }
            messages.add(message);
            result.put("flag", "1");
            result.put("info", messages);
        }catch (Exception e){
            result.put("flag", "0");
            result.put("msg","操作失败");
        }
        return result;
    }
}

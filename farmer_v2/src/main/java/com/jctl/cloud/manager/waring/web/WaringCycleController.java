/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.waring.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jctl.cloud.common.annotation.waring.WaringMessagePush;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.relay.service.RelayService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jctl.cloud.common.config.Global;
import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.web.BaseController;
import com.jctl.cloud.common.utils.StringUtils;
import com.jctl.cloud.manager.waring.entity.WaringCycle;
import com.jctl.cloud.manager.waring.service.WaringCycleService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异常采集Controller
 *
 * @author lewkay
 * @version 2017-03-07
 */
@Controller
@RequestMapping(value = "${adminPath}/waring/waringCycle")
public class WaringCycleController extends BaseController {

    public WaringCycleController() {


    }


    @Value("#{config['farmerBoss']}")
    private String farmerBoss;

    @Value("#{config['farmerWork']}")
    private String farmerWork;

    @Value("#{config['regulator']}")
    private String regulator;


    @Autowired
    private NodeService nodeService;

    @Autowired
    private RelayService relayService;

    @Autowired
    private WaringCycleService waringCycleService;

    @ModelAttribute
    public WaringCycle get(@RequestParam(required = false) String id) {
        WaringCycle entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = waringCycleService.get(id);
        }
        if (entity == null) {
            entity = new WaringCycle();
        }
        return entity;
    }

    //	@RequiresPermissions("waring:waringCycle:view")
//    @WaringMessagePush(description = "描述信息,测试用的")
    @RequestMapping(value = {"list", ""})
    public String list(WaringCycle waringCycle, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<WaringCycle> page = waringCycleService.findPage(new Page<WaringCycle>(request, response), waringCycle);
        model.addAttribute("page", page);
        return "manager/waring/waringCycleList";
    }

    //	@RequiresPermissions("waring:waringCycle:view")
//    @WaringMessagePush(description = "描述信息,测试用的")
    @RequestMapping(value = "form")
    public String form(WaringCycle waringCycle, Model model) {

        boolean isAdmin = User.isAdmin(UserUtils.getUser().getId());
        List<Node> nodes = Lists.newArrayList();
        if (isAdmin) {
            nodes = nodeService.findList(new Node());
        } else {
            User user = UserUtils.getUser();
            List<Role> list = UserUtils.getRoleList();
            Node search = new Node();

            for (Role role : list) {

                if (role.getEnname().equals(farmerBoss) || role.getEnname().equals(regulator)) {
                    nodes = nodeService.selectAllNodeByUserId(UserUtils.getUser().getId());
                }
                if (role.getEnname().equals(farmerWork)) {
                    search.setUsedId(UserUtils.getUser().getId());
                    nodes = nodeService.findList(search);
                }
            }

        }


        model.addAttribute("waringCycle", waringCycle);
        model.addAttribute("nodes", nodes);
        return "manager/waring/waringCycleForm";
    }

    //	@RequiresPermissions("waring:waringCycle:edit")
//    @WaringMessagePush(description = "描述信息,测试用的")
    @RequestMapping(value = "save")
    @ResponseBody
    public Map save(WaringCycle waringCycle) {
        Map result = new HashMap();
        try {
            waringCycleService.save(waringCycle);
            result.put("status", 1);
        } catch (Exception e) {
            result.put("status", 0);
            e.printStackTrace();
        }
        return result;
//        if (!beanValidator(model, waringCycle)) {
//            return form(waringCycle, model);
//        }
//
//        addMessage(redirectAttributes, "保存异常策略成功");
//        return "redirect:" + Global.getAdminPath() + "/waring/waringCycle/?repage";
    }

    //	@RequiresPermissions("waring:waringCycle:edit")
    @RequestMapping(value = "delete")
    public String delete(WaringCycle waringCycle, RedirectAttributes redirectAttributes) {
        waringCycleService.delete(waringCycle);
        addMessage(redirectAttributes, "删除异常策略成功");
        return "redirect:" + Global.getAdminPath() + "/waring/waringCycle/?repage";
    }


    @RequestMapping(value = "add")
    @ResponseBody
    public Map<String, Object> add(WaringCycle waringCycle) {
        Map result = Maps.newHashMap();
        try {
            waringCycleService.save(waringCycle);
            result.put("status", 1);
        } catch (Exception e) {
            result.put("status", 0);
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "del")
    @ResponseBody
    public Map<String, Object> del(WaringCycle waringCycle) {
        Map result = Maps.newHashMap();
        try {
            waringCycleService.delete(waringCycle);
            result.put("status", 1);
        } catch (Exception e) {
            result.put("status", 0);
            e.printStackTrace();
        }
        return result;
    }




}
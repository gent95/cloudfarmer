/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.relay.web;

import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Maps;
import com.jctl.cloud.manager.farmer.entity.Farmer;
import com.jctl.cloud.manager.farmer.service.FarmerService;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.service.SystemService;
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

import java.util.*;

import com.jctl.cloud.common.config.Global;
import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.web.BaseController;
import com.jctl.cloud.common.utils.StringUtils;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.relay.service.RelayService;

import java.util.Date;

/**
 * 中继管理Controller
 *
 * @author ll
 * @version 2017-02-25
 */
@Controller
@RequestMapping(value = "${adminPath}/relay/relay")
public class
RelayController extends BaseController {

    @Autowired
    private RelayService relayService;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private FarmerService farmerService;
    @Autowired
    private SystemService systemService;

    @Value("#{config['farmerBoss']}")
    private String farmerBoss;

    @Value("#{config['farmerWork']}")
    private String farmerWork;

    @ModelAttribute
    public Relay get(@RequestParam(required = false) String id) {
        Relay entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = relayService.get(id);
        }
        if (entity == null) {
            entity = new Relay();
        }
        return entity;
    }

    @RequiresPermissions("relay:relay:view")
    @RequestMapping(value = {"list", ""})
    public String list(Relay relay, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Relay> page = null;
        User user = UserUtils.getUser();

        boolean isAdmin = User.isAdmin(UserUtils.getUser().getId());
        if (!isAdmin) {
            List<Role> list = UserUtils.getRoleList();
            for (Role role : list) {
                if (role.getEnname().equals(farmerBoss)) {
                    relay.setUser(user);

                }
            }
        }
        page = relayService.findPage(new Page<Relay>(request, response), relay);
        List<Relay> relays = new ArrayList<Relay>();
        if(page.getList()!=null){
            for (Relay relayTemp : page.getList()) {
                Integer num = nodeService.getNodeNum(relayTemp.getId());
                if (num != null)
                    relayTemp.setNodeNum(num);
                relays.add(relayTemp);
            }

        }
        page.getList().removeAll(page.getList());
        page.setList(relays);
        model.addAttribute("page", page);
        return "manager/relay/relayList";
    }

    @RequiresPermissions("relay:relay:view")
    @RequestMapping(value = "form")
    public String form(Relay relay, Model model) {
        Farmer farmer=new Farmer();
        Node node=new Node();
        boolean isAdmin=User.isAdmin(UserUtils.getUser().getId());
        if(!isAdmin){
            List<Role> roles=UserUtils.getRoleList();
            for(Role r:roles){
                if(r.getEnname().equals(farmerBoss)){
                    farmer.setUser(UserUtils.getUser());
                    node.setUser(UserUtils.getUser());
                }

            }
        }
        if(relay.getId()!=null&&relay.getId()!="") {
            node.setRelayId(Long.parseLong(relay.getId()));
            Page<Node> pages = nodeService.findPage(new Page<Node>(), node);
            model.addAttribute("page",pages);
        }
        List<Farmer> farmers=farmerService.findList(farmer);
        model.addAttribute("famers",farmers);
        model.addAttribute("relay", relay);

        return "manager/relay/relayForm";
    }

    @RequiresPermissions("relay:relay:edit")
    @RequestMapping(value = "save")
    public String save(Relay relay, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, relay)) {
            return form(relay, model);
        }
        if (relay.getId() == null || relay.getId().equals("")) {
            relay.setAddTime(new Date());
            relay.setUser(UserUtils.getUser());
        }else {
            Farmer farmer=farmerService.get(relay.getFarmerId().toString());
            if(farmer!=null&&farmer.getUsedId()!=null&&farmer.getUsedId()!="") {
                User user = systemService.getUser(farmer.getUsedId());
                relay.setUser(user);
                relay.setFarmer(farmer);
            }
        }
        relay.setDelFlag("1");
        relayService.save(relay);
        addMessage(redirectAttributes, "保存中继成功");
        return "redirect:" + Global.getAdminPath() + "/relay/relay/?repage";
    }

    @RequiresPermissions("relay:relay:edit")
    @RequestMapping(value = "delete")
    public String delete(Relay relay, RedirectAttributes redirectAttributes) {
        relay.setDelFlag("0");
        relayService.delete(relay);
        addMessage(redirectAttributes, "删除中继成功");
        return "redirect:" + Global.getAdminPath() + "/relay/relay/?repage";
    }

    @RequestMapping(value = "detectionNum")
    public
    @ResponseBody
    int detectionNum(Relay relay, Model model) {
        System.out.println(relay.getRelayNum() + "ssss");
        List<Relay> relayList = relayService.findRelayByNum(relay);
        System.out.println(relayList.size() + "sssssssss");
        return relayList.size();
    }

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeDate")
    public List<Map<Object, Object>> treeDate(HttpServletResponse response) {
        List<Map<Object, Object>> mapList = new ArrayList<Map<Object, Object>>();
        List<Relay> list = relayService.findList(new Relay());
        for (int i = 0; i < list.size(); i++) {
            Relay relay = list.get(i);
            Map<Object, Object> map = Maps.newHashMap();
            map.put("id", relay.getId());
            map.put("name", StringUtils.replace(relay.getRelayNum(), " ", ""));
            mapList.add(map);
        }
        return mapList;
    }
}
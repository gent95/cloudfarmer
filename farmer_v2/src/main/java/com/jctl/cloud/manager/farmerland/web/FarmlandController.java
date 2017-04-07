/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.farmerland.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Maps;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.relay.service.RelayService;
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

import com.jctl.cloud.common.config.Global;
import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.web.BaseController;
import com.jctl.cloud.common.utils.StringUtils;
import com.jctl.cloud.manager.farmerland.entity.Farmland;
import com.jctl.cloud.manager.farmerland.service.FarmlandService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 农田(大棚)Controller
 *
 * @author kay
 * @version 2017-02-25
 */
@Controller
@RequestMapping(value = "${adminPath}/farmerland/farmland")
public class FarmlandController extends BaseController {

    @Autowired
    private FarmlandService farmlandService;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private RelayService relayService;

    @Value("#{config['farmerBoss']}")
    private String farmerBoss;

    @Value("#{config['farmerWork']}")
    private String farmerWork;

    @ModelAttribute
    public Farmland get(@RequestParam(required = false) String id) {
        Farmland entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = farmlandService.get(id);
        }
        if (entity == null) {
            entity = new Farmland();
        }
        return entity;
    }

    @RequiresPermissions("farmerland:farmland:view")
    @RequestMapping(value = {"list", ""})
    public String list(Farmland farmland, HttpServletRequest request, HttpServletResponse response, Model model) {

        Page<Farmland> page = null;
        User user = UserUtils.getUser();
        boolean isAdmin = User.isAdmin(UserUtils.getUser().getId());
        if (!isAdmin) {
            List<Role> list = UserUtils.getRoleList();
            for (Role role : list) {
                if (role.getEnname().equals(farmerBoss)) {
                    farmland.setUser(user);
                }
                if (role.getEnname().equals(farmerWork)) {
                    farmland.setUsedId(user.getId());
                }
            }
        }

        page = farmlandService.findPage(new Page<Farmland>(request, response), farmland);
        List<Farmland> lists = new ArrayList<Farmland>();
        Integer nodeNumber;
        for (Farmland fa : page.getList()) {
            nodeNumber = nodeService.getNodeNumberByfarmlandId(fa.getId());
            fa.setNodeNumber(nodeNumber);
            lists.add(fa);
        }
        page.getList().removeAll(page.getList());
        page.setList(lists);
        model.addAttribute("page", page);
        return "manager/farmerland/farmlandList";
    }

    @RequiresPermissions("farmerland:farmland:view")
    @RequestMapping(value = "form")
    public String form(Farmland farmland, Model model) {
        if(farmland.getId()!=null&&farmland.getId()!=""){
            Node node=new Node();
            boolean isAdmin=User.isAdmin(UserUtils.getUser().getId());
            if(!isAdmin){
                List<Role> list=UserUtils.getRoleList();
                for(Role role:list){
                    if(role.getEnname().equals(farmerBoss)){
                        node.setUser(UserUtils.getUser());
                    }
                }
            }
            node.setFarmlandId(farmland.getId());
            Page<Node> page=nodeService.findPage(new Page<Node>(),node);
            model.addAttribute("page",page);
        }

        model.addAttribute("farmland", farmland);
        return "manager/farmerland/farmlandForm";
    }

    @RequiresPermissions("farmerland:farmland:edit")
    @RequestMapping(value = "save")
    public String save(Farmland farmland, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, farmland)) {
            return form(farmland, model);
        }
        if(farmland.getId()==null||farmland.getId().equals("")){
            farmland.setDelFlag("1");
            farmland.setUser(UserUtils.getUser());
            farmland.setAddTime(new Date());
            farmlandService.save(farmland);
        }
      else
        {
            Node node=new Node();
            node.setFarmlandId(farmland.getId());
            List<Node> lists=nodeService.findList(node);
            if(lists.size()>0) {
                for (Node no : lists) {
                    no.setUsedId(farmland.getUsedId());
                    nodeService.save(no);
                }
            }
            if(farmland.getFarmer()==null||farmland.getFarmer().getId().equals("")||farmland.getFarmer().getId()==null){
                farmlandService.updateFarmland(farmland);
            }else {
                farmlandService.save(farmland);
            }
        }

        addMessage(redirectAttributes, "保存农田(大棚)成功");
        return "redirect:" + Global.getAdminPath() + "/farmerland/farmland/?repage";
    }

    @RequiresPermissions("farmerland:farmland:edit")
    @RequestMapping(value = "delete")
    public String delete(Farmland farmland, RedirectAttributes redirectAttributes) {
        farmland.setDelFlag("0");
        farmlandService.delete(farmland);
        addMessage(redirectAttributes, "删除农田(大棚)成功");
        return "redirect:" + Global.getAdminPath() + "/farmerland/farmland/?repage";
    }

    @RequiresPermissions("user")
    @RequestMapping("treeDate")
    @ResponseBody
    public List<Map<String, Object>> treeDate(HttpServletResponse response) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Farmland> farmlands = farmlandService.findList(new Farmland());
        System.out.println(farmlands.size() + "sssssssssssssssssss");
        for (Farmland fa : farmlands) {
            Map<String, Object> maps = Maps.newHashMap();
            maps.put("id", fa.getId());
            maps.put("name", StringUtils.replace(fa.getAlias(), " ", ""));
            list.add(maps);
        }
        return list;
    }
    @RequestMapping("/getFarmerById")
    @ResponseBody
    public Map getFarmerById(String id,HttpServletRequest request){
        Map result=Maps.newHashMap();
        Farmland fa=farmlandService.get(id);
        result.put("info",fa);
        return result;
    }
}
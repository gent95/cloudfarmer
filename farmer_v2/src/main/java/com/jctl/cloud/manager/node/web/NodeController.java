/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.node.web;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.config.Global;
import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.utils.StringUtils;
import com.jctl.cloud.common.web.BaseController;
import com.jctl.cloud.manager.farmerland.entity.Farmland;
import com.jctl.cloud.manager.farmerland.service.FarmlandService;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.relay.service.RelayService;
import com.jctl.cloud.manager.timingstrategy.entity.NodeCollectionCycle;
import com.jctl.cloud.manager.timingstrategy.service.NodeCollectionCycleService;
import com.jctl.cloud.manager.waring.entity.WaringCycle;
import com.jctl.cloud.manager.waring.service.WaringCycleService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import com.jctl.cloud.utils.NodeControlUtil;
import com.jctl.cloud.utils.QutarzUtil;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 节点管理Controller
 *
 * @author ll
 * @version 2017-02-25
 */
@Controller
@RequestMapping(value = "${adminPath}/node/node")
public class NodeController extends BaseController {


    @Value("#{config['farmerBoss']}")
    private String farmerBoss;

    @Value("#{config['farmerWork']}")
    private String farmerWork;


    @Autowired
    private NodeService nodeService;

    @Autowired
    private NodeControlUtil dateResultUtil;
    @Autowired
    private FarmlandService farmlandService;
    @Autowired
    private RelayService relayService;
    @Autowired
    private WaringCycleService waringCycleService;

    @Autowired
    private NodeCollectionCycleService nodeCollectionCycleService;


    @ModelAttribute
    public Node get(@RequestParam(required = false) String id) {
        Node entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = nodeService.get(id);
        }
        if (entity == null) {
            entity = new Node();
        }
        return entity;
    }

    @RequiresPermissions("node:node:view")
    @RequestMapping(value = {"list", ""})
    public String list(Node node, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Node> page = null;
        List<Farmland> farmlands = null;
        User user = UserUtils.getUser();
        boolean isAdmin = User.isAdmin(UserUtils.getUser().getId());
        if (!isAdmin) {
            List<Role> list = UserUtils.getRoleList();
            for (Role role : list) {
                if (role.getEnname().equals(farmerBoss)) {
                    node.setUser(user);
                } else {
                    Farmland search = new Farmland();
                    search.setUsedId(UserUtils.getUser().getId());
                    farmlands = farmlandService.findList(search);
                }
            }
        }

        if (farmlands == null) {
            page = nodeService.findPage(new Page<Node>(request, response), node);
        } else {
            page = nodeService.findPage(new Page<Node>(request, response), node);
            List<Node> list=new ArrayList<>();
            for (Farmland farmland : farmlands) {
                Node nodeSearch = new Node();
                nodeSearch.setFarmlandId(farmland.getId());
                List<Node> result =nodeService.findList(nodeSearch);
                list.addAll(result);
            }
            page.setList(list);
        }
        model.addAttribute("page", page);
        return "manager/node/nodeList";
    }


    @RequiresPermissions("node:node:view")
    @RequestMapping(value = "form")
    public String form(Node node, Model model) {
        Farmland farmland = new Farmland();
        boolean isAdmin = User.isAdmin(UserUtils.getUser().getId());
        Relay relay=null;
        if (!isAdmin) {
            List<Role> list = UserUtils.getRoleList();
            for (Role role : list) {
                if (role.getEnname().equals(farmerBoss)) {
                    farmland.setUser(UserUtils.getUser());
                }
            }
        }
        if(node.getRelayName()!=null) {
        relay= relayService.getByMac(node.getRelayName());
            if(relay.getFarmer()!=null&&relay.getFarmer().getId()!=null&&!relay.getFarmer().getId().equals("")){
                farmland.setFarmer(relay.getFarmer());
               /* farmland.setUser(UserUtils.getUser());*/
            }
        }
        List<Farmland> lists = farmlandService.findList(farmland);
        model.addAttribute("node", node);
        model.addAttribute("relay",relay);
        model.addAttribute("lists", lists);
        return "manager/node/nodeForm";
    }

    @RequiresPermissions("node:node:edit")
    @RequestMapping(value = "save")
    public String save(Node node, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, node)) {
            return form(node, model);
        }

        if (node.getRelayId() != null || node.getRelayId() != -1) {
            if (node.getFarmlandId() != null ||!node.getFarmlandId().equals("")||node.getFarmlandId()!="-1") {
                Farmland farmland = new Farmland();
                farmland.setId(node.getFarmlandId());
                Relay re = relayService.get(node.getRelayId().toString());
                if (re.getFarmer() != null || re.getFarmer().getId() != null || re.getFarmer().getId() != "-1") {
                    farmland.setFarmer(re.getFarmer());
                    farmland.setRelay(re);
                    farmlandService.updateById(farmland);
                }
            }

        }
        node.setUsedId(farmlandService.get(node.getFarmlandId().toString()).getUsedId());
        nodeService.save(node);
        addMessage(redirectAttributes, "保存节点成功");
        return "redirect:" + Global.getAdminPath() + "/node/node/?repage";
    }

    @RequiresPermissions("node:node:edit")
    @RequestMapping(value = "delete")
    public String delete(Node node, RedirectAttributes redirectAttributes) {
        node.setDelFlag("0");
        nodeService.delete(node);
        addMessage(redirectAttributes, "删除节点成功");
        return "redirect:" + Global.getAdminPath() + "/node/node/?0";
    }

    /**
     * 验证新增验证是否重复
     *
     * @param node
     * @return
     */
    @RequestMapping(value = "fetechNode")
    public
    @ResponseBody
    int fetechtionNode(Node node) {
        List<Node> nodeList = nodeService.findAllByNum(node);
        return nodeList.size();
    }

    /**
     * 立即刷新当前节点各项信息
     *
     * @param node node对象必须存在当前node的id
     * @return
     */
    @RequestMapping("/refreshNodeInfo")
    @ResponseBody
    public Map<String, Object> refreshNodeInfo(Node node) {
        Map<String, Object> result = new HashMap<>();
        try {
            dateResultUtil.refreshNodeByNodeId(node);
            result.put("status", 1);//刷新成功
            result.put("msg", "操作成功");//刷新成功
        } catch (Exception e) {
            result.put("msg", "操作失败");
            result.put("status", 0);
            System.out.println("==================" + node.getNodeNum() + "掉线,操作失败!!!===============");
        }
        return result;
    }

    @RequestMapping("strategyManagerment")
    public String strategyManagerment(Node node, Model model) {
        System.out.println("==========这个是节点编号============" + node.getNodeNum());
        NodeCollectionCycle nCC = new NodeCollectionCycle();
        Node nodetmp = nodeService.get(node.getId());
        nCC.setNodeId(nodetmp.getNodeNum());
        try {
            NodeCollectionCycle nodeCollectionCycle = nodeCollectionCycleService.findByNodeId(nCC);
            if (nodeCollectionCycle != null || nodeCollectionCycle.getCycleOn() != "" || nodeCollectionCycle.getCycleOff() != "" || nodeCollectionCycle.getCycleTime() != "") {
                List<WaringCycle> list = waringCycleService.findList(new WaringCycle());
                model.addAttribute("waringCycles", list);
                model.addAttribute("nodeCollectionCycle", nodeCollectionCycle);
                model.addAttribute("node", node);
                //这三行代码会报异常
                nodeCollectionCycle.setCycleTime(QutarzUtil.qutarzStrConvertTime(nodeCollectionCycle.getCycleTime()));
                nodeCollectionCycle.setCycleOn(QutarzUtil.qutarzStrConvertTime(nodeCollectionCycle.getCycleOn()));
                nodeCollectionCycle.setCycleOff(QutarzUtil.qutarzStrConvertTime(nodeCollectionCycle.getCycleOff()));
            }
        } catch (Exception e) {
            System.out.println("没有这个节点对应的开关策略");
        }
        return "manager/node/strategy";
    }

    /**
     * 手动开关控制
     *
     * @param id
     * @param task
     * @param model
     * @return
     */
    @RequestMapping("manual")
    public String manual(String id, String task, Model model) {
        Node node = nodeService.get(id);
        NodeControlUtil nodeControlUtil = new NodeControlUtil();
        Map<String, Object> result = Maps.newHashMap();
        try {
            if (task.equals("0")) {
                //执行关闭命令
                nodeControlUtil.closeNode(node);
            } else if (task.equals("1")) {
                //执行开启命令
                nodeControlUtil.openNode(node);
            }
        } catch (Exception e) {
            System.out.print(node.getNodeNum() + "开关指令执行失败");
        } finally {
            model.addAttribute("node", node);
            return strategyManagerment(node, model);
        }
    }

    @RequestMapping("/banUsed")
    public
    @ResponseBody
    Map<String, Object> banUsed(Node node, Model model) {

        Map<String, Object> result = Maps.newHashMap();
        try {
            nodeService.save(node);
            result.put("status", 1);//禁止使用成功
            result.put("msg", "操作成功");
        } catch (Exception e) {
            result.put("status", 0);//禁止使用失败
            result.put("msg", "操作成功");
        }
        return result;
    }
}
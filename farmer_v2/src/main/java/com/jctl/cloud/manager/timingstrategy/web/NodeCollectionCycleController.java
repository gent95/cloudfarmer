/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.timingstrategy.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import com.jctl.cloud.utils.QutarzUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jctl.cloud.common.config.Global;
import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.web.BaseController;
import com.jctl.cloud.common.utils.StringUtils;
import com.jctl.cloud.manager.timingstrategy.entity.NodeCollectionCycle;
import com.jctl.cloud.manager.timingstrategy.service.NodeCollectionCycleService;

import java.util.List;

/**
 * 节点定时任务Controller
 * @author gent
 * @version 2017-03-02
 */
@Controller
@RequestMapping(value = "${adminPath}/timingstrategy/nodeCollectionCycle")
public class NodeCollectionCycleController extends BaseController {
	@Value("#{config['farmerBoss']}")
	private String farmerBoss;

	@Value("#{config['farmerWork']}")
	private String farmerWork;
	@Autowired
	private NodeCollectionCycleService nodeCollectionCycleService;
	@Autowired
	private NodeService nodeService;
	
	@ModelAttribute
	public NodeCollectionCycle get(@RequestParam(required=false) String id) {
		NodeCollectionCycle entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = nodeCollectionCycleService.get(id);
		}
		if (entity == null){
			entity = new NodeCollectionCycle();
		}
		return entity;
	}
	
	@RequiresPermissions("timingstrategy:nodeCollectionCycle:view")
	@RequestMapping(value = {"list", ""})
	public String list(NodeCollectionCycle nodeCollectionCycle, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<NodeCollectionCycle> page = nodeCollectionCycleService.findPage(new Page<NodeCollectionCycle>(request, response), nodeCollectionCycle);
		User user = UserUtils.getUser();
		boolean isAdmin = User.isAdmin(UserUtils.getUser().getId());
		model.addAttribute("page", page);
		return "manager/timingstrategy/nodeCollectionCycleList";
	}

	@RequiresPermissions("timingstrategy:nodeCollectionCycle:view")
	@RequestMapping(value = "form")
	public String form(NodeCollectionCycle nodeCollectionCycle, Model model) {
		model.addAttribute("nodeCollectionCycle", nodeCollectionCycle);
		return "manager/timingstrategy/nodeCollectionCycleForm";
	}

	@RequiresPermissions("timingstrategy:nodeCollectionCycle:edit")
	@RequestMapping(value = "save")
	public String save(NodeCollectionCycle nodeCollectionCycle, Model model, RedirectAttributes redirectAttributes,String[] on,String[] off) {
		if (!beanValidator(model, nodeCollectionCycle)){
			return form(nodeCollectionCycle, model);
		}
		nodeCollectionCycleService.deleteByNodeId(nodeCollectionCycle);
		nodeCollectionCycle.setAddUserId(Long.parseLong(UserUtils.getUser().getId()));
		nodeCollectionCycle.setCycleTime(0+" "+nodeCollectionCycle.getCycleTime()+" * * * ?");
		nodeCollectionCycle.setCycleOn(QutarzUtil.dateConvertQutarzFormate(nodeCollectionCycle.getCycleOn()));
		nodeCollectionCycle.setCycleOff(QutarzUtil.dateConvertQutarzFormate(nodeCollectionCycle.getCycleOff(),off));
		nodeCollectionCycleService.save(nodeCollectionCycle);
		addMessage(redirectAttributes, "保存节点定时任务成功");
		Node node = nodeService.getByNodeNum(nodeCollectionCycle.getNodeId());
		//启动定时器
		QutarzUtil.start();
		return "redirect:"+Global.getAdminPath()+"/node/node/strategyManagerment?id="+node.getId();
	}
	
	@RequiresPermissions("timingstrategy:nodeCollectionCycle:edit")
	@RequestMapping(value = "delete")
	public String delete(NodeCollectionCycle nodeCollectionCycle, RedirectAttributes redirectAttributes) {
		nodeCollectionCycleService.delete(nodeCollectionCycle);
		addMessage(redirectAttributes, "删除节点定时任务成功");
		return "redirect:"+Global.getAdminPath()+"/timingstrategy/nodeCollectionCycle/?repage";
	}

}
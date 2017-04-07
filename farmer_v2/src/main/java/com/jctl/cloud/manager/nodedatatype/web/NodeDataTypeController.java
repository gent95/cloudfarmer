/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.nodedatatype.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.jctl.cloud.manager.nodedatatype.entity.NodeDataType;
import com.jctl.cloud.manager.nodedatatype.service.NodeDataTypeService;

/**
 * 节点数据类型管理Controller
 * @author ll
 * @version 2017-02-25
 */
@Controller
@RequestMapping(value = "${adminPath}/nodedatatype/nodeDataType")
public class NodeDataTypeController extends BaseController {

	@Autowired
	private NodeDataTypeService nodeDataTypeService;
	
	@ModelAttribute
	public NodeDataType get(@RequestParam(required=false) String id) {
		NodeDataType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = nodeDataTypeService.get(id);
		}
		if (entity == null){
			entity = new NodeDataType();
		}
		return entity;
	}
	
	@RequiresPermissions("nodedatatype:nodeDataType:view")
	@RequestMapping(value = {"list", ""})
	public String list(NodeDataType nodeDataType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<NodeDataType> page = nodeDataTypeService.findPage(new Page<NodeDataType>(request, response), nodeDataType); 
		model.addAttribute("page", page);
		return "manager/nodedatatype/nodeDataTypeList";
	}

	@RequiresPermissions("nodedatatype:nodeDataType:view")
	@RequestMapping(value = "form")
	public String form(NodeDataType nodeDataType, Model model) {
		model.addAttribute("nodeDataType", nodeDataType);
		return "manager/nodedatatype/nodeDataTypeForm";
	}

	@RequiresPermissions("nodedatatype:nodeDataType:edit")
	@RequestMapping(value = "save")
	public String save(NodeDataType nodeDataType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, nodeDataType)){
			return form(nodeDataType, model);
		}
		nodeDataTypeService.save(nodeDataType);
		addMessage(redirectAttributes, "保存数据类型成功");
		return "redirect:"+Global.getAdminPath()+"/nodedatatype/nodeDataType/?repage";
	}
	
	@RequiresPermissions("nodedatatype:nodeDataType:edit")
	@RequestMapping(value = "delete")
	public String delete(NodeDataType nodeDataType, RedirectAttributes redirectAttributes) {
		nodeDataTypeService.delete(nodeDataType);
		addMessage(redirectAttributes, "删除数据类型成功");
		return "redirect:"+Global.getAdminPath()+"/nodedatatype/nodeDataType/?repage";
	}

}
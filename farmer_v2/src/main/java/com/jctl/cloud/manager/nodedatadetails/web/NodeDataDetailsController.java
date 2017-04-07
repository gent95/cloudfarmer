/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.nodedatadetails.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jctl.cloud.common.utils.DateUtils;
import com.jctl.cloud.common.utils.excel.ExportExcel;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.utils.StringUtils;
import com.jctl.cloud.common.web.BaseController;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.nodedatadetails.entity.NodeDataDetails;
import com.jctl.cloud.manager.nodedatadetails.service.NodeDataDetailsService;

/**
 * 节点数据详情管理Controller
 * @author ll
 * @version 2017-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/nodedatadetails/nodeDataDetails")
public class NodeDataDetailsController extends BaseController {

	@Autowired
	private NodeDataDetailsService nodeDataDetailsService;
   @Autowired
	private NodeService nodeService;
	@ModelAttribute
	public NodeDataDetails get(@RequestParam(required=false) String id) {
		NodeDataDetails entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = nodeDataDetailsService.get(id);
		}
		if (entity == null){
			entity = new NodeDataDetails();
		}
		return entity;
	}

	@RequiresPermissions("nodedatadetails:nodeDataDetails:view")
	@RequestMapping(value = {"list", ""})
	public String list(NodeDataDetails nodeDataDetails,String nodeId, HttpServletRequest request, HttpServletResponse response, Model model) {
		Node node=nodeService.get(nodeId);
		nodeDataDetails.setNodeMac(node.getNodeNum());
		Page<NodeDataDetails> page = nodeDataDetailsService.findPage(new Page<NodeDataDetails>(request, response), nodeDataDetails);
		model.addAttribute("page", page);
		model.addAttribute("nodeMac",node.getNodeNum());
		model.addAttribute("nodeId",nodeId);
		return "manager/nodedatadetails/nodeDataDetailsList";
	}

	@RequiresPermissions("nodedatadetails:nodeDataDetails:view")
	@RequestMapping(value = "form")
	public String form(NodeDataDetails nodeDataDetails, Model model,String nodeId,HttpServletRequest request, HttpServletResponse response) {
		Node node=nodeService.get(nodeId);
		nodeDataDetails.setNodeMac(node.getNodeNum());
		System.out.println(node.getNodeNum()+"ssssssssssssssssssssssssssss");
		model.addAttribute("nodeMac",node.getNodeNum());
		model.addAttribute("nodeId",nodeId);
		return "manager/nodedatadetails/nodeDataDetailsForm";
	}

	@RequiresPermissions("nodedatadetails:nodeDataDetails:edit")
	@RequestMapping(value = "delete")
	public String  delete(NodeDataDetails nodeDataDetails,String nodeId, RedirectAttributes redirectAttributes) {
		nodeDataDetailsService.delete(nodeDataDetails);
		addMessage(redirectAttributes, "删除节点数据详情成功");
		return "redirect:"+adminPath+"/nodedatadetails/nodeDataDetails/?repage&nodeId="+nodeId;
	}


	/**
	 * 获取节点刷新页面数据
	 * @param nodeDataDetails
	 * @param model
	 * @return
	 */
	@RequestMapping("fetchLastData")
	public @ResponseBody Map<Object,List<NodeDataDetails>> fetchLastData(NodeDataDetails nodeDataDetails, Model model){
		List<NodeDataDetails> lists=nodeDataDetailsService.fetchLastData(nodeDataDetails);
		Map<Object,List<NodeDataDetails>> info=Maps.newHashMap();
		info.put("data",lists);
		return info;
	}


	@RequiresPermissions("nodedatadetails:nodeDataDetails:view")
	@RequestMapping(value = "export",method= RequestMethod.POST)
	public String Export(NodeDataDetails nodeDataDetails,HttpServletRequest request,HttpServletResponse response,RedirectAttributes attributes){
		String nodeId=nodeService.getByNodeMac(nodeDataDetails.getNodeMac()).getId();
		try {
			Page<NodeDataDetails> page = nodeDataDetailsService.findNodeDataDetails(new Page<NodeDataDetails>(request, response, -1), nodeDataDetails);
			//文件名
			String fileName = "节点详情数据"+ DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			//执行导出
			new ExportExcel("节点详情数据",//EXCEL标题
					NodeDataDetails.class).//放入Entity的class
					setDataList( page.getList()).//这里的查询结果放入List<Entity>
					write(response, fileName).
					dispose();

			return null;
		}catch (Exception e){
			e.printStackTrace();
			addMessage(attributes, "导出节点详情数据失败！失败信息："+e.getMessage());

		}
		return "redirect:"+adminPath+"/nodedatadetails/nodeDataDetails/?repage&nodeId="+nodeId;
	}

}
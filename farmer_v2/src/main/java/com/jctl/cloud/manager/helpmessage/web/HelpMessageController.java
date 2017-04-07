/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.helpmessage.web;

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
import com.jctl.cloud.manager.helpmessage.entity.HelpMessage;
import com.jctl.cloud.manager.helpmessage.service.HelpMessageService;

/**
 * 帮助信息Controller
 * @author 刘凯
 * @version 2017-03-23
 */
@Controller
@RequestMapping(value = "${adminPath}/helpmessage/helpMessage")
public class HelpMessageController extends BaseController {

	@Autowired
	private HelpMessageService helpMessageService;
	
	@ModelAttribute
	public HelpMessage get(@RequestParam(required=false) String id) {
		HelpMessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = helpMessageService.get(id);
		}
		if (entity == null){
			entity = new HelpMessage();
		}
		return entity;
	}
	
	@RequiresPermissions("helpmessage:helpMessage:view")
	@RequestMapping(value = {"list", ""})
	public String list(HelpMessage helpMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HelpMessage> page = helpMessageService.findPage(new Page<HelpMessage>(request, response), helpMessage); 
		model.addAttribute("page", page);
		return "manager/helpmessage/helpMessageList";
	}

	@RequiresPermissions("helpmessage:helpMessage:view")
	@RequestMapping(value = "form")
	public String form(HelpMessage helpMessage, Model model) {
		model.addAttribute("helpMessage", helpMessage);
		return "manager/helpmessage/helpMessageForm";
	}

	@RequiresPermissions("helpmessage:helpMessage:edit")
	@RequestMapping(value = "save")
	public String save(HelpMessage helpMessage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, helpMessage)){
			return form(helpMessage, model);
		}
		helpMessageService.save(helpMessage);
		addMessage(redirectAttributes, "保存帮助信息成功");
		return "redirect:"+Global.getAdminPath()+"/helpmessage/helpMessage/?repage";
	}
	
	@RequiresPermissions("helpmessage:helpMessage:edit")
	@RequestMapping(value = "delete")
	public String delete(HelpMessage helpMessage, RedirectAttributes redirectAttributes) {
		helpMessageService.delete(helpMessage);
		addMessage(redirectAttributes, "删除帮助信息成功");
		return "redirect:"+Global.getAdminPath()+"/helpmessage/helpMessage/?repage";
	}

}
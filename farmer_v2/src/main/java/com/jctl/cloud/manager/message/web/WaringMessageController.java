/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.message.web;

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
import com.jctl.cloud.manager.message.entity.WaringMessage;
import com.jctl.cloud.manager.message.service.WaringMessageService;

/**
 * 报警信息Controller
 *
 * @author kay
 * @version 2017-03-07
 */
@Controller
@RequestMapping(value = "${adminPath}/message/waringMessage")
public class WaringMessageController extends BaseController {

    @Autowired
    private WaringMessageService waringMessageService;

    @ModelAttribute
    public WaringMessage get(@RequestParam(required = false) String id) {
        WaringMessage entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = waringMessageService.get(id);
        }
        if (entity == null) {
            entity = new WaringMessage();
        }
        return entity;
    }

    //	@RequiresPermissions("message:waringMessage:view")
    @RequestMapping(value = {"list", ""})
    public String list(WaringMessage waringMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            Page<WaringMessage> page = waringMessageService.findPage(new Page<WaringMessage>(request, response), waringMessage);
            model.addAttribute("page", page);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "manager/message/waringMessageList";
    }

    //	@RequiresPermissions("message:waringMessage:view")
    @RequestMapping(value = "form")
    public String form(WaringMessage waringMessage, Model model) {
        try{
            model.addAttribute("waringMessage", waringMessage);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "manager/message/waringMessageForm";
    }

    //	@RequiresPermissions("message:waringMessage:edit")
    @RequestMapping(value = "save")
    public String save(WaringMessage waringMessage, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, waringMessage)) {
            return form(waringMessage, model);
        }
        waringMessageService.save(waringMessage);
        addMessage(redirectAttributes, "保存报警信息成功");
        return "redirect:" + Global.getAdminPath() + "/message/waringMessage/?repage";
    }

    //	@RequiresPermissions("message:waringMessage:edit")
    @RequestMapping(value = "delete")
    public String delete(WaringMessage waringMessage, RedirectAttributes redirectAttributes) {
        waringMessageService.delete(waringMessage);
        addMessage(redirectAttributes, "删除报警信息成功");
        return "redirect:" + Global.getAdminPath() + "/message/waringMessage/?repage";
    }

}
/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.region.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jctl.cloud.utils.WeatherUtils;
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
import com.jctl.cloud.manager.region.entity.Region;
import com.jctl.cloud.manager.region.service.RegionService;

import java.util.Map;

/**
 * 地区Controller
 * @author Lewkay
 * @version 2017-03-17
 */
@Controller
@RequestMapping(value = "${adminPath}/region/region")
public class RegionController extends BaseController {

	@Autowired
	private RegionService regionService;
	
	@ModelAttribute
	public Region get(@RequestParam(required=false) String id) {
		Region entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = regionService.get(id);
		}
		if (entity == null){
			entity = new Region();
		}
		return entity;
	}
	
//	@RequiresPermissions("region:region:view")
	@RequestMapping(value = {"list", ""})
	public String list(Region region, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Region> page = regionService.findPage(new Page<Region>(request, response), region); 
		model.addAttribute("page", page);
		return "manager/region/regionList";
	}

//	@RequiresPermissions("region:region:view")
	@RequestMapping(value = "form")
	public String form(Region region, Model model) {
		model.addAttribute("region", region);
		return "manager/region/regionForm";
	}

//	@RequiresPermissions("region:region:edit")
	@RequestMapping(value = "save")
	public String save(Region region, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, region)){
			return form(region, model);
		}
		regionService.save(region);
		addMessage(redirectAttributes, "保存地区成功");
		return "redirect:"+Global.getAdminPath()+"/region/region/?repage";
	}
	
//	@RequiresPermissions("region:region:edit")
	@RequestMapping(value = "delete")
	public String delete(Region region, RedirectAttributes redirectAttributes) {
		regionService.delete(region);
		addMessage(redirectAttributes, "删除地区成功");
		return "redirect:"+Global.getAdminPath()+"/region/region/?repage";
	}


	@RequestMapping(value = "getWeather")
	public Map get(Region region){
		return WeatherUtils.getWeather(region.getCitycode());
	}

}
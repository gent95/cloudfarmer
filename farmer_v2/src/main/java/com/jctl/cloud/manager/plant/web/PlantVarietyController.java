/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.plant.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.jctl.cloud.manager.plant.entity.PlantVariety;
import com.jctl.cloud.manager.plant.service.PlantVarietyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作物管理Controller
 * @author ll
 * @version 2017-02-25
 */
@Controller
@RequestMapping(value = "${adminPath}/plant/plantVariety")
public class PlantVarietyController extends BaseController {

	@Autowired
	private PlantVarietyService plantVarietyService;
	
	@ModelAttribute
	public PlantVariety get(@RequestParam(required=false) String id) {
		PlantVariety entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = plantVarietyService.get(id);
		}
		if (entity == null){
			entity = new PlantVariety();
		}
		return entity;
	}
	
	@RequiresPermissions("plant:plantVariety:view")
	@RequestMapping(value = {"list", ""})
	public String list(PlantVariety plantVariety, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PlantVariety> page = plantVarietyService.findPage(new Page<PlantVariety>(request, response), plantVariety); 
		model.addAttribute("page", page);
		return "manager/plant/plantVarietyList";
	}

	@RequiresPermissions("plant:plantVariety:view")
	@RequestMapping(value = "form")
	public String form(PlantVariety plantVariety, Model model) {
		model.addAttribute("plantVariety", plantVariety);
		return "manager/plant/plantVarietyForm";
	}

	@RequiresPermissions("plant:plantVariety:edit")
	@RequestMapping(value = "save")
	public String save(PlantVariety plantVariety, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, plantVariety)){
			return form(plantVariety, model);
		}
		plantVarietyService.save(plantVariety);
		addMessage(redirectAttributes, "保存作物成功");
		return "redirect:"+Global.getAdminPath()+"/plant/plantVariety/?repage";
	}
	
	@RequiresPermissions("plant:plantVariety:edit")
	@RequestMapping(value = "delete")
	public String delete(PlantVariety plantVariety, RedirectAttributes redirectAttributes) {
		plantVarietyService.delete(plantVariety);
		addMessage(redirectAttributes, "删除作物成功");
		return "redirect:"+Global.getAdminPath()+"/plant/plantVariety/?repage";
	}
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping("treeDate")
	public List<Map<Object,Object>> treeData(HttpServletResponse response){
		List<Map<Object,Object>> mapList=new ArrayList<Map<Object,Object>>();
		List<PlantVariety> list=plantVarietyService.findList(new PlantVariety());
		for(int i=0;i<list.size();i++){
			PlantVariety p=list.get(i);
			Map<Object,Object> map= Maps.newHashMap();
			map.put("id",p.getId());
			map.put("name", StringUtils.replace(p.getName(), " ", ""));
			mapList.add(map);
		}
		return  mapList;
	}

}
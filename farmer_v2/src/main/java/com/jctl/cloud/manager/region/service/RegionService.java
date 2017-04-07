/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.region.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.region.entity.Region;
import com.jctl.cloud.manager.region.dao.RegionDao;

/**
 * 地区Service
 * @author Lewkay
 * @version 2017-03-17
 */
@Service
@Transactional(readOnly = true)
public class RegionService extends CrudService<RegionDao, Region> {

	@Autowired
	private RegionDao regionDao;

	public Region get(String id) {
		return super.get(id);
	}
	
	public List<Region> findList(Region region) {
		return super.findList(region);
	}
	
	public Page<Region> findPage(Page<Region> page, Region region) {
		return super.findPage(page, region);
	}
	
	@Transactional(readOnly = false)
	public void save(Region region) {
		super.save(region);
	}
	
	@Transactional(readOnly = false)
	public void delete(Region region) {
		super.delete(region);
	}

    public Region selectByName(Region search) {
		return regionDao.selectByName(search);
    }
}
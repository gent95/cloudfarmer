/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.farmerland.service;

import java.util.List;

import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.farmerland.entity.Farmland;
import com.jctl.cloud.manager.farmerland.dao.FarmlandDao;

/**
 * 农田(大棚)Service
 *
 * @author kay
 * @version 2017-02-25
 */
@Service
@Transactional(readOnly = true)
public class FarmlandService extends CrudService<FarmlandDao, Farmland> {
    @Autowired
    private FarmlandDao farmlandDao;

    public Farmland get(String id) {
        return super.get(id);
    }

    public List<Farmland> findList(Farmland farmland) {
        return super.findList(farmland);
    }

    public Page<Farmland> findPage(Page<Farmland> page, Farmland farmland) {
        return super.findPage(page, farmland);
    }
    @Transactional(readOnly = false)
    public void updateFarmland(Farmland farmland){
        farmlandDao.updateFarmland(farmland);
    }
    @Transactional(readOnly = false)
    public void save(Farmland farmland) {
        if (farmland.getId() == null) {
            User user = UserUtils.getUser();
            farmland.setUser(user);
            farmland.setUsedId(user.getId());
        }
        super.save(farmland);
    }

    @Transactional(readOnly = false)
    public void updateById(Farmland farmland) {
        farmlandDao.updateById(farmland);
    }

    @Transactional(readOnly = false)
    public void delete(Farmland farmland) {
        super.delete(farmland);
    }

    public Integer findFarmerlandNumByFarmerId(String farmerId) {
        return farmlandDao.findFarmerlandNumByFarmerId(farmerId);
    }

}
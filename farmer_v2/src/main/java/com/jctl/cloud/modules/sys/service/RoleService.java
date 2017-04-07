package com.jctl.cloud.modules.sys.service;

import com.jctl.cloud.modules.sys.dao.RoleDao;
import com.jctl.cloud.modules.sys.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2017/3/23.
 */
@Service
@Transactional(readOnly = true)
public class RoleService {

    @Autowired
    private RoleDao roleDao;


    public Role findByEname(String eName) {


        Role search = new Role();
        search.setEnname(eName);
        return roleDao.getByEnname(search);
    }
}

/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.modules.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jctl.cloud.common.service.TreeService;
import com.jctl.cloud.modules.sys.entity.Area;
import com.jctl.cloud.modules.sys.entity.Office;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.modules.sys.dao.OfficeDao;
import com.jctl.cloud.modules.sys.utils.UserUtils;

/**
 * 机构Service
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

    @Autowired
    private UserService userService;

    @Autowired
    private SystemService systemService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private OfficeDao officeDao;

    public List<Office> findAll() {
        return UserUtils.getOfficeList();
    }

    public List<Office> findList(Boolean isAll) {
        if (isAll != null && isAll) {
            return UserUtils.getOfficeAllList();
        } else {
            return UserUtils.getOfficeList();
        }
    }

    @Transactional(readOnly = true)
    public List<Office> findList(Office office) {
        if (office != null) {
            office.setParentIds(office.getParentIds() + "%");
            return dao.findByParentIdsLike(office);
        }
        return new ArrayList<Office>();
    }
    @Transactional(readOnly = true)
    public List<Office> findAllList(Office office) {

            return officeDao.findAllList(office);

    }
    @Transactional(readOnly = true)
    public Office getOfficeName(Office office){
        return officeDao.getOfficeName(office);
    }

    @Transactional(readOnly = false)
    public void save(Office office) {
        super.save(office);
        UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
    }

    @Transactional(readOnly = false)
    public void delete(Office office) {
        super.delete(office);
        UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
    }

    @Transactional(readOnly = false)
    public void insert(Office office) {
        officeDao.insert(office);
    }

    public Integer findCount(Office search) {
        return officeDao.findCount(search);
    }

    @Transactional(readOnly = false)
    public String doRegister(User user) throws Exception {

        boolean of = verification(user.getMobile());
        if (of) {
            return "0";
        }
        Integer flag = userService.verification(user);
        if (flag == 404) {
            return "0";
        }
        //农场主创建专属公司  暂时已手机号命名
        Office office =getByType(1,null,user,null);
        insert(office);
        //创建部门（两个部门）
        Office farmerBoss =getByType(2,1,user,office);
        insert(farmerBoss);
        Office farmer = getByType(2,2,user,office);
        insert(farmer);
        //insert new User
        user.setCompany(office);
        user.setOffice(farmerBoss);
        //给权限
        Role role = roleService.findByEname("farmerBoss");
        user.setRole(role);
        user.preInsert();
        userService.insert(user);
        return "1";
    }


    private boolean verification(String mobile) {
        Office search = new Office();
        search.setName(mobile);
        Integer count = findCount(search);
        if (count >= 1) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param type  1公司 2 部门{1农场主  2 农户}
     * @return
     */
    private Office getByType(Integer type,Integer var1,User user,Office var2) {
        if (type == null) {
            return new Office();
        }

        if(type ==1){
            Office office=new Office();
            office.preInsert();
            office.setName(user.getCompanyName());
            office.setGrade("1");
            office.setType("1");
            office.setUseable("1");
            office.setDelFlag("0");
            office.setParent(new Office("0"));
            office.setParentIds("0,");
            office.setSort(10);
            office.setArea(new Area("1"));
            office.setCreateDate(new Date());
            office.setCreateBy(new User("1"));
            office.setUpdateBy(new User("1"));
            return office;
        }
        if(type ==2){
            if(var1 ==null){
                return new Office();
            }
            if(var1 ==1){
                Office farmerBoss=  new Office();
                farmerBoss.preInsert();
                farmerBoss.setName("农场主");
                farmerBoss.setGrade("1");
                farmerBoss.setType("2");
                farmerBoss.setUseable("1");
                farmerBoss.setDelFlag("0");
                farmerBoss.setParent(new Office(var2.getId()));
                farmerBoss.setParentIds("0," + var2.getId());
                farmerBoss.setArea(new Area("1"));
                farmerBoss.setCreateDate(new Date());
                farmerBoss.setSort(20);
                farmerBoss.setCreateBy(new User("1"));
                farmerBoss.setUpdateBy(new User("1"));
                return farmerBoss;
            }
            if(var1 ==2){
                Office farmer = new Office();
                farmer.preInsert();
                farmer.setName("农户");
                farmer.setGrade("1");
                farmer.setType("2");
                farmer.setUseable("1");
                farmer.setDelFlag("0");
                farmer.setParent(new Office(var2.getId()));
                farmer.setParentIds("0," + var2.getId());
                farmer.setArea(new Area("1"));
                farmer.setCreateDate(new Date());
                farmer.setSort(30);
                farmer.setCreateBy(new User("1"));
                farmer.setUpdateBy(new User("1"));
                return farmer;
            }
        }
        return new Office();
    }
}

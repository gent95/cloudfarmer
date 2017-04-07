package com.jctl.cloud.modules.sys.service;

import com.jctl.cloud.api.register.RegisterThread;
import com.jctl.cloud.modules.sys.dao.UserDao;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by LewKay on 2017/3/23.
 */

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getByLoginName(User loginUser) {
        return userDao.getByLoginName(loginUser);
    }

    public int verification(User user) {

        User search = UserUtils.getByLoginName(user.getLoginName());
        if (search != null) {
            return 404;
        }
        return 1;
    }

    public Integer insert(User user) {
        user.setPassword(SystemService.entryptPassword(user.getPassword()));

//        userDao.insertUserRole(user);
        new RegisterThread(user.getMobile(), user.getLoginName()).start();
        return userDao.insert(user);
    }


    @Transactional(readOnly = false)
    public void insertUserAndRole(User user) {
        userDao.insertUserAndRole(user);
    }

    public void save(User user) {
        userDao.update(user);
    }

    public List<User> getByCompany(User user) {
        return userDao.getByCompany(user);
    }
}

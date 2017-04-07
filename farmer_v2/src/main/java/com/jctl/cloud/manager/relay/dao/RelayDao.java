/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.relay.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.relay.entity.Relay;

import java.util.List;

/**
 * 中继管理DAO接口
 * @author ll
 * @version 2017-02-25
 */
@MyBatisDao
public interface RelayDao extends CrudDao<Relay> {

    Relay getByMac(String serverMac);

    List<Relay> findRelayByNum(Relay relay);

    Integer findRelayNumByFarmerId(String farmerId);

    List<Node> selectAllNodeByUserId();
    Relay findFarmerByRelayNum(String relayNum);

    List<Relay> findListByUser(Relay relay);
}
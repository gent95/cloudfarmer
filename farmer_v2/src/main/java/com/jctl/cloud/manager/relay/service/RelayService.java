/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.relay.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jctl.cloud.manager.farmer.service.FarmerService;
import com.jctl.cloud.manager.message.entity.WaringMessage;
import com.jctl.cloud.manager.message.service.WaringMessageService;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.node.thread.NodeSaveThread;
import com.jctl.cloud.manager.nodedatadetails.entity.NodeDataDetails;
import com.jctl.cloud.manager.nodedatadetails.service.NodeDataDetailsService;
import com.jctl.cloud.manager.waring.entity.WaringCycle;
import com.jctl.cloud.manager.waring.service.WaringCycleService;
import com.jctl.cloud.mina.entity.DataResultSet;
import com.jctl.cloud.mina.entity.GatewayResultSet;
import com.jctl.cloud.mina.entity.OpenCloseVO;
import com.jctl.cloud.mina.entity.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.relay.dao.RelayDao;

/**
 * 中继管理Service
 *
 * @author ll
 * @version 2017-02-25
 */
@Service
@Transactional(readOnly = true)
public class RelayService extends CrudService<RelayDao, Relay> {

    @Autowired
    private RelayDao relayDao;

    @Autowired
    private NodeService nodeService;

    @Autowired
    private WaringMessageService waringMessageService;

    @Autowired
    private WaringCycleService waringCycleService;

    @Autowired
    private NodeDataDetailsService nodeDataDetailsService;
    @Autowired
    private FarmerService farmerService;


    private final String message_code = "尊敬的用户您好，当前检测到设备";

    public Relay get(String id) {
        return super.get(id);
    }

    public List<Relay> findList(Relay relay) {
        return super.findList(relay);
    }

    public Page<Relay> findPage(Page<Relay> page, Relay relay) {
        return super.findPage(page, relay);
    }

    @Transactional(readOnly = false)
    public void save(Relay relay) {
        super.save(relay);
    }

    @Transactional(readOnly = false)
    public void delete(Relay relay) {
        super.delete(relay);
    }


    //增加自定义aop
    @Transactional(readOnly = false)
    public void saveOrUpdate(ResultSet resultSet) {
        //节点详情
        DataResultSet dataResultSet = resultSet.getDataResultSet();
        //中继挂载详情
        GatewayResultSet gatewayResultSet = resultSet.getGatewayResultSet();
        //开闭结果
        OpenCloseVO openCloseFalg = resultSet.getOpenCloseVo();
        String relayId;

        //修改节点开闭状态
        if (openCloseFalg != null) {
            String status = openCloseFalg.getOpenOrClose();
            Node node = nodeService.getByNodeMac(openCloseFalg.getNodeMac());
            if (status.equals("0000")) {
                node.setOpenFlag("0");
            }
            if (status.equals("0001")) {
                node.setOpenFlag("1");
            }
            nodeService.save(node);
        }

        //新增当前节点详情
        if (dataResultSet != null) {
            NodeDataDetails nodeData = new NodeDataDetails(new Date(), dataResultSet.getClientMac());

            //大气温度
            if (dataResultSet.getAirTemperature() != null) {
                nodeData.setAirTemperature(dataResultSet.getAirTemperature());
            }
            // 大气湿度
            if (dataResultSet.getAirHumidity() != null) {
                nodeData.setAirHumidity(dataResultSet.getAirHumidity());
            }

            // 1号菌棒温度
            if (dataResultSet.getSoilTemperature1() != null) {
                nodeData.setSoilTemperature1(dataResultSet.getSoilTemperature1());
            }
            // 1号菌棒湿度
            if (dataResultSet.getSoilHumidity1() != null) {
                nodeData.setSoilHumidity1(dataResultSet.getSoilHumidity1());
            }
            // 2号菌棒温度
            if (dataResultSet.getSoilTemperature2() != null) {
                nodeData.setSoilTemperature2(dataResultSet.getSoilTemperature2());
            }
            // 2号菌棒湿度
            if (dataResultSet.getSoilHumidity2() != null) {
                nodeData.setSoilHumidity2(dataResultSet.getSoilHumidity2());
            }
            // 3号菌棒温度
            if (dataResultSet.getSoilTemperature3() != null) {
                nodeData.setSoilTemperature3(dataResultSet.getSoilTemperature3());
            }
            // 3号菌棒湿度
            if (dataResultSet.getSoilHumidity3() != null) {
                nodeData.setSoilHumidity3(dataResultSet.getSoilHumidity3());
            }
            // 二氧化碳
            if (dataResultSet.getCo2() != null) {
                nodeData.setCo2(dataResultSet.getCo2());
            }
            //开关状态
            if (dataResultSet.getIsOpen() != null) {
                nodeData.setOpenFlag(dataResultSet.getIsOpen().toString());
            }
            //电池电量
            if (dataResultSet.getPowerSupply() != null) {
                nodeData.setPower(dataResultSet.getPowerSupply());
            }
            //切换频点
            if (dataResultSet.getFrequencyPoint() != null) {
                nodeData.setFrequencyPoint(dataResultSet.getFrequencyPoint());
            }
            //电电量池
            if (dataResultSet.getPower() != null) {
                nodeData.setPowerSupply(dataResultSet.getPower());
            }
//            sendWaringMessage(nodeData);
            nodeDataDetailsService.save(nodeData);
        }

        //新曾或修改当前中继和节点信息
        if (gatewayResultSet != null) {
            String serverMac = gatewayResultSet.getServerMac();
            String powerSupply = gatewayResultSet.getPowerSupply();
            Relay temp = new Relay();
            Relay relay = getByMac(serverMac);
            if (relay == null) {
//                relayId = relay.getId();
                temp.setPowerSupply(powerSupply);
                temp.setRelayNum(serverMac);
                temp.setAddTime(new Date());
                temp.setDelFlag("1");
                save(temp);
            } else {
                relay.setPowerSupply(powerSupply);
                relay.setUpdateTime(new Date());
                save(relay);
            }
            //绑定节点信息
            //启动一个线程去保存
            new NodeSaveThread(resultSet, serverMac).start();
//            nodeService.saveOrUpdate(resultSet,serverMac);
        }

    }

    public Relay getByMac(String serverMac) {

        return relayDao.getByMac(serverMac);
    }

    public List<Relay> findRelayByNum(Relay relay) {
        return relayDao.findRelayByNum(relay);
    }

    public Integer findRelayNumByFarmerId(String farmerId) {
        return relayDao.findRelayNumByFarmerId(farmerId);
    }


    public void sendWaringMessage(NodeDataDetails nodeData) {
        WaringCycle search = new WaringCycle();
        search.setNodeNum(nodeData.getNodeMac());
        List<WaringCycle> list = waringCycleService.findList(search);
        if (list != null && list.size() > 0) {
            for (WaringCycle waringCycle : list) {
                try {
                    //大气温度
                    if (waringCycle.getProperty().equals("airTemperature")) {
                        if (nodeData.getAirTemperature() > waringCycle.getMax()) {
                            waringMessageService.save(getWaringMessage(nodeData, "大气温度", 1));
                            if (nodeData.getAirTemperature() < waringCycle.getMin()) {
                                waringMessageService.save(getWaringMessage(nodeData, "大气温度", 0));
                            }
                        }
                    }
                    //大气湿度
                    if (waringCycle.getProperty().equals("airHumidity")) {
                        if (nodeData.getAirTemperature() > waringCycle.getMax()) {
                            waringMessageService.save(getWaringMessage(nodeData, "大气湿度", 1));
                            if (nodeData.getAirTemperature() < waringCycle.getMin()) {
                                waringMessageService.save(getWaringMessage(nodeData, "大气湿度", 0));
                            }
                        }
                    }

                    //1号菌棒温度
                    if (waringCycle.getProperty().equals("soilHumidity1")) {
                        if (nodeData.getAirTemperature() > waringCycle.getMax()) {
                            waringMessageService.save(getWaringMessage(nodeData, "1号菌棒温度", 1));
                            if (nodeData.getAirTemperature() < waringCycle.getMin()) {
                                waringMessageService.save(getWaringMessage(nodeData, "1号菌棒温度", 0));
                            }
                        }
                    }

                    //1号菌棒湿度
                    if (waringCycle.getProperty().equals("soilTemperature1")) {
                        if (nodeData.getAirTemperature() > waringCycle.getMax()) {
                            waringMessageService.save(getWaringMessage(nodeData, "1号菌棒湿度", 1));
                            if (nodeData.getAirTemperature() < waringCycle.getMin()) {
                                waringMessageService.save(getWaringMessage(nodeData, "1号菌棒湿度", 0));
                            }
                        }
                    }
                    //2号菌棒温度
                    if (waringCycle.getProperty().equals("soilHumidity2")) {
                        if (nodeData.getAirTemperature() > waringCycle.getMax()) {
                            waringMessageService.save(getWaringMessage(nodeData, "2号菌棒温度", 1));
                            if (nodeData.getAirTemperature() < waringCycle.getMin()) {
                                waringMessageService.save(getWaringMessage(nodeData, "2号菌棒温度", 0));
                            }
                        }
                    }
                    //2号菌棒湿度
                    if (waringCycle.getProperty().equals("soilTemperature2")) {
                        if (nodeData.getAirTemperature() > waringCycle.getMax()) {
                            waringMessageService.save(getWaringMessage(nodeData, "2号菌棒湿度", 1));
                            if (nodeData.getAirTemperature() < waringCycle.getMin()) {
                                waringMessageService.save(getWaringMessage(nodeData, "2号菌棒湿度", 0));
                            }
                        }
                    }
                    //3号菌棒温度
                    if (waringCycle.getProperty().equals("soilHumidity3")) {
                        if (nodeData.getAirTemperature() > waringCycle.getMax()) {
                            waringMessageService.save(getWaringMessage(nodeData, "大气湿度", 1));
                            if (nodeData.getAirTemperature() < waringCycle.getMin()) {
                                waringMessageService.save(getWaringMessage(nodeData, "大气湿度", 0));
                            }
                        }
                    }
                    //3号菌棒湿度
                    if (waringCycle.getProperty().equals("soilTemperature3")) {
                        if (nodeData.getAirTemperature() > waringCycle.getMax()) {
                            waringMessageService.save(getWaringMessage(nodeData, "3号菌棒湿度", 1));
                            if (nodeData.getAirTemperature() < waringCycle.getMin()) {
                                waringMessageService.save(getWaringMessage(nodeData, "3号菌棒湿度", 0));
                            }
                        }
                    }
                    //二氧化碳浓度
                    if (waringCycle.getProperty().equals("co2")) {
                        if (nodeData.getAirTemperature() > waringCycle.getMax()) {
                            waringMessageService.save(getWaringMessage(nodeData, "二氧化碳浓度", 1));
                            if (nodeData.getAirTemperature() < waringCycle.getMin()) {
                                waringMessageService.save(getWaringMessage(nodeData, "二氧化碳浓度", 0));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public WaringMessage getWaringMessage(NodeDataDetails nodeDataDetails, String str, Integer status) {
        WaringMessage message = new WaringMessage();
        message.setCreateDate(new Date());
        if (status == 0) {
            message.setNodeNum(nodeDataDetails.getNodeMac());
            message.setMessage(message_code + str + "低于预警值!请及时查看处理。");
        } else {
            message.setNodeNum(nodeDataDetails.getNodeMac());
            message.setMessage(message_code + str + "大于预警值!请及时查看处理。");
        }
        return message;
    }

    public Relay findFarmerByRelayNum(String relayNum) {
        return relayDao.findFarmerByRelayNum(relayNum);
    }

    public List<Node> selectAllNodeByUserId(String id) {
        return relayDao.selectAllNodeByUserId();
    }

    public List<Relay> findListByUser(Relay relay) {
        List<Relay> list = relayDao.findListByUser(relay);
        for (Relay temp: list) {
            Integer nodeNum = nodeService.getNodeNum(temp.getId());
            temp.setNodeNum(nodeNum);
            if(temp.getFarmerId() != null){
                temp.setFarmerName(farmerService.get(temp.getFarmerId().toString()).getName());
            }else {
                temp.setFarmerName("暂未绑定到农场！");
            }
//
        }
        return list;
    }
}
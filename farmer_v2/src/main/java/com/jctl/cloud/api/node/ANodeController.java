package com.jctl.cloud.api.node;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.common.utils.excel.annotation.ExcelField;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.nodedatadetails.entity.NodeDataDetails;
import com.jctl.cloud.manager.nodedatadetails.service.NodeDataDetailsService;
import com.jctl.cloud.manager.timingstrategy.entity.NodeCollectionCycle;
import com.jctl.cloud.manager.timingstrategy.service.NodeCollectionCycleService;
import com.jctl.cloud.manager.waring.entity.WaringCycle;
import com.jctl.cloud.manager.waring.service.WaringCycleService;
import com.jctl.cloud.modules.sys.entity.Role;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.modules.sys.utils.UserUtils;
import com.jctl.cloud.utils.NodeControlUtil;
import com.jctl.cloud.utils.QutarzUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/27.
 */
@Controller
@RequestMapping("node")
public class ANodeController {
    @Autowired
  NodeControlUtil nodeControlUtil ;
@Autowired
private NodeService nodeService;
@Autowired
private NodeDataDetailsService nodeDataDetailsService;
@Autowired
private WaringCycleService waringCycleService;
@Autowired
private NodeCollectionCycleService nodeCollectionCycleService;

    /**
     * 节点详情列表
     * @param nodeDataDetails
     * @return
     */
    @RequestMapping("detailList")
    @ResponseBody
    public Map listNodeDetail(NodeDataDetails nodeDataDetails){
    Map result= Maps.newHashMap();
    List lists=new ArrayList<>();
 try {
        List<NodeDataDetails> nodeDataDetailsLists=nodeDataDetailsService.findList(nodeDataDetails);
        if (nodeDataDetailsLists != null||nodeDataDetailsLists.size()>0) {
        for(NodeDataDetails dataDetails:nodeDataDetailsLists ){
            String[] proper=new String[]{"nodeMac","airTemperature","airHumidity","co2","openFlag","power","addTime"};
            Map maps=Maps.newHashMap();
            for(String property:proper){
                maps.put(property, Reflections.invokeGetter(dataDetails,property));
            }
            lists.add(maps);
        }
            result.put("flag","1");
            result.put("info",lists);
        } else {
            result.put("flag", "0");
            result.put("msg", "抱歉未查询到数据");
        }
    }catch (Exception e){
        result.put("flag","0");
        result.put("msg","操作失败");
    }
    return result;
    }

    /**
     * 节点管理
     */
    @RequestMapping("list")
    @ResponseBody
    public Map nodeList(Node node,String userId){
        Map result=Maps.newHashMap();
        List lists=new ArrayList();
        try {
            List<Node> nodeList = nodeService.findList(node);
            boolean isAdmin = User.isAdmin(userId);
            if (!isAdmin) {
                List<Role> list = UserUtils.getRoleList();
                for (Role role : list) {
                    if (role.getEnname().equals("farmerBoss")) {
                        node.setUser(UserUtils.get(userId));
                    }
                    if (role.getEnname().equals("farmerWork")) {
                        node.setUsedId(userId);
                    }
                }
            }
            if (nodeList != null||nodeList.size()>0) {
                String[] proper=new String[]{"id","nodeNum","farmlandName","usedName","power"};
                for(Node no:nodeList){
                    Map maps=Maps.newHashMap();
                    for(String property:proper){
                        maps.put(property,Reflections.invokeGetter(no,property));
                    }
                    lists.add(maps);
                }
                result.put("flag",1);
                result.put("info",lists);
            } else {
                result.put("flag", 0);
                result.put("msg", "抱歉未查询到数据");
            }
        }catch (Exception e){
            result.put("flag",0);
            result.put("msg","操作失败");
            e.printStackTrace();
        }
    return result;
    }
    /**
     * 节点详情
     */
    @RequestMapping("get")
    @ResponseBody
    public Map getNode(String id,String status){
        Map result=Maps.newHashMap();
        try {
            Node no = nodeService.get(id);
                String[] proper = new String[]{"id", "nodeNum", "type", "user.name", "usedName", "relayName", "cycle", "addTime", "farmlandName", "power"};
                Map maps = Maps.newHashMap();
                for (String property : proper) {
                    maps.put(property, Reflections.invokeGetter(no, property));
                }
                List lists = new ArrayList();
                lists.add(maps);
                result.put("flag", 1);
                result.put("info", lists);

        }catch (Exception e){
            result.put("flag",0);
            result.put("msg","操作失败");
            e.printStackTrace();
        }
        return  result;

    }
    @RequestMapping("update")
    @ResponseBody
    public Map updateNode(Node node, HttpServletRequest request){
        Map result=Maps.newHashMap();
        try {
            if(node.getId()!=null&&node.getId()!="") {
                nodeService.save(node);
                result.put("flag", 1);
                result.put("msg", "修改成功");
            }
        }catch (Exception e){
            result.put("flag",0);
            result.put("msg","修改失败");
        }
        return  result;
    }

    /**
     * 开关控制
     * @param nodeMac
     * @param status
     * @return
     */
    @RequestMapping("HandOpenClose")
    @ResponseBody
    public Map HandOpenClose(String nodeMac,String status){
        Node node=nodeService.getByNodeMac(nodeMac);
        Map result=Maps.newHashMap();
        try {
            if (status.equals(0)) {
                //执行关闭命令
                nodeControlUtil.closeNode(node);
                result.put("msg","成功关闭");
            }
            if (status.equals(1)) {
                nodeControlUtil.openNode(node);
                result.put("msg","成功打开");
            }
            result.put("flag","1");

        }catch (Exception e){
            result.put("flag","0");
            result.put("msg","操作失败");
        }
        return result;
    }

    /**
     * 周期控制
     * @return
     */
    @RequestMapping("cycleMsg")
    @ResponseBody
    public  Map cycle(NodeCollectionCycle nodeCollectionCycle,String[] off,String[] on){
        Map result=Maps.newHashMap();
        try {
            nodeCollectionCycleService.deleteByNodeId(nodeCollectionCycle);
            nodeCollectionCycleService.deleteByNodeId(nodeCollectionCycle);
            nodeCollectionCycle.setAddUserId(Long.parseLong(UserUtils.getUser().getId()));
            nodeCollectionCycle.setCycleTime(0 + " " + nodeCollectionCycle.getCycleTime() + " * * * ?");
            nodeCollectionCycle.setCycleOn(QutarzUtil.dateConvertQutarzFormate(nodeCollectionCycle.getCycleOn()));
            nodeCollectionCycle.setCycleOff(QutarzUtil.dateConvertQutarzFormate(nodeCollectionCycle.getCycleOff(), off));
            nodeCollectionCycleService.save(nodeCollectionCycle);
            //启动定时器
            QutarzUtil.start();
            result.put("flag",1);
            result.put("msg","设置成功");
        }catch (Exception e){
            result.put("flag",0);
            result.put("msg","操作失败");
        }
        return result;
    }

    /**
     * 智能控制
     * @return
     */
    @RequestMapping("autoMsg")
    @ResponseBody
    public Map autoNode(WaringCycle waringCycle){
        Map result=Maps.newHashMap();
        try {
            waringCycleService.save(waringCycle);
            result.put("flag",1);
            result.put("msg","设置成功");
        }catch (Exception e){
            result.put("flag",0);
            result.put("msg","操作失败");
            e.printStackTrace();
        }
        return result;
    }
}

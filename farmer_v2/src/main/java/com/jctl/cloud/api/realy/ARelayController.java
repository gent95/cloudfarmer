package com.jctl.cloud.api.realy;

import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.relay.service.RelayService;
import com.jctl.cloud.modules.sys.entity.User;
import com.jctl.cloud.utils.FrontUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LewKay on 2017/4/5.
 */

@Controller
@RequestMapping("relay")
public class ARelayController {


    @Autowired
    private RelayService relayService;

    @Autowired
    private NodeService nodeService;


    @RequestMapping("list")
    @ResponseBody
    public Map list(Relay relay, Model model) {
        Map result = new HashMap();
        List data = new ArrayList();
        try {
            User user = FrontUserUtils.getUser();
            if (user == null) {
                result.put("flag", 0);
                result.put("msg", "请先登陆！");
                return result;
            }
            relay.setUser(user);

            List<Relay> list = relayService.findListByUser(relay);
            String[] propertys = new String[]{"id","relayNum", "farmerName", "nodeNum", "powerSupply"};

            for (Relay relay1 : list) {
                Map map = new HashMap();
                for (String property : propertys) {
                    map.put(property, Reflections.invokeGetter(relay1, property));
                }
                data.add(map);
            }
            result.put("flag", 1);
            result.put("data", data);
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败！");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("info")
    @ResponseBody
    public Map info(Relay relay, Model model) {
        Map result = new HashMap();
        try {
            Relay relay1=relayService.get(relay);
            relay1.setNodeNum(nodeService.getNodeNum(relay1.getId()));
            result.put("flag", 1);
            result.put("data", relay1);

        }catch (Exception e){
            result.put("flag", 0);
            result.put("msg", "操作失败！");
            e.printStackTrace();
        }
        return result;
    }



}

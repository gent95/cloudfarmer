package com.jctl.cloud.api.nodeController;

import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.region.service.RegionService;
import com.jctl.cloud.utils.NodeControlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gent on 2017/2/27.
 */
@Controller
@RequestMapping("node")
public class ApiNodeController {

    @Autowired
    private NodeControlUtil nodeControlUtil;

    @Autowired
    private NodeService nodeService;


    @Autowired
    private RegionService regionService;

    /**
     * 关闭节点
     *
     * @param node
     * @return
     */
    @RequestMapping("closeNode")
    @ResponseBody
    public Map<String, Object> closeNode(String mm) {
//        List<Node> list = nodeService.findList(new Node());

        Map<String, Object> result = new HashMap();
//        try {
//            nodeControlUtil.closeNode(node);
//            result.put("status", 1);
//            result.put("msg", "操作成功");
//        } catch (Exception e) {
//            result.put("status", 0);
//            result.put("msg", "操作失败");
//            e.printStackTrace();
//        }
        return result;
    }

    /**
     * 打开节点
     *
     * @param node
     * @return
     */
    @RequestMapping("openNode")
    @ResponseBody
    public Map<String, Object> openNode(Node node) {
        Map<String, Object> result = new HashMap();
        try {
            nodeControlUtil.openNode(node);
            result.put("status", 1);
            result.put("msg", "操作成功");
        } catch (Exception e) {
            result.put("status", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }
}

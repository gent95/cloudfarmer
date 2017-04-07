package com.jctl.cloud.manager.console;

import com.jctl.cloud.common.utils.DateUtils;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.utils.NodeControlUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.Date;

/**
 * Created by gent on 2017/3/2.
 */
public class NodeConsole implements Job {
    private NodeControlUtil nodeControlUtil = new NodeControlUtil();
    public void execute(JobExecutionContext context){
        String nodeNumAndIndex = context.getTrigger().getName();
        String index = nodeNumAndIndex.substring(nodeNumAndIndex.length()-1);
        String nodeId = nodeNumAndIndex.substring(0,nodeNumAndIndex.length()-1);
        Node node = new Node();
        node.setId(nodeId);
        DateUtils dateUtils = new DateUtils();
        String dateTime = dateUtils.formatDateTime(new Date());
        try {
            if (index.equals("0")){
                nodeControlUtil.refreshNodeByNodeId(node);
                System.out.print(dateTime+"执行了获取"+node.getNodeNum()+"节点信息命令");
            }else if(index.equals("1")){
                nodeControlUtil.openNode(node);
                System.out.print(dateTime+"执行了开启"+node.getNodeNum()+"节点命令");
            }else if(index.equals("2")){
                nodeControlUtil.closeNode(node);
                System.out.print(dateTime+"执行了关闭"+node.getNodeNum()+"节点命令");
            }else{
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

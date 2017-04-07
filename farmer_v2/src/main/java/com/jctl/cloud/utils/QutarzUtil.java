package com.jctl.cloud.utils;

import com.jctl.cloud.common.utils.SpringContextHolder;
import com.jctl.cloud.manager.console.NodeConsole;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.timingstrategy.entity.NodeCollectionCycle;
import com.jctl.cloud.manager.timingstrategy.service.NodeCollectionCycleService;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Iterator;
import java.util.List;

/**
 * @author gent
 * @used 多任务动态定时
 * @date 2017年2月27日
 */
public class QutarzUtil {
    private static NodeService nodeService = SpringContextHolder.getBean(NodeService.class);
    private static NodeCollectionCycleService nodeCollectionCycleService = SpringContextHolder.getBean(NodeCollectionCycleService.class);
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    public static List<NodeCollectionCycle> getNclList(){
        NodeCollectionCycle nodeCollectionCycle = new NodeCollectionCycle();
        List<NodeCollectionCycle> list = nodeCollectionCycleService.findList(nodeCollectionCycle);
        return  list;
    }

    /**
     * 初始化定时器
     *
     * @throws Exception
     */
    public static void initJobTrigger() throws Exception {
        List<NodeCollectionCycle> list = getNclList();
        //开始搞事情
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobDetail jobDetail = null;
        CronTrigger cronTrigger = null;
        Iterator ite = list.iterator();
        while (ite.hasNext()) {
            //任务对象
            NodeCollectionCycle nodeCollectionCycle = (NodeCollectionCycle) ite.next();
            Node node = nodeService.getByNodeNum(nodeCollectionCycle.getNodeId());
            //定时表达式
            String cronExpression = nodeCollectionCycle.getCycleTime();

            if (node.getDelFlag().equals("1")){
                /**
                 * 循环策略
                 */
                //循环任务标识为"0"
                jobDetail = new JobDetail(node.getId() + "0", Scheduler.DEFAULT_GROUP, NodeConsole.class);
                cronTrigger = new CronTrigger(node.getId() + "0", Scheduler.DEFAULT_GROUP);
                //为触发器设置定时表达式
                cronTrigger.setCronExpression(cronExpression);
                //启动新增定时器任务
                scheduler.scheduleJob(jobDetail, cronTrigger);
                /**
                 * 开关策略
                 *
                 * 关闭:2 开启:1
                 */

                if (node.getOpenFlag()!="" && node.getOpenFlag() != null) {
                    if (node.getOpenFlag().equals("0")) {
                        cronExpression = nodeCollectionCycle.getCycleOn();
                        jobDetail = new JobDetail(node.getId() + "1", Scheduler.DEFAULT_GROUP, NodeConsole.class);
                        cronTrigger = new CronTrigger(node.getId() + "1", Scheduler.DEFAULT_GROUP);
                        //为触发器设置定时表达式
                        cronTrigger.setCronExpression(cronExpression);
                        //启动新增定时器任务
                        scheduler.scheduleJob(jobDetail, cronTrigger);
                    } else if (node.getOpenFlag().equals("1")) {
                        cronExpression = nodeCollectionCycle.getCycleOff();
                        jobDetail = new JobDetail(node.getId() + "2", Scheduler.DEFAULT_GROUP, NodeConsole.class);
                        cronTrigger = new CronTrigger(node.getId() + "2", Scheduler.DEFAULT_GROUP);
                        //为触发器设置定时表达式
                        cronTrigger.setCronExpression(cronExpression);
                        //启动新增定时器任务
                        scheduler.scheduleJob(jobDetail, cronTrigger);
                    }
                }
            }
        }
        //初始化任务只需要执行一次，执行一次后移除初始化触发器
        scheduler.unscheduleJob("InitTrigger", Scheduler.DEFAULT_GROUP);
    }

    public static void start() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            if (scheduler.isStarted()) {
                List<Node> nodeList = nodeService.findList(new Node());
                Iterator ite = nodeList.iterator();
                while (ite.hasNext()){
                    Node node = (Node)ite.next();
                    scheduler.deleteJob(node.getId()+"0",scheduler.DEFAULT_GROUP);
                    scheduler.deleteJob(node.getId()+"1",scheduler.DEFAULT_GROUP);
                    scheduler.deleteJob(node.getId()+"2",scheduler.DEFAULT_GROUP);
                }
                scheduler.standby();
            }
            initJobTrigger();
            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shutdown(){
        try{
            Scheduler scheduler = schedulerFactory.getScheduler();
            if (scheduler.isStarted()){
                scheduler.shutdown();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 将时间字符串转换成qutarz可识别的通配符字符串
     *
     * @param time  时间
     * @param weeks 周*,此处为可变参数可传入多个值
     * @return qutarz统配符字符串
     */
    public static String dateConvertQutarzFormate(String time, String[] weeks) {
        if (!time.equals(null) || !time.equals("")) {
            String[] t = time.split(":");
            String hour = t[0] + " ";
            String min = t[1] + " ";
            if (min.toCharArray()[0] == 0) {
                min = min.toCharArray()[1]+"";
            }
            StringBuffer sb = new StringBuffer();
            sb.append("* ");
            sb.append(min);
            sb.append(hour);
            sb.append("* ");
            if (!weeks.equals(null) || !weeks.equals("")) for (int i = 0; i <= weeks.length - 1; i++) {
                sb.append(weeks[i]);
                if (i == weeks.length - 1) {
                    sb.append(" ");
                } else {
                    sb.append(",");
                }
            }
            else {
                sb.append("*");
            }

            sb.append("?");
            return sb.toString();
        }
        return null;

    }

    /**
     * 将普通日期格式转换成qutarz通配符
     * @param time
     * @return
     */
    public static String dateConvertQutarzFormate(String time) {
        if (!time.equals(null) || !time.equals("")) {
            String[] t = time.split(":");
            String hour = t[0] + " ";
            String min = t[1] + " ";
            StringBuffer sb = new StringBuffer();
            sb.append("* ");
            sb.append(min);
            sb.append(hour);
            sb.append("* ");
            sb.append("* ");
            sb.append("?");
            return sb.toString();
        }
        return null;

    }

    /**
     * 将qutarz通配符字符串转换成通用日期格式 例如 :0 50 17 * * 1,2,3 ?
     *
     * @param qutarzStr 要转换的qutarz通配符字符串
     * @return 通用日期字符串 周一,周二,周三/17:50
     */
    public static String qutarzStrConvertDate(String qutarzStr) {
        String[] strings = qutarzStr.split(" ");
        StringBuffer date = new StringBuffer();
        String weeks = strings[5];
        String[] week = weeks.split(",");
        for (int i = 0; i < week.length; i++) {
            date.append("周");
            date.append(week[i]);
            if (i != week.length - 1) {
                date.append(",");
            } else {
                break;
            }
        }
        date.append("/");
        date.append(strings[2] + ":");
        date.append(strings[1]);
        return date.toString();
    }

    /**
     * 将qutarz通配符字符串转换成time时间 xx:xx:xx
     * @param qutarzStr
     * @return
     */
    public static String qutarzStrConvertTime(String qutarzStr) {
        StringBuffer sb = new StringBuffer();
        String[] strs = qutarzStr.split(" ");
        sb.append(strs[2]);
        sb.append(":");
        sb.append(strs[1]);
        sb.append(":");
        sb.append("00");
        return sb.toString();
    }

}

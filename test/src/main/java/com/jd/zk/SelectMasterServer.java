package com.jd.zk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Title: SelectMasterServer 对于多个server（worker），只允许一个执行，可以通过该类向zk注册服务，判断当前server实例是否是master，只有master可以执行
 * Description: SelectMasterServer  如果连不上ZK，不能启动。此事待解决
 *              通过zk选举出master。也可以通过配置文件指定固定ip作为master。
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/8/19
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class SelectMasterServer {

    private static Logger logger = LoggerFactory.getLogger(SelectMasterServer.class);

    private static String zkTopPath = "/aaa_selectMaster";

    //private static ZkClient zkClient = ZkClientUtil.getZkClient();

    public static Map<String, String> serverMasterId = new ConcurrentHashMap<String, String>();//server 对应的 masterId

    //private static Map<String ,IZkChildListener> ZkChildListeners = new HashMap<String, IZkChildListener>();//key 是 nodePath

    public static Map<String, String> MasterServerIds = new ConcurrentHashMap<String, String>();//key:taskName,value:serverId 在该map中的是通过配置文件指定的master

    public static Date CheckMasterDate = new Date();//检查master的时间点

    private static String serverId = SelectMasterServer.getServerID();
    private static String serverId2 = SelectMasterServer.getServerID();

    public static void main(String[] agrs) {
        try {
            logger.info("hello cc , serverId:" + serverId);
            final String serverName = "ccTask";
            SelectMasterServer.registerServer(serverName, serverId);


            ScheduledExecutorService sechduleCheck = Executors.newSingleThreadScheduledExecutor();//同一时刻只有一个任务在执行
            sechduleCheck.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    try {
                        logger.info("检查是否是master");
                        CheckMasterDate = new Date();//更新时间
                        boolean isMaster = SelectMasterServer.isMasterServer(serverName, serverId);
                        if (isMaster) {
                            logger.info("..................................");
                        } else {
                            logger.info(".");
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }

                }
            }, 5, 10, TimeUnit.SECONDS);


            ScheduledExecutorService sechduleCheck2 = Executors.newSingleThreadScheduledExecutor();//同一时刻只有一个任务在执行
            sechduleCheck2.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    try {
                        synchronized (CheckMasterDate) {
                            if ((new Date().getTime() - CheckMasterDate.getTime() > 60 * 1000)) {//如果超过一分钟没有去检查master，可能zk已经不可用了
                                logger.info("断开了。。。。。。" + CheckMasterDate);
                            }
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }, 5, 10, TimeUnit.SECONDS);


            SelectMasterServer.registerServer(serverName, serverId2);

            ScheduledExecutorService sechduleCheck3 = Executors.newSingleThreadScheduledExecutor();//同一时刻只有一个任务在执行
            sechduleCheck3.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    try {
                        logger.info("检查是否是master");
                        CheckMasterDate = new Date();//更新时间
                        boolean isMaster = SelectMasterServer.isMasterServer(serverName, serverId2);
                        if (isMaster) {
                            logger.info("..................................");
                        } else {
                            logger.info(".");
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }

                }
            }, 5, 10, TimeUnit.SECONDS);


        } catch (Exception e) {
            logger.info("cccccccccccccccccccccccccccccccccccccccccccccccccccc");
            logger.error(e.getMessage(), e);
        }
    }


    //如果在配置文件中指定 ccTask.masterIp=10.12.165.20 那么指定的ip就是master，不往zk注册
    public static void registerServer(String taskName, String serverId) {

        try {
            PropertyFactory propertyFactory = new PropertyFactory("worker.properties");
            String masterIp = (String) propertyFactory.getProperty(taskName + ".masterIp");

            if (masterIp != null && !masterIp.isEmpty()) {
                if (masterIp.equals(getLocalIp())) {//配置文件指定的ip与当前ip相同
                    synchronized (MasterServerIds) {
                        if(MasterServerIds.get(taskName) != ""){
                            MasterServerIds.put(taskName, serverId);//先注册先得master
                        }
                    }
                } else {
                    synchronized (MasterServerIds) {
                        MasterServerIds.put(taskName, "");
                    }
                }

                return;//返回，不向zk注册
            }

            String serverPath = zkTopPath + "/" + taskName;
            /*if (!zkClient.exists(serverPath)) {
                logger.info("不存在,创建：" + serverPath);
                zkClient.createPersistent(serverPath, null);
            }*/

            String nodePath = serverPath + "/" + serverId;
            //创建id的临时节点
            /*if (!zkClient.exists(nodePath)) {
                logger.info("不存在,创建：" + nodePath);
                zkClient.createEphemeral(nodePath, null);
            }*/
            //zkClient.create(zkPath,null, CreateMode.EPHEMERAL_SEQUENTIAL);//创建顺序节点，后面会自动加序列号

/*            IZkChildListener iZkChildListener = new IZkChildListener() {
                @Override
                public void handleChildChange(String path, List<String> list) throws Exception {//似乎没什么用
                    logger.info(path + "子节点大小：" + list.size());
                    Collections.sort(list);

                    int begin = path.indexOf("/",zkTopPath.length()) + 1;
                    int end  = path.lastIndexOf("/");
                    String taskName = path.substring(begin,end);

                    String masterID = getMasterServerId(taskName);
                    logger.info("master节点：" + masterID);
                    serverMasterId.put(taskName,masterID);
                }
            };
            ZkChildListeners.put(nodePath,iZkChildListener);
            zkClient.subscribeChildChanges(serverPath,iZkChildListener);*/

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    //反注册
    public static void unRegisterServer(String taskName, String serverId) {

/*        String serverPath = zkTopPath + "/" + taskName;
        String nodePath = serverPath + "/" + serverId;

        IZkChildListener iZkChildListener = ZkChildListeners.remove(nodePath);
        zkClient.unsubscribeChildChanges(serverPath, iZkChildListener);*/
    }


    private static String getMasterServerId(String taskName, String serverId) {
        String masterId = "";
        try {
            String serverPath = zkTopPath + "/" + taskName;
            List<String> childs = null;//zkClient.getChildren(serverPath);
            Collections.sort(childs);
            masterId = childs.get(0);//排序后，返回第一个
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        if (masterId.isEmpty()) {//如果没有获取到主节点，重新注册好吧
            registerServer(taskName, serverId);
        }

        return masterId;
    }

    public static boolean isMasterServer(String taskName, String serverId) {
        try {
            if (MasterServerIds.containsKey(taskName)) {//通过配置文件指定了masterIP
                if (MasterServerIds.get(taskName).equals(serverId)) {//serverId正是master
                    return true;
                } else {
                    return false;
                }
            }

            if (serverId != null) {
                return serverId.equals(getMasterServerId(taskName, serverId)) ? true : false;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    //每次调用返回不同,根据pid,ip,随机数生成
    //工具方法
    public static String getServerID() {

        String serverId = "";
        try {
            /*serverId = getPid() + "$" + NetUtils.getLocalAddress().getLocalHost().getHostAddress() + "$" + (UUID.randomUUID().toString().replaceAll("-", "")
                    .toUpperCase());*/
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            serverId = getPid() + "$" + "0.0.0.0" + "$" + (UUID.randomUUID().toString().replaceAll("-", "")
                    .toUpperCase());
        }

        return serverId;
    }

    private static int getPid() {
        int PID = -1;
        if (PID < 0) {
            try {
                RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
                String name = runtime.getName(); // format: "pid@hostname"
                PID = Integer.parseInt(name.substring(0, name.indexOf('@')));
            } catch (Throwable e) {
                PID = 0;
            }
        }
        return PID;
    }

    public static String getLocalIp() {
        try {
            return null;// NetUtils.getLocalAddress().getLocalHost().getHostAddress();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "";
        }
    }
}

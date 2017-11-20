package com.jd.zk;

import com.alibaba.fastjson.JSONObject;
import com.jd.jsf.zookeeper.IZkChildListener;
import com.jd.jsf.zookeeper.IZkDataListener;
import com.jd.jsf.zookeeper.ZkClient;
import com.jd.jsf.zookeeper.common.NetUtils;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Title: SelectMasterMain
 * Description: SelectMasterMain
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/8/19
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class SelectMasterMain {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static ZkClient zkClient = null;

    private static String path = "/aaa_selectMaster/scheduleTask/server";

    private static PropertyFactory propertyFactory;

    private static ScheduledExecutorService sechduleCheck = Executors.newSingleThreadScheduledExecutor();//同一时刻只有一个任务在执行

    private static String serverId = "";

    private static Map<String ,IZkChildListener> ZkChildListeners = new HashMap<String, IZkChildListener>();

    static {
        try {
            propertyFactory = new PropertyFactory("worker.properties");
            zkClient = new ZkClient((String) propertyFactory.getProperty("zk.address"), Long.valueOf(propertyFactory.getProperty("zk.connectionTimeout", "10000")),
                    Integer.valueOf(propertyFactory.getProperty("zk.sessionTimeout", "30000")));

            serverId = ScheduleServer.getPid() + "$" + NetUtils.getLocalAddress().getLocalHost().getHostAddress() + "$" + (UUID.randomUUID().toString().replaceAll("-", "")
                    .toUpperCase());

        } catch (IOException e) {
            logger.error("create zkClient error", e);
        }
    }


    public static void main(String[] agrs) {
        try {
            logger.info("hello cc");

/*            String ip = NetUtils.getLocalAddress().getLocalHost().getHostAddress();
            String id = ScheduleServer.getPid() + "$" + ip + "$" + (UUID.randomUUID().toString().replaceAll("-", "")
                    .toUpperCase());*/

            logger.info("本server id:" + serverId);

            registerServer(serverId);

            logger.info("master节点：" + getMasterId(path));


            sechduleCheck.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    try {
                        String masterID = getMasterId(path);
                        if(masterID == ""){
                            logger.info("master节点，重新注册");
                            unRegisterServer(path);//先反注册，取消监听
                            registerServer(serverId);//重新注册一下，监听path的子节点变化
                        }
                        logger.info("master节点：" + getMasterId(path));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, 5, 10, TimeUnit.SECONDS);



/*
            Thread.sleep(10000);
            server.setId("200");
            registerScheduleServer(server);
*/
            // 启动本地服务，然后hold住本地服务
            synchronized (Main.class) {
                while (true) {
                    try {
                        Main.class.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //注册服务
    public static void registerServer(String id) throws Exception {

        String zkPath = path + "/" + id;

/*        boolean exists = zkClient.exists(path);
        logger.info("是否存在：" + exists);
        if (!exists) {
            logger.info("不存在：" + path + ",创建");
            zkClient.createPersistent(path, true);//createParents
        }*/


        if(!zkClient.exists(zkPath)){
            logger.info("不存在,创建：" + zkPath);
            zkClient.createEphemeral(zkPath, null);
        }

        //zkClient.create(zkPath,null, CreateMode.EPHEMERAL_SEQUENTIAL);//创建顺序节点，后面会自动加序列号

        IZkChildListener iZkChildListener = new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                logger.info("子节点大小：" + list.size() + " s:" + s);
                Collections.sort(list);
/*                for(String c :list){
                    logger.info("子节点：" + c);
                }*/
                logger.info("master节点：" + getMasterId(path));
            }
        };

        ZkChildListeners.put(path,iZkChildListener);
        zkClient.subscribeChildChanges(path,iZkChildListener);
    }

    //反注册服务
    public static void unRegisterServer(String path) throws Exception {
        zkClient.unsubscribeChildChanges(path, ZkChildListeners.get(path));
        ZkChildListeners.remove(path);
    }

    //获取一个path下的master节点，没有，或者异常返回null
    public static String getMasterId(String path) {
        try {
            List<String> childs = zkClient.getChildren(path);
            Collections.sort(childs);
            return childs.get(0);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }
}

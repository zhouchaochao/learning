package com.jd.zk;

import com.alibaba.fastjson.JSONObject;
import com.jd.jsf.zookeeper.IZkChildListener;
import com.jd.jsf.zookeeper.IZkDataListener;
import com.jd.jsf.zookeeper.ZkClient;
import com.jd.jsf.zookeeper.common.NetUtils;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Title: Main
 * Description: Main
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/8/17
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static ZkClient zkClient = ZkClientUtil.getZkClient();

    public static void main(String[] agrs){
        try{
            logger.info("hello cc");

            String ip = NetUtils.getLocalAddress().getLocalHost().getHostAddress();
            String id = ScheduleServer.getPid()+"$"+ip + "$"+ (UUID.randomUUID().toString().replaceAll("-", "")
                    .toUpperCase());

            logger.info("id:" + id);

            Date current = new Date();
            CronExpression cronExpression = new CronExpression("0 */15 20 * * ? 2017");
            Date nextTime = cronExpression.getNextValidTimeAfter(current);
            logger.info(simpleDateFormat.format(nextTime));


            ScheduleServer server = new ScheduleServer();
            server.setId(id);
            server.setIp("1.2.3.4");
            server.setRegister(true);
            server.setStart(true);
            server.setWorkerType("ccWorker");

            registerScheduleServer(server);

            logger.info("master节点：" + getMasterId(server.getWorkerType()));
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



        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void registerScheduleServer(final ScheduleServer server) throws Exception {

        String zkPath = "/"+Constants.SAF_WORKER_ROOT+"/"+server.getWorkerType()+"/"+Constants.SAF_WORKER_SERVER+"/"+server.getId();

        boolean exists = zkClient.exists(zkPath);

        logger.info("是否存在："+exists);

        if(!exists){
            zkClient.createPersistent("/"+Constants.SAF_WORKER_ROOT+"/"+server.getWorkerType()+"/"+Constants.SAF_WORKER_SERVER,true);
        }


        server.setRegister(true);
        JSONObject serverJSON = (JSONObject) JSONObject.toJSON(server);
        byte[] serverValue = serverJSON.toJSONString().getBytes();
        //zkClient.createEphemeral(zkPath, serverValue);
        zkClient.create(zkPath,null, CreateMode.EPHEMERAL_SEQUENTIAL);//创建顺序节点，后面会自动加序列号

        zkClient.subscribeChildChanges("/"+Constants.SAF_WORKER_ROOT+"/"+server.getWorkerType()+"/"+Constants.SAF_WORKER_SERVER, new IZkChildListener(){

            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                logger.info("子节点大小：" + list.size() + " s:" +s);
                Collections.sort(list);
                for(String c :list){
                    logger.info("子节点：" + c);
                }

                logger.info("master节点：" + getMasterId(server.getWorkerType()));

            }
        });

        //schedule server 状态监听（便于管理端对worker进行管理）
        zkClient.subscribeDataChanges(zkPath,new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                logger.info("数据："+data);
                logger.info("[{}] schedule server {} stat changed to [{}]",server.getWorkerType(),server.getId(),true);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
            }
        });
    }


    public static String getMasterId(String workerType){
        try{
            String zkPath = "/"+Constants.SAF_WORKER_ROOT+"/"+ workerType +"/"+Constants.SAF_WORKER_SERVER;
            List<String> childs = zkClient.getChildren(zkPath);
            Collections.sort(childs);

            return childs.get(0);

        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return "";
    }
}

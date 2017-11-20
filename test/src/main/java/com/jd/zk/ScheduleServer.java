package com.jd.zk;

import com.alibaba.fastjson.JSONObject;
import com.jd.jsf.zookeeper.common.NetUtils;
import com.jd.jsf.zookeeper.common.StringUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * 调度Server信息
 *
 * @author : wutao
 * @version : ScheduleServer.java 2014/08/06 17:38
 * @copyright www.cc.com <http://www.cc.com/>
 */
public class ScheduleServer {

    /**
     * pid$ip$uuid
     */
    private String id;

    private String ip;

    private boolean isRegister;

    private String workerType;

    /**
     * worker 相关的具体业务参数
     */
    private JSONObject workerParameters;

    /**
     * 调度server是否启动,默认启动
     */
    private boolean start = true;



    /**
     * 创建调度server
     *
     * @param worker
     * @return
     */
    public static ScheduleServer createScheduleServer(Worker worker){
        ScheduleServer scheduleServer = new ScheduleServer();
        scheduleServer.workerType = worker.getWorkerType();
        scheduleServer.workerParameters = worker.getWorkerParameters();
        //0 代表开机启动
        if ( "0".equals(worker.status())){
            scheduleServer.setStart(true);
        } else {
            scheduleServer.setStart(false);
        }
        try {
            scheduleServer.ip = NetUtils.getLocalAddress().getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        }
        scheduleServer.id = getPid()+"$"+scheduleServer.ip + "$"+ (UUID.randomUUID().toString().replaceAll("-", "")
                .toUpperCase());

        return scheduleServer;

    }

    /**
     *
     * @param id
     * @return 从server id 中解析出来IP地址
     */
    public static String getIpFromID(String id){
        if (StringUtils.isBlank(id)){
            return null;
        }
        if ( id.indexOf("$") > 0 ){
            return id.split("$")[0];
        } else {
            return id;
        }
    }

    public static int getPid() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isRegister() {
        return isRegister;
    }

    public void setRegister(boolean isRegister) {
        this.isRegister = isRegister;
    }

    public String getWorkerType() {
        return workerType;
    }

    public void setWorkerType(String workerType) {
        this.workerType = workerType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public JSONObject getWorkerParameters() {
        return workerParameters;
    }

    public void setWorkerParameters(JSONObject workerParameters) {
        this.workerParameters = workerParameters;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean isWorking) {
        this.start = isWorking;
    }
}

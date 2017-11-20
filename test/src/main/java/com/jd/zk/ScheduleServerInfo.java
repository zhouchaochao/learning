package com.jd.zk;

import com.alibaba.fastjson.JSONObject;

/**
 * ScheduleServer 相关信息
 *
 * @author : wutao
 * @version : ScheduleServerInfo.java 2014/08/11 11:14
 * @copyright www.cc.com <http://www.cc.com/>
 */
public class ScheduleServerInfo {

    /**
     * 当前workerType下总的结点数目
     */
    private int serverNum;

    /**
     * 当前结点在所有（指定workerType）下结点序号
     */
    private int index;

    private String workerType;

    private boolean isMaster;

    private String id;

    private String ip;

    private JSONObject workerParameters;

    public ScheduleServerInfo(int serverNum, int index, String workerType) {
        this.serverNum = serverNum;
        this.index = index;
        this.workerType = workerType;
    }

    public ScheduleServerInfo() {
    }

    public int getServerNum() {
        return serverNum;
    }

    public void setServerNum(int serverNum) {
        this.serverNum = serverNum;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getWorkerType() {
        return workerType;
    }

    public void setWorkerType(String workerType) {
        this.workerType = workerType;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public void setMaster(boolean isMaster) {
        this.isMaster = isMaster;
    }

    public JSONObject getWorkerParameters() {
        return workerParameters;
    }

    public void setWorkerParameters(JSONObject workerParameters) {
        this.workerParameters = workerParameters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}

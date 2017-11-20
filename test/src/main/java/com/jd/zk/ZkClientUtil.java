package com.jd.zk;

import com.jd.jsf.zookeeper.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author : wutao
 * @version : ZkClientUtil.java 2014/08/19 16:33
 * @copyright www.cc.com <http://www.cc.com/>
 */
public class ZkClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(ZkClientUtil.class);

    private static ZkClient zkClient;

    private static PropertyFactory propertyFactory;

    static {
        try {
            propertyFactory = new PropertyFactory("worker.properties");
            zkClient = new ZkClient((String) propertyFactory.getProperty("zk.address"),Long.valueOf(propertyFactory.getProperty("zk.connectionTimeout","10000")),
                    Integer.valueOf(propertyFactory.getProperty("zk.sessionTimeout","30000")));
        } catch (IOException e) {
            logger.error("create zkClient error",e);
        }
    }

    public static ZkClient getZkClient(){
        return zkClient;
    }
}

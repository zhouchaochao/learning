package com.jd.zk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Title: SelectMasterServerMain
 * Description: SelectMasterServerMain
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/8/19
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class SelectMasterServerMain {

    private static Logger logger = LoggerFactory.getLogger(SelectMasterServerMain.class);

    private static ScheduledExecutorService sechduleCheck = Executors.newSingleThreadScheduledExecutor();//同一时刻只有一个任务在执行

    private static String serverId = SelectMasterServer.getServerID();


    public static void main(String[] agrs) {
        try {
            logger.info("hello cc , serverId:" + serverId);
            final String serverName = "ccTask";
            SelectMasterServer.registerServer(serverName, serverId);

            sechduleCheck.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    try {
                        logger.info("检查是否是master");
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

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

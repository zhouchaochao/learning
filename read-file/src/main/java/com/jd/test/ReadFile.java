package com.jd.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.PropertyUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Title: com.jd.com.jd.test.ReadFile
 * Description: com.jd.com.jd.test.ReadFile
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/8/11
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class ReadFile {

    private static Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

    public static int charArrLength = 100;

    public static boolean isPrint = false;

    public static void main(String[] agrs) {

        String filePath = PropertyUtil.getProperties("filePath");
        int threadNum = Integer.parseInt(PropertyUtil.getProperties("threadNum"));
        charArrLength = Integer.parseInt(PropertyUtil.getProperties("charArrLength"));
        int threadPoolSize = 1000;
        if (PropertyUtil.getProperties("threadPoolSize") != null) {
            threadPoolSize = Integer.parseInt(PropertyUtil.getProperties("threadPoolSize"));
        }
        if (PropertyUtil.getProperties("isPrint") != null) {
            isPrint = Boolean.parseBoolean(PropertyUtil.getProperties("isPrint"));
        }

        logger.info("threadNum:" + threadNum);
        logger.info("filePath:" + filePath);
        logger.info("charArrLength:" + charArrLength);
        logger.info("threadPoolSize:" + threadPoolSize);
        logger.info("isPrint:" + isPrint);

        ExecutorService threadPool = Executors.newFixedThreadPool(threadPoolSize);

        for (int i = 0; i < threadNum; i++) {
            threadPool.submit(new ReadFileThread("readFileThread" + i, filePath));
        }
    }
}

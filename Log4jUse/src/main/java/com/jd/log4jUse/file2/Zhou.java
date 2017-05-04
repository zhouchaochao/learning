package com.jd.log4jUse.file2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: Zhou
 * Description: Zhou
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/12/2
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class Zhou {

    private static Logger logger = LoggerFactory.getLogger(Zhou.class);

    public static void printLog(String log){
        logger.info(log);
    }
}

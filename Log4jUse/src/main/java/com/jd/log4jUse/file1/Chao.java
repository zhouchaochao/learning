package com.jd.log4jUse.file1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: Chao
 * Description: Chao
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/12/2
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class Chao {

    private static Logger logger = LoggerFactory.getLogger(Chao.class);

    public static void printLog(String log){
        logger.info(log);
    }

}

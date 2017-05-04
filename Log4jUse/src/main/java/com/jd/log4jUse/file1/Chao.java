package com.jd.log4jUse.file1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: Chao
 * Description: Chao
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/12/2
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class Chao {

    private static Logger logger = LoggerFactory.getLogger(Chao.class);

    public static void printLog(String log){
        logger.info(log);
    }

}

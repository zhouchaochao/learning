package com.jd.log4jUse;

import com.jd.log4jUse.file1.Chao;
import com.jd.log4jUse.file2.Zhou;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: com.jd.log4jUse.Main
 * Description: com.jd.log4jUse.Main   参考：http://www.cnblogs.com/ITtangtang/p/3926665.html
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/12/2
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            logger.info(i + "");
            Zhou.printLog(i + "");
            Chao.printLog(i + "");
        }
    }
}

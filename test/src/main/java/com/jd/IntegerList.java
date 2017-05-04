package com.jd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Title: IntegerList
 * Description: IntegerList
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/9/9
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class IntegerList {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args){

        List<Integer> AlarmedCluster = Collections.synchronizedList(new ArrayList<>());//已经报过警的集群,不再重复报警

        AlarmedCluster.add(1);
        AlarmedCluster.add(new Integer(2));
        AlarmedCluster.add(100);
        AlarmedCluster.add(new Integer(200));
        AlarmedCluster.add(10000);
        AlarmedCluster.add(new Integer(20000));
        AlarmedCluster.add(1000000);
        AlarmedCluster.add(new Integer(2000000));

        AlarmedCluster.remove(new Integer(2));
        AlarmedCluster.remove(new Integer(100));
        AlarmedCluster.remove(new Integer(20000));
        AlarmedCluster.remove(new Integer(1000000));

        AlarmedCluster.forEach(i -> {
            logger.info("" + i);
        });

    }



}

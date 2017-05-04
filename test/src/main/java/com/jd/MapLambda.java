package com.jd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Title: MapLambda
 * Description: MapLambda
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/9/6
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class MapLambda {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] agrs) {
        Map<String,String> map = new LinkedHashMap<String, String>();//有序，也就是取出来的顺序和扔进去的顺序一样
        map.put("a","123");
        map.put("d","100");
        map.put("b","100");
        map.put("c","100");
        map.forEach((k,v)->{logger.info(k + " " + v);});

        Map<String,String> hashMap = new HashMap<String, String>();//无序，也就是内部会排序，存取顺序变了
        hashMap.put("a","123");
        hashMap.put("d","100");
        hashMap.put("b","100");
        hashMap.put("c","100");
        hashMap.forEach((k,v)->{logger.info(k + " " + v);});
    }
}

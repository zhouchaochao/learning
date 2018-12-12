package com.cc;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * Title: AppleService
 * Description: AppleService
 * Date:  2018/11/1
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class AppleService {

    private static final Logger logger = LoggerFactory.getLogger(AppleService.class);

    private Integer count = 1;

    public int increamentInt(Integer abc) {
        count += abc;
        return count;
    }

    public String increamentStr(String abc) {
        count += Integer.parseInt(abc);
        return "" + count;
    }

    public int increamentIntBase(int abc) {
        count += abc;
        return count;
    }

    public Long getTimeSecond(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(dateString).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Long(0);
    }

    public Long add(String a, Integer b, int c, Long d,long e) {
        try {
            return Integer.parseInt(a) + b + c + d + e;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0L;
    }


    public Object multiArgument(String ... arr) {
        try {
            return arr.length + " -> " +  Arrays.toString(arr);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0L;
    }

    /**
     *
     * @param key
     * @param jsonMap
     * @param defaultValue 执行抛出异常或者key不存在于map中时
     * @return
     */
    public Object getValueFromMap(String key, String jsonMap, Object defaultValue) {
        try {
            Map<String, Object> map = JSON.parseObject(jsonMap, Map.class);
            if (map.containsKey(key)) {
                return map.get(key);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return defaultValue == null ? key : defaultValue;
    }

    public Object arrFunction(String key, String[] arr) {
        try {
            return key + " -> " + JSON.toJSONString(arr);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "";
    }



}

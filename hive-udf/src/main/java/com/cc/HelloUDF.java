package com.cc;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * Title: HelloUDF
 * Description: HelloUDF
 * Date:  2018/7/12
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class HelloUDF extends UDF {

    public String evaluate(String value) {
        if (value == null) {
            return "";
        }
        return value + value;
    }

    public static void main(String[] args) {
        System.out.println(new HelloUDF().evaluate("abc"));
    }
}

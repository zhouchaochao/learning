package com.cc;

import jline.internal.TestAccessible;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.JavaBooleanObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.JavaStringObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Title: CommonTest
 * Description: CommonTest
 * Date:  2018/7/16
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class CommonTest {

    private static final Logger logger = LoggerFactory.getLogger(CommonTest.class);

    public static void main(String[] args) throws Exception {
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("c");
        list.add("b");

        //String[] arr = (String[])list.toArray();
        //Collections.sort(list);
        //System.out.println(list);

        DuplicateRemovalUDF duplicateRemovalUDF = new DuplicateRemovalUDF();
        GenericUDF.DeferredObject[] arguments = new GenericUDF.DeferredObject[1];

        GenericUDF.DeferredJavaObject deferredJavaObject = new GenericUDF.DeferredJavaObject(list);
        arguments[0] = deferredJavaObject;

        Object result = evaluate(list);
        System.out.println(result);


        test();
        testDuplicateRemovalUDF();

    }


    public static Object evaluate(List<String> list) throws HiveException {

        // get the list and string from the deferred objects using the object inspectors

        // check for nulls
        if (list == null) {
            return null;
        }

        //String[] arr = (String[])list.toArray();
        //Arrays.sort(arr);//升序排序

        Collections.sort(list);

        String return_value = "";
        String[] before_kv = null;
        for (String item : list) {
            String[] kv = item.split(":");
            if (kv.length == 2) {
                if (before_kv == null || !kv[1].equals(before_kv[1])) {//与前一个不重复
                    return_value += kv[1];
                }
                before_kv = kv;
            } else {
                return_value += item;
            }
        }
        return return_value;

    }


    public static void test() throws Exception{
        // 建立需要的模型
        ComplexUDFExample example = new ComplexUDFExample();
        ObjectInspector stringOI = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
        ObjectInspector listOI = ObjectInspectorFactory.getStandardListObjectInspector(stringOI);
        JavaBooleanObjectInspector resultInspector = (JavaBooleanObjectInspector) example.initialize(new ObjectInspector[]{listOI, stringOI});

        // create the actual UDF arguments
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");

        // 测试结果

        // 存在的值
        Object result = example.evaluate(new GenericUDF.DeferredObject[]{new GenericUDF.DeferredJavaObject(list), new GenericUDF.DeferredJavaObject("d")});
        System.out.println(resultInspector.get(result));
    }



    public static void testDuplicateRemovalUDF() throws Exception{
        // 建立需要的模型
        DuplicateRemovalUDF example = new DuplicateRemovalUDF();
        ObjectInspector stringOI = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
        ObjectInspector listOI = ObjectInspectorFactory.getStandardListObjectInspector(stringOI);
        JavaStringObjectInspector resultInspector = (JavaStringObjectInspector) example.initialize(new ObjectInspector[]{listOI, stringOI});

        // create the actual UDF arguments
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");

        // 测试结果

        // 存在的值
        Object result = example.evaluate(new GenericUDF.DeferredObject[]{new GenericUDF.DeferredJavaObject(list), new GenericUDF.DeferredJavaObject("_")});
        System.out.println(resultInspector.getPrimitiveJavaObject(result));
    }

}

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

        testComplexUDFExample();
        testDuplicateRemovalUDF();

    }


    public static void testComplexUDFExample() throws Exception{
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
        list.add("101:a");
        list.add("102:b");
        list.add("103:c");
        list.add("200:c");
        list.add("104:c");
        list.add("105:a");
        list.add("106:a");
        list.add("107:a");

        // 测试结果

        // 存在的值
        Object result = example.evaluate(new GenericUDF.DeferredObject[]{new GenericUDF.DeferredJavaObject(list), new GenericUDF.DeferredJavaObject("_")});
        System.out.println(resultInspector.getPrimitiveJavaObject(result));
    }

}

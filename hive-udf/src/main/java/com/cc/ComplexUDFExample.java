package com.cc;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ListObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Title: ComplexUDFExample
 * Description: ComplexUDFExample
 * Date:  2018/7/13
 *
 * 参考：
 * [Hive UDF开发指南] https://www.cnblogs.com/hd-zg/p/5947468.html
 * [一起学Hive]之十八-Hive UDF开发] https://www.cnblogs.com/1130136248wlxk/articles/5519276.html
 *
 * 判断第二个参数是否在第一个参数(list)中
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class ComplexUDFExample extends GenericUDF {

    private static final Logger logger = LoggerFactory.getLogger(ComplexUDFExample.class);

    ListObjectInspector listOI;
    StringObjectInspector elementOI;

    @Override
    public String getDisplayString(String[] arg0) {
        return "arrayContainsExample()"; // this should probably be better
    }

    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if (arguments.length != 2) {
            throw new UDFArgumentLengthException("arrayContainsExample only takes 2 arguments: List<T>, T");
        }
        // 1. Check we received the right object types.
        ObjectInspector a = arguments[0];
        ObjectInspector b = arguments[1];
        if (!(a instanceof ListObjectInspector) || !(b instanceof StringObjectInspector)) {
            throw new UDFArgumentException("first argument must be a list / array, second argument must be a string");
        }
        this.listOI = (ListObjectInspector) a;
        this.elementOI = (StringObjectInspector) b;

        // 2. Check that the list contains strings
        if(!(listOI.getListElementObjectInspector() instanceof StringObjectInspector)) {
            throw new UDFArgumentException("first argument must be a list of strings");
        }

        // the return type of our function is a boolean, so we provide the correct object inspector
        return PrimitiveObjectInspectorFactory.javaBooleanObjectInspector;
    }

    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {

        // get the list and string from the deferred objects using the object inspectors
        List<String> list = (List<String>) this.listOI.getList(arguments[0].get());
        String arg = elementOI.getPrimitiveJavaObject(arguments[1].get());

        // check for nulls
        if (list == null || arg == null) {
            return null;
        }

        // see if our list contains the value we need
        for(String s: list) {
            if (arg.equals(s)) return new Boolean(true);
        }
        return new Boolean(false);
    }

}

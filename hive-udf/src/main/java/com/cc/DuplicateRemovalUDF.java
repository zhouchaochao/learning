package com.cc;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ListObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.apache.hadoop.io.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Title: ComplexUDFExample
 * Description: ComplexUDFExample
 * 接受list,按照升序排序，相邻去重。如果字符串中包含:,则去掉:号以及它前面的部分。
 * Date:  2018/7/13
 * <p>
 * 参考：
 * [Hive UDF开发指南] https://www.cnblogs.com/hd-zg/p/5947468.html
 * [一起学Hive]之十八-Hive UDF开发] https://www.cnblogs.com/1130136248wlxk/articles/5519276.html
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class DuplicateRemovalUDF extends GenericUDF {

    private static final Logger logger = LoggerFactory.getLogger(DuplicateRemovalUDF.class);

    ListObjectInspector listOI;
    StringObjectInspector elementOI;

    @Override
    public String getDisplayString(String[] arg0) {
        return "arrayContainsExample()"; // this should probably be better
    }

    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if (arguments.length != 2) {
            throw new UDFArgumentLengthException("arrayContainsExample only takes 2 arguments: List<T>,String");
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
        if (!(listOI.getListElementObjectInspector() instanceof StringObjectInspector)) {
            throw new UDFArgumentException("first argument must be a list of strings");
        }

        // the return type of our function is a String, so we provide the correct object inspector
        return PrimitiveObjectInspectorFactory.javaStringObjectInspector;//.javaBooleanObjectInspector;
    }

    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {

        // get the list and string from the deferred objects using the object inspectors
        List<String> list = (List<String>) this.listOI.getList(arguments[0].get());
        String split_value = elementOI.getPrimitiveJavaObject(arguments[1].get());

        // check for nulls
        if (list == null) {
            return null;
        }

        List<String> cast = new ArrayList<String>();
        for (Object item : list) {
            if (item instanceof Text) {
                Text t = (Text) item;
                cast.add(t.toString());
            } else {
                cast.add(item.toString());
            }
        }

        Collections.sort(cast);//升序排序

        String return_value = "";
        String[] before_kv = null;
        for (String item : cast) {
            String[] kv = item.split(":");
            if (kv.length == 2) {
                if (before_kv == null) {//第一个
                    return_value += kv[1];
                } else if (!kv[1].equals(before_kv[1])) {//与前一个不重复
                    return_value += split_value + kv[1];
                }
                before_kv = kv;
            } else {
                return_value += (return_value.equals("") ? "" : split_value) + item;
            }
        }
        return return_value;

    }

}

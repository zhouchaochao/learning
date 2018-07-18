package com.cc;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Title: TotalNumOfLettersGenericUDAF
 * Description: TotalNumOfLettersGenericUDAF
 * Date:  2018/7/13
 *
 * 参考：
 * [Hive-UDAF] https://www.jianshu.com/p/7ebc8f9c9b78
 * [Hive UDAF介绍与开发] https://www.cnblogs.com/itxuexiwang/p/6264547.html
 * [Hive UDAF开发之同时计算最大值与最小值] https://www.cnblogs.com/juefan/p/3598290.html
 * [Hive UDAF开发详解] https://blog.csdn.net/kent7306/article/details/50110067
 * []
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */
@Description(name = "trace", value = "_FUNC_(expr) - 计算轨迹")
public class TraceUDAF extends AbstractGenericUDAFResolver {

    private static final Logger logger = LoggerFactory.getLogger(TraceUDAF.class);

    @Override
    public GenericUDAFEvaluator getEvaluator(TypeInfo[] parameters)
            throws SemanticException {
        if (parameters.length != 1) {
            throw new UDFArgumentTypeException(parameters.length - 1,
                    "Exactly one argument is expected.");
        }

        ObjectInspector oi = TypeInfoUtils.getStandardJavaObjectInspectorFromTypeInfo(parameters[0]);

        if (oi.getCategory() != ObjectInspector.Category.PRIMITIVE) {
            throw new UDFArgumentTypeException(0,
                    "Argument must be PRIMITIVE, but "
                            + oi.getCategory().name()
                            + " was passed.");
        }

        PrimitiveObjectInspector inputOI = (PrimitiveObjectInspector) oi;

        if (inputOI.getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
            throw new UDFArgumentTypeException(0,
                    "Argument must be String, but "
                            + inputOI.getPrimitiveCategory().name()
                            + " was passed.");
        }

        return new TraceEvaluator();
    }

    public static class TraceEvaluator extends GenericUDAFEvaluator {

        PrimitiveObjectInspector inputOI;
        ObjectInspector outputOI;
        PrimitiveObjectInspector stringOI;

        String result_trace = "";

        @Override
        public ObjectInspector init(Mode m, ObjectInspector[] parameters)
                throws HiveException {

            assert (parameters.length == 1);
            super.init(m, parameters);

            //map阶段读取sql列，输入为String基础数据格式
            if (m == Mode.PARTIAL1 || m == Mode.COMPLETE) {
                inputOI = (PrimitiveObjectInspector) parameters[0];
            } else {
                //其余阶段，输入为Integer基础数据格式
                stringOI = (PrimitiveObjectInspector) parameters[0];
            }

            // 指定各个阶段输出数据格式都为String类型
            outputOI = ObjectInspectorFactory.getReflectionObjectInspector(String.class,
                    ObjectInspectorFactory.ObjectInspectorOptions.JAVA);
            return outputOI;

        }

        /**
         * 存储当前字符总数的类
         */
        static class TraceAgg implements AggregationBuffer {
            String trace = "";

            void add(String str) {
                trace += str + ";";
            }
        }

        @Override
        public AggregationBuffer getNewAggregationBuffer() throws HiveException {
            TraceAgg result = new TraceAgg();
            return result;
        }

        @Override
        public void reset(AggregationBuffer agg) throws HiveException {
            TraceAgg myagg = (TraceAgg) agg;
            myagg.trace = "";
        }

        private boolean warned = false;

        //map阶段调用，只要把保存当前数据的对象agg，再加上输入的参数，就可以了。
        @Override
        public void iterate(AggregationBuffer agg, Object[] parameters)
                throws HiveException {
            assert (parameters.length == 1);
            if (parameters[0] != null) {
                TraceAgg myagg = (TraceAgg) agg;
                Object p1 = ((PrimitiveObjectInspector) inputOI).getPrimitiveJavaObject(parameters[0]);
                myagg.add(p1.toString());
            }
        }

        //mapper结束要返回的结果，还有combiner结束返回的结果
        @Override
        public Object terminatePartial(AggregationBuffer agg) throws HiveException {
            TraceAgg myagg = (TraceAgg) agg;
            result_trace = myagg.trace;
            return result_trace;
            //return terminate(agg);
        }

        //combiner合并map返回的结果，还有reducer合并mapper或combiner返回的结果。
        @Override
        public void merge(AggregationBuffer agg, Object partial)
                throws HiveException {
            if (partial != null) {
                TraceAgg myagg = (TraceAgg) agg;
                String partialValue = (String) stringOI.getPrimitiveJavaObject(partial);
                myagg.add(partialValue);
            }
        }

        //reducer返回结果，或者是只有mapper，没有reducer时，在mapper端返回结果。
        @Override
        public Object terminate(AggregationBuffer agg) throws HiveException {
            TraceAgg myagg = (TraceAgg) agg;
            result_trace = myagg.trace;

            String[] arr = result_trace.split(";");
            Arrays.sort(arr);//升序排序

            String return_value = "";
            String[] before_kv = null;
            for (String item : arr) {
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
    }
}

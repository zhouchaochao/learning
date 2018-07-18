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

/**
 * Title: TotalNumOfLettersGenericUDAF
 * Description: TotalNumOfLettersGenericUDAF
 * Date:  2018/7/13
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */
@Description(name = "trace", value = "_FUNC_(expr) - 计算轨迹")
public class StrConcatUDAF extends AbstractGenericUDAFResolver {

    private static final Logger logger = LoggerFactory.getLogger(StrConcatUDAF.class);

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
                trace += str;
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

        /**
         *

         1.实现
         TraceAgg myagg = (TraceAgg) agg;
         result_trace = myagg.trace;
         return result_trace;
         执行：
         SELECT
         v3,cc_trace_udaf(v2)
         FROM
         (
         select 1 AS v1,'world' AS v2,'a' AS v3
         UNION ALL
         select 2 AS v1,'hi' AS v2,'a' AS v3
         UNION ALL
         select 4 AS v1,'hi' AS v2,'a' AS v3
         UNION ALL
         select 3 AS v1,'ab' AS v2,'b' AS v3
         ) t1
         GROUP BY v3
         的结果:
         v3      _c1
         a       worldhihi
         b       ab



         2.实现
         TraceAgg myagg = (TraceAgg) agg;
         return myagg.trace;
         执行：
         SELECT
         v3,cc_trace_udaf(v2)
         FROM
         (
         select 1 AS v1,'world' AS v2,'a' AS v3
         UNION ALL
         select 2 AS v1,'hi' AS v2,'a' AS v3
         UNION ALL
         select 4 AS v1,'hi' AS v2,'a' AS v3
         UNION ALL
         select 3 AS v1,'ab' AS v2,'b' AS v3
         ) t1
         GROUP BY v3
         的结果:
         v3      _c1
         a       worldhihi
         b       ab

         * @param agg
         * @return
         * @throws HiveException
         */
        //mapper结束要返回的结果，还有combiner结束返回的结果
        @Override
        public Object terminatePartial(AggregationBuffer agg) throws HiveException {
            TraceAgg myagg = (TraceAgg) agg;
            //result_trace = myagg.trace;
            return myagg.trace;
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
            return result_trace;
        }
    }
}

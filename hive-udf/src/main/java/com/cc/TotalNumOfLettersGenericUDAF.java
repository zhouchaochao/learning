package com.cc;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.serde2.objectinspector.*;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.DoubleObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.typeinfo.PrimitiveTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.DoubleWritable;

import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: TotalNumOfLettersGenericUDAF
 * Description: TotalNumOfLettersGenericUDAF
 * Date:  2018/7/13
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */
@Description(name = "letters", value = "_FUNC_(expr) - 返回该列中所有字符串的字符总数")
public class TotalNumOfLettersGenericUDAF extends AbstractGenericUDAFResolver {

    private static final Logger logger = LoggerFactory.getLogger(TotalNumOfLettersGenericUDAF.class);

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

        return new TotalNumOfLettersEvaluator();
    }

    public static class TotalNumOfLettersEvaluator extends GenericUDAFEvaluator {

        PrimitiveObjectInspector inputOI;
        ObjectInspector outputOI;
        PrimitiveObjectInspector integerOI;

        int total = 0;

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
                integerOI = (PrimitiveObjectInspector) parameters[0];
            }

            // 指定各个阶段输出数据格式都为Integer类型
            outputOI = ObjectInspectorFactory.getReflectionObjectInspector(Integer.class,
                    ObjectInspectorFactory.ObjectInspectorOptions.JAVA);
            return outputOI;

        }

        /**
         * 存储当前字符总数的类
         */
        static class LetterSumAgg implements AggregationBuffer {
            int sum = 0;

            void add(int num) {
                sum += num;
            }
        }

        @Override
        public AggregationBuffer getNewAggregationBuffer() throws HiveException {
            LetterSumAgg result = new LetterSumAgg();
            return result;
        }

        @Override
        public void reset(AggregationBuffer agg) throws HiveException {
            //LetterSumAgg myagg = new LetterSumAgg();
            LetterSumAgg myagg = (LetterSumAgg) agg;
            myagg.sum = 0;
        }

        private boolean warned = false;

        //map阶段调用，只要把保存当前数据的对象agg，再加上输入的参数，就可以了。
        @Override
        public void iterate(AggregationBuffer agg, Object[] parameters)
                throws HiveException {
            assert (parameters.length == 1);
            if (parameters[0] != null) {
                LetterSumAgg myagg = (LetterSumAgg) agg;
                Object p1 = ((PrimitiveObjectInspector) inputOI).getPrimitiveJavaObject(parameters[0]);
                myagg.add(String.valueOf(p1).length());
            }
        }

        //mapper结束要返回的结果，还有combiner结束返回的结果
        @Override
        public Object terminatePartial(AggregationBuffer agg) throws HiveException {
            LetterSumAgg myagg = (LetterSumAgg) agg;
            total += myagg.sum;
            return total;
        }

        //combiner合并map返回的结果，还有reducer合并mapper或combiner返回的结果。
        @Override
        public void merge(AggregationBuffer agg, Object partial)
                throws HiveException {
            if (partial != null) {

                LetterSumAgg myagg1 = (LetterSumAgg) agg;

                Integer partialSum = (Integer) integerOI.getPrimitiveJavaObject(partial);

                LetterSumAgg myagg2 = new LetterSumAgg();

                myagg2.add(partialSum);
                myagg1.add(myagg2.sum);
            }
        }

        //reducer返回结果，或者是只有mapper，没有reducer时，在mapper端返回结果。
        @Override
        public Object terminate(AggregationBuffer agg) throws HiveException {
            LetterSumAgg myagg = (LetterSumAgg) agg;
            total = myagg.sum;
            return myagg.sum;
        }

    }


}


import com.cc.AppleService;
import com.cc.GroovyUtil;
import groovy.lang.MissingPropertyException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Title: GroovyUtilTest
 * Description: GroovyUtilTest
 * Date:  2018/8/6
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class GroovyUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(GroovyUtilTest.class);

    @Test
    public void testCalculate() {
        Map<String, Object> infos = new HashMap<>();
        infos.put("orderPrice", 30.1);
        infos.put("returnMoney", 100.5);
        infos.put("deductMoney", 5);
        infos.put("user", "王二狗");
        infos.put("userType", "乘客");
        infos.put("orderNum", 1000023);
        infos.put("orderPrice", 36.8);
        infos.put("area", "北京");


        String expression = "${orderPrice+returnMoney-deductMoney}";
        String result = GroovyUtil.calculate(expression, infos);
        logger.info(result.toString() + "表达式：" + expression);

        expression = "尊敬的${user}${userType},您在${area}产生的订单编号为:${orderNum}的订单的费用为${orderPrice+orderPrice},请核对。 ";
        result = GroovyUtil.calculate(expression, infos);
        logger.info(result.toString() + "表达式：" + expression);

        //单引号
        expression = "尊敬的${user}${userType},您在${area}产生的'订单'编号:${orderNum}的费用为${orderPrice+orderPrice},请核对。 ";
        result = GroovyUtil.calculate(expression, infos);
        logger.info(result.toString() + "表达式：" + expression);

        //双引号
        expression = "尊敬的${user}${userType},您在${area}产生的\"订单\"编号:${orderNum}的费用为${orderPrice+orderPrice},请核对。 ";
        result = GroovyUtil.calculate(expression, infos);
        logger.info(result.toString() + "表达式：" + expression);

        try {
            expression = "尊敬的${unknowParameter}${userType},您在${area}产生的订单";
            result = GroovyUtil.calculate(expression, infos);
            logger.info(result.toString() + "表达式：" + expression);
        } catch (MissingPropertyException e) {
            //logger.error(e.getMessage(), e);
            logger.info("如果缺少参数，抛MissingPropertyException异常:" + e);
        }


        expression = "${if(closureTimeSec instanceof String) { closureTimeSec = closureTimeSec.toInteger();};if(closureTimeSec==0){return (new Date()).format('yyyy-MM-dd HH:mm:ss');}else {return new Date(closureTimeSec).format('yyyy-MM-dd HH:mm:ss');}}";
        result = GroovyUtil.calculate(expression, infos);
        logger.info(result.toString() + "表达式：" + expression);
    }


    @Test
    public void testMathCalculate() {
        Map<String, Object> infos = new HashMap<>();
        infos.put("orderPrice", 30.1);
        infos.put("returnMoney", 100.5);
        infos.put("deductMoney", 5);
        infos.put("orderNum", 1000023);
        infos.put("orderPrice", 36.8);
        infos.put("idArray", new int[]{1, 2, 4});//支持数组
        infos.put("name", "2元空驶补偿已到账，可到钱包查看");//支持数组
        infos.put("area", "北京");
        infos.put("closureTimeSec", "0");
        infos.put("detourRate", 0.123456789);
        infos.put("age", "36");

        String expression = "100+100 -deductMoney";
        Object result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "。类型：" + result.getClass().toString() + "表达式：" + expression);

        expression = "orderPrice+returnMoney-deductMoney";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "。类型：" + result.getClass().toString() + "表达式：" + expression);

        //加减乘除
        expression = "(1 * orderPrice+returnMoney-deductMoney)/+1000";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "。类型：" + result.getClass().toString() + "表达式：" + expression);

        //布尔表达式
        expression = "orderPrice>2 && orderPrice==2";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "。类型：" + result.getClass().toString() + "表达式：" + expression);

        //布尔表达式
        expression = "orderPrice>2 || orderPrice==2";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "。类型：" + result.getClass().toString() + "表达式：" + expression);

        //in 操作
        expression = "2 in idArray";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "。类型：" + result.getClass().toString() + "表达式：" + expression);

        expression = "3 in idArray";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "。类型：" + result.getClass().toString() + "表达式：" + expression);

        //正则匹配
        expression = "orderPrice>0&&name==~\".元空驶补偿已到账，可到钱包查看\"";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "。类型：" + result.getClass().toString() + "表达式：" + expression);

        expression = "name.equals(\"2元空驶补偿已到账，可到钱包查看\")";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "。类型：" + result.getClass().toString() + "表达式：" + expression);

        //字符串包含
        expression = "name.contains(\"元空驶补偿已到账，可到钱包查看\")";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "。类型：" + result.getClass().toString() + "表达式：" + expression);

        expression = "if(closureTimeSec instanceof String) { closureTimeSec = closureTimeSec.toInteger();};if(closureTimeSec==0){return (new Date()).format('yyyy-MM-dd HH:mm:ss');}else {return new Date(closureTimeSec).format('yyyy-MM-dd HH:mm:ss');}";
        Object functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + "表达式：" + expression);

        //转换成百分数，保留4位小数
        expression = "return (Math.round(detourRate*1000000)/10000);";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + "表达式：" + expression);

        //转换数据类型
        expression = "if(age==null) {return -1;};age.toInteger()";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + "表达式：" + expression);




    }

    @Test
    public void functionTest(){
        Map<String, Object> infos = new HashMap<>();
        infos.put("orderPrice", 30.1);
        infos.put("returnMoney", 100.5);
        infos.put("deductMoney", 5);
        infos.put("user", "王二狗");
        infos.put("userType", "乘客");
        infos.put("orderNum", 1000023);
        infos.put("orderPrice", 36.8);
        infos.put("lineInt", 3);
        infos.put("lineStr", "3");
        infos.put("dateString", "2018-11-01 11:11:11");
        infos.put("nullObj", null);

        AppleService appleService = new AppleService();
        infos.put("appleService",appleService);

        String expression = "appleService.getTimeSecond(dateString)";
        Object result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);

        expression = "appleService.getTimeSecond(\"${dateString}\")";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);

        expression = "appleService.increamentStr(lineStr)";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);

        expression = "appleService.increamentStr(\"${lineStr}\")";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);

        expression = "appleService.increamentStr(\"${lineInt}\")";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);

        //可以把int转换成string
        expression = "appleService.increamentStr(lineInt.toString())";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);

        //TODO 不可以
        /*expression = "appleService.increamentStr(lineInt)";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);*/

        expression = "appleService.increamentInt(lineInt)";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);

        //TODO 不可以
        /*expression = "appleService.increamentInt(lineStr)";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);*/

        //可以把string转换成int
        expression = "appleService.increamentInt(lineStr.toInteger())";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);

        //也可以的
        expression = "appleService.increamentIntBase(lineInt)";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);

        expression = "appleService.add(\"${lineInt}\",lineInt,lineInt,lineInt,lineInt)";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);

        expression = "appleService.multiArgument(\"${lineInt}\",lineStr)";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);

        expression = "appleService.getValueFromMap(\"one\",'{\"one\":\"'+lineStr+'\",\"two\":2,\"100\":\"one hundred\"}',\"没查到的默认值\")";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);

        expression = "appleService.getValueFromMap(\"111111\",'{\"one\":\"1\",\"two\":2,\"100\":\"one hundred\"}',\"没查到的默认值\")";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);

        expression = "appleService.getValueFromMap(\"111111\",'{\"one\":\"1\",\"two\":2,\"100\":\"one hundred\"}',null)";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);

        expression = "appleService.arrFunction(\"arrayTest\",['a','b',lineInt,lineStr] as String[])";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);

        expression = "def strs = ['a','b',lineInt,lineStr] as String[];appleService.arrFunction(\"arrayTest\",strs)";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);

        expression = "try {def tmp = lineStr; return tmp;} catch(Exception ex) {return ex.toString();}";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);

        expression = "try {def tmp = buCunZai; return tmp;} catch(MissingPropertyException ex) {return \"bu cun zai\";}";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);

        //null可以识别
        expression = "try {def tmp = nullObj; if(tmp==null) return \"this is null\";return tmp;} catch(Exception ex) {return ex.toString();}";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "   -->  表达式：" + expression);

    }


    @Test
    public void performanceTest() {

        int totalTimes = 10000;

        Long begin = System.currentTimeMillis();
        for (int i = 0; i < totalTimes; i++) {
            Map<String, Object> infos = new HashMap<>();
            infos.put("orderPrice", 30.1);
            infos.put("returnMoney", 100.5);
            infos.put("deductMoney", 5);
            infos.put("user", "王二狗");
            infos.put("userType", "乘客");
            infos.put("orderNum", 1000023);
            infos.put("orderPrice", 36.8);
            infos.put("area", "北京");

            String expression = "orderPrice+returnMoney-deductMoney";
            Object result = GroovyUtil.mathCalculate(expression, infos);
            logger.info(result.toString() + "表达式：" + expression);

            expression = "尊敬的${user}${userType},您在${area}产生的订单编号为:${orderNum}的订单的费用为${orderPrice+orderPrice},请核对。 ";
            result = GroovyUtil.calculate(expression, infos);
            logger.info(result.toString() + "表达式：" + expression);

            expression = "尊敬的${user}${userType},您在${area}产生的\"订单\"编号为:${orderNum}的订单的费用为${orderPrice+orderPrice},请核对。 ";
            result = GroovyUtil.calculate(expression, infos);
            logger.info(result.toString() + "表达式：" + expression);


        }
        Long end = System.currentTimeMillis();
        logger.info(totalTimes * 3 + "次总耗时ms:" + (end-begin));
        logger.info("平均每次耗时ms:" + ((double)(end-begin))/(totalTimes * 3));
    }


}

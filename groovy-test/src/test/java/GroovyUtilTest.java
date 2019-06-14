
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.cc.AppleService;
import com.cc.GroovyUtil;
import groovy.lang.MissingPropertyException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        infos.put("intValue", 36);
        infos.put("doubleValue", 12.3456789);
        infos.put("booleanStr", "true");
        infos.put("strValue", "123");
        infos.put("text", "hello,world.2018   ");
        infos.put("dateStr", "2018-12-12 11:11:11");
        infos.put("seconds",1544584271);
        infos.put("jsonObject", JSON.parseObject("{\"book\":[\"b1\",\"b2\"]}"));
        infos.put("apiResult", "{\"status\":1,\"driverName\":\"李翠花\",\"nickName\":\"所谓伊人\"}");


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
        logger.info(functionResult + " 表达式：" + expression);

        //转换数据类型
        expression = "if(age==null) {return -1;};return age";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //转换数据类型
        expression = "intValue.toInteger()";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //转换数据类型
        expression = "intValue.toDouble()";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //转换数据类型
        expression = "intValue.toString()";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //转换数据类型
        expression = "if(intValue instanceof Integer) {return true;} else {return false;}";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //转换数据类型
        expression = "if(intValue instanceof String) {return true;} else {return false;}";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //转换数据类型
        expression = "if(intValue instanceof Double) {return true;} else {return false;}";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //转换数据类型
        expression = "if(intValue instanceof Long) {return true;} else {return false;}";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //取绝对值
        expression = "if(intValue < 0) {return -intValue;} else {return intValue;}";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //转换数据类型
        expression = "doubleValue.toInteger()";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //转换数据类型
        expression = "doubleValue.toString()";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //转换数据类型
        expression = "booleanStr.toBoolean()";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //字符串转数值
        expression = "strValue.toInteger()";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //字符串转数值
        expression = "strValue.toDouble()";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //字符串转数值
        expression = "strValue.toBoolean()";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //字符串转数值
        expression = "strValue.toLong()";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //字符串转数值
        expression = "strValue.toBigInteger()";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //数值精度处理,保留两位小数
        expression = "Math.round(doubleValue*100)/100";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //公式计算
        expression = " 10*doubleValue+intValue";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //需要对字符串需要截取，正则替换等
        expression = "text.size()";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //需要对字符串需要截取，正则替换等
        expression = "text.length()";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        expression = "text.trim()";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        expression = "text.replaceAll(\"hello\",\"HELLO\")";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        expression = "text.replaceAll('hello','HELLO')";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        expression = "text.contains(\"hello\")";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        expression = "text.substring(0,10)";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        expression = "text.startsWith(\"hello\")";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        expression = "text.endsWith(\" \")";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        expression = "text.equals(\"hello\")";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        expression = "text.toUpperCase()";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        expression = "text.toLowerCase()";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //正则匹配
        expression = "text.matches(\"\\\\w{5},\\\\w{5}\\\\.\\\\d{4}\\\\s*\")";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //正则表达式提取
        expression = "String regular=\"(\\\\w{5}),(\\\\w{5})\\\\.(\\\\d{4}).*\"; java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regular);  java.util.regex.Matcher matcher = pattern.matcher(text);if(matcher.find()){return matcher.group(3)} else {return '没有匹配到';}";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //时间日期 时间戳转秒 try catch
        expression = "try {String pattern = \"yyyy-MM-dd HH:mm:ss\";return new java.text.SimpleDateFormat(pattern).parse(dateStr).getTime()/1000; } catch (Exception e) { return 0; }";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //时间日期 时间戳转秒
        expression = "return new java.text.SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").parse(dateStr).getTime()/1000;";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //时间日期 秒转日期
        expression = "new java.text.SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").format(new java.util.Date(1000l * seconds))";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //字符串拼接
        expression = "\"所在区域是：\"+area";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //json对象数组转换成字符串
        expression = "jsonObject.toJSONString()";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //json对象解析
        expression = "com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(apiResult);\n" +
                "        if (jsonObject.containsKey(\"status\")&&jsonObject.getInteger(\"status\").equals(0)) { return com.alibaba.fastjson.JSONPath.eval(jsonObject, \"driverName\"); } else {return com.alibaba.fastjson.JSONPath.eval(jsonObject, \"nickName\");}\n";
        functionResult = GroovyUtil.mathCalculate(expression, infos);
        logger.info(functionResult + " 表达式：" + expression);

        //
        expression = "new Random().nextInt(1000)";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "。类型：" + result.getClass().toString() + "表达式：" + expression);

        //
        expression = "System.currentTimeMillis()";
        result = GroovyUtil.mathCalculate(expression, infos);
        logger.info(result.toString() + "。类型：" + result.getClass().toString() + "表达式：" + expression);

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

package com.cc;

import groovy.lang.Binding;

import groovy.lang.GroovyShell;

import groovy.lang.Script;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Title: GroovyShellExample
 * Description: GroovyShellExample
 * Date:  2018/8/4
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class GroovyShellExample {

    private static final Logger logger = LoggerFactory.getLogger(GroovyShellExample.class);

    private static Map<String, Class> scriptCache = new ConcurrentHashMap<String, Class>();

    public static void main(String args[]) {

        testTextReplace();
        testExpressionCalculate();
        testGroovyCalculate();
        testFixGCGroovyCalculate();
        //testGC();
        testShell();
    }

    private static void testShell(){
        Binding binding = new Binding();
        binding.setVariable("x", 10);
        binding.setVariable("language", "Groovy");
        GroovyShell shell = new GroovyShell(binding);
        Object value = shell.evaluate("println\"Welcome to $language\"; y = x * 2; z = x * 3; return x ");
        logger.info(value.toString());
        logger.info(binding.getVariable("y").toString());
        logger.info(binding.getVariable("z").toString());
    }


    private static void testGC(){
        while (true) {
            //testGroovyCalculate();
            testFixGCGroovyCalculate();
        }
    }


    private static void testTextReplace() {
        Map<String, Object> infos = new HashMap<>();
        infos.put("user", "王二狗");
        infos.put("userType", "乘客");
        infos.put("orderNum", 1000023);
        infos.put("orderPrice", 36.8);
        infos.put("area", "北京");

        String originalText = "尊敬的${user}${userType},您在${area}产生的订单编号:${orderNum}的费用为${orderPrice+orderPrice},请核对。 ";
        String result = textReplace(originalText, infos);
        logger.info(result);

        //单引号没问题
        originalText = "尊敬的${user}${userType},您在${area}产生的'订单'编号:${orderNum}的费用为${orderPrice+orderPrice},请核对。 ";
        result = textReplace(originalText, infos);
        logger.info(result);

        //双引号需要三个转义符
        originalText = "尊敬的${user}${userType},您在${area}产生的\\\"订单\\\"编号:${orderNum}的费用为${orderPrice+orderPrice},请核对。 ";
        result = textReplace(originalText, infos);
        logger.info(result);





        /**
         //既可以使用${user}，也可以使用$user，但是$user如果和后面的字符连起来，会有无法识别的问题。
         originalText = "尊敬的$user$userType,您在$area产生的订单编号:${orderNum}的费用为${orderPrice},请核对。 ";
         result = textReplace(originalText, infos);
         logger.info(result);
         */

    }


    public static String textReplace(String textContent, Map<String, Object> infos) {

        if (textContent != null) {
            textContent = "\"" + textContent + "\"";

            Binding binding = new Binding();
            GroovyShell shell = new GroovyShell(binding);
            if (!infos.isEmpty()) {
                for (Map.Entry<String, Object> entry : infos.entrySet()) {
                    binding.setVariable(entry.getKey(), entry.getValue());
                }
            }

            return shell.evaluate(textContent).toString();

        } else {
            return "";
        }
    }


    private static void testExpressionCalculate() {
        Map<String, Object> infos = new HashMap<>();
        infos.put("orderPrice", 30.1);
        infos.put("returnMoney", 100.5);
        infos.put("deductMoney", 5);
        infos.put("person", new Person(10));
        infos.put("idArray", new int[]{1,2,4});
        String expression = "orderPrice+returnMoney-deductMoney";
        Object result = expressionCalculate(expression, infos);
        logger.info(expression + "=" + result.toString() + "。类型：" + result.getClass().toString());

        //对象
        expression = "3*person.age";
        result = expressionCalculate(expression, infos);
        logger.info(expression + "=" + result.toString() + "。类型：" + result.getClass().toString());

        expression = "4*deductMoney";
        result = expressionCalculate(expression, infos);
        logger.info(expression + "=" + result.toString() + "。类型：" + result.getClass().toString());

        expression = "2*orderPrice+returnMoney-deductMoney/2";
        result = expressionCalculate(expression, infos);
        logger.info(expression + "=" + result.toString() + "。类型：" + result.getClass().toString());

        expression = "(1 * orderPrice+returnMoney-deductMoney)/2";
        result = expressionCalculate(expression, infos);
        logger.info(expression + "=" + result.toString() + "。类型：" + result.getClass().toString());

        //布尔表达式
        expression = "orderPrice>2";
        result = expressionCalculate(expression, infos);
        logger.info(expression + "=" + result.toString() + "。类型：" + result.getClass().toString());

        expression = "orderPrice>2 & orderPrice==2";
        result = expressionCalculate(expression, infos);
        logger.info(expression + "=" + result.toString() + "。类型：" + result.getClass().toString());

        expression = "2 in idArray";
        result = expressionCalculate(expression, infos);
        logger.info(expression + "=" + result.toString() + "。类型：" + result.getClass().toString());

        expression = "3 in idArray";
        result = expressionCalculate(expression, infos);
        logger.info(expression + "=" + result.toString() + "。类型：" + result.getClass().toString());


        // ${orderPrice} 无法识别
/*
        expression = "${orderPrice+returnMoney-deductMoney}";
        result = expressionCalculate(expression, infos);
        logger.info(expression + "=" + result.toString() + "。类型：" + result.getClass().toString());
*/


        // $orderPrice 无法识别
        /*expression = "$orderPrice+returnMoney-deductMoney";
        result = expressionCalculate(expression, infos);
        logger.info(result);*/
    }


    public static Object expressionCalculate(String expression, Map<String, Object> infos) {

        if (expression != null) {
            Binding binding = new Binding();
            if (!infos.isEmpty()) {
                for (Map.Entry<String, Object> entry : infos.entrySet()) {
                    binding.setVariable(entry.getKey(), entry.getValue());
                }
            }
            GroovyShell shell = new GroovyShell(binding);
            return shell.evaluate(expression);
        } else {
            return null;
        }
    }


    private static void testGroovyCalculate() {
        Map<String, Object> infos = new HashMap<>();
        infos.put("orderPrice", 30.1);
        infos.put("returnMoney", 100.5);
        infos.put("deductMoney", 5);
        infos.put("user", "王二狗");
        infos.put("userType", "乘客");
        infos.put("orderNum", 1000023);
        infos.put("orderPrice", 36.8);
        infos.put("area", "北京");
        infos.put("person", new Person(10));
        String expression = "${orderPrice+returnMoney-deductMoney}";
        Object result = groovyCalculate(expression, infos);
        logger.info(expression + "=" + result.toString() + "。类型：" + result.getClass().toString());

        //对象
        expression = "尊敬的${user}${userType},您在${area}产生的订单编号:${orderNum}的费用为${orderPrice+orderPrice},请核对。 ";
        result = groovyCalculate(expression, infos);
        logger.info(expression + "=" + result.toString() + "。类型：" + result.getClass().toString());
    }


    /**
     * 存在不停构造class的问题
     * @param groovyExpression
     * @param infos
     * @return
     */
    public static Object groovyCalculate(String groovyExpression, Map<String, Object> infos) {
        if (groovyExpression != null) {
            groovyExpression = "\"" + groovyExpression + "\"";
            Binding binding = new Binding();
            if (!infos.isEmpty()) {
                for (Map.Entry<String, Object> entry : infos.entrySet()) {
                    binding.setVariable(entry.getKey(), entry.getValue());
                }
            }
            GroovyShell shell = new GroovyShell(binding);
            //return shell.evaluate(groovyExpression);
            return shell.parse(groovyExpression).run();//.evaluate(groovyExpression);
        } else {
            return null;
        }
    }



    private static void testFixGCGroovyCalculate() {
        Map<String, Object> infos = new HashMap<>();
        infos.put("orderPrice", 30.1);
        infos.put("returnMoney", 100.5);
        infos.put("deductMoney", 5);
        infos.put("user", "王二狗");
        infos.put("userType", "乘客");
        infos.put("orderNum", 1000023);
        infos.put("orderPrice", 36.8);
        infos.put("area", "北京");
        infos.put("person", new Person(10));
        String expression = "${orderPrice+returnMoney-deductMoney}";
        Object result = fixGCGroovyCalculate(expression, infos);
        logger.info(expression + "=" + result.toString() + "。类型：" + result.getClass().toString());

        //对象
        expression = "尊敬的${user}${userType},您在${area}产生的订单编号:${orderNum}的费用为${orderPrice+orderPrice},请核对。 ";
        result = fixGCGroovyCalculate(expression, infos);
        logger.info(expression + "=" + result.toString() + "。类型：" + result.getClass().toString());
    }


    /**
     * 通过缓存解决了类过多的问题
     */
    public static Object fixGCGroovyCalculate(String groovyExpression, Map<String, Object> infos) {
        if (groovyExpression != null) {
            groovyExpression = "\"" + groovyExpression + "\"";
            Binding binding = new Binding();
            if (!infos.isEmpty()) {
                for (Map.Entry<String, Object> entry : infos.entrySet()) {
                    binding.setVariable(entry.getKey(), entry.getValue());
                }
            }

            Script shell = null;
            if (scriptCache.containsKey(groovyExpression)) {
                Class scriptClass = scriptCache.get(groovyExpression);
                shell = InvokerHelper.createScript(scriptClass, binding);
            } else {
                shell = new GroovyShell(binding).parse(groovyExpression);
                scriptCache.put(groovyExpression,shell.getClass());
            }

            return shell.run();
        } else {
            return null;
        }
    }


    static class Person {

        int age;

        public Person(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}

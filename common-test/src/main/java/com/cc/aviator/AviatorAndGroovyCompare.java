package com.cc.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: AviatorAndGroovyCompare
 * @date: 2024-05-11
 */

public class AviatorAndGroovyCompare {

    private static final Logger LOGGER = LoggerFactory.getLogger(AviatorAndGroovyCompare.class);

    private static final int TIMES = 10000;

    public static void main(String[] args) {
        System.out.println("第1次：");
        new AviatorAndGroovyCompare().aviatorTest();
        //new AviatorAndGroovyCompare().groovyTest();
        new AviatorAndGroovyCompare().groovyCacheTest();

        System.out.println("第2次：");
        new AviatorAndGroovyCompare().aviatorTest();
        //new AviatorAndGroovyCompare().groovyTest();
        new AviatorAndGroovyCompare().groovyCacheTest();

        System.out.println("第3次：");
        new AviatorAndGroovyCompare().aviatorTest();
        //new AviatorAndGroovyCompare().groovyTest();
        new AviatorAndGroovyCompare().groovyCacheTest();
    }

    private String aviatorTest() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("countryCode", "BR");
        paramMap.put("price", 1.5);
        paramMap.put("cityId", 55000199L);
        // 集合
        paramMap.put("cityIdSet", new HashSet<Long>() {{
            add(100L);
            add(200L);
            add(55000199L);
        }});
        String expression = "countryCode == \"BR\" && include(cityIdSet, cityId) && price >= 1.5";

        AviatorEvaluator.getInstance().compile(expression, true);

        long start = System.currentTimeMillis();
        boolean finalResult = true;
        for (int i = 0; i < TIMES; i++) {
            Expression script = AviatorEvaluator.getInstance().compile(expression, true);
            Object result = script.execute(paramMap);
            finalResult = finalResult && (Boolean) result;
        }
        long end = System.currentTimeMillis();
        String result = "aviator:" + finalResult + " TIMES:" + TIMES + " elapse:" + (end - start);
        System.out.println(result);
        return result;
    }

    private String groovyTest() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("countryCode", "BR");
        paramMap.put("price", 1.5);
        paramMap.put("cityId", 55000199L);
        // 集合
        paramMap.put("cityIdSet", new HashSet<Long>() {{
            add(100L);
            add(200L);
            add(55000199L);
        }});

        String expression = "countryCode == \"BR\" && cityId in cityIdSet && price >= 1.5";

        Binding binding = new Binding();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            binding.setVariable(entry.getKey(), entry.getValue());
        }

        long start = System.currentTimeMillis();
        boolean finalResult = true;
        for (int i = 0; i < TIMES; i++) {
            GroovyShell shell = new GroovyShell(binding);
            Object result = shell.evaluate(expression);
            finalResult = finalResult && (Boolean) result;
        }
        long end = System.currentTimeMillis();
        String result = "groovy:" + finalResult + " TIMES:" + TIMES + " elapse:" + (end - start);
        System.out.println(result);
        return result;
    }

    private String groovyCacheTest() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("countryCode", "BR");
        paramMap.put("price", 1.5);
        paramMap.put("cityId", 55000199L);
        // 集合
        paramMap.put("cityIdSet", new HashSet<Long>() {{
            add(100L);
            add(200L);
            add(55000199L);
        }});

        String expression = "countryCode == \"BR\" && cityId in cityIdSet && price >= 1.5";

        Binding binding = new Binding();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            binding.setVariable(entry.getKey(), entry.getValue());
        }

        Class scriptClass = new GroovyShell(binding).parse(expression).getClass();

        long start = System.currentTimeMillis();
        boolean finalResult = true;
        for (int i = 0; i < TIMES; i++) {
            Script script = InvokerHelper.createScript(scriptClass, binding);
            finalResult = finalResult && (Boolean) script.run();
        }
        long end = System.currentTimeMillis();
        String result = "groovyCache:" + finalResult + " TIMES:" + TIMES + " elapse:" + (end - start);
        System.out.println(result);
        return result;
    }
}

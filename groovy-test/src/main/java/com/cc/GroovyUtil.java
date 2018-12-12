package com.cc;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.InvokerHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Title: GroovyUtil
 * Description: GroovyUtil工具类，提供groovy表达式的计算。支持数值计算，布尔计算，文本中变量替换等。线程安全
 * Date:  2018/8/6
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class GroovyUtil {

    //缓存脚本编译生成的Script类，解决groovy解析脚本动态生成过多class类占满Perm区不断触发fullGC的bug。
    private static final Map<String, Class> SCRIPT_CLASS_CACHE = new ConcurrentHashMap<String, Class>();

    /**
     * @param groovyExpression 符合groovy语法的表达式
     * @param infos            表达式需要使用的变量
     * @return 返回字符串
     */
    public static String calculate(String groovyExpression, Map<String, Object> infos) {
        if (groovyExpression != null) {
            String replaceChar = "~";
            if (groovyExpression.contains(replaceChar)) {
                replaceChar = "`";
                if (groovyExpression.contains(replaceChar)) {
                    replaceChar = "￥";
                    if (groovyExpression.contains(replaceChar)) {
                        replaceChar = "`￥~";
                        if (groovyExpression.contains(replaceChar)) {
                            return groovyExpression;
                        }
                    }
                }
            }
            groovyExpression = groovyExpression.replaceAll("\"",replaceChar);
            groovyExpression = "\"" + groovyExpression + "\"";
            Binding binding = createBinding(infos);
            Script shell = createScript(groovyExpression, binding);
            String result = shell.run().toString();
            return result.replaceAll(replaceChar,"\"");
        } else {
            return null;
        }
    }




    /**
     * 这个是数学公式计算的方法。返回值类型代表计算结果的类型
     *
     * @param mathExpression 数学表达式,变量不包含$符号 例如：(1 * orderPrice+returnMoney-deductMoney)/2 + 1000
     * @param infos          计算表达式需要的变量
     * @return 返回计算结果，class表示数据类型
     */
    public static Object mathCalculate(String mathExpression, Map<String, Object> infos) {
        if (mathExpression != null) {
            Binding binding = createBinding(infos);
            Script shell = createScript(mathExpression, binding);
            return shell.run();
        } else {
            return null;
        }
    }


    private static Script createScript(String groovyExpression, Binding binding) {
        Script script = null;
        if (SCRIPT_CLASS_CACHE.containsKey(groovyExpression)) {
            Class scriptClass = SCRIPT_CLASS_CACHE.get(groovyExpression);
            script = InvokerHelper.createScript(scriptClass, binding);
        } else {
            script = new GroovyShell(binding).parse(groovyExpression);
            SCRIPT_CLASS_CACHE.put(groovyExpression, script.getClass());
        }
        return script;
    }

    private static Binding createBinding(Map<String, Object> infos) {
        //Binding binding = new Binding();//可以切换
        Binding binding = new ReturnNullValueBinding();//自己搞的一个容错的Binding
        if (!infos.isEmpty()) {
            for (Map.Entry<String, Object> entry : infos.entrySet()) {
                binding.setVariable(entry.getKey(), entry.getValue());
            }
        }
        return binding;
    }

}

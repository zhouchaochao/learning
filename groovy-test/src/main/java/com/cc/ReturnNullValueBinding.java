package com.cc;

import groovy.lang.Binding;
import groovy.lang.MissingPropertyException;

import java.util.Map;

/**
 * GroovyUtil 中可以使用这个Binding。缺少变量时也不会报错
 */
public class ReturnNullValueBinding extends Binding {

    public ReturnNullValueBinding() {
    }

    /**
     * 原来的binding没有参数时会报错，改为返回null
     * @param name
     * @return
     */
    @Override
    public Object getVariable(String name) {
        Map variables = getVariables();
        if (variables == null) {
            throw new MissingPropertyException(name, this.getClass());
        } else {
            Object result = variables.get(name);
            if (result == null && !variables.containsKey(name)) {
                return null;//不抛出异常，而是返回null
            } else {
                return result;
            }
        }
    }
}

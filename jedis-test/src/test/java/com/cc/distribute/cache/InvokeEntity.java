package com.cc.distribute.cache;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: InvokeEntity
 * Description: InvokeEntity
 * Date:  2018/8/30
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class InvokeEntity {

    String className = "";
    String methodName = "";
    List<Class> paramsType = new ArrayList<>();
    List<Object> paramsValue = new ArrayList<>();

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<Class> getParamsType() {
        return paramsType;
    }

    public void setParamsType(List<Class> paramsType) {
        this.paramsType = paramsType;
    }

    public void addParamsType(Class paramsType) {
        this.paramsType.add(paramsType);
    }

    public List<Object> getParamsValue() {
        return paramsValue;
    }

    public void setParamsValue(List<Object> paramsValue) {
        this.paramsValue = paramsValue;
    }

    public void addParam(Object param) {
        this.paramsValue.add(param);
        this.paramsType.add(param.getClass());
    }
}

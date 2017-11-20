package com.jd.zk;

import com.alibaba.fastjson.JSONObject;

/**
 * @author : wutao
 * @version : Worker.java 2014/08/06 17:22
 * @copyright www.cc.com <http://www.cc.com/>
 */
public interface Worker {


    /**
     * worker 具体业务逻辑操作执行
     *
     * worker common 会根据返回的值去做一些事情，比如报警等
     *
     * 因此需要正确的返回运行结果
     *
     * @return true 如果执行成功，否则返回false
     */
    boolean run();


    /**
     * worker type与worker一一对应
     *
     * worker type （用在结点路径当中）
     * @return
     */
    String getWorkerType();

    /**
     * worker执行时间的表达式
     *
     * @return
     */
    String cronExpression();

    /**
     *
     * @return 返回worker相关JSON格式参数(worker业务相关参数),如果没有可以返回null
     */
    JSONObject getWorkerParameters();


    /**
     *
     * @return 到达执行立即执行而不受上次worker是否有没有执行完没关系,返回true，否则返回false
     */
    boolean isImmediate();

    /**
     *
     * @return worker的状态
     */
    String status();

    /**
     * 提供给worker具体实现类，销毁资源的方法
     */
    void destroy();


    /**
     * 提供给worker具体实现类，初始化的方法
     *
     * worker执行前的准备工作可以在此方法中完成
     *
     */
    void init();
}

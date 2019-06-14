package com.cc.distribute.cache;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Title: CacheTest
 * Description: CacheTest
 * Date:  2018/8/30
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class CacheTest {

    private static final Logger logger = LoggerFactory.getLogger(CacheTest.class);



    @Test
    public void abc() throws Exception{

        FormCache formCache = new FormCache();
        formCache.setInvalidate("10",2l);

    }




}

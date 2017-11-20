/**
 *  ContextFactory.java Created on 2012-5-9 上午10:57:23
 * 
 *  Copyright (c) 2012 by www.cc.com.
 */
package com.jd.zk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * Title: Bean工厂，保存了spring加载的applicationContext<br>
 * Description: <br>
 * Company: www.cc.com <br>
 * 
 * @author <a href=mailto:zhanggeng@cc.com>章耿</a>
 */
public class PropertyFactory {
	
	private static Logger logger = LoggerFactory.getLogger(PropertyFactory.class);
	
	private static Properties properties = new Properties();
	
	public PropertyFactory(String... resource){
		try {
			for (String fileName : resource) {
				InputStream inputStream = PropertyFactory.class.getClassLoader().getResourceAsStream(fileName);
				properties.load(inputStream);
				inputStream.close();
				
			}
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		
	}
	
	public static Object getProperty(String key){
		return  properties.get(key);
	}
    public static String getProperty(String key,String defaultValue){
		return  properties.getProperty(key,defaultValue);
	}
	public static Set<Object> getPropertyKeys(){
		return  properties.keySet();
	}
	
	
	public static void setProperty(String key,Object value){
		properties.put(key, value);
	}
	
}

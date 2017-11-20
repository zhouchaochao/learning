
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Title: TimeLimitCache  这是把map当做set用
 * Description: TimeLimitCache
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/10/10
 *
 * @author <a href=mailto:zhouzhichao@cc.com>chaochao</a>
 */
public class TimeLimitCache {

    private final static Logger logger = LoggerFactory.getLogger(TimeLimitCache.class);

    //其实只有键有用，值没什么用，只是让它不为null
    private static final LoadingCache<String, String> MY_CACHE = CacheBuilder
            .newBuilder().concurrencyLevel(10)
            .expireAfterWrite(10, TimeUnit.SECONDS)//失效时间
            .build(new CacheLoader<String, String>() {
                public String load(String key) throws Exception {
                    //logger.info("加载数据" + key);
                    return null;
                }
            });


    public static String getCache(String key) {
        try {
            return MY_CACHE.get(key);
        } catch (Exception e) {
            logger.info("获取缓存数据失败：" + key);
        }
        return null;
    }

    public static void setCache(String key, String value) {
        MY_CACHE.put(key, value);
    }


    public static void main(String[] args) {

        try {
            TimeLimitCache.setCache("1", "chao");
            //TimeLimitCache.setCache("2", null);//不能存null
            logger.info(TimeLimitCache.getCache("1"));

            Thread.sleep(6000);
            logger.info(TimeLimitCache.getCache("1"));
            logger.info(TimeLimitCache.getCache("2"));

            Thread.sleep(6000);
            logger.info(TimeLimitCache.getCache("1"));

            TimeLimitCache.setCache("1", "chao");
            Thread.sleep(6000);
            logger.info(TimeLimitCache.getCache("1"));

        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }
}

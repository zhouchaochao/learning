import com.cc.util.ThreadLocalUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: ThreadLocalUtilTest
 * Description: ThreadLocalUtilTest
 * Date:  2018/11/6
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class ThreadLocalUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(ThreadLocalUtilTest.class);

    @Test
    public void aTest(){
        ThreadLocalUtil.set("age", "1");

        final Object tmp = ThreadLocalUtil.get("age");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ThreadLocalUtil.set("age", tmp);
                    Thread.sleep(2000);
                    logger.info("子线程中获取到：" + ThreadLocalUtil.get("age"));
                    ThreadLocalUtil.set("age", null);
                    logger.info("清理后子线程中获取到：" + ThreadLocalUtil.get("age"));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }).start();

        logger.info("外部线程获取到：" + ThreadLocalUtil.get("age"));

        try {
            Thread.sleep(4000);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        logger.info("清理后外部线程获取到：" + ThreadLocalUtil.get("age"));
    }


}

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Format;

/**
 * Title: JsonTest
 * Description: JsonTest
 * Date:  2019/2/1
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class JsonTest {

    private static final Logger logger = LoggerFactory.getLogger(JsonTest.class);

    @Test
    public void json32FieldsBugTest(){
        Apple apple = new Apple();
        for (int i = 1; i <= 32; i++) {
            //System.out.println("s" + i + "= \"a" + i + "\";");
        }
        String jsonStr = JSON.toJSONString(apple);
        System.out.println("jsonStr:" + jsonStr);
        Apple appleDeserializeFromJson = JSON.parseObject(jsonStr, Apple.class);

    }

}

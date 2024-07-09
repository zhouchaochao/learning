package com.cc.aviator;

import com.cc.leetCode.HappyNumber;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: AviatorTest
 * @date: 2024-05-11
 */

public class AviatorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AviatorTest.class);

    public static void main(String[] args) {
        new AviatorTest().test();
    }

    private void test() {
        String result = AviatorEvaluator.execute("1.2 + 3 + 4").toString();
        System.out.println(result);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("countryCode", "BR");
        paramMap.put("price", 1.5);
        result = AviatorEvaluator.execute("countryCode == \"BR\"", paramMap).toString();
        System.out.println(result);

        result = AviatorEvaluator.execute("countryCode == \"MX\"", paramMap).toString();
        System.out.println(result);

        result = AviatorEvaluator.execute("countryCode == \"BR\" && price >= 1.5", paramMap).toString();
        System.out.println(result);

        result = AviatorEvaluator.execute("countryCode == \"BR\" && price > 1.5?1:0", paramMap).toString();
        System.out.println(result);

        // 加了一个缓存
        result = AviatorEvaluator.compile("countryCode == \"BR\" && price > 1.5?1:0", true).execute(paramMap).toString();
        System.out.println(result);

        Expression script = AviatorEvaluator.getInstance().compile("println('Hello, AviatorScript!');", true);
        System.out.println(script.execute());

        script = AviatorEvaluator.getInstance().compile("countryCode == \"BR\" && price >= 1.5?1:0", true);
        System.out.println(script.execute(paramMap));

        // 集合
        paramMap.put("cityIdSet", new HashSet<Long>() {{
            add(100L);
            add(200L);
            add(55000199L);
        }});
        paramMap.put("cityId", 200L);
        script = AviatorEvaluator.getInstance().compile("include(cityIdSet, cityId)", true);
        System.out.println(script.execute(paramMap));

        paramMap.put("cityId", 300L);
        script = AviatorEvaluator.getInstance().compile("include(seq.set(200, 300), cityId)", true);
        System.out.println(script.execute(paramMap));

        paramMap.put("cityId", 55000199L);
        script = AviatorEvaluator.getInstance().compile("countryCode == \"BR\" && include(seq.set(55000197, 55000198, 55000199), cityId) && price >= 1.5", true);
        System.out.println(script.execute(paramMap));

        paramMap.put("cityId", 55000199L);
        script = AviatorEvaluator.getInstance().compile("countryCode == \"BR\" && include(cityIdSet, cityId) && price >= 1.5", true);
        System.out.println(script.execute(paramMap));

        paramMap.put("cityId", 55000199L);
        script = AviatorEvaluator.getInstance().compile("countryCode == \"XX\" && (price > 3 || price < 2)", true);
        System.out.println(script.execute(paramMap));

        paramMap.put("cityId", 55000199L);
        script = AviatorEvaluator.getInstance().compile("countryCode == \"XX\" && price > 3 || price < 2", true);
        System.out.println(script.execute(paramMap));
    }

}

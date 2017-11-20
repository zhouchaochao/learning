package com.jd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: IpToIntMain
 * Description: IpToIntMain  将一个ip转换成用int表示
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2017/1/10
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class IpToIntMain {

    private static Logger logger = LoggerFactory.getLogger(IpToIntMain.class);

    public static void main(String[] args){

        //将一个ip转换成int表示
        String ip1 = "1.2.3.5";
        String ip2 = "";
        int ipInt = 0;
        System.out.println("ip1：" + ip1);

        String[] items = ip1.split("\\.");
        for (int i = 0; i < items.length; i++) {
            ipInt = (ipInt << 8) | Integer.parseInt(items[i]);
        }
        System.out.println("ip转换成int表示：" + Integer.toHexString(ipInt));

        for (int i = 0; i < 4; i++) {
            ip2 = ((ipInt >> 8 * i) & 0x000000ff) + "." + ip2;
        }
        ip2 = ip2.substring(0,ip2.length()-1);
        System.out.println("ip2：" + ip2);
    }

}

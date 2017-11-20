package com.cc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Title: LocalIp
 * Description: LocalIp 获取本机IP
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2017/5/4
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class LocalIp {

    private static Logger logger = LoggerFactory.getLogger(LocalIp.class);

    public static void main(String[] args){

        try {
            getLocalIp1();
            getLocalIp2();
            getLocalIp3();// 这个输出正确
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }


    /**
     *
     * linux输出：ip:127.0.0.1 hostName:A01-R11-224F10-I122-107.JD.LOCAL
     * windows输出：ip:10.12.165.26 hostName:ZB-B6F1J32
     */
    public static void getLocalIp1(){
        try{
            InetAddress addr = InetAddress.getLocalHost();
            logger.info("getLocalIp1 ip:" + addr.getHostAddress() + " hostName:" + addr.getHostName());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
    }


    /**
     *
     * linux输出：
     * hostName:eth0 ip：fe80:0:0:0:a6dc:beff:feee:5bda%eth0
     * hostName:eth0 ip：172.20.122.107
     * hostName:lo ip：127.0.0.1
     * hostName:lo ip：0:0:0:0:0:0:0:1%lo
     *
     * windows输出：
     * hostName：lo ip:127.0.0.1
     * hostName：lo ip:0:0:0:0:0:0:0:1
     * hostName：net4 ip:fe80:0:0:0:0:5efe:c0a8:4501%net4
     * hostName：net4 ip:fe80:0:0:0:0:5efe:c0a8:e801%net4
     * hostName：eth3 ip:10.12.165.26
     * hostName：eth3 ip:fe80:0:0:0:70f4:f3dd:6c9c:324f%eth3
     * hostName：net5 ip:fe80:0:0:0:0:5efe:a0c:a51a%net5
     * hostName：eth4 ip:192.168.232.1
     * hostName：eth4 ip:fe80:0:0:0:c084:1e9c:56d3:b3dd%eth4
     * hostName：eth5 ip:192.168.69.1
     * hostName：eth5 ip:fe80:0:0:0:f5ef:7feb:2eb7:bd34%eth5
     *
     */
    public static void getLocalIp2(){
        try{
            Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
            while (n.hasMoreElements()) {
                NetworkInterface e = n.nextElement();
                Enumeration<InetAddress> a = e.getInetAddresses();
                while (a.hasMoreElements()) {
                    InetAddress addr = a.nextElement();
                    logger.info("getLocalIp2 hostName：" + e.getName() + " ip:" + addr.getHostAddress());
                }
            }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * 这个输出正确
     *
     * linux输出：
     * ip:172.20.122.107 hostName:172.20.122.107
     *
     * windows输出：
     * ip:10.12.165.26 hostName:ZB-B6F1J32
     */
    public static void getLocalIp3(){
        try{
            //JSF工具类
            InetAddress addr = NetUtils.getLocalAddress();
            if (addr != null) {
                logger.info("getLocalIp3 ip:" + addr.getHostAddress() + " hostName:" + addr.getHostName());
            }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
    }

}

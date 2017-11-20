package com.jd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Properties;

/**
 * Title: SystemProperty
 * Description: SystemProperty 获取系统变量
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2017/3/16
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class SystemProperty {

    private static Logger logger = LoggerFactory.getLogger(SystemProperty.class);

    public static void main(String[] args){
        try{
            property();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private static void property() throws UnknownHostException {
        Runtime r = Runtime.getRuntime();
        Properties props = System.getProperties();
        InetAddress addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress();
        Map<String, String> map = System.getenv();
        String userName = map.get("USERNAME");// 获取用户名
        String computerName = map.get("COMPUTERNAME");// 获取计算机名
        String userDomain = map.get("USERDOMAIN");// 获取计算机域名
        System.out.println("用户名:    " + userName);
        System.out.println("计算机名:    " + computerName);
        System.out.println("计算机域名:    " + userDomain);
        System.out.println("本地ip地址:    " + ip);
        System.out.println("本地主机名:    " + addr.getHostName());
        System.out.println("JVM可以使用的总内存:    " + r.totalMemory());
        System.out.println("JVM可以使用的剩余内存:    " + r.freeMemory());
        System.out.println("JVM可以使用的处理器个数:    " + r.availableProcessors());
        System.out.println("Java的运行环境版本：    " + props.getProperty("java.version"));
        System.out.println("Java的运行环境供应商：    " + props.getProperty("java.vendor"));
        System.out.println("Java供应商的URL：    " + props.getProperty("java.vendor.url"));
        System.out.println("Java的安装路径：    " + props.getProperty("java.home"));
        System.out.println("Java的虚拟机规范版本：    " + props.getProperty("java.vm.specification.version"));
        System.out.println("Java的虚拟机规范供应商：    " + props.getProperty("java.vm.specification.vendor"));
        System.out.println("Java的虚拟机规范名称：    " + props.getProperty("java.vm.specification.name"));
        System.out.println("Java的虚拟机实现版本：    " + props.getProperty("java.vm.version"));
        System.out.println("Java的虚拟机实现供应商：    " + props.getProperty("java.vm.vendor"));
        System.out.println("Java的虚拟机实现名称：    " + props.getProperty("java.vm.name"));
        System.out.println("Java运行时环境规范版本：    " + props.getProperty("java.specification.version"));
        System.out.println("Java运行时环境规范供应商：    " + props.getProperty("java.specification.vender"));
        System.out.println("Java运行时环境规范名称：    " + props.getProperty("java.specification.name"));
        System.out.println("Java的类格式版本号：    " + props.getProperty("java.class.version"));
        System.out.println("Java的类路径：    " + props.getProperty("java.class.path"));
        System.out.println("加载库时搜索的路径列表：    " + props.getProperty("java.library.path"));
        System.out.println("默认的临时文件路径：    " + props.getProperty("java.io.tmpdir"));
        System.out.println("一个或多个扩展目录的路径：    " + props.getProperty("java.ext.dirs"));
        System.out.println("操作系统的名称：    " + props.getProperty("os.name"));
        System.out.println("操作系统的构架：    " + props.getProperty("os.arch"));
        System.out.println("操作系统的版本：    " + props.getProperty("os.version"));
        System.out.println("文件分隔符：    " + props.getProperty("file.separator"));
        System.out.println("路径分隔符：    " + props.getProperty("path.separator"));
        System.out.println("行分隔符：    " + props.getProperty("line.separator"));
        System.out.println("用户的账户名称：    " + props.getProperty("user.name"));
        System.out.println("用户的主目录：    " + props.getProperty("user.home"));
        System.out.println("用户的当前工作目录：    " + props.getProperty("user.dir"));
    }


    /** result in Windows
    用户名:    zhouchaochao
    计算机名:    ZB-B6F1J32
    计算机域名:    360BUYAD
    本地ip地址:    10.12.165.31
    本地主机名:    ZB-B6F1J32
    JVM可以使用的总内存:    128974848
    JVM可以使用的剩余内存:    122158584
    JVM可以使用的处理器个数:    4
    Java的运行环境版本：    1.8.0_60
    Java的运行环境供应商：    Oracle Corporation
    Java供应商的URL：    http://java.oracle.com/
    Java的安装路径：    C:\Program Files\Java\jdk1.8.0_60\jre
    Java的虚拟机规范版本：    1.8
    Java的虚拟机规范供应商：    Oracle Corporation
    Java的虚拟机规范名称：    Java Virtual Machine Specification
    Java的虚拟机实现版本：    25.60-b23
    Java的虚拟机实现供应商：    Oracle Corporation
    Java的虚拟机实现名称：    Java HotSpot(TM) 64-Bit Server VM
    Java运行时环境规范版本：    1.8
    Java运行时环境规范供应商：    null
    Java运行时环境规范名称：    Java Platform API Specification
    Java的类格式版本号：    52.0
    Java的类路径：    C:\Program Files\Java\jdk1.8.0_60\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\deploy.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\ext\access-bridge-64.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\ext\cldrdata.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\ext\dnsns.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\ext\jaccess.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\ext\jfxrt.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\ext\localedata.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\ext\nashorn.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\ext\sunec.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\ext\sunjce_provider.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\ext\sunmscapi.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\ext\sunpkcs11.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\ext\zipfs.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\javaws.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\jce.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\jfr.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\jfxswt.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\management-agent.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\plugin.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\resources.jar;C:\Program Files\Java\jdk1.8.0_60\jre\lib\rt.jar;C:\Users\zhouchaochao\IdeaProjects\learning\test\target\classes;C:\Users\zhouchaochao\.m2\repository\log4j\log4j\1.2.17\log4j-1.2.17.jar;C:\Users\zhouchaochao\.m2\repository\org\slf4j\slf4j-api\1.7.2\slf4j-api-1.7.2.jar;C:\Users\zhouchaochao\.m2\repository\org\slf4j\slf4j-log4j12\1.7.2\slf4j-log4j12-1.7.2.jar;C:\Users\zhouchaochao\.m2\repository\com\jd\saf-zookeeper-common\2.1.0-SNAPSHOT\saf-zookeeper-common-2.1.0-SNAPSHOT.jar;C:\Users\zhouchaochao\.m2\repository\org\apache\zookeeper\zookeeper\3.4.6\zookeeper-3.4.6.jar;C:\Users\zhouchaochao\.m2\repository\com\google\guava\guava\17.0\guava-17.0.jar;C:\Users\zhouchaochao\.m2\repository\com\alibaba\fastjson\1.2.8\fastjson-1.2.8.jar;C:\Program Files (x86)\JetBrains\IntelliJ IDEA 15.0.2\lib\idea_rt.jar
    加载库时搜索的路径列表：    C:\Program Files\Java\jdk1.8.0_60\bin;C:\Windows\Sun\Java\bin;C:\Windows\system32;C:\Windows;C:\dlllib;C:\Program Files (x86)\Common Files\NetSarang;C:\Program Files\Java\jdk1.8.0_60\bin;C:\Program Files\Java\jdk1.8.0_60\jre\bin;C:\ProgramData\Oracle\Java\javapath;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;C:\mysql5.6.24\bin;.
    默认的临时文件路径：    C:\Users\ZHOUZH~1\AppData\Local\Temp\
    一个或多个扩展目录的路径：    C:\Program Files\Java\jdk1.8.0_60\jre\lib\ext;C:\Windows\Sun\Java\lib\ext
    操作系统的名称：    Windows 7
    操作系统的构架：    amd64
    操作系统的版本：    6.1
    文件分隔符：    \
    路径分隔符：    ;
    行分隔符：

    用户的账户名称：    zhouchaochao
    用户的主目录：    C:\Users\zhouchaochao
    用户的当前工作目录：    C:\Users\zhouchaochao\IdeaProjects\learning

     */

/** result in linux
    用户名:    null
    计算机名:    null
    计算机域名:    null
    本地ip地址:    192.168.200.198
    本地主机名:    200_198
    JVM可以使用的总内存:    251658240
    JVM可以使用的剩余内存:    243712704
    JVM可以使用的处理器个数:    4
    Java的运行环境版本：    1.8.0_60
    Java的运行环境供应商：    Oracle Corporation
    Java供应商的URL：    http://java.oracle.com/
    Java的安装路径：    /export/servers/jdk1.8.0_60/jre
    Java的虚拟机规范版本：    1.8
    Java的虚拟机规范供应商：    Oracle Corporation
    Java的虚拟机规范名称：    Java Virtual Machine Specification
    Java的虚拟机实现版本：    25.60-b23
    Java的虚拟机实现供应商：    Oracle Corporation
    Java的虚拟机实现名称：    Java HotSpot(TM) 64-Bit Server VM
    Java运行时环境规范版本：    1.8
    Java运行时环境规范供应商：    null
    Java运行时环境规范名称：    Java Platform API Specification
    Java的类格式版本号：    52.0
    Java的类路径：    ../conf/:../lib/commons-codec-1.6.jar:../lib/slf4j-log4j12-1.7.2.jar:../lib/jss-sdk-java-1.2.0-SNAPSHOT.jar:../lib/guava-14.0.jar:../lib/httpcore-4.2.1.jar:../lib/sigar-learning-1.0-SNAPSHOT.jar:../lib/commons-lang-2.6.jar:../lib/httpclient-4.2.1.jar:../lib/log4j-1.2.17.jar:../lib/jackson-core-asl-1.9.12.jar:../lib/joda-time-2.2.jar:../lib/slf4j-api-1.7.2.jar:../lib/fastjson-1.2.28.jar:../lib/sigar-1.6.4.jar:../lib/jackson-mapper-asl-1.9.12.jar:../lib/commons-logging-1.1.1.jar:../lib/commons-io-2.4.jar
    加载库时搜索的路径列表：    /usr/java/packages/lib/amd64:/usr/lib64:/lib64:/lib:/usr/lib
    默认的临时文件路径：    /tmp
    一个或多个扩展目录的路径：    /export/servers/jdk1.8.0_60/jre/lib/ext:/usr/java/packages/lib/ext
    操作系统的名称：    Linux
    操作系统的构架：    amd64
    操作系统的版本：    2.6.32-431.el6.x86_64
    文件分隔符：    /
    路径分隔符：    :
    行分隔符：

    用户的账户名称：    root
    用户的主目录：    /root
    用户的当前工作目录：    /export/zcc/sigar-learning-1.0-SNAPSHOT-assembly/bin
 */

}

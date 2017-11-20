package com.jd;

import com.jd.zk.CronExpression;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Title: Main
 * Description: Main
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/8/17
 *
 * @author <a href=mailto:zhouzhichao@cc.com>chaochao</a>
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] agrs) {
        try {

            printObj(new Person());

            //1.7 version       "transport_address": "inet[/172.28.141.15:9340]"
            //2.1 5.2 version   "transport_address": "172.28.140.46:30100"
            String ipPort = "inet[/172.28.141.15:9340]";
            //ipPort = "172.28.140.46:30100";
            if (ipPort.startsWith("i")) {
                ipPort = ipPort.substring(6);//去掉前面的 inet[/
                ipPort = ipPort.substring(0, ipPort.length() - 1);//去掉最后的]
            }
            System.out.println("ipPort:" + ipPort);

            if (true) return;

            //logger.info("hello cc");
            String[] arra = "".split(" ");
            int abc = arra.length;

            String tokens = "价格,品牌,适用人群,类别,类型,分类,材质,表盘形状,机芯类型,防水,表盘颜色,表带材质,日历显示,表带颜色,风格,秒表计时功能,包装单位,颜色,功能,剂型,症状,国产/进口,尺码,经营范围类别,进口类别,国家基本药物,储运条件,厂家授权,是否冷链,是否拆零,处方类型,用法,药品类别,是否医保,商品类别,包装,上市时间,款式,适用场景,产地,流行元素,规格,人群,国家及地区,容量,用途,图案,蓝帽标识,价位,年代,厚度,适用对象,重量,领型,尺寸";
            Map<String,String> results = new HashMap<>();
            for (String token : tokens.split(",")){
                results.put(token,"");
            }

            String filePath = "C:/Users/zhouzhichao/cate3_expandnames";
            File file = new File(filePath);
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {

                BufferedReader bufferedReader = null;
                try {
                    String encoding = "UTF-8";
                    InputStreamReader reader = new InputStreamReader(new FileInputStream(files[i]), encoding);//考虑到编码格式
                    bufferedReader = new BufferedReader(reader);

                    String lineTxt = null;
                    while ((lineTxt = bufferedReader.readLine()) != null) {

                        String[] arr = lineTxt.split(",");
                        if(arr.length == 4){
                            System.out.println(arr[2] + "-->" + arr[3]);
                        }else if(arr.length == 5 || arr.length == 8){
                            System.out.println(arr[2] + "-->" + lineTxt.substring(lineTxt.indexOf(arr[3])));
                        }else{
                            System.out.println("长度不符合要求： "+ arr.length +files[i].getAbsolutePath()  + "   lineTxt:" +lineTxt );
                        }
                    }
                } catch (Exception e) {
                    System.out.println("读取文件内容出错");
                    e.printStackTrace();
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e1) {
                        }
                    }
                }
            }

            String key = "品牌";
            String value = "松下 索尼 日立 JVC 三星 东芝 三洋 佳能";
            if(results.containsKey(key)){
                results.put(key,results.get(key)+ " " + value);
            }
/*
            key = "类别";
            value = "DV带摄像机 光盘摄像机 高清/专业摄像机 光盘+硬盘摄像机 硬盘摄像机 闪存摄像机";
            if(results.containsKey(key)){
                results.put(key,results.get(key)+ " " + value);
            }

            key = "品牌";
            value = "三星 东芝 三洋 佳能 CC abc";
            if(results.containsKey(key)){
                results.put(key,results.get(key)+ " " + value);
            }

            key = "类别";
            value = "DV带摄像机 光盘摄像机 cpu";
            if(results.containsKey(key)){
                results.put(key,results.get(key)+ " " + value);
            }*/

            for (Map.Entry<String,String> entry : results.entrySet()){
                String values = entry.getValue().trim();
                if (values.equals("")) {
                    continue;
                }
                String[] items = values.split(" ");
                Set<String> itemSet = new HashSet<>();
                for (String item : items){
                    itemSet.add(item);
                }
                String duplicateRemovedValue = "";
                Iterator<String> iterator = itemSet.iterator();
                while (iterator.hasNext()){
                    duplicateRemovedValue += iterator.next() + "，";
                }
                if (duplicateRemovedValue.length() > 0){//去掉最后多余的，
                    duplicateRemovedValue = duplicateRemovedValue.substring(0,duplicateRemovedValue.length()-1);
                }

                System.out.println(entry.getKey() + ":" + duplicateRemovedValue);
            }




            /*String filePath = "C:/Users/zhouzhichao/wareInput.txt";
            BufferedReader bufferedReader = null;
            try {
                String encoding = "GBK";
                InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath), encoding);//考虑到编码格式
                bufferedReader = new BufferedReader(reader);

                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    String[] arr = lineTxt.split("\t");
                    System.out.println(arr[3] + "\t" + arr[5].replaceAll("\u0001",";"));
                }
            } catch (Exception e) {
                System.out.println("读取文件内容出错");
                e.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e1) {
                    }
                }
            }*/


            if (true) return;


            String conditions = "简约 刺绣 镂空 图腾 明星款 拼接 拼色 编织 水钻 亮片 拉链装饰 渐变 钩花 车缝线 铆钉 复古 磨破 网状 串珠 褶皱 金属装饰 皮革拼接 蕾丝 多口袋 徽章 蜡染";

            String head = "<div class=\"form-group\">\n" +
                    "<label class=\"filterConditionLable\">abc</label><b>:</b>\n" +
                    "<select id=\"abc\" class=\"filterConditionSelect\" multiple=\"multiple\">";
            String tail = "</select>\n" +
                    "</div>";


            String[] arr = conditions.split(" ");
            System.out.println(head);
            for (String item:arr) {
                System.out.println("<option value=\""+item.trim()+"\">"+item.trim()+"</option>");
            }
            System.out.println(tail);

            if (1<2) return;


            String regularStr = "h5_(\\d{4}[_\\.]\\d{2}[_\\.]\\d{2})$";
            String index = "h5_2017_01_09";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
            Pattern regular = Pattern.compile("(apm|h5)_(\\d{4}[_\\.]\\d{2}[_\\.]\\d{2})$");
            Matcher matcher1 = regular.matcher("h5_2017_01_09");
            System.out.println(matcher1.matches());//必须先执行.matches()，后面的group才有用
            System.out.println(matcher1.groupCount());
            System.out.println("group(0):" + matcher1.group(0));
            System.out.println("group(1):" + matcher1.group(1));
            System.out.println("group(2):" + matcher1.group(2));
            System.out.println(getMatchDateString(matcher1,dateFormat));

            if (true) return;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
            logger.info("" + simpleDateFormat.parse("2016.08.20"));

            Pattern pattern = Pattern.compile("//");
            Matcher matcher = pattern.matcher("abc");
            if (matcher.matches()) {// 匹配
                String matcherStr = matcher.group(1);//索引中的日期字符串
            }

            String s = "/aaa_selectMaster/scheduleTask/server";
            String zkTopPath = "/aaa_selectMaster";
            int begin = s.indexOf("/", zkTopPath.length()) + 1;
            int end = s.lastIndexOf("/");
            s = s.substring(begin, end);
            logger.info(s);

            Date current = new Date();
            CronExpression cronExpression = new CronExpression("0 */15 20 * * ? 2017");
            Date nextTime = cronExpression.getNextValidTimeAfter(current);
            logger.info(simpleDateFormat.format(nextTime));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }


    }



    //提取日期字符串
    private static String getMatchDateString(Matcher matcher, DateFormat dateFormat) {
        for (int i = 1; i <= matcher.groupCount(); i++) {
            String matcherStr = matcher.group(i);//索引中的日期字符串
            try {
                dateFormat.parse(matcherStr);
                return matcherStr;
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return null;
    }

    private static void printObj(Object object){
        logger.info(object.toString());
    }

}

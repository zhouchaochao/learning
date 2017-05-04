package com.jd.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Title: WriteFileMethod 写文件方法
 * Description: WriteFileMethod
 *         参考：http://www.cnblogs.com/lovebread/archive/2009/11/23/1609122.html
 *
 *
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/11/3
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class WriteFileMethod {

    private static Logger logger = LoggerFactory.getLogger(WriteFileMethod.class);

    public static void main(String[] args){
        String fileName = "C:/Users/zhouzhichao/text2.txt";

        //writeFileWithBufferedWriter(fileName);
        writeFileWithFileWriter(fileName);

        //按方法A追加文件
        appendMethodA(fileName, "A ");
        appendMethodA(fileName, "A append end. \n");

        //按方法B追加文件
        appendMethodB(fileName, "B ");
        appendMethodB(fileName, "B append end. \n");

        //appendMethodA("C:/Users/zhouzhichao/text3.txt", "create file if not exist! A");//如果不存在，会创建文件，但是不会创建路径
        //appendMethodB("C:/Users/zhouzhichao/text4.txt", "create file if not exist! B");//如果不存在，会创建文件，但是不会创建路径
    }


    public static void writeFileWithBufferedWriter(String filePath) {

        BufferedWriter bufferedWriter = null;
        try {
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                        new FileOutputStream(file), encoding);//考虑到编码格式
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                bufferedWriter.write("write ");
                bufferedWriter.write("txt");
                bufferedWriter.write("\r\n天凉好个秋！");

            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e1) {
                }
            }
        }
    }


    public static void writeFileWithFileWriter(String fileName) {
        try {
            //如果文件不存在，会创建，如果路径不存在，抛出异常
            FileWriter writer = new FileWriter(fileName, false);//true表示以追加形式写文件,false表示覆盖原来的
            writer.write("haha");
            writer.write("\r\na");//换行无效 TODO //"\r\n"
            writer.write("\nb");
            writer.write("c");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * A方法追加文件：使用RandomAccessFile
     */
    public static void appendMethodA(String fileName, String content) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            //将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * B方法追加文件：使用FileWriter
     */
    public static void appendMethodB(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

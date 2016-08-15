package com.jd.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Title: ReadFileThread
 * Description: ReadFileThread
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/8/11
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class ReadFileThread implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(ReadFileThread.class);

    private String name = "";
    private String fileName;

    public ReadFileThread(String name, String fileName) {
        this.name = name;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            int times = 0;
            while (true) {
                Reader reader = null;
                logger.info(name + " read file" + fileName + " 次数：" + (++times));
                try {
                    //"以字符为单位读取文件内容，一次读多个字节
                    // 一次读多个字符
                    char[] tempchars = new char[ReadFile.charArrLength];
                    int charread = 0;
                    reader = new InputStreamReader(new FileInputStream(fileName));
                    // 读入多个字符到字符数组中，charread为一次读取字符数
                    while ((charread = reader.read(tempchars)) != -1) {
                        if (ReadFile.isPrint) {
                            logger.info(new String(tempchars));
                        }
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e1) {
                            logger.error(e1.getMessage(), e1);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
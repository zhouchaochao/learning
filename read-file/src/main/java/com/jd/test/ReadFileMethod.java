package com.jd.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.PropertyUtil;

import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Title: ReadFileMethod 读文件方法
 * Description: ReadFileMethod
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/11/3
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class ReadFileMethod {

    private static Logger logger = LoggerFactory.getLogger(ReadFileMethod.class);

    public static void main(String[] args) {
        String fileName = "C:/Users/zhouchaochao/text1.txt";


        File file = new File("C:/Users/zhouchaochao/createPathFile1.txt");
        if (file.isFile() && file.exists()) { //判断文件是否存在
            file.delete();
            logger.info("delete file:" + file);
        } else {
            logger.info("file.isFile():" + file.isFile() + " file.exists():" + file.exists());
            try {
                file.createNewFile();//只能创建文件。如果父目录不存在，创建失败
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File dir = new File("C:/Users/zhouchaochao/abc/def/createDir");
        if (dir.isDirectory()) {
            logger.info("isDirectory");
        } else {
            logger.info("not isDirectory");
            //dir.mkdirs();//可以创建多级目录。如果父目录不存在，创建。
            //dir.mkdir();//只能创建一级新目录。如果父目录不存在，失败。
        }


        //readFileByBytes(fileName);
        //readFileByChars(fileName);
        readFileByLines(fileName);
        //readFileByRandomAccess(fileName);
        //readTxtFile(fileName);

        //createPathFile("C:/Users/zhouchaochao/1/2/createPathFile1.txt");
    }

    /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     *
     * @param filePath
     */
    public static void readTxtFile(String filePath) {

        BufferedReader bufferedReader = null;
        try {
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
/*                InputStreamReader reader = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                bufferedReader = new BufferedReader(reader);*/

                bufferedReader = new BufferedReader(new FileReader(file));//FileReader 可以直接一步到位

                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    System.out.println(lineTxt);
                }
            } else {
                System.out.println("找不到指定的文件");
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


    /**
     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     */
    public static void readFileByBytes(String fileName) {
        File file = new File(fileName);
        InputStream in = null;
        try {
            System.out.println("以字节为单位读取文件内容，一次读一个字节：");
            // 一次读一个字节
            in = new FileInputStream(file);
            int tempbyte;
            while ((tempbyte = in.read()) != -1) {
                System.out.write(tempbyte);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try {
            System.out.println("以字节为单位读取文件内容，一次读多个字节：");
            // 一次读多个字节
            byte[] tempbytes = new byte[100];
            int byteread = 0;
            in = new FileInputStream(fileName);
            ReadFileMethod.showAvailableBytes(in);
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while ((byteread = in.read(tempbytes)) != -1) {
                System.out.write(tempbytes, 0, byteread);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     */
    public static void readFileByChars(String fileName) {
        File file = new File(fileName);
        Reader reader = null;
        try {
            System.out.println("以字符为单位读取文件内容，一次读一个字节：");
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                    System.out.print((char) tempchar);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("以字符为单位读取文件内容，一次读多个字节：");
            // 一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(fileName));
            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != '\r')) {
                    System.out.print(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            System.out.print(tempchars[i]);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static List<String> readFileByLines(String fileName) {
        List<String> lines = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                lines.add(tempString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * 随机读取文件内容
     */
    public static void readFileByRandomAccess(String fileName) {
        RandomAccessFile randomFile = null;
        try {
            System.out.println("随机读取一段文件内容：");
            // 打开一个随机访问文件流，按只读方式
            randomFile = new RandomAccessFile(fileName, "r");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 读文件的起始位置
            int beginIndex = (fileLength > 4) ? 4 : 0;
            // 将读文件的开始位置移到beginIndex位置。
            randomFile.seek(beginIndex);
            byte[] bytes = new byte[10];
            int byteread = 0;
            // 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
            // 将一次读取的字节数赋给byteread
            while ((byteread = randomFile.read(bytes)) != -1) {
                System.out.write(bytes, 0, byteread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e1) {
                }
            }
        }
    }


    /**
     * 显示输入流中还剩的字节数
     */
    private static void showAvailableBytes(InputStream in) {
        try {
            System.out.println("当前字节输入流中的字节数为:" + in.available());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //创建path，file
    public static void createPathFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            logger.info(file.getAbsolutePath());
            logger.info(file.getPath());
            File parentDir = file.getParentFile();
            logger.info(parentDir.getPath());
            parentDir.mkdirs();//创建目录。支持多级
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

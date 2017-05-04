package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 * Title: FileUtils 文件操作工具类
 * Description: FileUtils
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/11/3
 * @author <a>zhanggeng</a>
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class FileUtils {

    /**
     * slf4j Logger for this class
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 得到项目所在路径
     *
     * @return 项目所在路径
     */
    public static String getBaseDirName() {
        String fileName = null;
        // 先取classes
        java.net.URL url1 = FileUtils.class.getResource("/");
        if (url1 != null) {
            fileName = url1.getFile();
        } else {
            // 取不到再取lib
            String jarpath = getCodeBase(FileUtils.class);
            if (jarpath != null) {
                int jsfidx = jarpath.lastIndexOf("jsf-");
                if (jsfidx > -1) { // 如果有jsf-开头的jar包
                    fileName = jarpath.substring(0, jsfidx);
                } else {
                    int sepidx = jarpath.lastIndexOf(File.separator);
                    if (sepidx > -1) {
                        fileName = jarpath.substring(0, sepidx + 1);
                    }
                }
            }
        }
        // 将冒号去掉 “/”换成“-”
        if (fileName != null) {
            fileName = fileName.replace(":", "").replace(File.separator, "/")
                    .replace("/", "-");
            if (fileName.startsWith("-")) {
                fileName = fileName.substring(1);
            }
        } else {
            LOGGER.warn("can not parse webapp baseDir path");
            fileName = "UNKNOW_";
        }
        return fileName;
    }

    /**
     * 得到USER_HOME目录
     *
     * @param base
     *         用户目录下文件夹
     * @return 得到用户目录
     */
    public static String getUserHomeDir(String base) throws Exception {
        String userhome = System.getProperty("user.home");
        File file = new File(userhome, base);
        if (file.exists()) {
            if (!file.isDirectory()) {
                LOGGER.error("{} exists, but not directory", file.getAbsolutePath());
                throw new Exception(file.getAbsolutePath() + " exists, but not directory");
            }
        } else {
            file.mkdirs(); // 可能创建不成功
        }
        return file.getAbsolutePath();
    }

    /**
     * 读取文件内容
     *
     * @param file
     *            文件
     * @return 文件内容
     */
    public static String file2String(File file) throws IOException {
        if (file == null || !file.exists() || !file.isFile() || !file.canRead()) {
            return null;
        }
        FileReader reader = null;
        StringWriter writer = null;
        try {
            reader = new FileReader(file);
            writer = new StringWriter();
            char[] cbuf = new char[1024];
            int len = 0;
            while ((len = reader.read(cbuf)) != -1) {
                writer.write(cbuf, 0, len);
            }
            return writer.toString();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();

                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 字符流写文件 较快
     *
     * @param file
     *            文件
     * @param data
     *            数据
     */
    public static boolean string2File(File file, String data) throws IOException {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, false);
            writer.write(data);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return true;
    }


    /**
     * 得到类所在地址，可以是文件，也可以是jar包
     *
     * @param cls
     *         the cls
     * @return the code base
     */
    public static String getCodeBase(Class<?> cls) {

        if (cls == null)
            return null;
        ProtectionDomain domain = cls.getProtectionDomain();
        if (domain == null)
            return null;
        CodeSource source = domain.getCodeSource();
        if (source == null)
            return null;
        URL location = source.getLocation();
        if (location == null)
            return null;
        return location.getFile();
    }

}

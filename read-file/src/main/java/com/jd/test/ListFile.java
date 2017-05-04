package com.jd.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Title: ListFile
 * Description: ListFile
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2017/3/15
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class ListFile {

    private static Logger logger = LoggerFactory.getLogger(ListFile.class);

    private static String pathStr = "/export/Logs";

    public static void main(String[] args) {
        try {
            listDirectFiles(pathStr);

            //递归列出所有文件（不包含文件夹）
            List<Path> allChilds = listAllFilesInDirectory(Paths.get(pathStr));
            allChilds.forEach(path -> {logger.info("全路径：" + path.toString() + " --> " + path.getFileName());});

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listDirectFiles(String pathStr) throws Exception {

        File file = new File(pathStr);

        //列出直接孩子文件
        String[] directFils = file.list();
        for (int i = 0; i < directFils.length; i++) {
            System.out.println(directFils[i]);
        }

        //列出直接孩子文件
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            System.out.println((files[i].isFile() ? "文件" : "路径") + " getName:" + files[i].getName() + " getPath:" + files[i].getPath());
        }

        Path path = Paths.get(pathStr);
        System.out.println(pathStr + " 是文件夹吗:" + Files.isDirectory(path));
        Files.list(path).forEach(childPath -> {
            System.out.println(childPath + " isDirectory:" + Files.isDirectory(childPath) + " getFileName:" + childPath.getFileName());
        });

        return;
    }


    //递归遍历
    private static List<Path> listAllFilesInDirectory(Path path) throws Exception {
        List<Path> result = new ArrayList<>();
        boolean isDire = Files.isDirectory(path);
        if (isDire) {
            Files.list(path).forEach(path1 -> {
                if (Files.isDirectory(path1)) {
                    try {
                        result.addAll(listAllFilesInDirectory(path1));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    result.add(path1);
                }
            });
        } else {
            result.add(path);
        }
        return result;
    }

}

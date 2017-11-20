package com.jd.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Title: AddPrefixSuffixForText 按行读取文件，为文件每行前面，后面添加额外的字符串,替换字符等
 * Description: AddPrefixSuffixForText
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/11/3
 *
 * @author <a href=mailto:zhouzhichao@cc.com>chaochao</a>
 */
public class AddPrefixSuffixForText {

    private static Logger logger = LoggerFactory.getLogger(AddPrefixSuffixForText.class);

    public static void main(String[] args){

        //String userhome = System.getProperty("user.home");// C:\Users\zhouzhichao
        //logger.info(userhome);
        //logger.info("当前系统的分隔符：" + File.separator);// \

        String inputFileName = "C:/Users/zhouzhichao/input.txt";//  "C:\\Users\\zhouzhichao\\text1.txt";//两种路径，Windows下都支持
        String outputFileName = "C:/Users/zhouzhichao/output.txt";//  "C:\\Users\\zhouzhichao\\text1.txt";//两种路径，Windows下都支持
        readFileAndWriteFile(inputFileName,outputFileName);
    }

    public static void readFileAndWriteFile(String inputFileName,String outputFileName){
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File inputFile = new File(inputFileName);
            File outputFile = new File(outputFileName);

            if (inputFile.isFile() && inputFile.exists()) { //判断文件是否存在
/*                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(inputFile));
                bufferedReader = new BufferedReader(inputStreamReader);*/
                bufferedReader = new BufferedReader(new FileReader(inputFile));//FileWriter直接一步到位
            }else{
                System.out.println("找不到指定的文件" + inputFile);
                return;
            }

            if (outputFile.isFile() && outputFile.exists()) { //判断文件是否存在
/*                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(outputFile), "GBK");//考虑到编码格式
                bufferedWriter = new BufferedWriter(outputStreamWriter);*/
                bufferedWriter = new BufferedWriter(new FileWriter(outputFile));//FileWriter直接一步到位
            } else {
                System.out.println("找不到指定的文件" + outputFile);
                return;
            }


            String line = null;
            int lineNum = 1;
            while ((line = bufferedReader.readLine()) != null) {
                //System.out.println("line " + lineNum + ": " + line.trim());
                System.out.println("putStr(result,\"" + line.trim() + "\",\"\");//");
                String outputStr = handleStr(line);
                //System.out.println(outputStr);
                bufferedWriter.write(outputStr);
                bufferedWriter.newLine();
                lineNum++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    //加上前缀，后缀，替换引号
    private static String handleStr(String str){
        String prefix = "s += \"";
        String suffix = "\";";

        //str = str.replaceAll("\"","\\\\\"");//将"替换成\"
        return prefix + str + suffix;
        //return str;
    }
}
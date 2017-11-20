import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.Date;

/**
 * Title: WriteCsvFile
 * Description: WriteCsvFile 逗号分隔值（Comma-Separated Values，CSV，有时也称为字符分隔值，
 * 因为分隔字符也可以不是逗号），其文件以纯文本形式存储表格数据（数字和文本）。
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2017/2/24
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class WriteCsvFile {

    private static Logger logger = LoggerFactory.getLogger(WriteCsvFile.class);

    public static void main(String[] args){

        String fileName = "C:/Users/zhouchaochao/csvFile.csv";
        //writeCsvFile(fileName);

        String[][] arrs = new String[3][3];
        for (int i = 0; i < 3; i++) {
            arrs[i] = new String[3];
            for (int j = 0; j < arrs[i].length; j++) {
                arrs[i][j] = i + j + "";
            }
        }

        writeCsvFile(fileName,arrs);
    }

    private static void writeCsvFile(String fileName){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(fileName,false);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            BufferedWriter bw = new BufferedWriter(outputStreamWriter);

            String split = ",";
            String sufix = "\n";//换行
            bw.write("a" + split + "b" + sufix);
            bw.write("1" + split + "2" + sufix);
            bw.write("z" + split + "z" + split + "c" + sufix);
            bw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void writeCsvFile(String fileName,String[][] arr){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(fileName,false);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            BufferedWriter bw = new BufferedWriter(outputStreamWriter);

            String split = ",";
            String sufix = "\n";//换行

            String writeLine = null;
            for (String[] lines : arr){
                writeLine = "";
                for(String token : lines){
                    writeLine += token + ",";
                }
                bw.write(writeLine.substring(0,writeLine.length()-1) + "\n");
            }

            bw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

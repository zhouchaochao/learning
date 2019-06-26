import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Title: ExcelFile
 * Description: ExcelFile 这个是使用jxl包，功能弱一点
 * Date:  2018/7/2
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class ExcelFile {

    private static final Logger logger = LoggerFactory.getLogger(ExcelFile.class);

    public static void main(String[] args) {
        writeExcel();
    }



    private static void writeExcel(){

        WritableWorkbook workbook = null;

        try {
            //Files.deleteIfExists(Paths.get(FileUtil.getPath(tempDir), excelFileName));

            // 创建xls文件
            // 测试发现xls可以，xlsx不行
            File file = new File("/Users/cc/logs/tmp/excel_test.xls");
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("s1", 0);
            setSheetHeader(sheet);

            setTableSheetContent(sheet, new String[]{"2017","小米","100"}, 1);
            setTableSheetContent(sheet, new String[]{"2018","华为","200"}, 2);
            setTableSheetContent(sheet, new String[]{"2019","魅族","200"}, 3);

            workbook.write();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }


    private static void setSheetHeader(WritableSheet sheet) throws WriteException {
        String[] headers = new String[]{"id", "name", "value"};
        for (int i = 0; i < headers.length; i++) {
            Label label = new Label(i, 0, headers[i]);
            sheet.addCell(label);
        }
    }


    private static void setTableSheetContent(WritableSheet sheet, String[] values, int row) throws WriteException {
        int column = 0;
        Label label0 = new Label(column, row, values[column]);
        sheet.addCell(label0);

        column++;
        Label label1 = new Label(column, row, values[column]);
        sheet.addCell(label1);

        column++;
        Label label2 = new Label(column, row, values[column]);
        sheet.addCell(label2);
    }

}

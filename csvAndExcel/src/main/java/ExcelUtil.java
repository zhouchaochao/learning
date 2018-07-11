import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Title: ExcelUtil
 * Description: ExcelUtil
 * Date:  2018/7/9
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class ExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";


    /**
     * 读取Excel，去掉空行空列
     * 注意：对于数值类型，会被转换成double。
     * @param filePath
     * @return
     */
    public static List<List<String>> readExcel(String filePath) {

        FileInputStream in = null;
        Workbook workBook = null;
        List<List<String>> excel_content = new ArrayList<List<String>>();

        try {
            in = new FileInputStream(filePath);
            if (filePath.endsWith(EXCEL_XLS)) {     //Excel&nbsp;2003
                workBook = new HSSFWorkbook(in);
            } else if (filePath.endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
                workBook = new XSSFWorkbook(in);
            }

            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);

            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算.  如果Excel总共10行，rowNumber=9
            logger.info("rowNumber:" + rowNumber);

            for (int i = 0; i <= rowNumber; i++) {//注意，这里是<=rowNumber
                Row row = sheet.getRow(i);

                List<String> rowResult = new ArrayList<String>();

                int columnNum = row.getLastCellNum();//如果有10列，columnNum=10
                for (int j = 0; j < columnNum; j++) {
                    Cell cell = row.getCell(j);
                    String cellValue = getStringCellValue(cell);
                    cellValue = preProcessData(cellValue);

                    if (!cellValue.isEmpty()) {
                        rowResult.add(cellValue);
                    }
                }

                if (!rowResult.isEmpty()) {
                    excel_content.add(rowResult);
                    logger.info("行:" + i + " 列长度:" + columnNum + " 内容:" + rowResult);
                } else {
                    logger.warn("有一个空行:" + "行:" + i + " 列长度:" + columnNum);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("数据读取成功.读取行数：" + excel_content.size());
        return excel_content;
    }



    public static void writeExcel(String filePath,List<List<String>> content) {

        if (content == null || content.isEmpty()) {
            return;
        }

        //第一步创建workbook
        Workbook wb = null;

        if (filePath.endsWith(EXCEL_XLS)) {     //Excel&nbsp;2003
            wb = new HSSFWorkbook();
        } else if (filePath.endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
            wb = new XSSFWorkbook();
        }

        //第二步创建sheet
        Sheet sheet = wb.createSheet("sheet1");

        List<String> heads = content.get(0);

        /*if (heads != null && !heads.isEmpty()) {
            //第三步创建行row:添加表头0行
            Row row = sheet.createRow(0);
            CellStyle style = wb.createCellStyle();

            for (int i = 0; i < heads.size(); i++) {
                //第四步创建单元格
                Cell cell = row.createCell(i); //第一个单元格
                cell.setCellValue(heads.get(i));
                cell.setCellStyle(style);
            }
        }*/


        for (int i = 0; i < content.size(); i++) {
            List<String> rowContent = content.get(i);
            //创建行
            Row row = sheet.createRow(i);
            for (int j = 0; j < rowContent.size(); j++) {
                //创建单元格并且添加数据
                row.createCell(j).setCellValue(rowContent.get(j));
            }
        }

        //第六步将生成excel文件保存到指定路径下
        try {
            FileOutputStream fout = new FileOutputStream(filePath);
            wb.write(fout);
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Excel文件生成成功...");
    }


    /**
     * 去掉空格，空字符串返回 ""
     *
     * @param input
     * @return
     */
    public static String preProcessData(String input) {

        if (input == null) {
            return "";
        }

        return input;
    }


    public static String getStringCellValue(Cell cell) {

        String strCell = "";
        if (cell == null) return strCell;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                strCell = cell.getRichStringCellValue().getString().trim();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                evaluator.evaluateFormulaCell(cell);
                CellValue cellValue = evaluator.evaluate(cell);
                strCell = String.valueOf(cellValue.getNumberValue());
                break;
            default:
                strCell = "";
        }
        return strCell;
    }


}

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Title: ExcelFileReadWrite
 * Description: ExcelFileReadWrite  支持.xls和.xlsx结尾的Excel文件
 * Date:  2018/7/2
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class ExcelFileReadWrite {

    private static final Logger logger = LoggerFactory.getLogger(ExcelFileReadWrite.class);

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";


    public static void main(String[] args) {
        writeExcel("/Users/cc/logs/tmp/excel/excel2003.xls");
        writeExcel("/Users/cc/logs/tmp/excel/excel2010.xlsx");

        readExcel("/Users/cc/logs/tmp/excel/excel2003.xls");
        readExcel("/Users/cc/logs/tmp/excel/excel2010.xlsx");

        List<List<String>> content = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                row.add("value"+ i + "-" + j);
            }
            content.add(row);
        }
        ExcelUtil.writeExcel("/Users/cc/logs/tmp/excel/excelUtilTest.xlsx",content);
        List<List<String>> readContent = ExcelUtil.readExcel("/Users/cc/logs/tmp/excel/excelUtilTest.xlsx");
        logger.info(Arrays.toString(readContent.get(0).toArray()));
    }


    public static void writeExcel(String filePath) {

        //第一步创建workbook
        Workbook wb = null;

        if (filePath.endsWith(EXCEL_XLS)) {     //Excel&nbsp;2003
            wb = new HSSFWorkbook();
        } else if (filePath.endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
            wb = new XSSFWorkbook();
        }

        //第二步创建sheet
        Sheet sheet = wb.createSheet("测试");

        //第三步创建行row:添加表头0行
        Row row = sheet.createRow(0);
        CellStyle style = wb.createCellStyle();
        //style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //居中

        //第四步创建单元格
        Cell cell = row.createCell(0); //第一个单元格
        cell.setCellValue("姓名");
        cell.setCellStyle(style);


        cell = row.createCell(1);         //第二个单元格
        cell.setCellValue("年龄");
        cell.setCellStyle(style);


        //第五步插入数据

        for (int i = 0; i < 5; i++) {
            //创建行
            row = sheet.createRow(i + 1);
            //创建单元格并且添加数据
            row.createCell(0).setCellValue("aa" + i);
            row.createCell(1).setCellValue(i);
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


    public static void readExcel(String finalXlsxPath) {
        FileInputStream in = null;
        Workbook workBook = null;
        try {
            in = new FileInputStream(finalXlsxPath);
            if (finalXlsxPath.endsWith(EXCEL_XLS)) {     //Excel&nbsp;2003
                workBook = new HSSFWorkbook(in);
            } else if (finalXlsxPath.endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
                workBook = new XSSFWorkbook(in);
            }

            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);

            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
            System.out.println("原始数据总行数，除属性列：" + rowNumber);
            for (int i = 1; i <= rowNumber; i++) {
                Row row = sheet.getRow(i);
                int columnNum = row.getLastCellNum();
                for (int j = 0; j < columnNum; j++) {
                    Cell cell = row.getCell(j);
                    logger.info(getStringCellValue(cell));
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
        System.out.println("数据读取成功");
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

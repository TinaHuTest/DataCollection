package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.FileCreate;
import db.Listmap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcel {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";
    private FileCreate filecreate = new FileCreate();

    //    public static void main(String[] args) {
//
//        Map<String, String> dataMap=new HashMap<String, String>();
//        dataMap.put("BankName", "BankName");
//        dataMap.put("Addr", "Addr");
//        dataMap.put("Phone", "Phone");
//        List<Map> list=new ArrayList<Map>();
//        list.add(dataMap);
//        writeExcel(list, 3, "D:/writeExcel.xlsx");
//
//    }
    public  boolean initExcel(String projectName,String filepath)  {
        Map<String, String> sheet1 = new HashMap<String, String>();
        Map<String, String> sheet2 = new HashMap<String, String>();
        Map<String, String> sheet3 = new HashMap<String, String>();
        Map<String, String> sheet4 = new HashMap<String, String>();
        Map<String, String> sheet5 = new HashMap<String, String>();
        String sheetName1 = "天气宝每日盈亏";
        String sheetName2 = "天气宝每日参与人数及人次";
        String sheetName3 = "城市纷争每日盈亏";
        String sheetName4 = "列城每日人数及人次";
        String sheetName5 = "至今参与总人数";
        sheet1.put("cell0", "日期");
        sheet1.put("cell1", "城市");
        sheet1.put("cell2", "题目");
        sheet1.put("cell3", "答案");
        sheet1.put("cell4", "实际投注");
        sheet1.put("cell5", "需支付");
        sheet1.put("cell6", "盈亏");

        sheet2.put("cell0", "日期");
        sheet2.put("cell1", "人数");
        sheet2.put("cell2", "人次");

        sheet3.put("cell0", "日期");
        sheet3.put("cell1", "答案");
        sheet3.put("cell2", "投注资金");
        sheet3.put("cell3", "需支付资金");
        sheet3.put("cell4", "盈亏");

        sheet4.put("cell0", "日期");
        sheet4.put("cell1", "人数");
        sheet4.put("cell2", "人次");

        sheet5.put("cell0","日期");
        sheet5.put("cell1","竞猜总人数");
        sheet5.put("cell2","预测总人数");

        File file = new File(filepath);
        if (!file.exists()) {
            try {
//                File file = new File(fileName);
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                Workbook workBook = new XSSFWorkbook();
                workBook.createSheet();
                workBook.createSheet();
                workBook.createSheet();
                workBook.createSheet();
                workBook.createSheet();
                workBook.setSheetName(0, sheetName1);
                workBook.setSheetName(1, sheetName2);
                workBook.setSheetName(2, sheetName3);
                workBook.setSheetName(3, sheetName4);
                workBook.setSheetName(4, sheetName5);
                int sheetNumber = workBook.getNumberOfSheets();
                for (int sheet = 0; sheet < sheetNumber; sheet++) {
                    Sheet sheetWork = workBook.getSheetAt(sheet);
                    Row row = sheetWork.createRow(0);
//                    String sheetSelect = "sheet"+i;
                    if (sheet == 0) {
                        for (int i = 0; i < sheet1.size(); i++) {
                            Cell cellName = row.createCell(i);
                            cellName.setCellValue(sheet1.get("cell" + i));
//                            System.out.println(sheet1.get("cell"+i));
                        }
                    }else if (sheet==1){
                        for (int i = 0; i < sheet2.size(); i++) {
                            Cell cellName = row.createCell(i);
                            cellName.setCellValue(sheet2.get("cell" + i));
                        }
                    }else if (sheet==2){
                        for (int i = 0; i < sheet3.size(); i++) {
                            Cell cellName = row.createCell(i);
                            cellName.setCellValue(sheet3.get("cell" + i));
                        }
                    }else if (sheet==3){
                        for (int i = 0; i < sheet4.size(); i++) {
                            Cell cellName = row.createCell(i);
                            cellName.setCellValue(sheet4.get("cell" + i));
                        }
                    }else if (sheet==4){
                        for (int i = 0; i < sheet5.size(); i++) {
                            Cell cellName = row.createCell(i);
                            cellName.setCellValue(sheet5.get("cell" + i));
                        }
                    }
                }
//                fileOutputStream.close();
//                fileOutputStream = new FileOutputStream(file);
                workBook.write(fileOutputStream);
            } catch (IOException e) {
                System.out.println("File name not exist.");
                e.printStackTrace();
                return  false;
            }
        }
        return true;
    }

    public  void writeExcel(Listmap dataList, String finalXlsxPath) {
        OutputStream out = null;
        try {
            // 获取总列数
            int columnNumCount = dataList.size();
            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);
            String sheetname = sheet.getSheetName();
            System.out.println(sheetname);
            /**
             * 删除原有数据，除了属性列
             */
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
//            System.out.println("原始数据总行数，除属性列：" + rowNumber);
//            for (int i = 1; i <= rowNumber; i++) {
//                Row row = sheet.getRow(i);
//                sheet.removeRow(row);
//            }
//            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
//            out =  new FileOutputStream(finalXlsxPath);
//            workBook.write(out);
            /**
             * 往Excel中写新数据
             */
            for (int j = 0; j < dataList.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j + rowNumber + 1);
                // 得到要插入的每一条记录
                Map<String, String> dataMap = dataList.index(j);
                String time = dataMap.get("time").toString();
                String city = dataMap.get("city").toString();
                String title = dataMap.get("title").toString();
                String answer = dataMap.get("answer").toString();
                String total_amount = dataMap.get("total_amount").toString();
                String total_pay = dataMap.get("total_pay").toString();
                String cost = dataMap.get("cost").toString();
                for (int k = 0; k < dataMap.size(); k++) {
                    // 在一行内循环
                    Cell first = row.createCell(0);
                    first.setCellValue(time);

                    Cell second = row.createCell(1);
                    second.setCellValue(city);

                    Cell third = row.createCell(2);
                    third.setCellValue(title);
                    Cell four = row.createCell(3);
                    four.setCellValue(answer);

                    Cell fourq = row.createCell(4);
                    fourq.setCellValue(total_amount);

                    Cell fourw1 = row.createCell(5);
                    fourw1.setCellValue(total_pay);

                    Cell fourw12 = row.createCell(6);
                    fourw12.setCellValue(cost);
                }
            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(finalXlsxPath);
            workBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据导出成功");
    }

    public void writeExcelSheet2(String type, List<String> dataList, String finalXlsxPath) {
        OutputStream out = null;
        try {
            // 获取List的长度
            int litSize = dataList.size();
            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = null;
            if (type.equals("竞猜")) {
                sheet = workBook.getSheetAt(1);
                String sheetName1 = sheet.getSheetName();
                System.out.println(sheetName1);
            } else {
                sheet = workBook.getSheetAt(3);
                String sheetName1 = sheet.getSheetName();
                System.out.println(sheetName1);
            }

            /**
             * 删除原有数据，除了属性列
             */
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
//            System.out.println("原始数据总行数，除属性列：" + rowNumber);
//            for (int i = 1; i <= rowNumber; i++) {
//                Row row = sheet.getRow(i);
//                sheet.removeRow(row);
//            }
//            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
//            out =  new FileOutputStream(finalXlsxPath);
//            workBook.write(out);
            /**
             * 往Excel中写新数据
             */
            Row row = sheet.createRow(rowNumber + 1);

            for (int i = 0; i < litSize; i++) {
//                String time = dataList.get("日期").toString();
//                String personNumber = dataList.get("人数").toString();
                Cell first = row.createCell(i);
                first.setCellValue(dataList.get(i));
            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(finalXlsxPath);
            workBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据导出成功");
    }
    public  void writeExcelSheet3(Listmap dataList, String finalXlsxPath) {
        OutputStream out = null;
        try {
            // 获取总列数
            int columnNumCount = dataList.size();
            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(2);
            String sheetname = sheet.getSheetName();
            System.out.println(sheetname);
            /**
             * 删除原有数据，除了属性列
             */
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
//            System.out.println("原始数据总行数，除属性列：" + rowNumber);
//            for (int i = 1; i <= rowNumber; i++) {
//                Row row = sheet.getRow(i);
//                sheet.removeRow(row);
//            }
//            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
//            out =  new FileOutputStream(finalXlsxPath);
//            workBook.write(out);
            /**
             * 往Excel中写新数据
             */
            for (int j = 0; j < dataList.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j + rowNumber + 1);
                // 得到要插入的每一条记录
                Map<String, String> dataMap = dataList.index(j);
                String time = dataMap.get("time").toString();
                String answer = dataMap.get("answer").toString();
                String total_amount = dataMap.get("total_amount").toString();
                String total_pay = dataMap.get("total_pay").toString();
                String cost = dataMap.get("cost").toString();
                for (int k = 0; k < dataMap.size(); k++) {
                    // 在一行内循环
                    Cell first = row.createCell(0);
                    first.setCellValue(time);

                    Cell second = row.createCell(1);
                    second.setCellValue(answer);

                    Cell third = row.createCell(2);
                    third.setCellValue(total_amount);

                    Cell four = row.createCell(3);
                    four.setCellValue(total_pay);

                    Cell five = row.createCell(4);
                    five.setCellValue(cost);
                }
            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(finalXlsxPath);
            workBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据导出成功");
    }

    public  void writeExcelSheet4(String date,List<Integer> sumCount, String finalXlsxPath) {
        OutputStream out = null;
        try {
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(4);
            String sheetname = sheet.getSheetName();
            System.out.println(sheetname);
            /**
             * 删除原有数据，除了属性列
             */
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
//            System.out.println("原始数据总行数，除属性列：" + rowNumber);
//            for (int i = 1; i <= rowNumber; i++) {
//                Row row = sheet.getRow(i);
//                sheet.removeRow(row);
//            }
//            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
//            out =  new FileOutputStream(finalXlsxPath);
//            workBook.write(out);
            /**
             * 往Excel中写新数据
             */
            // 创建一行：从第二行开始，跳过属性列
            Row row = sheet.createRow( rowNumber+ 1);
            Cell first = row.createCell(0);
            first.setCellValue(date);

            Cell second = row.createCell(1);
            second.setCellValue(sumCount.get(0));

            Cell third = row.createCell(2);
            third.setCellValue(sumCount.get(1));

            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(finalXlsxPath);
            workBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据导出成功");
    }

    /**
     * 判断Excel的版本,获取Workbook
     *
     * @param in
     * @param filename
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(File file) throws IOException {
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if (file.getName().endsWith(EXCEL_XLS)) {     //Excel&nbsp;2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }
}

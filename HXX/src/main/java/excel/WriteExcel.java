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
    public  boolean initExcel(String projectName,String filepath)  {
        Map<String, String> sheet1 = new HashMap<String, String>();
        Map<String, String> sheet2 = new HashMap<String, String>();
        Map<String, String> sheet3 = new HashMap<String, String>();
        Map<String, String> sheet4 = new HashMap<String, String>();
        Map<String, String> sheet5 = new HashMap<String, String>();
        Map<String, String> sheet6 = new HashMap<String, String>();
        Map<String, String> sheet7 = new HashMap<String, String>();

        String sheetName1 = "天气宝每日盈亏";
        String sheetName2 = "天气宝每日参与人数及人次";
        String sheetName3 = "城市纷争每日盈亏";
        String sheetName4 = "列城每日人数及人次";
        String sheetName5 = "至今参与总人数";
        String sheetName6 ="用户下注次数和下注数量";
        String sheetName7 ="用户下注问题详情";
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

        sheet6.put("cell0","用户ID");
        sheet6.put("cell1","用户昵称");
        sheet6.put("cell2","用户下注次数");
        sheet6.put("cell3","用户下注数量");


        sheet7.put("cell0","日期");
        sheet7.put("cell1","用户ID");
        sheet7.put("cell2","用户昵称");
        sheet7.put("cell3","用户下注问题");
        sheet7.put("cell4","用户下注问题选项");
        sheet7.put("cell5","用户下注次数");
        sheet7.put("cell6","用户下注数量");

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
                workBook.createSheet();
                workBook.createSheet();
                workBook.setSheetName(0, sheetName1);
                workBook.setSheetName(1, sheetName2);
                workBook.setSheetName(2, sheetName3);
                workBook.setSheetName(3, sheetName4);
                workBook.setSheetName(4, sheetName5);
                workBook.setSheetName(5, sheetName6);
                workBook.setSheetName(6, sheetName7);
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
                    }else if(sheet==5){
                        for (int i = 0; i < sheet6.size(); i++) {
                            Cell cellName = row.createCell(i);
                            cellName.setCellValue(sheet6.get("cell" + i));
                        }
                    } else if(sheet==6){
                        for (int i = 0; i < sheet7.size(); i++) {
                            Cell cellName = row.createCell(i);
                            cellName.setCellValue(sheet7.get("cell" + i));
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
//            int columnNumCount = dataList.size();
            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);
//            String sheetname = sheet.getSheetName();
//            System.out.println(sheetname);


            int rowNumber = sheet.getLastRowNum()+1;    // 第一行从0开始算
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
                String time = dataMap.get("time");
                String city = dataMap.get("city");
                String title = dataMap.get("title");
                String answer = dataMap.get("answer");
                String total_amount = dataMap.get("total_amount");
                String total_pay = dataMap.get("total_pay");
                String cost = dataMap.get("cost");
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

                    Cell fifth = row.createCell(4);
                    fifth.setCellValue(total_amount);

                    Cell six = row.createCell(5);
                    six.setCellValue(total_pay);

                    Cell seven = row.createCell(6);
                    seven.setCellValue(cost);
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
//        System.out.println("数据导出成功");
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
//                String sheetName1 = sheet.getSheetName();
//                System.out.println(sheetName1);
            } else {
                sheet = workBook.getSheetAt(3);
//                String sheetName1 = sheet.getSheetName();
//                System.out.println(sheetName1);
            }
//            删除原有数据，除了属性列
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
//        System.out.println("数据导出成功");
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
//            String sheetname = sheet.getSheetName();
//            System.out.println(sheetname);
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
                String time = dataMap.get("time");
                String answer = dataMap.get("answer");
                String total_amount = dataMap.get("total_amount");
                String total_pay = dataMap.get("total_pay");
                String cost = dataMap.get("cost");
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
//        System.out.println("数据导出成功");
    }

    public  void writeExcelSheet4(String date,List<Integer> sumCount, String finalXlsxPath) {
        OutputStream out = null;
        try {
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(4);
//            String sheetname = sheet.getSheetName();
//            System.out.println(sheetname);
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
//        System.out.println("数据导出成功");
    }

    /***
     *  写入sheet6，写入用户的信息和用户下注数量和下注次数
     * @param finalXlsxPath
     */
    public  void writeExcelSheet6(Listmap map, String finalXlsxPath) {
        OutputStream out = null;
        try {
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(5);
//            String sheetname = sheet.getSheetName();
//            System.out.println(sheetname);
            /**
             * 删除原有数据，除了属性列
             */
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
            System.out.println("原始数据总行数，除属性列：" + rowNumber);
            for (int i = 1; i <= rowNumber; i++) {
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }
            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);
            /**
             * 往Excel中写新数据
             */
            // 创建一行：从第二行开始，跳过属性列
            for (int j = 0; j < map.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j+1);
                // 得到要插入的每一条记录
                Map<String, String> dataMap = map.index(j);
                String userid = dataMap.get("userid");
                String nickname = dataMap.get("nickname");
                String count = dataMap.get("count");
                String mount = dataMap.get("mount");
                for (int k = 0; k < dataMap.size(); k++) {
                    // 在一行内循环
                    Cell first = row.createCell(0);
                    first.setCellValue(userid);

                    Cell second = row.createCell(1);
                    second.setCellValue(nickname);

                    Cell third = row.createCell(2);
                    third.setCellValue(count);

                    Cell four = row.createCell(3);
                    four.setCellValue(mount);

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
//        System.out.println("数据导出成功");
    }
    //写书sheet7，展示用户下注的问题详情
    public  void writeExcelSheet7(Listmap dataList, String finalXlsxPath) {
        OutputStream out = null;
        try {
            // 获取总列数
//            int columnNumCount = dataList.size();
            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(6);
//            String sheetname = sheet.getSheetName();
//            System.out.println(sheetname);


            int rowNumber = sheet.getLastRowNum()+1;    // 第一行从0开始算
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
                String time = dataMap.get("time");
                String city = dataMap.get("userid");
                String title = dataMap.get("nickname");
                String answer = dataMap.get("title");
                String total_amount = dataMap.get("content");
                String total_pay = dataMap.get("count");
                String cost = dataMap.get("summount");
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

                    Cell fifth = row.createCell(4);
                    fifth.setCellValue(total_amount);

                    Cell six = row.createCell(5);
                    six.setCellValue(total_pay);

                    Cell seven = row.createCell(6);
                    seven.setCellValue(cost);
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
//        System.out.println("数据导出成功");
    }


    /**
     * 判断Excel的版本,获取Workbook
     *
     * @param
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

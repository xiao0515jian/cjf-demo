package com.cjf.demo.utils;

import com.alibaba.fastjson.JSONObject;
import com.cjf.demo.response.AreaOuterMonthResponse;
import com.cjf.demo.response.CarSeriesData;
import com.cjf.demo.vo.InnerMonthReleaseVO;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : chenjianfeng
 * @date : 2023/1/14
 */
public class ReadExcelFileUtil {



    @Test
    public void test() throws IOException, InvalidFormatException {
        //getInnerReleaseList();
        getInnerReleaseList1();
        //getInnerReleaseList2();
//        getInnerReleaseList3();
    }
    public static List<InnerMonthReleaseVO> getInnerReleaseList() throws IOException, InvalidFormatException {
        Workbook wb = new XSSFWorkbook(new File("D:\\upload-dir\\工作簿1.xlsx"));
        Sheet sheet = wb.getSheetAt(0);
        Row row = null;
        Cell cell = null;
        List<InnerMonthReleaseVO> list = new ArrayList<>();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            //获取每一行数据
            row = sheet.getRow(i);

            for(int j = 0; j < 9; j++){
                InnerMonthReleaseVO po = new InnerMonthReleaseVO();
                po.setRegionalArea(row.getCell(0).getStringCellValue());
                po.setSmallArea(row.getCell(1).getStringCellValue());
                po.setDealerCode(row.getCell(2).getStringCellValue());
                po.setCarSeries(sheet.getRow(0).getCell(j+4).getStringCellValue());
                po.setAakValue(Integer.parseInt(String.valueOf(Math.round(row.getCell(j+4).getNumericCellValue()))));
                list.add(po);
                //System.out.println(JSONObject.toJSONString(po));
            }

        }
        return list;
    }

    public static List<AreaOuterMonthResponse> getInnerReleaseList1() throws IOException, InvalidFormatException {
        Workbook wb = new XSSFWorkbook(new File("D:\\upload-dir\\内控数据.xlsx"));
        Sheet sheet = wb.getSheetAt(1);
        Row row = null;
        Cell cell = null;
        List<AreaOuterMonthResponse> list = new ArrayList<>();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            //获取每一行数据
            row = sheet.getRow(i);
            AreaOuterMonthResponse po = new AreaOuterMonthResponse();
            po.setAreaId(row.getCell(0).getStringCellValue());
            po.setSum(Integer.parseInt(String.valueOf(Math.round(row.getCell(1).getNumericCellValue()))));
            List<CarSeriesData> carSeriesDataList = new ArrayList<>();
            for(int j = 0; j < 10; j++){
                CarSeriesData car = new CarSeriesData();
                car.setCarSeries(sheet.getRow(0).getCell(j+1).getStringCellValue());
                car.setNumber(Integer.parseInt(String.valueOf(Math.round(row.getCell(j+1).getNumericCellValue()))));
                carSeriesDataList.add(car);
            }
            po.setCarSeriesDataList(carSeriesDataList);
            list.add(po);

        }
        System.out.println(JSONObject.toJSONString(list));
        return list;
    }

    public static List<AreaOuterMonthResponse> getInnerReleaseList2() throws IOException, InvalidFormatException {
        Workbook wb = new XSSFWorkbook(new File("D:\\upload-dir\\内控数据.xlsx"));
        Sheet sheet = wb.getSheetAt(2);
        Row row = null;
        Cell cell = null;
        List<AreaOuterMonthResponse> list = new ArrayList<>();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            //获取每一行数据
            row = sheet.getRow(i);
            AreaOuterMonthResponse po = new AreaOuterMonthResponse();
            po.setAreaId(row.getCell(0).getStringCellValue());
            po.setSmallArea(row.getCell(1).getStringCellValue());
            po.setSum(Integer.parseInt(String.valueOf(Math.round(row.getCell(2).getNumericCellValue()))));
            List<CarSeriesData> carSeriesDataList = new ArrayList<>();
            for(int j = 0; j < 10; j++){
                CarSeriesData car = new CarSeriesData();
                car.setCarSeries(sheet.getRow(0).getCell(j+2).getStringCellValue());
                car.setNumber(Integer.parseInt(String.valueOf(Math.round(row.getCell(j+2).getNumericCellValue()))));
                carSeriesDataList.add(car);
            }
            po.setCarSeriesDataList(carSeriesDataList);
            list.add(po);

        }
        System.out.println(JSONObject.toJSONString(list));
        return list;
    }

    public static List<AreaOuterMonthResponse> getInnerReleaseList3() throws IOException, InvalidFormatException {
        Workbook wb = new XSSFWorkbook(new File("D:\\upload-dir\\内控数据.xlsx"));
        Sheet sheet = wb.getSheetAt(3);
        Row row = null;
        Cell cell = null;
        List<AreaOuterMonthResponse> list = new ArrayList<>();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            //获取每一行数据
            row = sheet.getRow(i);
            AreaOuterMonthResponse po = new AreaOuterMonthResponse();
            po.setAreaId(row.getCell(0).getStringCellValue());
            po.setSmallArea(row.getCell(1).getStringCellValue());
            po.setDealer(row.getCell(2).getStringCellValue());
            po.setSum(Integer.parseInt(String.valueOf(Math.round(row.getCell(3).getNumericCellValue()))));
            List<CarSeriesData> carSeriesDataList = new ArrayList<>();
            for(int j = 0; j < 10; j++){
                CarSeriesData car = new CarSeriesData();
                car.setCarSeries(sheet.getRow(0).getCell(j+3).getStringCellValue());
                car.setNumber(Integer.parseInt(String.valueOf(Math.round(row.getCell(j+3).getNumericCellValue()))));
                carSeriesDataList.add(car);
            }
            po.setCarSeriesDataList(carSeriesDataList);
            list.add(po);

        }
        writeData(list);

        return list;
    }

    public static void writeData(List<AreaOuterMonthResponse> list){
        Workbook wba = new XSSFWorkbook();
        Sheet sheet1 = wba.createSheet("内控导入版");
        // ---------------下面开始设置表的第二行,通常为字段名----------------------
        Row row1 = sheet1.createRow(0);
        // 字段名使用的字体
        Font columnHeadFont = wba.createFont();
        columnHeadFont.setFontName("宋体");
        // 字体大小
        columnHeadFont.setFontHeightInPoints((short) 10);
        // 列头的样式
        CellStyle columnHeadStyle = wba.createCellStyle();
        // 设置上面已经设置好的字体样式
        columnHeadStyle.setFont(columnHeadFont);
        columnHeadStyle.setLocked(true);
        columnHeadStyle.setWrapText(false);
        columnHeadStyle.setAlignment(HorizontalAlignment.CENTER);
        columnHeadStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置第二行的行高
        row1.setHeight((short) 500);
        // 创建在这行中的列
        Cell cell1 = null;
        List<String> propertyList = new ArrayList<>();
        propertyList.add("年");
        propertyList.add("月");
        propertyList.add("车系");
        propertyList.add("大区");
        propertyList.add("小区");
        propertyList.add("经销商");
        propertyList.add("目标数");
        propertyList.add("备注");
        // 获取表需要的列数
        for (int i = 0; i < propertyList.size(); i++) {
            cell1 = row1.createCell(i);
            // 获取数组中的表头字段名
            cell1.setCellValue(propertyList.get(i));
            // 给它设置风格
            cell1.setCellStyle(columnHeadStyle);
        }
        // ---------------下面开始设置表里面的内容-----------------------------
        // 设置字体
        Font font = wba.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);
        // 设置其风格
        CellStyle style = wba.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(false);
        Row rowa = null;
        Cell cella = null;
        int index = 1;
        // 赋值
        for (AreaOuterMonthResponse itVo : list) {
            for(CarSeriesData vo : itVo.getCarSeriesDataList()){
                if(!vo.getCarSeries().equals("合计")){
                    rowa = sheet1.createRow(index);
                    cella = rowa.createCell(0);
                    cella.setCellValue("2023");
                    cella.setCellStyle(style);
                    cella = rowa.createCell(1);
                    cella.setCellValue("01");
                    cella.setCellStyle(style);
                    cella = rowa.createCell(2);
                    cella.setCellValue(vo.getCarSeries());
                    cella.setCellStyle(style);
                    cella = rowa.createCell(6);
                    cella.setCellValue(vo.getNumber());
                    cella.setCellStyle(style);
                    cella = rowa.createCell(3);
                    cella.setCellValue(itVo.getAreaId());
                    cella.setCellStyle(style);
                    cella = rowa.createCell(4);
                    cella.setCellValue(itVo.getSmallArea());
                    cella.setCellStyle(style);
                    cella = rowa.createCell(5);
                    cella.setCellValue(itVo.getDealer());
                    cella.setCellStyle(style);
                    cella = rowa.createCell(7);
                    cella.setCellValue("");
                    cella.setCellStyle(style);

                    index += 1;
                }
            }
        }

        try {
            FileOutputStream fout = new FileOutputStream("D:/upload-dir/测试内控导入数据.xlsx");
            wba.write(fout);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

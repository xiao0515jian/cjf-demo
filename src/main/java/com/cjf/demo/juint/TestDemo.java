package com.cjf.demo.juint;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import com.cjf.demo.enums.DetailStatisticsTypeEnum;
import com.cjf.demo.po.Employee;
import com.cjf.demo.utils.DateUtils;
import com.cjf.demo.vo.InnerMonthReleaseVO;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : chenjianfeng
 * @date : 2022/8/9
 */
public class TestDemo {
    @Test
    public void LambdaNew() {
        //before jdk8
        List<String> names = Arrays.asList("Tom", "Sun", "Lily", "Amanda");
        for (String name : names) {
            System.out.println(name);
        }
        //after jdk8
        names.forEach(n -> System.out.println(n));
    }

    @Test
    public void testStream() {
        List<Integer> nums = Lists.newArrayList(1,1,null,2,3,4,null,5,6,7,8,9,10);
        System.out.println("求和："+nums
                .stream()//转成Stream
                .filter(team -> team!=null)//过滤
                .distinct()//去重
                .mapToInt(num->num*2)//map操作
                .skip(2)//跳过前2个元素
                .limit(4)//限制取前4个元素
                .peek(System.out::println)//流式处理对象函数
                .sum());
    }

    public interface JDK8Interface{
        //1、接口中可以定义静态方法
        static void staticMethod() {
            System.out.println("接口中的静态方法");
        }
        //2、使用default之后就可以定义普通方法的方法体了
        default void DefaultMethod() {
            System.out.println("接口中的默认方法");
        }
    }

    @Test
    public void base64Test() {
        final String test = "就是要测试加解密sd! !asdfas#^((&^*";
        String encoded = Base64.getEncoder().encodeToString(test.getBytes(StandardCharsets.UTF_8));
        System.out.println("加密后="+  encoded);

        final String decoded = new String( Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
        System.out.println("解密后="+ decoded);
    }

    @Test
    public void TimeTest() {
        //1、Clock
        final Clock clock = Clock.systemUTC();
        System.out.println("clock = "+ clock.getZone());

        //2、
        final String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
//        final LocalDate date = LocalDate.now();
        System.out.println("date = " + date);
        final LocalDate dateFromClock = LocalDate.now(clock);
        System.out.println("dateFromClock = "+ dateFromClock);

        //3、
        final LocalTime time = LocalTime.now();
        System.out.println("time = " + time);
        final LocalTime timeFromClock = LocalTime.now(clock);
        System.out.println("timeFromClock = "+ timeFromClock);

        //4、
        final LocalDateTime datetime = LocalDateTime.now();
        System.out.println("datetime = " + datetime);
        final LocalDateTime datetimeFromClock = LocalDateTime.now(clock);
        System.out.println("datetimeFromClock = "+ datetimeFromClock);

        //5、
        final ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println("zonedDateTime = "+zonedDateTime);
        final ZonedDateTime zonedDateTimef = ZonedDateTime.now(clock);
        System.out.println("zonedDateTimef = "+zonedDateTimef);
        final ZonedDateTime zonedDateTimec =ZonedDateTime.now(ZoneId.of("America/New_York"));
        System.out.println("zonedDateTimec = "+zonedDateTimec);

    }

    /**
     * 获取当前月初日期
     * @param month
     * @return
     */
    public String getCurrentStartDate(String month){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date= LocalDate.parse(month, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate firstDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        return firstDayOfMonth.format(formatter);
    }
    /**
     * 获取下一月
     * @return
     */
    public static String getNextMonth() {
        LocalDate date = LocalDate.now().plus(1, ChronoUnit.MONTHS);
        return date.toString().substring(5,7);
    }
    /**
     * 获取当前月末日期
     * @param month
     * @return
     */
    public String getCurrentEndDate(String month){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date= LocalDate.parse(month, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        return lastDayOfMonth.format(formatter);
    }

    public String getFirstDayOfMonth(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return format.format(cale.getTime());
    }
    public String getLastDayOfMonth(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return format.format(cale.getTime());
    }

    /**
     * 获取上月月初
     * @return
     */
    public static String getFirstDayOfBeforeMonth(){
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, -1);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return DateUtils.formatDate(cale.getTime(),DateUtils.FORMAT_DATE);
    }

    /**
     * 获取上月月末
     * @return
     */
    public static String getLastDayOfBeforeMonth(){
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return DateUtils.formatDate(cale.getTime(),DateUtils.FORMAT_DATE);
    }
    /**
     * 获取上一年当月月初
     * @return
     */
    public static String getFirstDayOfBeforeYearMonth(){
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.YEAR,-1);
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return DateUtils.formatDate(cale.getTime(),DateUtils.FORMAT_DATE);
    }

    /**
     * 获取上一年当月月末
     * @return
     */
    public static String getLastDayOfBeforeYearMonth(){
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.YEAR,-1);
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return DateUtils.formatDate(cale.getTime(),DateUtils.FORMAT_DATE);
    }
    public static String calculate(String n1, String n2) {
        Assert.notBlank(n1);
        Assert.notBlank(n2);
        return multiply(divide(n1,n2),"100") + "%";
    }
    /**
     * 减法
     * @param n1
     * @param n2
     * @return
     */
    public static String subtract(String n1, String n2) {
        Assert.notBlank(n1);
        Assert.notBlank(n2);
        BigDecimal b1 = new BigDecimal(n1);
        BigDecimal b2 = new BigDecimal(n2);
        return b1.subtract(b2).toString();
    }

    /**
     * 除法
     * @param n1
     * @param n2
     * @return
     */
    public static String divide(String n1, String n2) {
        Assert.notBlank(n1);
        Assert.notBlank(n2);
        BigDecimal b1 = new BigDecimal(n1);
        BigDecimal b2 = new BigDecimal(n2);
        if (b2.compareTo(BigDecimal.ZERO) == 0) {
            return "0";
        }
        String result = b1.divide(b2,2,BigDecimal.ROUND_HALF_UP).toString();
        return result;
    }

    /**
     * 乘法
     * @param n1
     * @param n2
     * @return
     */
    public static String multiply(String n1, String n2) {
        Assert.notBlank(n1);
        Assert.notBlank(n2);
        BigDecimal b1 = new BigDecimal(n1);
        BigDecimal b2 = new BigDecimal(n2);
        String result = b1.multiply(b2).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
        return result;
    }
    public static String splitYearMonth(String data){
        return data.substring(0,4);
    }

    /**
     *
     * @param list 内控月发布版列表
     * @param code 类型
     * @return 大区、小区、经销商 汇总
     */
    public static Map<String, Map<String,Integer>> calculateCarSeriesSum(List<InnerMonthReleaseVO> list, String code) {

        Map<String, Map<String,Integer>> map = new HashMap<>();

        if(Objects.equals(code, "1")){
            map = list.stream().collect(Collectors.groupingBy(InnerMonthReleaseVO::getRegionalArea,
                    Collectors.groupingBy(InnerMonthReleaseVO::getCarSeries,
                            Collectors.summingInt(InnerMonthReleaseVO::getAakValue))));

        }

        if(Objects.equals(code, "2")){
            map = list.stream().collect(Collectors.groupingBy(InnerMonthReleaseVO::getSmallArea,
                    Collectors.groupingBy(InnerMonthReleaseVO::getCarSeries,
                            Collectors.summingInt(InnerMonthReleaseVO::getAakValue))));
        }

        return map;
    }

    protected List<InnerMonthReleaseVO> innerMonthList =(List<InnerMonthReleaseVO>) Arrays.asList(
            new InnerMonthReleaseVO("北区","吉林北", "EZJ608", "H5-2",10),
            new InnerMonthReleaseVO("北区","吉林北", "EZJ609", "H5-2",10),
            new InnerMonthReleaseVO("北区","长春", "EZJ601", "H5",10),
            new InnerMonthReleaseVO("东区","上海", "EZJ608", "H5-2",10),
            new InnerMonthReleaseVO("东区","杭州", "EZJ608", "H9",10),
            new InnerMonthReleaseVO("南区","贵州", "EZJ608", "H5-2",10),
            new InnerMonthReleaseVO("南区","贵州", "EZJ608", "H5-2",10),
            new InnerMonthReleaseVO("南区","云南", "EZJ608", "H7",10),
            new InnerMonthReleaseVO("西区","西安", "EZJ608", "H5-2",10),
            new InnerMonthReleaseVO("西区","西安", "EZJ608", "H5",10),
            new InnerMonthReleaseVO("西区","安徽", "EZJ608", "HS5",10),
            new InnerMonthReleaseVO("西区","安徽", "EZJ608", "H9",10),
            new InnerMonthReleaseVO("西区","安徽", "EZJ608", "H7",10)
    );

    @Test
    public void test999() {
//        Map<String,Integer> map = new HashMap<>();
//        map.put("H5",2);
//        map.put("H6",3);
//        map.put("H7",4);
//        map.put("H8",5);
//        map.put("H9",6);
//        map.put("合计",map.values().stream().mapToInt(Integer::intValue).sum());
//
//        System.out.println(map);
//        System.out.println(calculateCarSeriesSum(innerMonthList,"1"));;
        List<String> areaList = new ArrayList<>();
        areaList.add("北区");
        areaList.add("东区");

        //List<InnerMonthReleaseVO> list = innerMonthList.stream().filter(data -> areaList.contains(data.getRegionalArea())).collect(Collectors.toList());
        //list.forEach(data -> System.out.println(data));

        LinkedHashMap<String, LinkedHashMap<String, List<InnerMonthReleaseVO>>>  result =
                innerMonthList.stream()
                        .sorted(Comparator.comparing(InnerMonthReleaseVO::getRegionalArea)
                                .thenComparing(InnerMonthReleaseVO::getSmallArea))
                        .collect(Collectors.groupingBy(InnerMonthReleaseVO::getRegionalArea,LinkedHashMap::new,
                                                   Collectors.groupingBy(InnerMonthReleaseVO::getSmallArea,LinkedHashMap::new,Collectors.toList())));
        result.forEach((k,v) -> {
            System.out.println(k+","+v.keySet());
        });


    }
    @Test
    public void test888() {
//        System.out.println(getFirstDayOfMonth());
//        System.out.println(getLastDayOfMonth());
//        System.out.println(getFirstDayOfBeforeMonth());
//        System.out.println(getLastDayOfBeforeMonth());
//        System.out.println(getFirstDayOfBeforeYearMonth());
//        System.out.println(getLastDayOfBeforeYearMonth());
//        System.out.println(calculate("2","3"));
//        System.out.println(subtract("2","1"));
        System.out.println(splitYearMonth("2022-09-09"));

    }


    @Test
    public void eseDateTime() {
        //自定时间
        LocalDate date = LocalDateTimeUtil.parseDate("2021-12-30");
        if(date.getMonthValue() == 12){
            LocalDate plus1  = date.plus(1,ChronoUnit.YEARS);
            System.out.println(plus1.getYear());
        }
        System.out.println(date.getYear());
        //LocalDate date = LocalDate.now();
        LocalDate minus1 = date.minus(1, ChronoUnit.MONTHS);
        LocalDate plus1 = date.plus(1, ChronoUnit.MONTHS);
        System.out.println("自定义时间上个月的今天" + minus1);//2021-06-30
        System.out.println("自定义时间下个月的今天" + plus1);//2021-06-31
        System.out.println(getNextMonth());
    }
    @Test
    public void queryLastMonth() {
        List<String> list = new ArrayList<>();
        list.add("0.1%");
        list.add("0.1%");
        list.add("0.1%");
        list.add("0.1%");
        int index = 0;
        for(int i = 0; i < list.size(); i++){
            for(int j = 0; j < list.size(); j++){
                index = i + j + 1;
                System.out.println(index);
            }
        }
    }

    @Test
    public void test55555() {
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建excel
        HSSFSheet sheet = wb.createSheet("经销商端城市份额目标分解");
        // ---------------下面开始设置表的第二行,通常为字段名----------------------

        String path = "D:\\upload-dir\\";
        String name = "经销商端城市份额目标分解";
        String xlsxPath = path + name + ".xlsx";
        try {
            OutputStream os = new FileOutputStream(xlsxPath);
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test555666() throws IOException {
        String path = "D:\\upload-dir\\";
        String fileName = "经销商端城市份额目标分解";
        String xlsxPath = path + fileName + ".xlsx";
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("经销商端城市份额目标分解");
        // ---------------下面开始设置表的第二行,通常为字段名----------------------
        Row row1 = sheet.createRow(0);
        // 字段名使用的字体
        Font columnHeadFont = wb.createFont();
        columnHeadFont.setFontName("宋体");
        // 字体大小
        columnHeadFont.setFontHeightInPoints((short) 10);
        // 列头的样式
        CellStyle columnHeadStyle = wb.createCellStyle();
        // 设置上面已经设置好的字体样式
        columnHeadStyle.setFont(columnHeadFont);
        columnHeadStyle.setLocked(true);
        columnHeadStyle.setWrapText(false);
        columnHeadStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置第二行的行高
        row1.setHeight((short) 500);
        // 创建在这行中的列
        Cell cell1 = null;
        List<String> propertyList = new ArrayList<>();
        propertyList.add("序号");
        propertyList.add("年");
        propertyList.add("月");
        propertyList.add("车型");
        propertyList.add("区域编码");
        propertyList.add("区域");
        propertyList.add("省份编码");
        propertyList.add("省份");
        propertyList.add("城市编码");
        propertyList.add("城市");
        propertyList.add("N+1月目标市场预测");
        propertyList.add("N+1月目标份额");
        // 获取表需要的列数
        for (int i = 0; i < propertyList.size(); i++) {
            cell1 = row1.createCell(i);
            // 获取数组中的表头字段名
            cell1.setCellValue(propertyList.get(i));
            // 给它设置风格
            cell1.setCellStyle(columnHeadStyle);
        }
        saveFile(wb,fileName);
    }
    public void saveFile(Workbook wb,String fileName) {
        String path = "D:\\upload-dir\\";
        String xlsxPath = path + fileName + ".xlsx";
        OutputStream os = null;
        try {
            os = new FileOutputStream(xlsxPath);
            wb.write(os);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test() throws ParseException {
        List<String> cars = new ArrayList<>();
        List<String> sortList = new ArrayList<>();
        cars.add("H5");
        cars.add("H5-2");
        cars.add("HS5");
        cars.add("HS7");
        cars.add("H7");
        cars.add("H9");
        System.out.println(JSONObject.toJSONString(cars));
        sortList.addAll(cars.stream().sorted(Comparator.comparing(e -> e)).collect(Collectors.toList()));
        System.out.println(JSONObject.toJSONString(sortList));
    }
    public boolean isTokenExpired(String token) throws ParseException {
        Date expiredDate = DateUtils.parseDate("2022-10-19 00:00:00",DateUtils.FORMAT_DATE_TIME);
        return expiredDate.before(new Date());
    }

    /**
     * 获取当前年
     * @return
     */
    public static String getCurrentYear() {
        return String.valueOf(LocalDate.now().getYear());
    }
    /**
     * 获取当前月
     * @return
     */
    public static String getCurrentMonth() {
        return String.valueOf(LocalDate.now().getMonthValue());
    }
}

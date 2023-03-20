package com.cjf.demo.juint;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjf.demo.config.CommonConst;
import com.cjf.demo.enums.FileTypeEnum;
import com.cjf.demo.enums.IndexEnums;
import com.cjf.demo.po.Area;
import com.cjf.demo.po.AreaRelationVO;
import com.cjf.demo.po.Employee;
import com.cjf.demo.service.MyFunction;
import com.cjf.demo.service.MyPredicate;
import com.cjf.demo.service.impl.FilterEmployeeByAge;
import com.cjf.demo.service.impl.FilterEmployeeByWeight;
import com.cjf.demo.utils.DateUtils;
import com.cjf.demo.utils.IdUtils;
import com.cjf.demo.utils.StringUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * @author : chenjianfeng
 * @date : 2022/8/9
 */
public class TestStream {

    protected List<Employee> arrayList =(List<Employee>) Arrays.asList(
            new Employee(1,"张三", 16, 99.99D),
            new Employee(2,"李四", 38, 55.55D),
            new Employee(4,"田七", 18, 49.33D),
            new Employee(3,"王五", 60, 66.66D),
            new Employee(5,"赵六", 16, 77.77D),
            new Employee(6,"",19,50D),
            new Employee(7,"孙悟空",99,99999.0D),
            new Employee(6,"孙悟空",99,99999.0D),
            new Employee(7,"齐天大圣",99,99999.0D),
            new Employee(7,"齐天大圣",99,99999.0D),
            new Employee(7,"齐天大圣",99,99999.0D)
    );
    protected List<Area> areaList =(List<Area>) Arrays.asList(
            new Area("北区","辽宁省", "沈阳市"),
            new Area("北区","辽宁省", "大连市"),
            new Area("北区","吉林省", "长春市"),
            new Area("西区","云南省", "昆明市"),
            new Area("西区","云南省", "曲靖市"),
            new Area("北区","北京市", "北京市"),
            new Area("西区","四川省", "成都市"),
            new Area("东区","安徽省", "合肥市"),
            new Area("南区","广东省", "佛山市"),
            new Area("南区","广东省", "东莞市"),
            new Area("南区","广东省", "广州市"),
            new Area("南区","广东省", "深圳市")

    );
    protected List<Area> areaList1 =(List<Area>) Arrays.asList(
            new Area("北区","辽宁省", "沈阳市"),
            new Area("北区","辽宁省", "大连市"),
            new Area("北区","吉林省", "长春市"),
            new Area("西区","云南省", "昆明市"),
            new Area("西区","云南省", "曲靖市"),
            new Area("北区","北京市", "北京市"),
            new Area("西区","四川省", "成都市"),
            new Area("东区","安徽省", "合肥市"),
            new Area("南区","广东省", "佛山市"),
            new Area("南区","广东省", "东莞市"),
            new Area("南区","广东省", "广州市"),
            new Area("南区","广东省", "深圳市")

    );
    /*
     * 优化方案1
     * */
    public List<Employee> filterEmployee(List<Employee> list, MyPredicate<Employee> myPredicate){
        List<Employee> employees = new ArrayList<Employee>();
        for(Employee e : list){
            if(myPredicate.filter(e)){
                employees.add(e);
            }
        }
        return employees;
    }
    @Test
    public void test1() {
        ArrayList<Employee> employees=new ArrayList<Employee>();
        for (Employee employee:arrayList) {
            if(employee.getAge()>18){
                employees.add(employee);
            }
        }
        System.out.println("年龄大于18的："+employees);
        employees=new ArrayList<Employee>();
        for (Employee employee:arrayList) {
            if(employee.getWeight()>50){
                employees.add(employee);
            }
        }
        System.out.println("体重大于50的："+employees);


        List<Employee> employeeList = this.filterEmployee(arrayList, new FilterEmployeeByAge());
        System.out.println("年龄大于18的：");
        for (Employee employee:employeeList) {
            System.out.println(employee);
        }
        System.out.println("体重大于50的：");
        employeeList=this.filterEmployee(arrayList,new FilterEmployeeByWeight());
        for (Employee employee:employeeList) {
            System.out.println(employee);
        }

    }
    /*
     *
     * 方案2使用内部类
     * */
    @Test
    public void test2(){
        List<Employee> employees = this.filterEmployee(arrayList, new MyPredicate<Employee>() {
            public boolean filter(Employee employee) {
                return employee.getAge() > 18;
            }
        });
        for (Employee employee:employees) {
            System.out.println(employee);
        }
    }
    /*
     * 方案3使用Lambda
     * */
    @Test
    public void test3(){
        filterEmployee(arrayList,(e)->e.getAge()>18).forEach(System.out::println);
    }
    /*
     * 方案4使用Stream API
     * limit
     * */
    @Test
    public void test4(){
        arrayList.stream().filter(employee -> employee.getAge()>18).forEach(System.out::println);
        //限制几名
        arrayList.stream().filter(employee -> employee.getWeight()>50).limit(2).forEach(System.out::println);
    }
    /*
     * 拿取实体类的个别属性
     *
     * */
    @Test
    public void test5(){
        arrayList.stream().filter(employee -> employee.getAge() > 18).map(Employee::getName).forEach(System.out::println);
        arrayList.stream().filter(employee -> employee.getWeight()>50).map(Employee::getName).forEach(System.out::append);
    }
    /*
     * 先比较年龄，如果年龄相同再比较姓名
     * */
    @Test
    public void test6(){
        Collections.sort(
                arrayList,(e1,e2)->{
                    if(e1.getAge()==e2.getAge()){
                        return e1.getName().compareTo(e2.getName());
                    }
                    return Integer.compare(e1.getAge(),e2.getAge());
                    //如果想实现倒叙的排序，直接在Integer前加-
                    //return -Integer.compare(e1.getAge(),e2.getAge());
                }
        );
        arrayList.stream().forEach(System.out::println);
    }
    public String stringHander(String str, MyFunction myFunction){
        return myFunction.getValue(str);
    }
    /*
     * 大小写，截取
     * */
    @Test
    public void test7(){
        String str = stringHander("wuchengen", e -> e.toUpperCase());
        System.out.println(str);
        String subStr = stringHander("wuchengen", e -> e.substring(0,5));
        System.out.println(subStr);

    }
    /*
     * 排序
     * */
    @Test
    public void test8(){
        List<Employee> employees = arrayList.stream().sorted(Comparator.comparing(Employee::getAge)).collect(Collectors.toList());
        employees.stream().forEach(System.out::println);
    }
    /*
     * 字符串返回结果
     * */
    @Test
    public void test9(){
        String str = arrayList.stream().filter(employee -> employee.getAge() > 18).map(Employee::getName).collect(Collectors.joining(","));
        System.out.println(str);
    }
    @Test
    public void test91(){
        AtomicInteger a = new AtomicInteger();
        AtomicInteger b = new AtomicInteger();
        for(int i = 0; i< 5;i++){
            System.out.println("a:"+a.getAndAdd(1));
            System.out.println("b:"+b.addAndGet(1));
        }

    }
    /*
     *聚合函数的应用
     * */
    @Test
    public void test10(){
        List<Employee> arrayList = new ArrayList<>();
        long count = arrayList.stream().filter(employee -> employee.getName().isEmpty()).count();
        IntSummaryStatistics intSummaryStatistics = arrayList.stream().mapToInt(Employee::getAge).summaryStatistics();
        int max = intSummaryStatistics.getMax();
        int min = intSummaryStatistics.getMin();
        long sum = intSummaryStatistics.getSum();
        double average = intSummaryStatistics.getAverage();
        System.out.println("姓名为空的人数："+count);
        System.out.println("最大的年龄："+max);
        System.out.println("最小的年龄："+min);
        System.out.println("年龄和："+sum);
        System.out.println("平均年龄："+average);
        List<Employee> employees = arrayList.stream().filter(employee -> employee.getAge() == min).collect(Collectors.toList());
        System.out.println("最小年龄的员工信息"+employees);
    }
    /*
     * 其中map有三种构造方法的重载：
     * 正常的key-value结构
     * toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper);
     * key存在冲突时，解决方案
     * toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper,BinaryOperator<U> mergeFunction);
     * 第四个参数是排序的方式
     * toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper,BinaryOperator<U> mergeFunction, Supplier<M> mapSupplier);
     * */

    @Test
    public void test11(){
        //得到姓名中包含"三"的人的姓名和员工号
        Map<Integer, String> map = arrayList.stream().filter(employee -> employee.getName().contains("三")).collect(Collectors.toMap(Employee::getId, Employee::getName));
        System.out.println(map);
        //若key存在冲突那么，可以调用tomap的第二个重载方式
        Map<Integer, String> map2 = arrayList.stream().collect(Collectors.toMap(Employee::getId, Employee::getName, (n, m) -> n +","+m));
        System.out.println(map2);
        //tomap四个参数，第四个参数是排序方式
        Map<Integer, String> map3 = arrayList.stream().collect(Collectors.toMap(Employee::getId, Employee::getName, (n, m) -> n +","+m,TreeMap::new));
        System.out.println(map3);
    }

    @Test
    public void test12(){
        arrayList.sort(Comparator.comparing(Employee::getId).reversed());
        IntStream.range(0,arrayList.size()).forEach(i -> {
            arrayList.get(i).setRanking(i);
            System.out.println(arrayList.get(i+1));
        });
        //System.out.println(queryWeakIndexList());
    }
    @Test
    public void test13(){
        List<Map<String,Integer>> list = new ArrayList<>();
        Map<String,Integer> map = new HashMap<>();
        map.put("A",1);
        list.add(map);
        Map<String,Integer> map1 = new HashMap<>();
        map1.put("B",2);
        list.add(map1);
        Map<String,Integer> map2 = new HashMap<>();
        map2.put("C",3);
        list.add(map2);
        Map<String,Integer> map3 = new HashMap<>();
        map3.put("D",4);
        list.add(map3);
        Map<String,Integer> map4 = new HashMap<>();
        map4.put("D",6);
        list.add(map4);
        Map<String,Integer> m = list.stream().flatMap(x -> x.entrySet().stream()).collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue(),(key1,key2)->key1));
        m.forEach((k,v) -> {
            System.out.println("K:"+k+",v:"+v);
        });
    }

    public static Map<String,String> queryWeakIndexList(){
        Map<String,String> map = new HashMap<>();
        for(IndexEnums indexEnums : IndexEnums.values()){
            if(indexEnums.getWeakIndex() && !indexEnums.getWeakIndexShowName().equals("STD") && !indexEnums.getWeakIndexShowName().equals("AAK")){
                map.put(indexEnums.getCode(),indexEnums.getWeakIndexShowName());
            }
        }
        return map;
    }
    public static Map<String,String> queryCoreWeakIndexList(){
        Map<String,String> map = new HashMap<>();
        for(IndexEnums indexEnums : IndexEnums.values()){
            if(indexEnums.getWeakIndex() && !indexEnums.getWeakIndexShowName().equals("三大邀约到店率")
                    && !indexEnums.getWeakIndexShowName().equals("展厅客流") && !indexEnums.getWeakIndexShowName().equals("三小邀约到店率")
                    && !indexEnums.getWeakIndexShowName().equals("活动客流") && !indexEnums.getWeakIndexShowName().equals("网络广告邀约到店率")){
                map.put(indexEnums.getCode(),indexEnums.getWeakIndexShowName());
            }
        }
        return map;
    }
    @Test
    public void queryLastMonth() {
        IntStream.range(0,arrayList.size()).forEach(i->{
            System.out.println(arrayList.get(i));
        });
    }

    @Test
    public void test111(){
        queryCoreWeakIndexList().forEach((k,v) -> {
            System.out.println("K:"+k+",v:"+v);
        });
    }
    @Test
    public void test222() throws ParseException {
        List<Employee> dataList = arrayList;
        dataList = dataList.stream().sorted(Comparator.comparing(Employee::getAge).reversed()).collect(Collectors.toList());
//        String size = "119.5KB";
//        System.out.println(convertData(size));
        //System.out.println(JSONObject.toJSONString(dataList));
        initParam(new HashMap<>());

    }

    public HashMap<String, Object> initParam(Map<String, Object> paramMap) {
        HashMap<String, Object> factorQueryMap = new HashMap<>();
        Calendar current = Calendar.getInstance();
//        current.add(Calendar.MONTH, -1);
//        factorQueryMap.put(CommonConst.CURRENT_MONTH, DateUtils.formatDate(current, CommonConst.FORMAT_MONTH));
//        factorQueryMap.put(CommonConst.CURRENT_MONTH_DAY, DateUtils.formatDate(current, CommonConst.FORMAT_DAY));
//        factorQueryMap.put(CommonConst.CURRENT_YEAR, DateUtils.formatDate(current, CommonConst.FORMAT_YEAR));
//        current.add(Calendar.MONTH, 1);
//        factorQueryMap.put(CommonConst.TARGET_MONTH, DateUtils.formatDate(current, CommonConst.FORMAT_MONTH));
//        factorQueryMap.put(CommonConst.TARGET_YEAR, DateUtils.formatDate(current, CommonConst.FORMAT_YEAR));
//        current.add(Calendar.MONTH, -2);
//        factorQueryMap.put(CommonConst.LAST_MONTH_MONTH, DateUtils.formatDate(current, CommonConst.FORMAT_MONTH));
//        factorQueryMap.put(CommonConst.LAST_MONTH_YEAR, DateUtils.formatDate(current, CommonConst.FORMAT_YEAR));
//        current.add(Calendar.MONTH, 1);
//        current.add(Calendar.YEAR, -1);
//        factorQueryMap.put(CommonConst.LAST_YEAR_MONTH, DateUtils.formatDate(current, CommonConst.FORMAT_MONTH));
//        factorQueryMap.put(CommonConst.LAST_YEAR_YEAR, DateUtils.formatDate(current, CommonConst.FORMAT_YEAR));
        factorQueryMap.put(CommonConst.CURRENT_MONTH, DateUtils.formatDate(current, CommonConst.FORMAT_MONTH));
        factorQueryMap.put(CommonConst.CURRENT_MONTH_DAY, DateUtils.formatDate(current, CommonConst.FORMAT_DAY));
        factorQueryMap.put(CommonConst.CURRENT_YEAR, DateUtils.formatDate(current, CommonConst.FORMAT_YEAR));
        current.add(Calendar.MONTH, 1);
        factorQueryMap.put(CommonConst.TARGET_MONTH, DateUtils.formatDate(current, CommonConst.FORMAT_MONTH));
        factorQueryMap.put(CommonConst.TARGET_YEAR, DateUtils.formatDate(current, CommonConst.FORMAT_YEAR));
        current.add(Calendar.MONTH, -2);
        factorQueryMap.put(CommonConst.LAST_MONTH_MONTH, DateUtils.formatDate(current, CommonConst.FORMAT_MONTH));
        factorQueryMap.put(CommonConst.LAST_MONTH_YEAR, DateUtils.formatDate(current, CommonConst.FORMAT_YEAR));
        current.add(Calendar.MONTH, 1);
        current.add(Calendar.YEAR, -1);
        factorQueryMap.put(CommonConst.LAST_YEAR_MONTH, DateUtils.formatDate(current, CommonConst.FORMAT_MONTH));
        factorQueryMap.put(CommonConst.LAST_YEAR_YEAR, DateUtils.formatDate(current, CommonConst.FORMAT_YEAR));
        System.out.println(JSONObject.toJSONString(factorQueryMap));
        return factorQueryMap;
    }

    public static int[] getDateScope(String startDate, String endDate) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = df.parse(startDate);
        Date date2 = df.parse(endDate);
        long nd = 1000 * 24 * 60 * 60;// ⼀天的毫秒数
        long nh = 1000 * 60 * 60;// ⼀⼩时的毫秒数
        long nm = 1000 * 60;// ⼀分钟的毫秒数
        long ns = 1000;// ⼀秒钟的毫秒数
        long diff;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        // 相差总的毫秒数
        diff = date2.getTime() - date1.getTime();
        day = diff / nd;// 计算差多少天
        hour = diff % nd / nh + day * 24;// 计算差多少⼩时
        min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
        sec = diff % nd % nh % nm / ns;// 计算差多少秒

        int[] scopes = new int[]{(int)day, (int)(hour - day * 24), (int)(min - day * 24 * 60), (int)sec};

        return scopes;
    }


    @Test
    public void testAA(){
        List<AreaRelationVO> voList = new ArrayList<>();
        Map<String,List<Area>> areaMap = areaList.stream().collect(Collectors.groupingBy(Area::getArea));
        areaMap.forEach((k,v) -> {
            AreaRelationVO vo = new AreaRelationVO();
            vo.setName(k);
            vo.setChilds(getChild(v));
            voList.add(vo);
        });
        System.out.println(JSONObject.toJSONString(voList));

        Integer a = 1;
        Integer b = 1;
        System.out.println(a.equals(b));
    }
    private List<AreaRelationVO> getChild(List<Area>list) {
        List<AreaRelationVO> voList = new ArrayList<>();
        Map<String,List<Area>> provinceMap = list.stream().collect(Collectors.groupingBy(Area::getProvince));
        //分别遍历每个顶级父类
        provinceMap.forEach((k,v) -> {
            AreaRelationVO vo = new AreaRelationVO();
            vo.setName(k);
            vo.setChilds(convert(v));
            voList.add(vo);
        });
        return voList;
    }
    private long convertData(String fileSize) {
        long size = 0;
        if(StringUtils.isEmpty(fileSize)){
            return size;
        }
        String sizeStr = "";
        if(fileSize.contains("GB")){
            sizeStr = fileSize.replace("GB","");
            size=  new BigDecimal(sizeStr).multiply( new BigDecimal(1073741824)).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
        }else if(fileSize.contains("MB")){
            sizeStr = fileSize.replace("MB","");
            size=  new BigDecimal(sizeStr).multiply( new BigDecimal(1048576)).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
        }else if(fileSize.contains("KB")){
            sizeStr = fileSize.replace("KB","");
            size=  new BigDecimal(sizeStr).multiply( new BigDecimal(1024)).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
        }else{
            sizeStr = fileSize.replace("B","");
            size=  new BigDecimal(sizeStr).multiply( new BigDecimal(1)).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
        }
        return size;
    }

    private List<AreaRelationVO> convert(List<Area> list) {
        List<AreaRelationVO> voList = new ArrayList<>();
        list.forEach(e -> {
            AreaRelationVO vo = new AreaRelationVO();
            vo.setName(e.getCity());
            voList.add(vo);
        });
        return voList;
    }

    @Test
    public void TestSS(){
//        long y= Math.abs(Integer.MIN_VALUE);
//        System.out.println(y);
//        int[] arr = {50,45,40,20,25,35,30,10,15};
        Integer a1 = 1;
        Integer b1 = 1;
        Integer c1 = null;
        BigDecimal zero = new BigDecimal(0);
        if(a1 != null){
            BigDecimal sale2b = new BigDecimal(a1);
            zero = zero.add(sale2b);
        }
        if(b1 != null){
            BigDecimal sale2c = new BigDecimal(b1);
            zero = zero.add(sale2c);
        }
        if(c1 != null){
            BigDecimal sale2m = new BigDecimal(c1);
            zero = zero.add(sale2m);
        }
        System.out.println(JSONObject.toJSONString(zero));

    }

    @Test
    public  void  test(){
//        List<Area> unique = areaList.stream().collect(Collectors.collectingAndThen(
//                        Collectors.toCollection(() -> new TreeSet<>(Collectors.comparingLong(Area::getArea))), ArrayList::new)
//        );
//        Map<Integer,String> map = new HashMap<>();
//        map.put(1,"s");
//        System.out.println(map.get(2));
        String a = "0.12342";
        String p = "^[0-9]+\\.{0,1}[0-9]{0,4}$";
        String str = "[{\"orgIds\": [\"10047795\"], \"parentIds\": [{\"id\": \"10047795\", \"orgName\": \"党群人事部\", \"parentId\": \"10031478\"}, {\"id\": \"10031478\", \"orgName\": \"营销中心\", \"parentId\": \"00001425\"}, {\"id\": \"00001425\", \"orgName\": \"中国第一汽车集团有限公司\", \"parentId\": \"\"}], \"businessUnits\": \"FAW50\", \"isMajorDeptForUser\": \"true\"}]";
        System.out.println(str.contains("10047795"));
        System.out.println(checkParam(a,p));

    }
    public static boolean checkParam(String num,String pattern) {
        return Pattern.matches(pattern, num);
    }
    @Test
    public  void  test123(){
        String name = "市场预测模板 (15).xlsx";
        System.out.println(name.contains("xlsx"));
        System.out.println(FileTypeEnum.get(name));
    }
    public static String subtract(String n1, String n2) {
        Assert.notBlank(n1);
        Assert.notBlank(n2);
        BigDecimal b1 = new BigDecimal(n1);
        BigDecimal b2 = new BigDecimal(n2);
        return b1.subtract(b2).toString();
    }
    @Test
    public void test2222(){
        int difference = Math.abs(Integer.parseInt(subtract(String.valueOf(3614),String.valueOf(3614))));
        System.out.println(difference);
    }

    @Test
    void isValid() throws Exception {

        System.out.println(isValid("2022-11-18 11:16:58","yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateUtils.parseDate("2022-11-18 11:16:58",DateUtils.FORMAT_DATE_TIME));
        System.out.println(parse("2022-11-18 11:16:58",DateUtils.FORMAT_DATE_TIME));
    }

    public static boolean isValid(String dateStr, String formatPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);
        try {
            sdf.parse(dateStr);
        } catch (ParseException ignored) {
            return false;
        }
        return true;
    }
    public static Date parse(String dateStr, String formatPattern) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);

        try {
            return sdf.parse(dateStr);
        } catch (ParseException ignored) {
        }

        throw new Exception("unable to parse the date " + dateStr + " with pattern " + formatPattern);
    }

    /**
     * 数字前面自动补零
     * @param length 长度
     * @param number 数字
     * @return
     */
    public static String geFourNumber(int length,int number){
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(length);
        formatter.setGroupingUsed(false);
        return formatter.format(number);
    }


    /**
     * <h>插入堆</h>
     * <li>1. 插入到完全二叉树的右子树</li>
     * <li>2. 上滤：和父节点比较，比父节点大则交换位置</li>
     * <li>3. 不断交换，完成插入</li>
     *
     * @param x
     */
    public void insert(List<Integer> list,int x) {

        //插入到数组末，相当于插入到完全二叉树的最右子树
        list.add(x);
        //获得当前插入节点的数组下标,是从 1 开始计
        int index = list.size() - 1;
        //该节点的父节点下标
        int pIndex = index / 2;
        //上滤
        while (index > 1) {
            //最大堆：如果当前节点比父节点值要大，则交换
            if (x > list.get(pIndex)) {
                list.set(index, list.get(pIndex));
                index = pIndex;
                //下一个父节点
                pIndex = index / 2;
            } else {
                //小则不用调整
                break;
            }
        }
        // 最终找到index 的位置，把值放进去
        list.set(index, x);
    }

//    @Test
//    public void test31(){
//        LinkedHashMap<Integer, List<Employee>>  auxMatStandardMap = arrayList.stream()
//                .collect(Collectors.groupingBy(Employee::getId,
//                        LinkedHashMap::new,Collectors.toList()));
//        auxMatStandardMap.forEach((k,v) -> System.out.println(v));
//        System.out.println(auxMatStandardMap);
//    }

    @Test
    public void test32(){
//        test321();
        String a = "1,111,111.33";
        LocalDate now = LocalDate.now();
        Date curDate = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        java.sql.Date planGenerationDate = new java.sql.Date(1678204800000l);
        boolean result = planGenerationDate.getTime() < curDate.getTime();
        System.out.println(result);
        //System.out.println(a.replace(",",""));
    }

    public static Date strToDate(String dateStr) {
        Date result;
        String format = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate date2 = LocalDate.parse(dateStr, formatter);
        LocalDateTime dateTime = LocalDateTime.parse(dateStr,formatter);
        long milleSecond = dateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
//        date2.
        result = new Date(milleSecond);
        return result;
    }


    public String test321(){
        String a = "0.00";
        if(StringUtils.isEmpty(a)){
            return "";
        }
        int decimalDigit = 0;

        BigDecimal value = new BigDecimal(a);

        // 去掉小数点多余0
        String trailingZerosValue = value.stripTrailingZeros().toString();

        String[] values = trailingZerosValue.split("\\.");
        if(values.length > 1){
            // 获取小数点位数
            decimalDigit = values[1].length();
        }
        System.out.println(StringUtil.valueToThousandFormat(a,decimalDigit,true));
        return StringUtil.valueToThousandFormat(a,decimalDigit,true);
    }

    @Test
    public void test322(){
        String str1 = "[{\"auxMateriel1\":\"DCFC\",\"auxMateriel1Des\":\"随车辅材\",\"auxMateriel2\":\"SQ\",\"auxMateriel2Des\":\"色漆\",\"density\":\"16\",\"glueHeight\":\"15\",\"glueWidth\":\"14\",\"id\":\"b3ffbeb2aa56714410339471adbab15a\",\"levelCode\":\"B\",\"materialCode\":\"12345\",\"materialDes\":\"我是fffff\",\"normAuxId\":\"8494d87c0b11f5e7eaded69ab665cb62\",\"number\":\"3\",\"opportunityCode\":\"S-20230301-0011\",\"positionCode\":\"BCS\",\"positionDes\":\"白车身\",\"positionItemCode\":\"5000020-DD01\",\"price\":2,\"processCode\":\"MQ\",\"processDes\":\"面漆\",\"quota\":17,\"stationCode\":\"BCSZC\",\"stationDes\":\"白车身总成\",\"supplierCode\":\"www\",\"supplierDes\":\"我是w\",\"targetCost\":34,\"trackLength\":\"13\",\"unitCode\":\"12\",\"vehicleSeries\":\"EQM5\",\"vehicleType\":\"SED\",\"workmanship\":\"TZ\",\"workmanshipDes\":\"涂装\"}]";
        String str2 = "";
        System.out.println(compareToData(str1,str2));
    }

    @Test
    public void test323(){
        System.out.println(IdUtils.fastSimpleUUID());  //最常用
        System.out.println(IdUtils.fastUUID());
        System.out.println(IdUtils.randomUUID());
        System.out.println(IdUtils.simpleUUID());
    }

    @Test
    public void test324(){

        System.out.println(new Date());
        System.out.println(addDay(new Date(),1));
    }

    /**
     * 日期增加天数
     * @param date 日期
     * @param number 天
     * @return 结果日期
     */
    public static Date addDay(Date date,int number){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, number);
        return calendar.getTime();
    }


    public static boolean compareToData(String a, String b){
        // 生成一个MD5加密计算摘要
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(a.getBytes());
            String s = new BigInteger(1, md.digest()).toString(16);

            // 计算md5函数
            MessageDigest md2 = MessageDigest.getInstance("MD5");
            md2.update(b.getBytes());
            String s1 = new BigInteger(1, md2.digest()).toString(16);

            if(Objects.equals(s1,s)){
                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }


}

package com.cjf.demo.utils;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import com.cjf.demo.constants.Constant;
import com.cjf.demo.enums.RegionEnum;
import com.cjf.demo.response.AreaOuterMonthResponse;
import com.cjf.demo.response.CarSeriesData;
import com.cjf.demo.vo.CarSeriesConfig;
import com.cjf.demo.vo.InnerMonthReleaseVO;
import com.cjf.demo.vo.KeyAndValue;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MathUtil {

    /**
     * 除法
     * @param n1 数字1
     * @param n2 数字2
     * @return 结果
     */
    public static String divideToString(String n1, String n2,int scale) {
        Assert.notBlank(n1);
        Assert.notBlank(n2);
        BigDecimal b1 = new BigDecimal(n1);
        BigDecimal b2 = new BigDecimal(n2);
        if (b2.compareTo(BigDecimal.ZERO) == 0) {
            return "0";
        }
        return b1.divide(b2,scale, RoundingMode.HALF_UP).toString();
    }

    /**
     * 乘法
     * @param n1 数字1
     * @param n2 数字2
     * @return 结果
     */
    public static String multiplyToString(String n1, String n2,int scale) {
        Assert.notBlank(n1);
        Assert.notBlank(n2);
        BigDecimal b1 = new BigDecimal(n1);
        BigDecimal b2 = new BigDecimal(n2);

        return b1.multiply(b2).setScale(scale, RoundingMode.HALF_UP).toString();
    }

    /**
     * 除法
     * @param n1 数字1
     * @param n2 数字2
     * @return 结果
     */
    public static BigDecimal divideToDecimal(String n1, String n2, int scale) {
        Assert.notBlank(n1);
        Assert.notBlank(n2);
        BigDecimal b1 = new BigDecimal(n1);
        BigDecimal b2 = new BigDecimal(n2);
        if (b2.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return b1.divide(b2,scale, RoundingMode.HALF_UP);
    }

    /**
     * 乘法
     * @param n1 数字1
     * @param n2 数字2
     * @return 结果
     */
    public static BigDecimal multiplyToDecimal(String n1, String n2, int scale) {
        Assert.notBlank(n1);
        Assert.notBlank(n2);
        BigDecimal b1 = new BigDecimal(n1);
        BigDecimal b2 = new BigDecimal(n2);

        return b1.multiply(b2).setScale(scale, RoundingMode.HALF_UP);
    }


    public static Float rate(Float t1, Float t2) {
        if (t1 == null || t2 == null) {
            return 0f;
        }
        return rateNumber(t1, t2).floatValue();
    }

    public static Float rate(String t1, String t2) {
        if (t1 == null || t2 == null) {
            return 0f;
        }
        return rateNumber(t1, t2).floatValue();
    }

    private static BigDecimal rateNumber(Number n1, Number n2) {
        return rateNumber(n1.toString(), n2.toString());
    }


    public static BigDecimal rateNumber(String n1, String n2) {
        Assert.notBlank(n1);
        Assert.notBlank(n2);
        BigDecimal b1 = new BigDecimal(n1);
        BigDecimal b2 = new BigDecimal(n2);
        if (b2.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return b1.divide(b2, 3, RoundingMode.HALF_UP);
    }


    /**
     * 减法
     * @param n1 数字1
     * @param n2 数字2
     * @return 结果
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
     * @param n1 数字1
     * @param n2 数字2
     * @return 结果
     */
    public static String divide(String n1, String n2,int scale) {
        Assert.notBlank(n1);
        Assert.notBlank(n2);
        BigDecimal b1 = new BigDecimal(n1);
        BigDecimal b2 = new BigDecimal(n2);
        if (b2.compareTo(BigDecimal.ZERO) == 0) {
            return "0";
        }
        return b1.divide(b2,scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 乘法
     * @param n1 数字1
     * @param n2 数字2
     * @return 结果
     */
    public static String multiply(String n1, String n2,int scale) {
        Assert.notBlank(n1);
        Assert.notBlank(n2);
        BigDecimal b1 = new BigDecimal(n1);
        BigDecimal b2 = new BigDecimal(n2);

        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static LinkedHashMap<String, LinkedHashMap<String, Integer>> calculateOuterMonth(
            LinkedHashMap<String, LinkedHashMap<String, Integer>> innerMonthAreaMap,
            LinkedHashMap<String, Integer> importMonthMap) {

        LinkedHashMap<String, LinkedHashMap<String, Integer>> result = new LinkedHashMap<>();

        List<KeyAndValue> allList = new ArrayList<>();

        innerMonthAreaMap.forEach((k,v) -> v.forEach((k1, v1) -> allList.add(KeyAndValue.builder()
                .key(k1)
                .value(v1)
                .build())));

        Map<String,Integer> sumMap = allList.stream().collect(Collectors.groupingBy(
                KeyAndValue::getKey,Collectors.summingInt(KeyAndValue::getValue)));

        AtomicInteger index = new AtomicInteger(Constant.OEN);
        LinkedHashMap<String, String> temRateMap = new LinkedHashMap<>();
        innerMonthAreaMap.forEach((k,v) -> {
            LinkedHashMap<String, Integer> temMap = new LinkedHashMap<>();
            v.forEach((k1,v2) -> {
                if(index.get() < innerMonthAreaMap.size()) {
                    temMap.put(k1,Integer.parseInt(calculateAreaCarSeriesValue(
                            calculateAreaCarSeriesRate(v2,sumMap.get(k1)),
                            importMonthMap.get(k1))));
                    temRateMap.put(k1,add(ifNullToZeroStr(temRateMap.get(k1)),
                            calculateAreaCarSeriesRate(v2,sumMap.get(k1))));
                } else {
                    System.out.println(k+":"+k1+"系数："+subtract(Constant.ONE,temRateMap.get(k1)));
                    temMap.put(k1,Integer.parseInt(calculateAreaCarSeriesValue(
                            subtract(Constant.ONE,temRateMap.get(k1)),
                            importMonthMap.get(k1))));
                }
            });
            result.put(k,temMap);
            index.getAndIncrement();
        });

        return result;
    }

    private static String ifNullToZeroStr(String value) {

        return Objects.isNull(value) ? Constant.ZERO_STR : value;

    }

    /**
     * 加法
     * @param n1 数字1
     * @param n2 数字2
     * @return 结果
     */
    public static String add(String n1, String n2) {
        Assert.notBlank(n1);
        Assert.notBlank(n2);
        BigDecimal b1 = new BigDecimal(n1);
        BigDecimal b2 = new BigDecimal(n2);
        return b1.add(b2).toString();
    }

    public static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Integer>>>> convertDealerResultToMap(
            List<InnerMonthReleaseVO> innerMonthReleaseList,
            LinkedHashMap<String, LinkedHashMap<String, Integer>> map) {

        LinkedHashMap<String,LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Integer>>>> result = new LinkedHashMap<>();

        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<InnerMonthReleaseVO>>>>  areaMap =
                innerMonthReleaseList.stream()
                        .sorted(Comparator.comparing(InnerMonthReleaseVO::getRegionalArea)
                                .thenComparing(InnerMonthReleaseVO::getSmallArea)
                                .thenComparing(InnerMonthReleaseVO::getDealerCode))
                        .collect(Collectors.groupingBy(InnerMonthReleaseVO::getRegionalArea,LinkedHashMap::new,
                                Collectors.groupingBy(InnerMonthReleaseVO::getSmallArea,LinkedHashMap::new,
                                        Collectors.groupingBy(InnerMonthReleaseVO::getDealerCode,LinkedHashMap::new,Collectors.toList()))));
        // 大区
        areaMap.forEach((k,v) -> {
            // 小区
            LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Integer>>> smallAreaMap = new LinkedHashMap<>();
            v.forEach((k1,v1) -> {
                // 经销商
                LinkedHashMap<String, LinkedHashMap<String, Integer>> dealerMap = new LinkedHashMap<>();
                v1.keySet().forEach(dealer -> {
                    // 车系-经销商
                    LinkedHashMap<String,Integer> temMap = new LinkedHashMap<>();
                    map.forEach((k3,v3) ->
                            temMap.put(k3,v3.get(dealer))
                    );
                    dealerMap.put(dealer,temMap);
                });
                smallAreaMap.put(k1,dealerMap);
            });
            result.put(k,smallAreaMap);
        });

        return result;
    }

    public static LinkedHashMap<String, LinkedHashMap<String, Integer>> convertListToDealerMap(
            List<AreaOuterMonthResponse> list) {
        LinkedHashMap<String, LinkedHashMap<String, Integer>> result = new LinkedHashMap<>();

        list.stream().sorted(Comparator.comparing(AreaOuterMonthResponse::getSmallArea)).forEach(data -> {

            LinkedHashMap<String, Integer> temMap = new LinkedHashMap<>();

            data.getCarSeriesDataList().stream().sorted(Comparator.comparing(CarSeriesData::getCarSeries))
                    .forEach(data1 -> temMap.put(data1.getCarSeries(),data1.getNumber()));

//            temMap.put(Constant.COL_SUM,temMap.values().stream().mapToInt(Integer::intValue).sum());

            result.put(data.getSmallArea(),temMap);
        });

        return result;
    }

    @Test
    public void test1(){
        String a = "1";
        String b = "0.0001";
//        System.out.println(subtract(a,b));
        Map<String,Integer> v = new HashMap<>();
        v.put("吉林北",1);
        v.put("沈阳",2);
        v.put("长春",3);
        v.put("大连",4);
        v.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .limit(1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new))
                .forEach((k2,v2) -> System.out.println(k2+","+v2));
//        v.forEach((k1,v1) -> System.out.println(k1+","+v1));

    }

    /**
     * 计算各区车系比率
     * @param innerAreaCarSeriesSum 内控各区各车系合计值
     * @param innerCarSeriesSum 内控各车系合计值
     * @return
     */
    private static String calculateAreaCarSeriesRate(Integer innerAreaCarSeriesSum, Integer innerCarSeriesSum) {
        return divide(Integer.toString(innerAreaCarSeriesSum),Integer.toString(innerCarSeriesSum),3);

    }

    /**
     * 计算各区车系值
     * @param innerAreaCarSeriesRate 内控各区各车系比率
     * @param importCarSeriesSum 导入车系合计值
     * @return
     */
    private static String calculateAreaCarSeriesValue(String innerAreaCarSeriesRate, Integer importCarSeriesSum) {
        String newValue = Objects.isNull(importCarSeriesSum) ? Constant.ZERO_STR : Integer.toString(importCarSeriesSum);
        return multiply(innerAreaCarSeriesRate,newValue,0);
    }


    public static void main(String[] args) {

    }

    /**
     * 最终调整
     * @param result 返回结果
     * @param rowResultMap 横向调平结果
     * @param colResultMap 纵向调平结果
     */
//    public static void finalLeveling(List<AreaOuterMonthResponse> result,
//                                      LinkedHashMap<String, LinkedHashMap<String, Integer>> rowResultMap,
//                                      LinkedHashMap<String, LinkedHashMap<String, Integer>> colResultMap) {
//        rowResultMap.forEach((k,v) -> {
//            List<CarSeriesData> carSeriesDataList = new ArrayList<>();
//            colResultMap.forEach((k1,v1) -> carSeriesDataList.add(CarSeriesData.builder()
//                    .carSeries(k1)
//                    .number(v1.get(k))
//                    .build()));
//
//            result.add(AreaOuterMonthResponse.builder()
//                    .areaId(k)
//                    .carSeriesDataList(carSeriesDataList)
//                    .build());
//        });
//    }
    /**
     * 最终调整
     * @param rowResultMap 横向调平结果
     * @param colResultMap 纵向调平结果
     * @param code 类型
     */
    public static List<AreaOuterMonthResponse> finalLeveling(LinkedHashMap<String, LinkedHashMap<String, Integer>> rowResultMap,
                                                            LinkedHashMap<String, LinkedHashMap<String, Integer>> colResultMap,
                                                            String code) {
        List<AreaOuterMonthResponse> result = new ArrayList<>();
        rowResultMap.forEach((k,v) -> {
            List<CarSeriesData> carSeriesDataList = new ArrayList<>();
            colResultMap.forEach((k1,v1) -> carSeriesDataList.add(CarSeriesData.builder()
                    .carSeries(k1)
                    .number(v1.get(k))
                    .build()));
            AreaOuterMonthResponse response = new AreaOuterMonthResponse();
            if(Objects.equals(RegionEnum.AREA.getCode(),code)){
                response.setAreaId(k);
            }else if(Objects.equals(RegionEnum.SMALL_AREA.getCode(),code)){
                response.setSmallArea(k);
            }else if(Objects.equals(RegionEnum.DEALER.getCode(),code)){
                response.setDealer(k);
            }
            int sum = carSeriesDataList.stream().filter(data -> !Objects.equals(data.getCarSeries(),"合计")).mapToInt(CarSeriesData::getNumber).sum();
            response.setSum(sum);
            carSeriesDataList.stream().filter(data -> Objects.equals(data.getCarSeries(),"合计")).forEach(data1 -> data1.setNumber(sum));
            response.setCarSeriesDataList(carSeriesDataList);
            result.add(response);
        });

        return result;
    }

    /**
     * 纵向调平
     *
     * @param rowResultMap          需要调平数据
     * @param importMonthMap        导入月汇总
     * @param innerMonthReleaseList 内控发布版数据
     * @param code 类型
     * @return 纵向调平结果
     */
    public static LinkedHashMap<String, LinkedHashMap<String, Integer>> colLeveling(
            LinkedHashMap<String, LinkedHashMap<String, Integer>> rowResultMap,
            LinkedHashMap<String, Integer> importMonthMap,
            List<InnerMonthReleaseVO> innerMonthReleaseList, String code) {

        LinkedHashMap<String, LinkedHashMap<String, Integer>> colResult = new LinkedHashMap<>();
        importMonthMap.forEach((k,v) -> {
            LinkedHashMap<String,Integer> colMap = new LinkedHashMap<>();
            rowResultMap.forEach((k1,v1) -> colMap.put(k1,v1.get(k)));

            // 纵向求和
            int colTotal = colMap.values().stream().mapToInt(i -> i).sum();
            int difference = Math.abs(Integer.parseInt(subtract(Integer.toString(v),Integer.toString(colTotal))));
            if(difference != 0){
                String oneStr = Integer.parseInt(subtract(String.valueOf(v),String.valueOf(colTotal))) > 0 ? Constant.ONE : "-1";
                LinkedHashMap<String, Integer> sortColMap;
                if(Objects.equals(oneStr,Constant.ONE)){
                    // 纵向排序正序
                    sortColMap = colMap.entrySet()
                            .stream()
                            .sorted((Map.Entry.comparingByValue()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                }else{
                    // 纵向排序倒序
                    sortColMap = colMap.entrySet()
                            .stream()
                            .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                }


                while (difference > 0){
                    for(Map.Entry<String, Integer> entry : sortColMap.entrySet()){
                        if(difference > 0){

                            int tempValue = Constant.ZERO;
                            if(Objects.equals(RegionEnum.AREA.getCode(),code)){
                                tempValue = innerMonthReleaseList.stream().filter(data -> Objects.equals(data.getRegionalArea(),entry.getKey())
                                        && Objects.equals(data.getCarSeries(),k)).mapToInt(InnerMonthReleaseVO::getAakValue).sum();
                            } else if (Objects.equals(RegionEnum.SMALL_AREA.getCode(),code)) {
                                tempValue = innerMonthReleaseList.stream().filter(data -> Objects.equals(data.getSmallArea(),entry.getKey())
                                        && Objects.equals(data.getCarSeries(),k)).mapToInt(InnerMonthReleaseVO::getAakValue).sum();
                            } else if (Objects.equals(RegionEnum.DEALER.getCode(),code)){
                                tempValue = innerMonthReleaseList.stream().filter(data -> Objects.equals(data.getDealerCode(),entry.getKey())
                                        && Objects.equals(data.getCarSeries(),k)).mapToInt(InnerMonthReleaseVO::getAakValue).sum();
                            }

                            if(tempValue != Constant.ZERO || Objects.equals(k,Constant.ROW_SUM)){
                                if(Objects.equals(oneStr,Constant.NEGATIVE_ONE)){
                                    if(colMap.get(entry.getKey()) != Constant.ZERO){
                                        colMap.put(entry.getKey(),Integer.parseInt(add(Integer.toString(colMap.get(entry.getKey())),oneStr)));
                                        difference--;
                                    }
                                }else{
                                    colMap.put(entry.getKey(),Integer.parseInt(add(Integer.toString(colMap.get(entry.getKey())),oneStr)));
                                    difference--;
                                }
                            }
                        }
                    }
                }
            }
            colResult.put(k,colMap);
        });
        return colResult;
    }

    /**
     * 横向调平
     *
     * @param resultMap             需要调平数据
     * @param hotCarTotal           热门车系数量合计（导入）
     * @param hotCars               热门车系
     * @param importMonthMap        导入月汇总
     * @param innerMonthReleaseList
     * @param code
     * @return 横向调平结果
     */
    public static LinkedHashMap<String, LinkedHashMap<String, Integer>> rowLeveling(
            LinkedHashMap<String, LinkedHashMap<String, Integer>> resultMap,
            Integer hotCarTotal,
            List<String> hotCars,
            LinkedHashMap<String, Integer> importMonthMap,
            List<InnerMonthReleaseVO> innerMonthReleaseList,
            String code) {
        LinkedHashMap<String, LinkedHashMap<String, Integer>> rowResultMap = new LinkedHashMap<>();
        // 计算出纵向差值
        LinkedHashMap<String,Integer> colDiffMap = new LinkedHashMap<>();
        importMonthMap.entrySet().stream().filter(mapA -> !Objects.equals(mapA.getKey(),"合计")).forEach(entry -> {
            int calColSum = Constant.ZERO;
            for(Map.Entry<String, LinkedHashMap<String, Integer>> map : resultMap.entrySet()){
                calColSum += map.getValue().get(entry.getKey());
            }
            String difference = subtract(String.valueOf(entry.getValue()),String.valueOf(calColSum));
            colDiffMap.put(entry.getKey(),Integer.parseInt(difference));
        });

        resultMap.forEach((k,v) -> {
            int rowTotal = v.get("合计");
            int carSeriesTotal = v.entrySet().stream()
                    .filter(mapA -> !Objects.equals(mapA.getKey(),"合计"))
                    .mapToInt(Map.Entry::getValue).sum();
            int difference = Integer.parseInt(subtract(String.valueOf(rowTotal),String.valueOf(carSeriesTotal)));

            LinkedHashMap<String,Integer> sortColMap = new LinkedHashMap<>();
            if(difference != 0){
                if(difference < 0){
                    // 纵向排序倒序
                    sortColMap = colDiffMap.entrySet()
                            .stream()
                            .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                }else{
                    // 纵向排序正序
                    sortColMap = colDiffMap.entrySet()
                            .stream()
                            .sorted((Map.Entry.comparingByValue()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                }
                while (difference != 0){
                    for(Map.Entry<String, Integer> entry : sortColMap.entrySet()){
                        int tempValue = Constant.ZERO;
                        if(Objects.equals(RegionEnum.AREA.getCode(),code)){
                            tempValue = innerMonthReleaseList.stream().filter(data -> Objects.equals(data.getRegionalArea(),k)
                                    && Objects.equals(data.getCarSeries(),entry.getKey())).mapToInt(InnerMonthReleaseVO::getAakValue).sum();
                        } else if (Objects.equals(RegionEnum.SMALL_AREA.getCode(),code)) {
                            tempValue = innerMonthReleaseList.stream().filter(data -> Objects.equals(data.getSmallArea(),k)
                                    && Objects.equals(data.getCarSeries(),entry.getKey())).mapToInt(InnerMonthReleaseVO::getAakValue).sum();
                        } else if (Objects.equals(RegionEnum.DEALER.getCode(),code)){
                            tempValue = innerMonthReleaseList.stream().filter(data -> Objects.equals(data.getDealerCode(),k)
                                    && Objects.equals(data.getCarSeries(),entry.getKey())).mapToInt(InnerMonthReleaseVO::getAakValue).sum();
                        }

                        if(tempValue != Constant.ZERO || Objects.equals(k,Constant.ROW_SUM)){
                            if(difference < 0){
                                if(!Objects.equals(v.get(entry.getKey()), Constant.ZERO)){
                                    v.put(entry.getKey(),Integer.parseInt(subtract(Integer.toString(v.get(entry.getKey())),Constant.ONE)));
                                    difference++;
                                }
                            }else{
                                v.put(entry.getKey(),Integer.parseInt(add(Integer.toString(v.get(entry.getKey())),Constant.ONE)));
                                difference--;
                            }
                        }
                    }
                }
            }


//            for(String horCar : hotCars){
//                if(Objects.nonNull(v.get(horCar))){
//                    String newValue;
//                    if(index.get() < hotCars.size()) {
//                        newValue = calculateHotCarSeries(difference,importMonthMap.get(horCar),hotCarTotal);
//                        temValue += Integer.parseInt(newValue);
//                    }else {
//                        newValue = subtract(difference,Integer.toString(temValue));
//                    }
//                    if(Integer.parseInt(add(String.valueOf(v.get(horCar)),newValue)) > Constant.INTEGER_0) {
//                        v.put(horCar, Integer.parseInt(add(String.valueOf(v.get(horCar)), newValue)));
//                    }
//                    index.getAndIncrement();
//                }
//            }
            rowResultMap.put(k,v);
        });
        return rowResultMap;
    }

    /**
     *
     * @param difference 差值
     * @param value 要计算的单个热门车系值
     * @param hotCarTotal 热门车系合计
     * @return
     */
    private static String calculateHotCarSeries(String difference, Integer value, Integer hotCarTotal) {
        String valueStr = Objects.isNull(value) ? Constant.ZERO_STR : Integer.toString(value);
       return divide(multiply(difference,valueStr,0),Integer.toString(hotCarTotal),0);
    }

    /**
     * 调平
     *
     * @param resultMap             需要调平数据
     * @param carSeriesConfigList   热门车系
     * @param importMonthMap        导入汇总
     * @param code                  类型
     * @param innerMonthReleaseList 内控发布版列表
     */
    public static List<AreaOuterMonthResponse> levelingOuterMonth(LinkedHashMap<String, LinkedHashMap<String, Integer>> resultMap,
                                                                  List<CarSeriesConfig> carSeriesConfigList,
                                                                  LinkedHashMap<String, Integer> importMonthMap,
                                                                  String code, List<InnerMonthReleaseVO> innerMonthReleaseList) {
        List<String> hotCars = carSeriesConfigList.stream().map(CarSeriesConfig::getCarSeries).distinct().collect(Collectors.toList());
        Integer hotCarTotal = importMonthMap.entrySet().stream().filter(data -> hotCars.contains(data.getKey()))
                .mapToInt(Map.Entry::getValue).sum();
        // 横向调平
        System.out.println("需要调平值");
        resultMap.forEach((k,v) -> {
            System.out.println(k+":"+v);
        });
        System.out.println("横向调平");
        LinkedHashMap<String, LinkedHashMap<String, Integer>> rowResultMap = rowLeveling(
                resultMap,hotCarTotal,hotCars,importMonthMap,innerMonthReleaseList,code);
        rowResultMap.forEach((k,v) -> {
            System.out.println(k+":"+ v);
        });
        // 纵向调平
        System.out.println("纵向调平");
        LinkedHashMap<String, LinkedHashMap<String, Integer>> colResultMap = colLeveling(rowResultMap,importMonthMap,innerMonthReleaseList,code);
        colResultMap.forEach((k,v) -> {
            System.out.println(k+":"+ v);
        });
        // 最终调平
        System.out.println("最终调平");
        List<AreaOuterMonthResponse> result = finalLeveling(resultMap,colResultMap,code);
        System.out.println(JSONObject.toJSONString(result));
        return result;

    }

    /**
     * 车系-小区汇总 转 小区-车系汇总
     * @param innerMonthReleaseList 内控发布版
     * @param map 计算完成的 车系-小区汇总
     * @return 结果
     */
    public static LinkedHashMap<String,LinkedHashMap<String, LinkedHashMap<String, Integer>>> convertSmallAreaResultToMap(
            List<InnerMonthReleaseVO> innerMonthReleaseList,
            LinkedHashMap<String, LinkedHashMap<String, Integer>> map) {

        LinkedHashMap<String,LinkedHashMap<String, LinkedHashMap<String, Integer>>> result = new LinkedHashMap<>();

//        LinkedHashMap<String, LinkedHashMap<String, List<InnerMonthReleaseVO>>>  areaMap =
//                innerMonthReleaseList.stream()
//                        .sorted(Comparator.comparing(InnerMonthReleaseVO::getRegionalArea)
//                                .thenComparing(InnerMonthReleaseVO::getSmallArea))
//                        .collect(Collectors.groupingBy(InnerMonthReleaseVO::getRegionalArea,LinkedHashMap::new,
//                                Collectors.groupingBy(InnerMonthReleaseVO::getSmallArea,LinkedHashMap::new,Collectors.toList())));
//
        LinkedHashMap<String, LinkedHashMap<String, List<InnerMonthReleaseVO>>> areaMap = getAreaSmallAreaMap();
        // 大区
        areaMap.forEach((k,v) -> {
            // 小区
            LinkedHashMap<String, LinkedHashMap<String, Integer>> smallAreaMap = new LinkedHashMap<>();
            v.keySet().forEach(smallArea -> {
                // 车系-小区
                LinkedHashMap<String,Integer> temMap = new LinkedHashMap<>();
                map.forEach((k2,v2) ->
                        temMap.put(k2,v2.get(smallArea))
                );
                System.out.println(k+":"+smallArea+":"+temMap);
                smallAreaMap.put(smallArea,temMap);
            });
            result.put(k,smallAreaMap);
        });

        return result;

    }

    private static LinkedHashMap<String, LinkedHashMap<String, List<InnerMonthReleaseVO>>> getAreaSmallAreaMap() {
        LinkedHashMap<String,LinkedHashMap<String, List<InnerMonthReleaseVO>>> result = new LinkedHashMap<>();
        LinkedHashMap<String, List<InnerMonthReleaseVO>> map = new LinkedHashMap<>();
        map.put("黑蒙",new ArrayList<>());
        map.put("吉林北",new ArrayList<>());
        map.put("吉林南",new ArrayList<>());
        map.put("冀南",new ArrayList<>());
        map.put("津冀北",new ArrayList<>());
        map.put("京蒙",new ArrayList<>());
        map.put("辽北蒙",new ArrayList<>());
        map.put("辽南",new ArrayList<>());
        map.put("山西",new ArrayList<>());
        map.put("豫北",new ArrayList<>());
        map.put("豫南",new ArrayList<>());
        result.put("北区",map);
        LinkedHashMap<String, List<InnerMonthReleaseVO>> map_1 = new LinkedHashMap<>();
        map_1.put("鲁东",new ArrayList<>());
        map_1.put("鲁南",new ArrayList<>());
        map_1.put("鲁西",new ArrayList<>());
        map_1.put("上海",new ArrayList<>());
        map_1.put("苏北",new ArrayList<>());
        map_1.put("苏南",new ArrayList<>());
        map_1.put("苏中",new ArrayList<>());
        map_1.put("皖北",new ArrayList<>());
        map_1.put("皖南",new ArrayList<>());
        map_1.put("浙北",new ArrayList<>());
        map_1.put("浙南",new ArrayList<>());
        map_1.put("浙中",new ArrayList<>());
        result.put("东区",map_1);
        LinkedHashMap<String, List<InnerMonthReleaseVO>> map_2 = new LinkedHashMap<>();
        map_2.put("鄂北",new ArrayList<>());
        map_2.put("鄂南",new ArrayList<>());
        map_2.put("广州",new ArrayList<>());
        map_2.put("江西",new ArrayList<>());
        map_2.put("闽琼",new ArrayList<>());
        map_2.put("深圳",new ArrayList<>());
        map_2.put("湘北",new ArrayList<>());
        map_2.put("湘南",new ArrayList<>());
        map_2.put("粤西北",new ArrayList<>());
        map_2.put("粤中东",new ArrayList<>());
        result.put("南区",map_2);
        LinkedHashMap<String, List<InnerMonthReleaseVO>> map_3 = new LinkedHashMap<>();
        map_3.put("成都",new ArrayList<>());
        map_3.put("川北",new ArrayList<>());
        map_3.put("滇北",new ArrayList<>());
        map_3.put("滇南",new ArrayList<>());
        map_3.put("甘宁青藏",new ArrayList<>());
        map_3.put("广西", new ArrayList<>());
        map_3.put("贵州",new ArrayList<>());
        map_3.put("陕西",new ArrayList<>());
        map_3.put("新疆",new ArrayList<>());
        map_3.put("渝川南",new ArrayList<>());
        result.put("西区",map_3);

        return result;
    }

    /**
     * list -> map
     * @param list 输入数据
     * @return 结果
     */
    public static LinkedHashMap<String, LinkedHashMap<String, Integer>> convertListToMap(List<AreaOuterMonthResponse> list) {

        LinkedHashMap<String, LinkedHashMap<String, Integer>> result = new LinkedHashMap<>();

        list.stream().sorted(Comparator.comparing(AreaOuterMonthResponse::getAreaId)).forEach(data -> {

            LinkedHashMap<String, Integer> temMap = new LinkedHashMap<>();

            data.getCarSeriesDataList().stream().sorted(Comparator.comparing(CarSeriesData::getCarSeries))
                    .forEach(data1 -> temMap.put(data1.getCarSeries(),data1.getNumber()));

//            temMap.put(Constant.COL_SUM,temMap.values().stream().mapToInt(Integer::intValue).sum());

            result.put(data.getAreaId(),temMap);
        });

        return result;
    }

    /**
     * 计算小区或经销商 车系合计
     *
     * @param innerMonthReleaseList         内控月发布数据
     * @param areaOuterMonthResponseList    外报大区-车系数据
     * @param calculateCarSeriesCoefficient 车系-比列系数
     * @param code 类型
     * @return 结果
     */
    public static LinkedHashMap<String, LinkedHashMap<String, Integer>> calculateSmallAreaOrDealerResultMap(
            List<InnerMonthReleaseVO> innerMonthReleaseList,
            List<AreaOuterMonthResponse> areaOuterMonthResponseList,
            LinkedHashMap<String, String> calculateCarSeriesCoefficient, String code) {

        // 1、计算内控车系-小区或经销商汇总
        LinkedHashMap<String, LinkedHashMap<String, Integer>> innerCarSeriesSmallAreaSum ;

        if(Objects.equals(RegionEnum.SMALL_AREA.getCode(),code)){
            innerCarSeriesSmallAreaSum = calculateInnerCarSeriesSmallAreaSum(innerMonthReleaseList);
        }else{
            innerCarSeriesSmallAreaSum = calculateInnerCarSeriesDealerSum(innerMonthReleaseList);
        }
        // 2、计算外报车系汇总
        LinkedHashMap<String, Integer> outerCarSeriesSum = calculateOuterCarSeriesSum(areaOuterMonthResponseList);

        LinkedHashMap<String,LinkedHashMap<String, Integer>> result = new LinkedHashMap<>();
        // 3、循环所有车系
        innerCarSeriesSmallAreaSum.forEach((k,v) -> {
            // 计算各车系系数
            String coefficient = calculateCarSeriesCoefficient.get(k);

            System.out.println("系数:"+k+":"+coefficient);
            System.out.println("原值:"+k+":"+v);
            // 循环小区或经销商
            LinkedHashMap<String,Integer> temMap = new LinkedHashMap<>();
            LinkedHashMap<String,Double> temMap2 = new LinkedHashMap<>();
            for(Map.Entry<String,Integer> map : v.entrySet()){
                // 实际值
                String actualValue = multiply(intToString(map.getValue()),coefficient,Constant.INTEGER_0);
                if(map.getValue() != Constant.INTEGER_0){
                    // 应的值
                    String dueValue = multiply(intToString(map.getValue()),coefficient,Constant.INTEGER_3);

                    temMap2.put(map.getKey(),Double.valueOf(subtract(dueValue,actualValue)));
                }
                temMap.put(map.getKey(),Integer.parseInt(actualValue));

            }
            System.out.println("计算值:"+k+":"+temMap);
            System.out.println("实际和应得差值:"+k+":"+temMap2);
            // 计算内控各车系合计
            Integer totalCount = temMap.values().stream().mapToInt(Integer::intValue).sum();
            //计算差值
            int difference = Integer.parseInt(subtract(intToString(totalCount), intToString(outerCarSeriesSum.get(k))));
            System.out.println("差值:"+k+":"+difference);

//            // 如果差值绝对值大于 1 继续计算
//            while (Math.abs(Integer.parseInt(difference)) > Constant.INTEGER_1){
//                // 内控计算合计小于 外报合计
//                if(Integer.parseInt(difference) > Constant.INTEGER_1){
//                    // 系数-0.0001
//                    coefficient = subtract(coefficient,Constant.COEFFICIENT);
//                }else if(Integer.parseInt(difference) < Constant.INTEGER_NEGATIVE_1){
//                    // 系数+0.0001
//                    coefficient = add(coefficient, Constant.COEFFICIENT);
//                }
//                // 循环小区或经销商
//                temMap.clear();
//                for(Map.Entry<String,Integer> map : v.entrySet()){
//                    temMap.put(map.getKey(),Integer.parseInt(multiply(intToString(map.getValue()),coefficient,0)));
//                }
//                // 计算内控各车系合计
//                Integer temTotalCount = temMap.values().stream().mapToInt(Integer::intValue).sum();
//                //计算差值
//                difference = subtract(intToString(temTotalCount), intToString(outerCarSeriesSum.get(k)));
//            }
//            System.out.println("新系数:"+k+":"+coefficient);
//            System.out.println("乘新系数值:"+k+":"+temMap);
//            System.out.println("新差值:"+k+":"+difference);
            while (difference != Constant.INTEGER_0){
                if(difference > Constant.INTEGER_0){
                    LinkedHashMap<String,Double> sortMap = temMap2.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByValue())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                    System.out.println("升序:"+sortMap);
                    for(Map.Entry<String,Double> map : sortMap.entrySet()){
                        if(difference != Constant.INTEGER_0){
                            if(temMap.get(map.getKey()) != Constant.INTEGER_0){
                                temMap.put(map.getKey(),Integer.parseInt(subtract(intToString(temMap.get(map.getKey())),Constant.INTEGER_STR_1)));
                            }
                            difference--;
                        }
                    }
                }else if(difference < Constant.INTEGER_0){
                    LinkedHashMap<String,Double> sortMap = temMap2.entrySet()
                            .stream()
                            .sorted((Map.Entry.<String, Double>comparingByValue().reversed()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                    System.out.println("降序:"+sortMap);
                    for(Map.Entry<String,Double> map : sortMap.entrySet()){
                        if(difference != Constant.INTEGER_0){
                            temMap.put(map.getKey(),Integer.parseInt(add(intToString(temMap.get(map.getKey())),Constant.INTEGER_STR_1)));
                            difference++;
                        }
                    }
                }
            }

            result.put(k,temMap);
            System.out.println("新值:"+k+":"+temMap);
        });
        System.out.println("--------------------------------------------------------------------------------------------------------");
        return result;
    }

    private static LinkedHashMap<String, LinkedHashMap<String, Integer>> getCarSeriesSmallAreaMap() {
        LinkedHashMap<String, LinkedHashMap<String, Integer>> map = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> map1 = new LinkedHashMap<>();
        map1.put("黑蒙",36);
        map1.put("吉林北",66);
        map1.put("吉林南",16);
        map1.put("冀南",103);
        map1.put("津冀北",80);
        map1.put("京蒙",23);
        map1.put("辽北蒙",34);
        map1.put("辽南",23);
        map1.put("山西",67);
        map1.put("豫北",120);
        map1.put("豫南",83);
        map1.put("鲁东",71);
        map1.put("鲁南",80);
        map1.put("鲁西",95);
        map1.put("上海",12);
        map1.put("苏北",51);
        map1.put("苏南",70);
        map1.put("苏中",27);
        map1.put("皖北",144);
        map1.put("皖南",50);
        map1.put("浙北",80);
        map1.put("浙南",38);
        map1.put("浙中",94);
        map1.put("鄂北",26);
        map1.put("鄂南",28);
        map1.put("广州",32);
        map1.put("江西",48);
        map1.put("闽琼",58);
        map1.put("深圳",24);
        map1.put("湘北",50);
        map1.put("湘南",36);
        map1.put("粤西北",27);
        map1.put("粤中东",83);
        map1.put("成都",140);
        map1.put("川北",62);
        map1.put("滇北",43);
        map1.put("滇南",78);
        map1.put("甘宁青藏",78);
        map1.put("广西",59);
        map1.put("贵州",62);
        map1.put("陕西",134);
        map1.put("新疆",48);
        map1.put("渝川南",125);

        map.put("H5经典",map1);

        LinkedHashMap<String, Integer> map2 = new LinkedHashMap<>();

        map2.put("黑蒙",106);
        map2.put("吉林北",283);
        map2.put("吉林南",62);
        map2.put("冀南",308);
        map2.put("津冀北",202);
        map2.put("京蒙",411);
        map2.put("辽北蒙",143);
        map2.put("辽南",146);
        map2.put("山西",157);
        map2.put("豫北",363);
        map2.put("豫南",344);
        map2.put("鲁东",267);
        map2.put("鲁南",152);
        map2.put("鲁西",221);
        map2.put("上海",200);
        map2.put("苏北",165);
        map2.put("苏南",203);
        map2.put("苏中",206);
        map2.put("皖北",314);
        map2.put("皖南",141);
        map2.put("浙北",240);
        map2.put("浙南",84);
        map2.put("浙中",203);
        map2.put("鄂北",179);
        map2.put("鄂南",181);
        map2.put("广州",136);
        map2.put("江西",262);
        map2.put("闽琼",326);
        map2.put("深圳",164);
        map2.put("湘北",226);
        map2.put("湘南",178);
        map2.put("粤西北",82);
        map2.put("粤中东",230);
        map2.put("成都",479);
        map2.put("川北",292);
        map2.put("滇北",152);
        map2.put("滇南",342);
        map2.put("甘宁青藏",280);
        map2.put("广西",211);
        map2.put("贵州",542);
        map2.put("陕西",354);
        map2.put("新疆",67);
        map2.put("渝川南",396);

        map.put("HS5",map2);

        LinkedHashMap<String, Integer> map3 = new LinkedHashMap<>();
        map3.put("黑蒙",1);
        map3.put("吉林北",3);
        map3.put("吉林南",1);
        map3.put("冀南",3);
        map3.put("津冀北",3);
        map3.put("京蒙",14);
        map3.put("辽北蒙",0);
        map3.put("辽南",1);
        map3.put("山西",11);
        map3.put("豫北",3);
        map3.put("豫南",0);
        map3.put("鲁东",2);
        map3.put("鲁南",0);
        map3.put("鲁西",4);
        map3.put("上海",4);
        map3.put("苏北",8);
        map3.put("苏南",7);
        map3.put("苏中",7);
        map3.put("皖北",6);
        map3.put("皖南",3);
        map3.put("浙北",16);
        map3.put("浙南",2);
        map3.put("浙中",0);
        map3.put("鄂北",6);
        map3.put("鄂南",4);
        map3.put("广州",5);
        map3.put("江西",25);
        map3.put("闽琼",12);
        map3.put("深圳",3);
        map3.put("湘北",8);
        map3.put("湘南",8);
        map3.put("粤西北",4);
        map3.put("粤中东",5);
        map3.put("成都",10);
        map3.put("川北",2);
        map3.put("滇北",0);
        map3.put("滇南",1);
        map3.put("甘宁青藏",13);
        map3.put("广西",3);
        map3.put("贵州",30);
        map3.put("陕西",4);
        map3.put("新疆",3);
        map3.put("渝川南",14);

        map.put("H7",map3);

        LinkedHashMap<String, Integer> map4 = new LinkedHashMap<>();
        map4.put("黑蒙",8);
        map4.put("吉林北",10);
        map4.put("吉林南",5);
        map4.put("冀南",8);
        map4.put("津冀北",9);
        map4.put("京蒙",21);
        map4.put("辽北蒙",5);
        map4.put("辽南",3);
        map4.put("山西",7);
        map4.put("豫北",6);
        map4.put("豫南",6);
        map4.put("鲁东",8);
        map4.put("鲁南",8);
        map4.put("鲁西",9);
        map4.put("上海",8);
        map4.put("苏北",9);
        map4.put("苏南",13);
        map4.put("苏中",5);
        map4.put("皖北",9);
        map4.put("皖南",5);
        map4.put("浙北",7);
        map4.put("浙南",4);
        map4.put("浙中",7);
        map4.put("鄂北",6);
        map4.put("鄂南",7);
        map4.put("广州",4);
        map4.put("江西",11);
        map4.put("闽琼",16);
        map4.put("深圳",5);
        map4.put("湘北",10);
        map4.put("湘南",5);
        map4.put("粤西北",2);
        map4.put("粤中东",6);
        map4.put("成都",20);
        map4.put("川北",15);
        map4.put("滇北",10);
        map4.put("滇南",16);
        map4.put("甘宁青藏",18);
        map4.put("广西",13);
        map4.put("贵州",19);
        map4.put("陕西",10);
        map4.put("新疆",5);
        map4.put("渝川南",16);

        map.put("HS7",map4);

        LinkedHashMap<String, Integer> map5 = new LinkedHashMap<>();
        map5.put("黑蒙",16);
        map5.put("吉林北",30);
        map5.put("吉林南",7);
        map5.put("冀南",48);
        map5.put("津冀北",20);
        map5.put("京蒙",70);
        map5.put("辽北蒙",20);
        map5.put("辽南",15);
        map5.put("山西",22);
        map5.put("豫北",38);
        map5.put("豫南",32);
        map5.put("鲁东",39);
        map5.put("鲁南",28);
        map5.put("鲁西",41);
        map5.put("上海",15);
        map5.put("苏北",23);
        map5.put("苏南",27);
        map5.put("苏中",32);
        map5.put("皖北",53);
        map5.put("皖南",15);
        map5.put("浙北",28);
        map5.put("浙南",7);
        map5.put("浙中",18);
        map5.put("鄂北",19);
        map5.put("鄂南",28);
        map5.put("广州",23);
        map5.put("江西",33);
        map5.put("闽琼",53);
        map5.put("深圳",26);
        map5.put("湘北",29);
        map5.put("湘南",20);
        map5.put("粤西北",10);
        map5.put("粤中东",35);
        map5.put("成都",39);
        map5.put("川北",17);
        map5.put("滇北",11);
        map5.put("滇南",19);
        map5.put("甘宁青藏",19);
        map5.put("广西",28);
        map5.put("贵州",50);
        map5.put("陕西",22);
        map5.put("新疆",9);
        map5.put("渝川南",30);

        map.put("H9",map5);

        LinkedHashMap<String, Integer> map6 = new LinkedHashMap<>();
        map6.put("黑蒙",1);
        map6.put("吉林北",3);
        map6.put("吉林南",0);
        map6.put("冀南",1);
        map6.put("津冀北",1);
        map6.put("京蒙",5);
        map6.put("辽北蒙",0);
        map6.put("辽南",3);
        map6.put("山西",2);
        map6.put("豫北",1);
        map6.put("豫南",0);
        map6.put("鲁东",2);
        map6.put("鲁南",2);
        map6.put("鲁西",2);
        map6.put("上海",8);
        map6.put("苏北",4);
        map6.put("苏南",5);
        map6.put("苏中",3);
        map6.put("皖北",2);
        map6.put("皖南",1);
        map6.put("浙北",4);
        map6.put("浙南",2);
        map6.put("浙中",3);
        map6.put("鄂北",1);
        map6.put("鄂南",2);
        map6.put("广州",5);
        map6.put("江西",0);
        map6.put("闽琼",7);
        map6.put("深圳",4);
        map6.put("湘北",3);
        map6.put("湘南",2);
        map6.put("粤西北",1);
        map6.put("粤中东",7);
        map6.put("成都",1);
        map6.put("川北",1);
        map6.put("滇北",1);
        map6.put("滇南",2);
        map6.put("甘宁青藏",3);
        map6.put("广西",1);
        map6.put("贵州",1);
        map6.put("陕西",1);
        map6.put("新疆",1);
        map6.put("渝川南",1);

        map.put("E-HS9",map6);

        LinkedHashMap<String, Integer> map7 = new LinkedHashMap<>();
        map7.put("黑蒙",47);
        map7.put("吉林北",66);
        map7.put("吉林南",25);
        map7.put("冀南",148);
        map7.put("津冀北",102);
        map7.put("京蒙",161);
        map7.put("辽北蒙",61);
        map7.put("辽南",52);
        map7.put("山西",103);
        map7.put("豫北",126);
        map7.put("豫南",89);
        map7.put("鲁东",161);
        map7.put("鲁南",135);
        map7.put("鲁西",154);
        map7.put("上海",122);
        map7.put("苏北",133);
        map7.put("苏南",160);
        map7.put("苏中",120);
        map7.put("皖北",248);
        map7.put("皖南",78);
        map7.put("浙北",188);
        map7.put("浙南",81);
        map7.put("浙中",161);
        map7.put("鄂北",99);
        map7.put("鄂南",150);
        map7.put("广州",116);
        map7.put("江西",191);
        map7.put("闽琼",260);
        map7.put("深圳",126);
        map7.put("湘北",131);
        map7.put("湘南",94);
        map7.put("粤西北",86);
        map7.put("粤中东",267);
        map7.put("成都",177);
        map7.put("川北",108);
        map7.put("滇北",68);
        map7.put("滇南",100);
        map7.put("甘宁青藏",82);
        map7.put("广西",163);
        map7.put("贵州",254);
        map7.put("陕西",134);
        map7.put("新疆",48);
        map7.put("渝川南",125);

        map.put("全新H5",map7);

        LinkedHashMap<String, Integer> map8 = new LinkedHashMap<>();
        map8.put("黑蒙",0);
        map8.put("吉林北",159);
        map8.put("吉林南",12);
        map8.put("冀南",49);
        map8.put("津冀北",48);
        map8.put("京蒙",73);
        map8.put("辽北蒙",28);
        map8.put("辽南",24);
        map8.put("山西",42);
        map8.put("豫北",58);
        map8.put("豫南",12);
        map8.put("鲁东",66);
        map8.put("鲁南",22);
        map8.put("鲁西",22);
        map8.put("上海",42);
        map8.put("苏北",26);
        map8.put("苏南",81);
        map8.put("苏中",67);
        map8.put("皖北",48);
        map8.put("皖南",7);
        map8.put("浙北",38);
        map8.put("浙南",43);
        map8.put("浙中",20);
        map8.put("鄂北",25);
        map8.put("鄂南",18);
        map8.put("广州",46);
        map8.put("江西",43);
        map8.put("闽琼",76);
        map8.put("深圳",33);
        map8.put("湘北",66);
        map8.put("湘南",14);
        map8.put("粤西北",28);
        map8.put("粤中东",54);
        map8.put("成都",118);
        map8.put("川北",24);
        map8.put("滇北",45);
        map8.put("滇南",58);
        map8.put("甘宁青藏",16);
        map8.put("广西",22);
        map8.put("贵州",44);
        map8.put("陕西",54);
        map8.put("新疆",10);
        map8.put("渝川南",65);

        map.put("E-QM5",map8);

        LinkedHashMap<String, Integer> map9 = new LinkedHashMap<>();
        map9.put("黑蒙",24);
        map9.put("吉林北",34);
        map9.put("吉林南",4);
        map9.put("冀南",33);
        map9.put("津冀北",26);
        map9.put("京蒙",90);
        map9.put("辽北蒙",20);
        map9.put("辽南",25);
        map9.put("山西",44);
        map9.put("豫北",36);
        map9.put("豫南",16);
        map9.put("鲁东",30);
        map9.put("鲁南",12);
        map9.put("鲁西",25);
        map9.put("上海",16);
        map9.put("苏北",15);
        map9.put("苏南",19);
        map9.put("苏中",18);
        map9.put("皖北",33);
        map9.put("皖南",15);
        map9.put("浙北",30);
        map9.put("浙南",15);
        map9.put("浙中",26);
        map9.put("鄂北",17);
        map9.put("鄂南",14);
        map9.put("广州",19);
        map9.put("江西",28);
        map9.put("闽琼",48);
        map9.put("深圳",26);
        map9.put("湘北",18);
        map9.put("湘南",8);
        map9.put("粤西北",8);
        map9.put("粤中东",35);
        map9.put("成都",18);
        map9.put("川北",17);
        map9.put("滇北",8);
        map9.put("滇南",7);
        map9.put("甘宁青藏",28);
        map9.put("广西",24);
        map9.put("贵州",9);
        map9.put("陕西",38);
        map9.put("新疆",10);
        map9.put("渝川南",14);

        map.put("HQ9",map9);

        LinkedHashMap<String, Integer> map10 = new LinkedHashMap<>();
        map10.put("黑蒙",239);
        map10.put("吉林北",654);
        map10.put("吉林南",132);
        map10.put("冀南",701);
        map10.put("津冀北",491);
        map10.put("京蒙",868);
        map10.put("辽北蒙",311);
        map10.put("辽南",292);
        map10.put("山西",455);
        map10.put("豫北",751);
        map10.put("豫南",582);
        map10.put("鲁东",646);
        map10.put("鲁南",439);
        map10.put("鲁西",573);
        map10.put("上海",427);
        map10.put("苏北",434);
        map10.put("苏南",585);
        map10.put("苏中",485);
        map10.put("皖北",857);
        map10.put("皖南",315);
        map10.put("浙北",631);
        map10.put("浙南",276);
        map10.put("浙中",532);
        map10.put("鄂北",378);
        map10.put("鄂南",432);
        map10.put("广州",386);
        map10.put("江西",641);
        map10.put("闽琼",856);
        map10.put("深圳",411);
        map10.put("湘北",541);
        map10.put("湘南",365);
        map10.put("粤西北",248);
        map10.put("粤中东",722);
        map10.put("成都",1002);
        map10.put("川北",538);
        map10.put("滇北",338);
        map10.put("滇南",623);
        map10.put("甘宁青藏",518);
        map10.put("广西",527);
        map10.put("贵州",1123);
        map10.put("陕西",674);
        map10.put("新疆",170);
        map10.put("渝川南",777);

        map.put("合计",map10);

        return map;
    }

    public static LinkedHashMap<String,LinkedHashMap<String, LinkedHashMap<String, Integer>>> getAreaMap() {
        LinkedHashMap<String,LinkedHashMap<String, LinkedHashMap<String, Integer>>> result = new LinkedHashMap<>();
        LinkedHashMap<String, LinkedHashMap<String, Integer>> map = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> map1 = new LinkedHashMap<>();
        map1.put("H5经典",36);// 3614
        map1.put("HS5",106);// 12729
        map1.put("H7",1);// 418
        map1.put("HS7",8); //845
        map1.put("H9",16); //2271
        map1.put("E-HS9",1); //198
        map1.put("全新H5",47); //6401
        map1.put("E-QM5",0); //2524
        map1.put("HQ9",24); //2000
        map1.put("合计",239); //31000
        map.put("黑蒙",map1);

        LinkedHashMap<String, Integer> map2 = new LinkedHashMap<>();
        map2.put("H5经典",66);// 3614
        map2.put("HS5",283);// 12729
        map2.put("H7",3);// 418
        map2.put("HS7",10); //845
        map2.put("H9",30); //2271
        map2.put("E-HS9",3); //198
        map2.put("全新H5",66); //6401
        map2.put("E-QM5",159); //2524
        map2.put("HQ9",34); //2000
        map2.put("合计",654); //31000
        map.put("吉林北",map2);

        //吉林南						132	16	62	1	5	7	0	25	12	4

        LinkedHashMap<String, Integer> map3 = new LinkedHashMap<>();
        map3.put("H5经典",16);// 3614
        map3.put("HS5",62);// 12729
        map3.put("H7",1);// 418
        map3.put("HS7",5); //845
        map3.put("H9",7); //2271
        map3.put("E-HS9",0); //198
        map3.put("全新H5",25); //6401
        map3.put("E-QM5",12); //2524
        map3.put("HQ9",4); //2000
        map3.put("合计",132); //31000
        map.put("吉林南",map3);

        //冀南						701	103	308	3	8	48	1	148	49	33

        LinkedHashMap<String, Integer> map4 = new LinkedHashMap<>();
        map4.put("H5经典",103);// 3614
        map4.put("HS5",308);// 12729
        map4.put("H7",3);// 418
        map4.put("HS7",8); //845
        map4.put("H9",48); //2271
        map4.put("E-HS9",1); //198
        map4.put("全新H5",148); //6401
        map4.put("E-QM5",49); //2524
        map4.put("HQ9",33); //2000
        map4.put("合计",701); //31000
        map.put("冀南",map4);

        //津冀北						491	80	202	3	9	20	1	102	48	26

        LinkedHashMap<String, Integer> map5 = new LinkedHashMap<>();
        map5.put("H5经典",80);// 3614
        map5.put("HS5",202);// 12729
        map5.put("H7",3);// 418
        map5.put("HS7",9); //845
        map5.put("H9",20); //2271
        map5.put("E-HS9",1); //198
        map5.put("全新H5",102); //6401
        map5.put("E-QM5",48); //2524
        map5.put("HQ9",26); //2000
        map5.put("合计",491); //31000
        map.put("津冀北",map5);

        //京蒙						868	23	411	14	21	70	5	161	73	90

        LinkedHashMap<String, Integer> map6 = new LinkedHashMap<>();
        map6.put("H5经典",23);// 3614
        map6.put("HS5",411);// 12729
        map6.put("H7",14);// 418
        map6.put("HS7",21); //845
        map6.put("H9",70); //2271
        map6.put("E-HS9",5); //198
        map6.put("全新H5",161); //6401
        map6.put("E-QM5",73); //2524
        map6.put("HQ9",90); //2000
        map6.put("合计",868); //31000
        map.put("京蒙",map6);

        //辽北蒙						311	34	143	0	5	20	0	61	28	20

        LinkedHashMap<String, Integer> map7 = new LinkedHashMap<>();
        map7.put("H5经典",34);// 3614
        map7.put("HS5",143);// 12729
        map7.put("H7",0);// 418
        map7.put("HS7",5); //845
        map7.put("H9",20); //2271
        map7.put("E-HS9",0); //198
        map7.put("全新H5",61); //6401
        map7.put("E-QM5",28); //2524
        map7.put("HQ9",20); //2000
        map7.put("合计",311); //31000
        map.put("辽北蒙",map7);

        //辽南						292	23	146	1	3	15	3	52	24	25

        LinkedHashMap<String, Integer> map8 = new LinkedHashMap<>();
        map8.put("H5经典",23);// 3614
        map8.put("HS5",146);// 12729
        map8.put("H7",1);// 418
        map8.put("HS7",3); //845
        map8.put("H9",15); //2271
        map8.put("E-HS9",3); //198
        map8.put("全新H5",52); //6401
        map8.put("E-QM5",24); //2524
        map8.put("HQ9",25); //2000
        map8.put("合计",292); //31000
        map.put("辽南",map8);

        //山西						455	67	157	11	7	22	2	103	42	44

        LinkedHashMap<String, Integer> map9 = new LinkedHashMap<>();
        map9.put("H5经典",67);// 3614
        map9.put("HS5",157);// 12729
        map9.put("H7",11);// 418
        map9.put("HS7",7); //845
        map9.put("H9",22); //2271
        map9.put("E-HS9",2); //198
        map9.put("全新H5",103); //6401
        map9.put("E-QM5",42); //2524
        map9.put("HQ9",44); //2000
        map9.put("合计",455); //31000
        map.put("山西",map9);

        //豫北						751	120	363	3	6	38	1	126	58	36

        LinkedHashMap<String, Integer> map10 = new LinkedHashMap<>();
        map10.put("H5经典",120);// 3614
        map10.put("HS5",363);// 12729
        map10.put("H7",3);// 418
        map10.put("HS7",6); //845
        map10.put("H9",38); //2271
        map10.put("E-HS9",1); //198
        map10.put("全新H5",126); //6401
        map10.put("E-QM5",58); //2524
        map10.put("HQ9",36); //2000
        map10.put("合计",751); //31000
        map.put("豫北",map10);

        //豫南						582	83	344	0	6	32	0	89	12	16

        LinkedHashMap<String, Integer> map11 = new LinkedHashMap<>();
        map11.put("H5经典",83);// 3614
        map11.put("HS5",344);// 12729
        map11.put("H7",0);// 418
        map11.put("HS7",6); //845
        map11.put("H9",32); //2271
        map11.put("E-HS9",0); //198
        map11.put("全新H5",89); //6401
        map11.put("E-QM5",12); //2524
        map11.put("HQ9",16); //2000
        map11.put("合计",582); //31000
        map.put("豫南",map11);


        result.put("北区",map);

        LinkedHashMap<String, LinkedHashMap<String, Integer>> map_1 = new LinkedHashMap<>();

        //东区	鲁东						646	71	267	2	8	39	2	161	66	30

        LinkedHashMap<String, Integer> map12 = new LinkedHashMap<>();
        map12.put("H5经典",71);// 3614
        map12.put("HS5",267);// 12729
        map12.put("H7",2);// 418
        map12.put("HS7",8); //845
        map12.put("H9",39); //2271
        map12.put("E-HS9",2); //198
        map12.put("全新H5",161); //6401
        map12.put("E-QM5",66); //2524
        map12.put("HQ9",30); //2000
        map12.put("合计",646); //31000
        map_1.put("鲁东",map12);

        //东区	鲁南						439	80	152	0	8	28	2	135	22	12

        LinkedHashMap<String, Integer> map13 = new LinkedHashMap<>();
        map13.put("H5经典",80);// 3614
        map13.put("HS5",152);// 12729
        map13.put("H7",0);// 418
        map13.put("HS7",8); //845
        map13.put("H9",28); //2271
        map13.put("E-HS9",2); //198
        map13.put("全新H5",135); //6401
        map13.put("E-QM5",22); //2524
        map13.put("HQ9",12); //2000
        map13.put("合计",439); //31000
        map_1.put("鲁南",map13);

        //东区	鲁西						573	95	221	4	9	41	2	154	22	25

        LinkedHashMap<String, Integer> map14 = new LinkedHashMap<>();
        map14.put("H5经典",95);// 3614
        map14.put("HS5",221);// 12729
        map14.put("H7",4);// 418
        map14.put("HS7",9); //845
        map14.put("H9",41); //2271
        map14.put("E-HS9",2); //198
        map14.put("全新H5",154); //6401
        map14.put("E-QM5",22); //2524
        map14.put("HQ9",25); //2000
        map14.put("合计",573); //31000
        map_1.put("鲁西",map14);

        //东区	上海						427	12	200	4	8	15	8	122	42	16

        LinkedHashMap<String, Integer> map15 = new LinkedHashMap<>();
        map15.put("H5经典",12);// 3614
        map15.put("HS5",200);// 12729
        map15.put("H7",4);// 418
        map15.put("HS7",8); //845
        map15.put("H9",15); //2271
        map15.put("E-HS9",8); //198
        map15.put("全新H5",122); //6401
        map15.put("E-QM5",42); //2524
        map15.put("HQ9",16); //2000
        map15.put("合计",427); //31000
        map_1.put("上海",map15);

        //东区	苏北						434	51	165	8	9	23	4	133	26	15

        LinkedHashMap<String, Integer> map16 = new LinkedHashMap<>();
        map16.put("H5经典",51);// 3614
        map16.put("HS5",165);// 12729
        map16.put("H7",8);// 418
        map16.put("HS7",9); //845
        map16.put("H9",23); //2271
        map16.put("E-HS9",4); //198
        map16.put("全新H5",133); //6401
        map16.put("E-QM5",26); //2524
        map16.put("HQ9",15); //2000
        map16.put("合计",434); //31000
        map_1.put("苏北",map16);

        //东区	苏南						585	70	203	7	13	27	5	160	81	19

        LinkedHashMap<String, Integer> map17 = new LinkedHashMap<>();
        map17.put("H5经典",70);// 3614
        map17.put("HS5",203);// 12729
        map17.put("H7",7);// 418
        map17.put("HS7",13); //845
        map17.put("H9",27); //2271
        map17.put("E-HS9",5); //198
        map17.put("全新H5",160); //6401
        map17.put("E-QM5",81); //2524
        map17.put("HQ9",19); //2000
        map17.put("合计",585); //31000
        map_1.put("苏南",map17);

        //东区	苏中						485	27	206	7	5	32	3	120	67	18

        LinkedHashMap<String, Integer> map18 = new LinkedHashMap<>();
        map18.put("H5经典",27);// 3614
        map18.put("HS5",206);// 12729
        map18.put("H7",7);// 418
        map18.put("HS7",5); //845
        map18.put("H9",32); //2271
        map18.put("E-HS9",3); //198
        map18.put("全新H5",120); //6401
        map18.put("E-QM5",67); //2524
        map18.put("HQ9",18); //2000
        map18.put("合计",485); //31000
        map_1.put("苏中",map18);

        //东区	皖北						857	144	314	6	9	53	2	248	48	33

        LinkedHashMap<String, Integer> map19 = new LinkedHashMap<>();
        map19.put("H5经典",144);// 3614
        map19.put("HS5",314);// 12729
        map19.put("H7",6);// 418
        map19.put("HS7",9); //845
        map19.put("H9",53); //2271
        map19.put("E-HS9",2); //198
        map19.put("全新H5",248); //6401
        map19.put("E-QM5",48); //2524
        map19.put("HQ9",33); //2000
        map19.put("合计",857); //31000
        map_1.put("皖北",map19);

        //东区	皖南						315	50	141	3	5	15	1	78	7	15

        LinkedHashMap<String, Integer> map20 = new LinkedHashMap<>();
        map20.put("H5经典",50);// 3614
        map20.put("HS5",141);// 12729
        map20.put("H7",3);// 418
        map20.put("HS7",5); //845
        map20.put("H9",15); //2271
        map20.put("E-HS9",1); //198
        map20.put("全新H5",78); //6401
        map20.put("E-QM5",7); //2524
        map20.put("HQ9",15); //2000
        map20.put("合计",315); //31000
        map_1.put("皖南",map20);

        //东区	浙北						631	80	240	16	7	28	4	188	38	30

        LinkedHashMap<String, Integer> map21 = new LinkedHashMap<>();
        map21.put("H5经典",80);// 3614
        map21.put("HS5",240);// 12729
        map21.put("H7",16);// 418
        map21.put("HS7",7); //845
        map21.put("H9",28); //2271
        map21.put("E-HS9",4); //198
        map21.put("全新H5",188); //6401
        map21.put("E-QM5",38); //2524
        map21.put("HQ9",30); //2000
        map21.put("合计",631); //31000
        map_1.put("浙北",map21);

        //东区	浙南						276	38	84	2	4	7	2	81	43	15

        LinkedHashMap<String, Integer> map22 = new LinkedHashMap<>();
        map22.put("H5经典",38);// 3614
        map22.put("HS5",84);// 12729
        map22.put("H7",2);// 418
        map22.put("HS7",4); //845
        map22.put("H9",7); //2271
        map22.put("E-HS9",2); //198
        map22.put("全新H5",81); //6401
        map22.put("E-QM5",43); //2524
        map22.put("HQ9",15); //2000
        map22.put("合计",276); //31000
        map_1.put("浙南",map22);

        //东区	浙中						532	94	203	0	7	18	3	161	20	26

        LinkedHashMap<String, Integer> map23 = new LinkedHashMap<>();
        map23.put("H5经典",94);// 3614
        map23.put("HS5",203);// 12729
        map23.put("H7",0);// 418
        map23.put("HS7",7); //845
        map23.put("H9",18); //2271
        map23.put("E-HS9",3); //198
        map23.put("全新H5",161); //6401
        map23.put("E-QM5",20); //2524
        map23.put("HQ9",26); //2000
        map23.put("合计",532); //31000
        map_1.put("浙中",map23);

        result.put("东区",map_1);

        LinkedHashMap<String, LinkedHashMap<String, Integer>> map_2 = new LinkedHashMap<>();
        //南区	鄂北						378	26	179	6	6	19	1	99	25	17

        LinkedHashMap<String, Integer> map24 = new LinkedHashMap<>();
        map24.put("H5经典",26);// 3614
        map24.put("HS5",179);// 12729
        map24.put("H7",6);// 418
        map24.put("HS7",6); //845
        map24.put("H9",19); //2271
        map24.put("E-HS9",1); //198
        map24.put("全新H5",99); //6401
        map24.put("E-QM5",25); //2524
        map24.put("HQ9",17); //2000
        map24.put("合计",378); //31000
        map_2.put("鄂北",map24);

        //南区	鄂南						432	28	181	4	7	28	2	150	18	14

        LinkedHashMap<String, Integer> map25 = new LinkedHashMap<>();
        map25.put("H5经典",28);// 3614
        map25.put("HS5",181);// 12729
        map25.put("H7",4);// 418
        map25.put("HS7",7); //845
        map25.put("H9",28); //2271
        map25.put("E-HS9",2); //198
        map25.put("全新H5",150); //6401
        map25.put("E-QM5",18); //2524
        map25.put("HQ9",14); //2000
        map25.put("合计",432); //31000
        map_2.put("鄂南",map25);

        //南区	广州						386	32	136	5	4	23	5	116	46	19

        LinkedHashMap<String, Integer> map26 = new LinkedHashMap<>();
        map26.put("H5经典",32);// 3614
        map26.put("HS5",136);// 12729
        map26.put("H7",5);// 418
        map26.put("HS7",4); //845
        map26.put("H9",23); //2271
        map26.put("E-HS9",5); //198
        map26.put("全新H5",116); //6401
        map26.put("E-QM5",46); //2524
        map26.put("HQ9",19); //2000
        map26.put("合计",386); //31000
        map_2.put("广州",map26);

        //南区	江西						641	48	262	25	11	33	0	191	43	28

        LinkedHashMap<String, Integer> map27 = new LinkedHashMap<>();
        map27.put("H5经典",48);// 3614
        map27.put("HS5",262);// 12729
        map27.put("H7",25);// 418
        map27.put("HS7",11); //845
        map27.put("H9",33); //2271
        map27.put("E-HS9",0); //198
        map27.put("全新H5",191); //6401
        map27.put("E-QM5",43); //2524
        map27.put("HQ9",28); //2000
        map27.put("合计",641); //31000
        map_2.put("江西",map27);

        //南区	闽琼						856	58	326	12	16	53	7	260	76	48

        LinkedHashMap<String, Integer> map28 = new LinkedHashMap<>();
        map28.put("H5经典",58);// 3614
        map28.put("HS5",326);// 12729
        map28.put("H7",12);// 418
        map28.put("HS7",16); //845
        map28.put("H9",53); //2271
        map28.put("E-HS9",7); //198
        map28.put("全新H5",260); //6401
        map28.put("E-QM5",76); //2524
        map28.put("HQ9",48); //2000
        map28.put("合计",856); //31000
        map_2.put("闽琼",map28);

        //南区	深圳						411	24	164	3	5	26	4	126	33	26

        LinkedHashMap<String, Integer> map29 = new LinkedHashMap<>();
        map29.put("H5经典",24);// 3614
        map29.put("HS5",164);// 12729
        map29.put("H7",3);// 418
        map29.put("HS7",5); //845
        map29.put("H9",26); //2271
        map29.put("E-HS9",4); //198
        map29.put("全新H5",126); //6401
        map29.put("E-QM5",33); //2524
        map29.put("HQ9",26); //2000
        map29.put("合计",411); //31000
        map_2.put("深圳",map29);

        //南区	湘北						541	50	226	8	10	29	3	131	66	18

        LinkedHashMap<String, Integer> map30 = new LinkedHashMap<>();
        map30.put("H5经典",50);// 3614
        map30.put("HS5",226);// 12729
        map30.put("H7",8);// 418
        map30.put("HS7",10); //845
        map30.put("H9",29); //2271
        map30.put("E-HS9",3); //198
        map30.put("全新H5",131); //6401
        map30.put("E-QM5",66); //2524
        map30.put("HQ9",18); //2000
        map30.put("合计",541); //31000
        map_2.put("湘北",map30);

        //南区	湘南						365	36	178	8	5	20	2	94	14	8

        LinkedHashMap<String, Integer> map31 = new LinkedHashMap<>();
        map31.put("H5经典",36);// 3614
        map31.put("HS5",178);// 12729
        map31.put("H7",8);// 418
        map31.put("HS7",5); //845
        map31.put("H9",20); //2271
        map31.put("E-HS9",2); //198
        map31.put("全新H5",94); //6401
        map31.put("E-QM5",14); //2524
        map31.put("HQ9",8); //2000
        map31.put("合计",365); //31000
        map_2.put("湘南",map31);

        //南区	粤西北						248	27	82	4	2	10	1	86	28	8

        LinkedHashMap<String, Integer> map32 = new LinkedHashMap<>();
        map32.put("H5经典",27);// 3614
        map32.put("HS5",82);// 12729
        map32.put("H7",4);// 418
        map32.put("HS7",2); //845
        map32.put("H9",10); //2271
        map32.put("E-HS9",1); //198
        map32.put("全新H5",86); //6401
        map32.put("E-QM5",28); //2524
        map32.put("HQ9",8); //2000
        map32.put("合计",248); //31000
        map_2.put("粤西北",map32);

        //南区	粤中东						722	83	230	5	6	35	7	267	54	35

        LinkedHashMap<String, Integer> map33 = new LinkedHashMap<>();
        map33.put("H5经典",83);// 3614
        map33.put("HS5",230);// 12729
        map33.put("H7",5);// 418
        map33.put("HS7",6); //845
        map33.put("H9",35); //2271
        map33.put("E-HS9",7); //198
        map33.put("全新H5",267); //6401
        map33.put("E-QM5",54); //2524
        map33.put("HQ9",35); //2000
        map33.put("合计",722); //31000
        map_2.put("粤中东",map33);

        result.put("南区",map_2);

        //西区	成都						1002	140	479	10	20	39	1	177	118	18
        LinkedHashMap<String, LinkedHashMap<String, Integer>> map_3 = new LinkedHashMap<>();

        LinkedHashMap<String, Integer> map34 = new LinkedHashMap<>();
        map34.put("H5经典",140);// 3614
        map34.put("HS5",479);// 12729
        map34.put("H7",10);// 418
        map34.put("HS7",20); //845
        map34.put("H9",39); //2271
        map34.put("E-HS9",1); //198
        map34.put("全新H5",177); //6401
        map34.put("E-QM5",118); //2524
        map34.put("HQ9",18); //2000
        map34.put("合计",1002); //31000
        map_3.put("成都",map34);

        //西区	川北						538	62	292	2	15	17	1	108	24	17

        LinkedHashMap<String, Integer> map35 = new LinkedHashMap<>();
        map35.put("H5经典",62);// 3614
        map35.put("HS5",292);// 12729
        map35.put("H7",2);// 418
        map35.put("HS7",15); //845
        map35.put("H9",17); //2271
        map35.put("E-HS9",1); //198
        map35.put("全新H5",108); //6401
        map35.put("E-QM5",24); //2524
        map35.put("HQ9",17); //2000
        map35.put("合计",538); //31000
        map_3.put("川北",map35);

        //西区	滇北						338	43	152	0	10	11	1	68	45	8

        LinkedHashMap<String, Integer> map36 = new LinkedHashMap<>();
        map36.put("H5经典",43);// 3614
        map36.put("HS5",152);// 12729
        map36.put("H7",0);// 418
        map36.put("HS7",10); //845
        map36.put("H9",11); //2271
        map36.put("E-HS9",1); //198
        map36.put("全新H5",68); //6401
        map36.put("E-QM5",45); //2524
        map36.put("HQ9",8); //2000
        map36.put("合计",338); //31000
        map_3.put("滇北",map36);

        //西区	滇南						623	78	342	1	16	19	2	100	58	7

        LinkedHashMap<String, Integer> map37 = new LinkedHashMap<>();
        map37.put("H5经典",78);// 3614
        map37.put("HS5",342);// 12729
        map37.put("H7",1);// 418
        map37.put("HS7",16); //845
        map37.put("H9",19); //2271
        map37.put("E-HS9",2); //198
        map37.put("全新H5",100); //6401
        map37.put("E-QM5",58); //2524
        map37.put("HQ9",7); //2000
        map37.put("合计",623); //31000
        map_3.put("滇南",map37);

        //西区	甘宁青藏						518	59	280	13	18	19	3	82	16	28

        LinkedHashMap<String, Integer> map38 = new LinkedHashMap<>();
        map38.put("H5经典",59);// 3614
        map38.put("HS5",280);// 12729
        map38.put("H7",13);// 418
        map38.put("HS7",18); //845
        map38.put("H9",19); //2271
        map38.put("E-HS9",3); //198
        map38.put("全新H5",82); //6401
        map38.put("E-QM5",16); //2524
        map38.put("HQ9",28); //2000
        map38.put("合计",518); //31000
        map_3.put("甘宁青藏",map38);

        //西区	广西						527	62	211	3	13	28	1	163	22	24

        LinkedHashMap<String, Integer> map39 = new LinkedHashMap<>();
        map39.put("H5经典",62);// 3614
        map39.put("HS5",211);// 12729
        map39.put("H7",3);// 418
        map39.put("HS7",13); //845
        map39.put("H9",28); //2271
        map39.put("E-HS9",1); //198
        map39.put("全新H5",163); //6401
        map39.put("E-QM5",22); //2524
        map39.put("HQ9",24); //2000
        map39.put("合计",527); //31000
        map_3.put("广西", map39);

        //西区	贵州						1123	174	542	30	19	50	1	254	44	9

        LinkedHashMap<String, Integer> map40 = new LinkedHashMap<>();
        map40.put("H5经典",174);// 3614
        map40.put("HS5",542);// 12729
        map40.put("H7",30);// 418
        map40.put("HS7",19); //845
        map40.put("H9",50); //2271
        map40.put("E-HS9",1); //198
        map40.put("全新H5",254); //6401
        map40.put("E-QM5",44); //2524
        map40.put("HQ9",9); //2000
        map40.put("合计",1123); //31000
        map_3.put("贵州",map40);

        //西区	陕西						674	57	354	4	10	22	1	134	54	38

        LinkedHashMap<String, Integer> map41 = new LinkedHashMap<>();
        map41.put("H5经典",57);// 3614
        map41.put("HS5",354);// 12729
        map41.put("H7",4);// 418
        map41.put("HS7",10); //845
        map41.put("H9",22); //2271
        map41.put("E-HS9",1); //198
        map41.put("全新H5",134); //6401
        map41.put("E-QM5",54); //2524
        map41.put("HQ9",38); //2000
        map41.put("合计",674); //31000
        map_3.put("陕西",map41);

        //西区	新疆						170	17	67	3	5	9	1	48	10	10

        LinkedHashMap<String, Integer> map42 = new LinkedHashMap<>();
        map42.put("H5经典",17);// 3614
        map42.put("HS5",67);// 12729
        map42.put("H7",3);// 418
        map42.put("HS7",5); //845
        map42.put("H9",9); //2271
        map42.put("E-HS9",1); //198
        map42.put("全新H5",48); //6401
        map42.put("E-QM5",10); //2524
        map42.put("HQ9",10); //2000
        map42.put("合计",170); //31000
        map_3.put("新疆",map42);

        //西区	渝川南						777	116	396	14	16	30	1	125	65	14

        LinkedHashMap<String, Integer> map43 = new LinkedHashMap<>();
        map43.put("H5经典",116);// 3614
        map43.put("HS5",396);// 12729
        map43.put("H7",14);// 418
        map43.put("HS7",16); //845
        map43.put("H9",30); //2271
        map43.put("E-HS9",1); //198
        map43.put("全新H5",125); //6401
        map43.put("E-QM5",65); //2524
        map43.put("HQ9",14); //2000
        map43.put("合计",777); //31000
        map_3.put("渝川南",map43);
        result.put("西区",map_3);

        return result;
    }

    /**
     * 计算外报车系汇总
     * @param list 外报大区-车系数据
     * @return 结果
     */
    private static LinkedHashMap<String, Integer> calculateOuterCarSeriesSum(List<AreaOuterMonthResponse> list) {

        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        list.forEach(data -> data.getCarSeriesDataList()
                .forEach(data1 -> result.put(data1.getCarSeries(),Integer.parseInt(
                        add(intToString(data1.getNumber()),intToString(result.get(data1.getCarSeries())))))));

        return result;
    }
    /**
     * 计算内控车系-小区汇总
     * @param list 内控月发布数据
     * @return 结果
     */
    private static LinkedHashMap<String, LinkedHashMap<String, Integer>> calculateInnerCarSeriesSmallAreaSum(
            List<InnerMonthReleaseVO> list) {
        LinkedHashMap<String, LinkedHashMap<String, Integer>> result = new LinkedHashMap<>();

        LinkedHashMap<String, List<InnerMonthReleaseVO>> carSeriesMap = list.stream().sorted(
                        Comparator.comparing(InnerMonthReleaseVO::getCarSeries)
                                .thenComparing(InnerMonthReleaseVO::getSmallArea))
                .collect(Collectors.groupingBy(InnerMonthReleaseVO::getCarSeries, LinkedHashMap::new, Collectors.toList()));
        // 循环
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        carSeriesMap.forEach((k,v) -> {
            LinkedHashMap<String, Integer> temp = new LinkedHashMap<>();
            for(InnerMonthReleaseVO data : v){

                int num = Integer.parseInt(add(intToString(temp.get(data.getSmallArea())),intToString(data.getAakValue())));
                temp.put(data.getSmallArea(),num);

                int zNum = Integer.parseInt(add(intToString(map.get(data.getSmallArea())),intToString(data.getAakValue())));
                map.put(data.getSmallArea(),zNum);
            }
            result.put(k,temp);
        });

        result.put(Constant.ROW_SUM,map);

        return result;
    }
    /**
     * 计算内控车系-经销商汇总
     * @param list 内控月发布数据
     * @return 结果
     */
    private static LinkedHashMap<String, LinkedHashMap<String, Integer>> calculateInnerCarSeriesDealerSum(
            List<InnerMonthReleaseVO> list) {
        LinkedHashMap<String, LinkedHashMap<String, Integer>> result = new LinkedHashMap<>();

        LinkedHashMap<String, List<InnerMonthReleaseVO>> carSeriesMap = list.stream().sorted(
                        Comparator.comparing(InnerMonthReleaseVO::getCarSeries)
                                .thenComparing(InnerMonthReleaseVO::getDealerCode))
                .collect(Collectors.groupingBy(InnerMonthReleaseVO::getCarSeries, LinkedHashMap::new, Collectors.toList()));
        // 循环
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        carSeriesMap.forEach((k,v) -> {
            LinkedHashMap<String, Integer> temp = new LinkedHashMap<>();
            for(InnerMonthReleaseVO data : v){

                int num = Integer.parseInt(add(intToString(temp.get(data.getDealerCode())),intToString(data.getAakValue())));
                temp.put(data.getDealerCode(),num);

                int zNum = Integer.parseInt(add(intToString(map.get(data.getDealerCode())),intToString(data.getAakValue())));
                map.put(data.getDealerCode(),zNum);
            }
            result.put(k,temp);
        });

        result.put(Constant.ROW_SUM,map);

        return result;
    }
    private static String intToString(Integer num){

        if(Objects.isNull(num)){

            return Constant.ZERO_STR;

        }

        return Integer.toString(num);
    }
}

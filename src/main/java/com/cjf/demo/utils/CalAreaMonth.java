package com.cjf.demo.utils;

import com.cjf.demo.constants.Constant;
import com.cjf.demo.enums.RegionEnum;
import com.cjf.demo.response.AreaOuterMonthResponse;
import com.cjf.demo.response.CarSeriesData;
import com.cjf.demo.vo.CarSeriesConfig;
import com.cjf.demo.vo.InnerMonthReleaseVO;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

/**
 * @author : chenjianfeng
 * @date : 2023/1/14
 */
public class CalAreaMonth {


    // 拆分大区
    @Test
    public void testOuterAreaMonth() throws IOException, InvalidFormatException {
        calOuterAreaMonth();
    }

    // 拆分小区
    @Test
    public void testOuterSmallAreaMonth() throws IOException, InvalidFormatException {
        calOuterSmallAreaMonth();
    }

    // 拆分经销商
    @Test
    public void testOuterDealerMonth() throws IOException, InvalidFormatException {
        List<InnerMonthReleaseVO> list = ReadExcelFileUtil.getInnerReleaseList();
        calOuterDealerMonth(list);
    }

    public List<AreaOuterMonthResponse> calOuterAreaMonth() throws IOException, InvalidFormatException {
        System.out.println("=================================大区拆分==================================");

        LinkedHashMap<String, LinkedHashMap<String, Integer>> innerMonthAreaMap = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("H5经典",812);
        map.put("HS5",2396);
        map.put("H7",59);
        map.put("HS7",92);
        map.put("H9",326);
        map.put("E-HS9",38);
        map.put("全新H5",1741);
        map.put("E-QM5",482);
        map.put("HQ9",254);
        map.put("合计",6200);
        innerMonthAreaMap.put("东区",map);

        LinkedHashMap<String, Integer> map1 = new LinkedHashMap<>();
        map1.put("H5经典",412);
        map1.put("HS5",1964);
        map1.put("H7",80);
        map1.put("HS7",72);
        map1.put("H9",276);
        map1.put("E-HS9",32);
        map1.put("全新H5",1520);
        map1.put("E-QM5",403);
        map1.put("HQ9",221);
        map1.put("合计",4980);
        innerMonthAreaMap.put("南区",map1);

        LinkedHashMap<String, Integer> map2 = new LinkedHashMap<>();
        map2.put("H5经典",808);
        map2.put("HS5",3115);
        map2.put("H7",80);
        map2.put("HS7",142);
        map2.put("H9",244);
        map2.put("E-HS9",13);
        map2.put("全新H5",1259);
        map2.put("E-QM5",456);
        map2.put("HQ9",173);
        map2.put("合计",6290);
        innerMonthAreaMap.put("西区",map2);

        LinkedHashMap<String, Integer> map3 = new LinkedHashMap<>();
        map3.put("H5经典",651);
        map3.put("HS5",2525);
        map3.put("H7",40);
        map3.put("HS7",88);
        map3.put("H9",318);
        map3.put("E-HS9",17);
        map3.put("全新H5",980);
        map3.put("E-QM5",505);
        map3.put("HQ9",352);
        map3.put("合计",5476);
        innerMonthAreaMap.put("北区",map3);

        LinkedHashMap<String, Integer> importMonthMap = new LinkedHashMap<>();
        importMonthMap.put("H5经典",1020);// 3614
        importMonthMap.put("HS5",3532);// 12729
        importMonthMap.put("H7",219);// 418
        importMonthMap.put("HS7",382); //845
        importMonthMap.put("H9",857); //2271
        importMonthMap.put("E-HS9",100); //198
        importMonthMap.put("全新H5",2023); //6401
        importMonthMap.put("E-QM5",1846); //2524
        importMonthMap.put("HQ9",967); //2000
        importMonthMap.put("合计",10946); //31000

        List<String> hotCars = Arrays.asList("HS5","全新H5");
        Integer hotCarTotal = importMonthMap.entrySet().stream().filter(data -> hotCars.contains(data.getKey()))
                .mapToInt(Map.Entry::getValue).sum();

        LinkedHashMap<String, LinkedHashMap<String, Integer>> resultMap = MathUtil.calculateOuterMonth(innerMonthAreaMap,importMonthMap);
        System.out.println("大区计算");
        resultMap.forEach((k,v) -> {
            System.out.println(k+":"+ v);
        });
        List<InnerMonthReleaseVO> innerMonthReleaseList = ReadExcelFileUtil.getInnerReleaseList();
        // 横向调平
        LinkedHashMap<String, LinkedHashMap<String, Integer>> rowResultMap = MathUtil.rowLeveling(resultMap,hotCarTotal,hotCars,importMonthMap, innerMonthReleaseList, RegionEnum.AREA.getCode());
        System.out.println("横向调平");
        rowResultMap.forEach((k,v) -> {
            System.out.println(k+":"+ v);
        });

        // 纵向调平
        System.out.println("纵向调平");
        LinkedHashMap<String, LinkedHashMap<String, Integer>> colResultMap = MathUtil.colLeveling(rowResultMap,importMonthMap, innerMonthReleaseList, RegionEnum.AREA.getCode());
        colResultMap.forEach((k,v) -> {
            System.out.println(k+":"+ v);
        });
        // 最终调平
        List<AreaOuterMonthResponse> result = MathUtil.finalLeveling(resultMap,colResultMap,RegionEnum.AREA.getCode());
        System.out.println("最终结果:");
        StringBuilder temp1Value = null;
        for (AreaOuterMonthResponse data:result){
            StringBuilder tempValue = new StringBuilder(Constant.NULL_CHAR);
            temp1Value = new StringBuilder(Constant.NULL_CHAR);
            for(CarSeriesData carSeriesData : data.getCarSeriesDataList()){
                tempValue.append(carSeriesData.getNumber()).append(Constant.COMMA);
                temp1Value.append(carSeriesData.getCarSeries()).append(Constant.COMMA);
            }
            System.out.println(data.getAreaId()+","+tempValue);
        }
        System.out.println("车系:"+temp1Value);
        return result;
    }

    public List<AreaOuterMonthResponse> calOuterSmallAreaMonth() throws IOException, InvalidFormatException {



        List<AreaOuterMonthResponse> result = new ArrayList<>();
        List<AreaOuterMonthResponse> areaOuterMonthList = calOuterAreaMonth();
        LinkedHashMap<String,String> calculateCarSeriesCoefficient = calculateCarSeriesCoefficient();
        List<InnerMonthReleaseVO> innerMonthReleaseList = ReadExcelFileUtil.getInnerReleaseList();
        System.out.println("=================================小区拆分==================================");

        System.out.println("小区计算----------------------------------------------------------------------------");
        // 1、计算
        LinkedHashMap<String, LinkedHashMap<String,Integer>> calculateResultMap = MathUtil.calculateSmallAreaOrDealerResultMap(
                innerMonthReleaseList,areaOuterMonthList,calculateCarSeriesCoefficient, RegionEnum.SMALL_AREA.getCode());

        // 2、大区-小区-车系 汇总
        LinkedHashMap<String,LinkedHashMap<String, LinkedHashMap<String, Integer>>> regionRelateSumMap =
                MathUtil.convertSmallAreaResultToMap(innerMonthReleaseList,calculateResultMap);

        // list->map
        LinkedHashMap<String,LinkedHashMap<String,Integer>> allAreaMonthMap = MathUtil.convertListToMap(areaOuterMonthList);
        System.out.println("小区调平---------------------------------------------------------------------------");
        // 3、大区遍历
        regionRelateSumMap.forEach((k,v) -> {
            // 获取外报各大区车系合计
            LinkedHashMap<String,Integer> areaMonthMap= allAreaMonthMap.get(k);
            System.out.println("外报"+k+":"+areaMonthMap);
            // 调平
            List<CarSeriesConfig> carSeriesConfigList = Arrays.asList(
                    new CarSeriesConfig(1l,"HS5","HS5",1),
                    new CarSeriesConfig(1l,"全新H5","全新H5",1)
            );
            List<AreaOuterMonthResponse> list = MathUtil.levelingOuterMonth(v,carSeriesConfigList,
                    areaMonthMap, RegionEnum.SMALL_AREA.getCode(),innerMonthReleaseList);
            list.forEach(data -> data.setAreaId(k));

            result.addAll(list);
        });
        System.out.println("===================================================");
        StringBuilder temp1Value = null;
        for (AreaOuterMonthResponse data:result){
            StringBuilder tempValue = new StringBuilder(Constant.NULL_CHAR);
            temp1Value = new StringBuilder(Constant.NULL_CHAR);
            for(CarSeriesData carSeriesData : data.getCarSeriesDataList()){
                tempValue.append(carSeriesData.getNumber()).append(Constant.COMMA);
                temp1Value.append(carSeriesData.getCarSeries()).append(Constant.COMMA);
            }
            System.out.println(data.getAreaId()+","+data.getSmallArea()+ ","+tempValue);
        }
        System.out.println("车系:"+temp1Value);

        return result;
    }


    public LinkedHashMap<String,String> calculateCarSeriesCoefficient(){
        LinkedHashMap<String,String> a = new LinkedHashMap<>();
        LinkedHashMap<String,Integer> innerMap = new LinkedHashMap<>();
        innerMap.put("H5经典",2683);// 3614
        innerMap.put("HS5",10000);// 12729
        innerMap.put("H7",259);// 418
        innerMap.put("HS7",394); //845
        innerMap.put("H9",1164); //2271
        innerMap.put("E-HS9",100); //198
        innerMap.put("全新H5",5500); //6401
        innerMap.put("E-QM5",1846); //2524
        innerMap.put("HQ9",1000); //2000
        innerMap.put("合计",22946); //31000

        LinkedHashMap<String, Integer> importMonthMap = new LinkedHashMap<>();
        importMonthMap.put("H5经典",1020);// 3614
        importMonthMap.put("HS5",3532);// 12729
        importMonthMap.put("H7",219);// 418
        importMonthMap.put("HS7",382); //845
        importMonthMap.put("H9",857); //2271
        importMonthMap.put("E-HS9",100); //198
        importMonthMap.put("全新H5",2023); //6401
        importMonthMap.put("E-QM5",1846); //2524
        importMonthMap.put("HQ9",967); //2000
        importMonthMap.put("合计",10946); //31000

        innerMap.forEach((k,v) -> {
            a.put(k, MathUtil.divide(String.valueOf(importMonthMap.get(k)),String.valueOf(v),3));
        });
//        a.forEach((k,v) -> System.out.println(k+","+v));
        return a;
    }

    private void calOuterDealerMonth(List<InnerMonthReleaseVO> innerMonthReleaseList) throws IOException, InvalidFormatException {

        List<AreaOuterMonthResponse> result = new ArrayList<>();

//        List<InnerMonthReleaseVO> innerMonthReleaseList = (List<InnerMonthReleaseVO>)CacheUtil.get("list");
        LinkedHashMap<String,String> calculateCarSeriesCoefficient = calculateCarSeriesCoefficient();
        List<AreaOuterMonthResponse> smallAreaOuterMonthResponseList = calOuterSmallAreaMonth();
        System.out.println("=================================经销商拆分==================================");
        calculateCarSeriesCoefficient.forEach((k,v) -> {
            System.out.println(k+""+v);
        });
        System.out.println("----------------------------------------------------------------------");

        // 1、计算
        LinkedHashMap<String, LinkedHashMap<String,Integer>> calculateResultMap = MathUtil.calculateSmallAreaOrDealerResultMap(
                innerMonthReleaseList,smallAreaOuterMonthResponseList, calculateCarSeriesCoefficient, RegionEnum.DEALER.getCode());
        // 2、大区-小区-经销商-车系 汇总
        LinkedHashMap<String,LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Integer>>>> regionRelateSumMap =
                MathUtil.convertDealerResultToMap(innerMonthReleaseList,calculateResultMap);

        // list->map
        LinkedHashMap<String,LinkedHashMap<String,Integer>> allSmallAreaMonthMap = MathUtil.convertListToDealerMap(smallAreaOuterMonthResponseList);

        System.out.println("小区调平---------------------------------------------------------------------------");
        // 3、大区遍历
        regionRelateSumMap.forEach((k,v) -> {
            // 小区遍历
            v.forEach((k1,v1) -> {
                // 获取外报各小区车系合计
                LinkedHashMap<String,Integer> smallAreaMonthMap= allSmallAreaMonthMap.get(k1);
                System.out.println("外报"+k+":"+k1+":"+smallAreaMonthMap);
                // 调平
                List<CarSeriesConfig> carSeriesConfigList = Arrays.asList(
                        new CarSeriesConfig(1l,"HS5","HS5",1),
                        new CarSeriesConfig(1l,"全新H5","全新H5",1)
                );
                List<AreaOuterMonthResponse> list = MathUtil.levelingOuterMonth(v1,carSeriesConfigList,
                        smallAreaMonthMap, RegionEnum.DEALER.getCode(), innerMonthReleaseList);
                list.forEach(data -> {
                    data.setAreaId(k);
                    data.setSmallArea(k1);
                });

                result.addAll(list);
            });
        });
        System.out.println("---------------------------------------------------------------------------");
        StringBuilder temp1Value = null;
        for (AreaOuterMonthResponse data:result){
            StringBuilder tempValue = new StringBuilder(Constant.NULL_CHAR);
            temp1Value = new StringBuilder(Constant.NULL_CHAR);
            for(CarSeriesData carSeriesData : data.getCarSeriesDataList()){
                tempValue.append(carSeriesData.getNumber()).append(Constant.COMMA);
                temp1Value.append(carSeriesData.getCarSeries()).append(Constant.COMMA);
            }
            System.out.println(data.getAreaId()+","+data.getSmallArea()+ ","+data.getDealer()+","+tempValue);
        }
        System.out.println("车系:"+temp1Value);
    }
}

package com.cjf.demo.juint;

import com.cjf.demo.po.UnitVO;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author : chenjianfeng
 * @date : 2023/5/11
 */
public class TestStreamTwo {

    protected List<UnitVO> arrayList = Arrays.asList(

            new UnitVO("BU2304181508451249","线索分配","MIDDLE",
                    Collections.singletonList(new UnitVO("BU2304181506328227", "线索收集","",
                            null))),
            new UnitVO("BU2304181510225705","线索跟进","MIDDLE",
                    Collections.singletonList(new UnitVO("BU2304181508451249", "线索分配","",
                            null))),
            new UnitVO("BU2304181506328227","线索收集","START",
                    Collections.singletonList(new UnitVO("", "","", null))),

            new UnitVO("BU2304181511512627","谈判投标","END",
                    Arrays.asList(new UnitVO("BU2304181510225705", "线索跟进","", null)))



    );

    @Test
    public void test_1(){
        LinkedList<UnitVO> list = new LinkedList<>();

        list.addFirst(arrayList.stream().filter(data -> Objects.equals(data.getPosition(),"START")).findFirst().orElse(null));
        AtomicInteger index = new AtomicInteger();
        while (list.size() != arrayList.size()-1){
            List<UnitVO> middleList = arrayList.stream().filter(data -> Objects.equals(data.getPosition(),"MIDDLE"))
                    .collect(Collectors.toList());
            for(UnitVO data : middleList){
                for(UnitVO preData : data.getPreUnitCodeList()){
                    if(Objects.equals(preData.getUnitCode(),list.get(index.get()).getUnitCode())){
                        list.add(data);
                    }
                }
            }
            index.getAndIncrement();
        }
        list.addLast(arrayList.stream().filter(data -> Objects.equals(data.getPosition(),"END")).findFirst().orElse(null));


        list.forEach(data -> System.out.println(data));
    }
}

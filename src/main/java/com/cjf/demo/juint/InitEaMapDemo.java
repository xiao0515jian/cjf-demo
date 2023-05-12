package com.cjf.demo.juint;

import org.junit.jupiter.api.Test;

/**
 * @author : chenjianfeng
 * @date : 2023/3/27
 */
public class InitEaMapDemo {

    @Test
    public void test1(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 1; i < 5 ; i++){
            stringBuilder.insert(0, "-L"+i+"名称");
        }


        System.out.println(stringBuilder.deleteCharAt(0));
    }
}

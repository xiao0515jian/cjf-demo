package com.cjf.demo.juint;

import org.mockito.internal.util.StringUtil;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : chenjianfeng
 * @date : 2022/8/22
 */
public class BigDecimalDemo {
    public static void main(String[] args) {
//        System.out.println(0.09 + 0.01);
//        System.out.println(1.0 - 0.32);
//        System.out.println(1.015 * 100);
//        System.out.println(1.301 / 100);
//
//        // public BigDecimal add(BigDecimal augend):加
//        BigDecimal bd1 = new BigDecimal("0.09");
//        BigDecimal bd2 = new BigDecimal("0.01");
//        System.out.println("add:" + bd1.add(bd2));
//        System.out.println("----------------------");
//        // public BigDecimal subtract(BigDecimal subtrahend):减
//        BigDecimal bd3 = new BigDecimal("1.0");
//        BigDecimal bd4 = new BigDecimal("0.32");
//        System.out.println("subtract:" + bd3.subtract(bd4));
//        System.out.println("----------------------");
//        // public BigDecimal multiply(BigDecimal multiplicand):乘
//        BigDecimal bd5 = new BigDecimal("1.015");
//        BigDecimal bd6 = new BigDecimal("100");
//        System.out.println("multiply:" + bd5.multiply(bd6));
//        System.out.println("----------------------");
//        // public BigDecimal divide(BigDecimal divisor):除
//        BigDecimal bd7 = new BigDecimal("1.301");
//        BigDecimal bd8 = new BigDecimal("100");
//        System.out.println("divide:" + bd7.divide(bd8));
//        System.out.println("divide:" + bd7.divide(bd8,3,BigDecimal.ROUND_HALF_UP));
//        System.out.println("divide:" + bd7.divide(bd8,9,BigDecimal.ROUND_HALF_UP));

//        System.out.println(floatToPercent(divideS(3,7),0));;
        System.out.println(longToString(12L));

    }

    public static long StringToLong(String number){
        if(StringUtils.isEmpty(number)){
            return 0l;
        }
        BigDecimal a = new BigDecimal(number);
        BigDecimal b = new BigDecimal(10000);
        return a.multiply(b).longValue();
    }

    public static String longToString(Long number) {
        if(number == null){
            return "";
        }
        BigDecimal a = new BigDecimal(number);
        BigDecimal b = new BigDecimal(10000);
        return a.divide(b).toString();
    }

    /**
     * 除法
     * @param divisor 除数
     * @param dividend 被除数
     * @return
     */
    public static String divideS(Integer divisor, Integer dividend){
        if(dividend == null || dividend.equals(0)){
            return "0";
        }
        BigDecimal a = new BigDecimal(divisor.toString());
        BigDecimal b = new BigDecimal(dividend.toString());
        return a.divide(b,2,BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String floatToPercent(String data, int n){
        if(null == data){
            return "-";
        }
        if("0".equals(data) || "0.0".equals(data)){
            return "0%";
        }
        return new BigDecimal(data).multiply( new BigDecimal(100)).setScale(n, BigDecimal.ROUND_HALF_UP).toString() + "%";
    }
}

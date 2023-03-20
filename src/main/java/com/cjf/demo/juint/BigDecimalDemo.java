package com.cjf.demo.juint;

import cn.hutool.core.lang.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Objects;

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
        // public BigDecimal add(BigDecimal augend):加
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
//        System.out.println(longToString(12L));
        //System.out.println(add("1","2"));
//        BigDecimal b1 = new BigDecimal("1.000");
//        BigDecimal b2 = new BigDecimal("1");
//        BigDecimal b3 = new BigDecimal("2");
//        System.out.println(b2.compareTo(b3));
//        System.out.println(b1.compareTo(b2));
        System.out.println(test1());
    }

    public static boolean test1(){
        String str1 = "test{}[```333sss]";
        String str2 = "test{}[```333sss]";
        // 生成一个MD5加密计算摘要
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str1.getBytes());
            String s = new BigInteger(1, md.digest()).toString(16);
            System.out.println(s);
            // 计算md5函数
            MessageDigest md2 = MessageDigest.getInstance("MD5");
            md2.update(str2.getBytes());
            String s1 = new BigInteger(1, md2.digest()).toString(16);
            System.out.println(s1);
            System.out.println(Objects.equals(s1,s));
        } catch (Exception e) {

        }
        return false;
    }


    public static String add(String n1, String n2) {
        Assert.notBlank(n1);
        Assert.notBlank(n2);

        BigDecimal b1 = new BigDecimal(n1);
        BigDecimal b2 = new BigDecimal(n2);
        return b1.add(b2).toString();
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

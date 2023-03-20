package com.cjf.demo.utils;

import lombok.var;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Optional;

/**
 * @author : chenjianfeng
 * @date : 2023/3/1
 */
public class StringUtil {
    /**
     * 此方法只做千位符格式化处理，不做小数位舍入的处理
     * 小数位的舍入需要格式化之前处理好
     * @param value 需要格式化的值
     * @param decimalDigit 小数位数
     * @param decimalIsFillZero 小数位是否补零 默认false
     * @return
     */
    public static String valueToThousandFormat(Object value, Integer decimalDigit, Boolean decimalIsFillZero) {

        if (null == value || "".equals(value)) {
            return "";
        }

        var decimalValue = BigDecimal.ZERO;
        // Double类型会出现科学计数，要处理一下，Optional也要处理，不能直接转换
        // 若有其他类型需要处理，可以自己添加
        if (value instanceof Optional && ((Optional<?>) value).get() instanceof Double) {

            var tempValue = ((Optional<?>) value).get();
            if (tempValue != null) {
                decimalValue = new BigDecimal((Double) tempValue);
            }
        } else if (value instanceof Double) {
            decimalValue = new BigDecimal((Double) value);
        } else {
            decimalValue = new BigDecimal(value.toString());
        }

        StringBuilder pattern = new StringBuilder("#,##0");
        // 处理小数位和补零
        if (decimalDigit != null && decimalDigit > 0) {

            if (decimalIsFillZero == null) {
                decimalIsFillZero = Boolean.FALSE;
            }

            pattern.append(".");
            String padSymbol = decimalIsFillZero ? "0" : "#";

            for (var i = 0; i < decimalDigit; i++) {
                pattern.append(padSymbol);
            }
        }

        DecimalFormat df = new DecimalFormat(pattern.toString());
        return df.format(decimalValue);
    }
}

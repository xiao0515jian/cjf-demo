package com.cjf.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : chenjianfeng
 * @date : 2022/12/30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthInfoVO {

    private String monthName;

    private String startData;

    private String endData;
}

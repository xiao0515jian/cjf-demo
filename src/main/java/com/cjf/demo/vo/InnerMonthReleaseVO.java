package com.cjf.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : chenjianfeng
 * @date : 2023/1/9
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InnerMonthReleaseVO {

    private String regionalArea;

    private String smallArea;

    private String dealerCode;

    private String carSeries;

    private Integer aakValue;
}

package com.cjf.demo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 大区外报月数据
 * @author : chenjianfeng
 * @date : 2023/1/9
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AreaOuterMonthResponse {

    private String areaId;

    private String smallArea;

    private String dealer;

    private Integer sum;

    private List<CarSeriesData> carSeriesDataList;

}

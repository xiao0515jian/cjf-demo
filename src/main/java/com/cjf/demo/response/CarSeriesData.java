package com.cjf.demo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 车系数据
 * @author : chenjianfeng
 * @date : 2023/1/9
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarSeriesData {

    private String carSeries;

    private Integer number;

    @Override
    public String toString() {
        return carSeries + ':' + number;
    }
}

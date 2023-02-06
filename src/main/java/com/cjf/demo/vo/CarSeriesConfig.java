package com.cjf.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarSeriesConfig {

    private Long id;

    private String carSeries;

    private String carSeriesName;

    private Integer type;
}
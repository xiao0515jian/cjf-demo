package com.cjf.demo.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessUpdateVo {


    private String code;

    private String processVersion;

    private Integer recordStatus;
}

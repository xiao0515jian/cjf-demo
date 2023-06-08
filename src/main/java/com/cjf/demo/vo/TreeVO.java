package com.cjf.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TreeVO {

    private String id;

    private String level;

    private String name;

    private Boolean flag;
}

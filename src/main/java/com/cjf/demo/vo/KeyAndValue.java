package com.cjf.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : chenjianfeng
 * @date : 2023/1/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyAndValue {

    private String key;

    private Integer value;

}

package com.cjf.demo.po;


import lombok.Data;

import java.util.List;

/**
 * @author : chenjianfeng
 * @date : 2022/8/9
 */
@Data
public class UnitVO {

    private String unitCode;

    private String unitName;

    private String position;

    private List<UnitVO> preUnitCodeList;

    public UnitVO(String unitCode, String unitName, String position, List<UnitVO> preUnitCodeList) {
        this.unitCode = unitCode;
        this.unitName = unitName;
        this.position = position;
        this.preUnitCodeList = preUnitCodeList;
    }

    public UnitVO() {
    }
}

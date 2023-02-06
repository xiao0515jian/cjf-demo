package com.cjf.demo.enums;


/**
 * 区域枚举
 */
public enum RegionEnum {

    AREA("area", "大区"),

    SMALL_AREA("smallArea", "小区"),

    DEALER("dealer", "经销商"),
    ;

    private String code;
    private String name;

    RegionEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public String getCode() {
        return this.code;
    }

    public String getName() {
        return name;
    }

}

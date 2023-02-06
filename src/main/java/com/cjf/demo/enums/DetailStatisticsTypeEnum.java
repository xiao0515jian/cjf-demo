package com.cjf.demo.enums;

/**
 * @author : chenjianfeng
 * @date : 2022/8/23
 */
public enum DetailStatisticsTypeEnum {

    TASK_VALID("1", "有效任务占比"),

    TASK_UN_VALID("2", "无效任务占比"),

    TASK_DELAYED("3", "拖期任务占比"),

    TASK_AAK_COMPLETED("4", "AAK经销商占比");


    private String code;
    private String name;

    DetailStatisticsTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

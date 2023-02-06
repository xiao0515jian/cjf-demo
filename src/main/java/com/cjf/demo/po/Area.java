package com.cjf.demo.po;

import lombok.Data;

/**
 * @author : chenjianfeng
 * @date : 2022/9/2
 */
@Data
public class Area {

    private String area;

    private String province;

    private String city;

    public Area(String area, String province, String city) {
        this.area = area;
        this.province = province;
        this.city = city;
    }

}

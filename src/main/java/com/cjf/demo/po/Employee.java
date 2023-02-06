package com.cjf.demo.po;


import lombok.Data;

/**
 * @author : chenjianfeng
 * @date : 2022/8/9
 */
@Data
public class Employee {
    private Integer id;
    private String name;
    private Integer age;
    private Double weight;

    private Integer ranking;

    public Employee(Integer id, String name, Integer age, Double weight) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.weight = weight;
    }

    public Employee() {
    }
}

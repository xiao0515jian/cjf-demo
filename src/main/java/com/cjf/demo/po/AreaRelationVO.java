package com.cjf.demo.po;

import lombok.Data;

import java.util.List;

@Data
public class AreaRelationVO {


    private String name;


    private List<AreaRelationVO> childs;
}

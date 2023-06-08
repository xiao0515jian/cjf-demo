package com.cjf.demo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessProcessResponse {


    private String id;


    private String code;


    private String name;

    private String level;

    private String busMainlineId;


    private String deptId;


    private String premisesId;


    private String busScene;


    private String description;


    private Integer recordStatus;


    private String parentId;


    private String createdBy;


    private String created;


    private String processVersion;


    private String fileType;


    private List<BusinessProcessResponse> children;


    private List<ProcessNodeResponse> nodeList;

    private Boolean disabled;
}

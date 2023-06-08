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
public class ProcessNodeResponse {

    private String nodeId;

    private String nodeName;

    private Integer nodeSeq;

    private String nodeType;

    private String processStage;

    private String roleName;

    private String roleId;

    private String roleCode;


    private String nodePosition;


    private String desc;

    private List<PreNodeResponse> preNodeList;
}

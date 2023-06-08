package com.cjf.demo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreNodeResponse {

    private String preNodeId;

    private String preNodeName;

    private String nodeCondition;
}

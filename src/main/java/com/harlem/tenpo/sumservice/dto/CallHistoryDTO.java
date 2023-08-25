package com.harlem.tenpo.sumservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CallHistoryDTO {

    private String method;
    private String endpoint;
    private String status;
    private String response;
}

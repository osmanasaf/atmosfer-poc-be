package org.atmosfer.seed.businessservice.dto;

import lombok.Data;
import org.atmosfer.seed.businessservice.type.ApprovalStatus;
import org.atmosfer.seed.businessservice.type.PositionStatus;

@Data
public class PositionDto {
    private String name;
    private String detail;
    private PositionStatus status;
    private String city;
}

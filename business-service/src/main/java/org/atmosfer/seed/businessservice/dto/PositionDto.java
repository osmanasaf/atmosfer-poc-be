package org.atmosfer.seed.businessservice.dto;

import lombok.Data;
import org.atmosfer.seed.businessservice.type.ApprovalStatus;
import org.atmosfer.seed.businessservice.type.PositionStatus;
import org.atmosfer.seed.businessservice.type.WorkType;

@Data
public class PositionDto {
    private String name;
    private String id;
    private String detail;
    private PositionStatus status;
    private String city;
    private WorkType workType;
    private Integer applicantCount;
}

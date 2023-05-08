package org.atmosfer.seed.businessservice.dto;

import lombok.Data;
import org.atmosfer.seed.businessservice.type.ApprovalStatus;

@Data
public class ApproveDto {
    private ApprovalStatus status;
    private String message;
}

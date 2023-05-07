package org.atmosfer.seed.businessservice.dto;

import lombok.Data;
import org.atmosfer.seed.businessservice.type.ApprovalStatus;
import org.atmosfer.seed.businessservice.type.PositionStatus;

@Data
public class PositionDto {

        private String name;
        private String detail;
        private PositionStatus status = PositionStatus.AVAILABLE;
        private ApprovalStatus hrApprovalStatus = ApprovalStatus.WAITING;
        private ApprovalStatus techinalApprovalStatus = ApprovalStatus.WAITING;
        private ApprovalStatus financeApprovalStatus = ApprovalStatus.WAITING;

}

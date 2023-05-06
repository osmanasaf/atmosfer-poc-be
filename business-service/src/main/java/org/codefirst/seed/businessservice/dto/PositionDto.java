package org.codefirst.seed.businessservice.dto;

import lombok.Data;
import org.codefirst.seed.businessservice.type.ApprovalStatus;
import org.codefirst.seed.businessservice.type.PositionStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
public class PositionDto {

        private String name;
        private String detail;
        private PositionStatus status = PositionStatus.AVAILABLE;
        private ApprovalStatus hrApprovalStatus = ApprovalStatus.WAITING;
        private ApprovalStatus techinalApprovalStatus = ApprovalStatus.WAITING;
        private ApprovalStatus financeApprovalStatus = ApprovalStatus.WAITING;

}

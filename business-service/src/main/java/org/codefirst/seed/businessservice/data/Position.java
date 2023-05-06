package org.codefirst.seed.businessservice.data;

import lombok.Data;
import org.codefirst.seed.businessservice.controller.ApplyPositionDto;
import org.codefirst.seed.businessservice.type.ApprovalStatus;
import org.codefirst.seed.businessservice.type.PositionStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@RedisHash("Position")
@Data
public class Position implements Serializable {
    @Id private String id;
    private String name;
    private String detail;
    private PositionStatus status;
    private ApprovalStatus hrApprovalStatus;
    private ApprovalStatus techinalApprovalStatus;
    private ApprovalStatus financeApprovalStatus;
    //elimizde user entity olmadığı için user id bıraktım feign client ile user servisten user bilgileri alınabilir
    private List<ApplyPositionDto> applicant;

    public void addApplicant(ApplyPositionDto applyPositionDto) {
        this.applicant.add(applyPositionDto);
    }

    //başvuran kişinin bilgileri(cv, tecrübe vs) nasıl alınacak?

}

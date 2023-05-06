package org.codefirst.seed.businessservice.service.ApproveService;

import lombok.AllArgsConstructor;
import org.codefirst.seed.businessservice.data.Position;
import org.codefirst.seed.businessservice.type.ApprovalStatus;
import org.codefirst.seed.businessservice.util.CheckUserRoleUtil;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TechnicalApproval extends Approval {


    private final CheckUserRoleUtil checkUserRoleUtil;

    public boolean isUserAuthorized() {
        return checkUserRoleUtil.isTechnicalUser();
    }

    public void approve(Position position){
        isUserAuthorized();
        position.setTechinalApprovalStatus(ApprovalStatus.APPROVED);
    }

    public void reject(Position position) {
        isUserAuthorized();
        position.setTechinalApprovalStatus(ApprovalStatus.REJECTED);
    }
}
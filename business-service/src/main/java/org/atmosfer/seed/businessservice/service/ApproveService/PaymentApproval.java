package org.atmosfer.seed.businessservice.service.ApproveService;

import lombok.AllArgsConstructor;
import org.atmosfer.seed.businessservice.data.Position;
import org.atmosfer.seed.businessservice.type.ApprovalStatus;
import org.atmosfer.seed.businessservice.util.CheckUserRoleUtil;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PaymentApproval {


    private final CheckUserRoleUtil checkUserRoleUtil;

    public boolean isUserAuthorized() {
       return checkUserRoleUtil.isPricingUser();
    }


}
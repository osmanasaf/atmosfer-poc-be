package org.codefirst.seed.businessservice.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.codefirst.seed.businessservice.type.UserRole;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CheckUserRoleUtil {
    private final JwtUtils jwtUtils;

    public boolean isTechnicalUser(){
        return checkUserRole(UserRole.TECHNICAL);
    }

    public boolean isAppUser(){
        return checkUserRole(UserRole.APPUSER);
    }

    public boolean isHrUser(){
        return checkUserRole(UserRole.HR);
    }

    public boolean isAdminUser(){
        return checkUserRole(UserRole.ADMIN);
    }

    public boolean isPricingUser(){
        return checkUserRole(UserRole.PRICING);
    }

    private boolean checkUserRole(UserRole userRole){
        UserRole currentUserRole = jwtUtils.getCurrentUserRole();
        if (currentUserRole == null) throw new RuntimeException("User role is null");
        if(!currentUserRole.equals(userRole)) throw new RuntimeException("User role is not authorized");
        return true;
    }
}

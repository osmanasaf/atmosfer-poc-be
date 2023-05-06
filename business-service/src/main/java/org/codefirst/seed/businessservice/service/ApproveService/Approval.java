package org.codefirst.seed.businessservice.service.ApproveService;

import org.codefirst.seed.businessservice.data.Position;
import org.springframework.stereotype.Service;

public abstract class Approval {

    public abstract boolean isUserAuthorized();
    public abstract void approve(Position position);
    public abstract void reject(Position position);
}

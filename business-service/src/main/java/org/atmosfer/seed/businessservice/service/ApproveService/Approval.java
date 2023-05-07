package org.atmosfer.seed.businessservice.service.ApproveService;

import org.atmosfer.seed.businessservice.data.Position;

public abstract class Approval {

    public abstract boolean isUserAuthorized();
    public abstract void approve(Position position);
    public abstract void reject(Position position);
}

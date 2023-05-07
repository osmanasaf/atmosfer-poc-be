package org.atmosfer.seed.businessservice.data;

import lombok.Data;
import org.atmosfer.seed.businessservice.type.ApprovalStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("PositionApply")
@Data
public class PositionApply {
    @Id
    private String id;
    private String name;
    private String tckn;
    private String surname;
    @Indexed
    private String email;
    private String phone;
    private String city;
    @Indexed
    private String positionId;
    private ApprovalStatus hrApprovalStatus;
    private ApprovalStatus techinalApprovalStatus;
    private ApprovalStatus financeApprovalStatus;
}

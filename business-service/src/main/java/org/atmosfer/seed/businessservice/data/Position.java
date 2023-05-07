package org.atmosfer.seed.businessservice.data;

import lombok.Data;
import org.atmosfer.seed.businessservice.type.PositionStatus;
import org.atmosfer.seed.businessservice.type.WorkType;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Position")
@Data
public class Position implements Serializable {
    @Id
    private String id;
    private String name;
    private String detail;
    private PositionStatus status;
    private String city;
    private WorkType workType;
}

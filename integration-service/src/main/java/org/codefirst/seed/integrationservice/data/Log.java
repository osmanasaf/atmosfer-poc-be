package org.codefirst.seed.integrationservice.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("log")
public class Log {
    @Id
    public String id;

    public String data;
}

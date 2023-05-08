package org.atmosfer.seed.businessservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDetailsDto {

    private String recipient;
    private String msgBody;
    private String subject;
}
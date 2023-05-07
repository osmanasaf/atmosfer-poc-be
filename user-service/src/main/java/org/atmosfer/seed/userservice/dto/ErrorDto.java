package org.atmosfer.seed.userservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class ErrorDto {
    private int resultCode;
    private String result;
    private String errorMessage;
    private String requestUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time = new Date();

}


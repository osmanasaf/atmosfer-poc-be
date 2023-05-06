package org.codefirst.seed.businessservice.controller;

import lombok.Builder;
import lombok.Data;
import org.codefirst.seed.businessservice.type.WorkType;

@Data
@Builder
public class ApplyPositionDto {
    private String name;
    private String tckn;
    private String surname;
    private byte[] cv;
    private String phone;
    private String city;
    private WorkType workType;
}

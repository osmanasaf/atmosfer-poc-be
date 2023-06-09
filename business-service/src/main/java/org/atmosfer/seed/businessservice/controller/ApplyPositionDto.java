package org.atmosfer.seed.businessservice.controller;

import lombok.Data;
import org.atmosfer.seed.businessservice.type.ApprovalStatus;

@Data
public class ApplyPositionDto {
    private String name;
    private String tckn;
    private String surname;
    private String email;
    private byte[] cv;
    private String phone;
    private String city;
    private ApprovalStatus hrApprovalStatus;
    private ApprovalStatus techinalApprovalStatus;
    private ApprovalStatus financeApprovalStatus;
}

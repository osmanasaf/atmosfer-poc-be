package org.atmosfer.seed.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.atmosfer.seed.businessservice.data.Position;
import org.atmosfer.seed.businessservice.data.PositionApply;
import org.atmosfer.seed.businessservice.dto.ApproveDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApproveService {
    private final PositionService positionService;
    private final MailService mailService;
    public void hrApproval(String positionId, ApproveDto dto) {
        PositionApply positionApply = positionService.getPositionApplyById(positionId);
        positionApply.setHrApprovalStatus(dto.getStatus());
        positionApply.setHrApprovalMessage(dto.getMessage());
        positionService.savePositionApply(positionApply);

        Position position = positionService.getPositionById(positionApply.getPositionId());
        mailService.sendHrInfoMail(positionApply.getEmail(), position.getName(), dto.getStatus());
    }

    public void technicalApproval(String positionId, ApproveDto dto) {
        PositionApply positionApply = positionService.getPositionApplyById(positionId);
        positionApply.setTechinalApprovalStatus(dto.getStatus());
        positionApply.setTechinalApprovalMessage(dto.getMessage());
        positionService.savePositionApply(positionApply);

        Position position = positionService.getPositionById(positionApply.getPositionId());
        mailService.sendTechnicalInfoMail(positionApply.getEmail(), position.getName(), dto.getStatus());
    }

    public void pricingApproval(String positionId, ApproveDto dto) {
        PositionApply positionApply = positionService.getPositionApplyById(positionId);
        positionApply.setFinanceApprovalStatus(dto.getStatus());
        positionApply.setFinanceApprovalMessage(dto.getMessage());
        positionService.savePositionApply(positionApply);

        Position position = positionService.getPositionById(positionApply.getPositionId());
        mailService.sendPricingInfoMail(positionApply.getEmail(), position.getName(), dto.getStatus());
    }
}

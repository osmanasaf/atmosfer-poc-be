package org.atmosfer.seed.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.atmosfer.seed.businessservice.controller.ApplyPositionDto;
import org.atmosfer.seed.businessservice.data.Position;
import org.atmosfer.seed.businessservice.repository.PositionRepository;
import org.atmosfer.seed.businessservice.service.ApproveService.Approval;
import org.atmosfer.seed.businessservice.service.ApproveService.HRApproval;
import org.atmosfer.seed.businessservice.service.ApproveService.PaymentApproval;
import org.atmosfer.seed.businessservice.service.ApproveService.TechnicalApproval;
import org.atmosfer.seed.businessservice.type.ApprovalStatus;
import org.atmosfer.seed.businessservice.type.PositionStatus;
import org.atmosfer.seed.businessservice.type.UserRole;
import org.atmosfer.seed.businessservice.util.CheckUserRoleUtil;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;
    private final CheckUserRoleUtil checkUserRoleUtil;
    private final HRApproval hrApproval;
    private final TechnicalApproval technicalApproval;
    private final PaymentApproval paymentApproval;


    @PostConstruct
    private void doJob() {
        Position position = new Position();
        position.setName("deneme");
        position.setDetail("detail");
        Position fromRepo = positionRepository.save(position);
        System.out.println(fromRepo);
    }

    public Position getPositionById(String id) {
        return positionRepository.findById(id).orElseThrow(() -> new RuntimeException("Position not found"));
    }

    public List<Position> getAvailablePositions() {
        checkUserRoleUtil.isAppUser();
        return positionRepository.findAllByPositionStatus(PositionStatus.AVAILABLE);
    }

    public void applyPosition(String id, ApplyPositionDto applyPositionDto) {
        checkUserRoleUtil.isAppUser();
        Position position = getPositionById(id);
        position.setStatus(PositionStatus.APPLIED);
        position.addApplicant(applyPositionDto);
        positionRepository.save(position);
    }

    public List<Position> allPositions() {
        checkUserRoleUtil.isAdminUser();
        return (List<Position>) positionRepository.findAll();
    }

    public List<Position> allOpenPositions() {
        checkUserRoleUtil.isHrUser();
        return positionRepository.findAllByPositionStatus(PositionStatus.CLOSED);
    }
    public void approvePosition(String id, ApprovalStatus approvalStatus, UserRole userRole) {
        Position position = getPositionById(id);
        Approval approval = getApproval(userRole);
        if (approvalStatus.equals(ApprovalStatus.APPROVED)) {
            approval.approve(position);
        } else {
            approval.reject(position);
        }
        positionRepository.save(position);
    }

    public Approval getApproval(UserRole userRole) {
        return switch (userRole) {
            case HR -> hrApproval;
            case TECHNICAL -> technicalApproval;
            case PRICING -> paymentApproval;
            default -> throw new RuntimeException("User role not found");
        };
    }

    public void hrApprove(String id, ApprovalStatus approvalStatus) {
        approvePosition(id, approvalStatus, UserRole.HR);
    }

    public void technicalApprove(String id, ApprovalStatus approvalStatus) {
        approvePosition(id, approvalStatus, UserRole.TECHNICAL);
    }

    public void paymentApprove(String id, ApprovalStatus approvalStatus) {
        approvePosition(id, approvalStatus, UserRole.PRICING);
    }
}

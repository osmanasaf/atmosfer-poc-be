package org.atmosfer.seed.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.atmosfer.seed.businessservice.controller.ApplyPositionDto;
import org.atmosfer.seed.businessservice.data.Position;
import org.atmosfer.seed.businessservice.dto.PositionDto;
import org.atmosfer.seed.businessservice.repository.PositionRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;
    private final CheckUserRoleUtil checkUserRoleUtil;


    public Position getPositionById(String id) {
        return positionRepository.findById(id).orElseThrow(() -> new RuntimeException("Position not found"));
    }

    public void applyPosition(String id, ApplyPositionDto applyPositionDto) {
        checkUserRoleUtil.isAppUser();
        Position position = getPositionById(id);
        position.addApplicant(applyPositionDto);
        positionRepository.save(position);
    }

    public void changePositionStatus(String id, PositionStatus status) {
        if (!checkUserRoleUtil.isAdminUser()) {
            throw new RuntimeException("Not Allowed");
        }
        Optional<Position> positionOpt = positionRepository.findById(id);
        if (positionOpt.isPresent()) {
            Position position = positionOpt.get();
            position.setStatus(status);
            positionRepository.save(position);
        } else {
            throw new RuntimeException("Not Found");
        }
    }

    public List<Position> allPositions() {
        if (checkUserRoleUtil.isAppUser()) {
            throw new RuntimeException("Not Allowed");
        }
        return (List<Position>) positionRepository.findAll();
    }

    public List<Position> allOpenPositions() {
        if (checkUserRoleUtil.isAppUser()) {
            throw new RuntimeException("Not Allowed");
        }
        return ((List<Position>) positionRepository.findAll()).stream().filter(position -> position.getStatus()
                .equals(PositionStatus.OPEN)).collect(Collectors.toList());
    }

    public void create(PositionDto dto) {
        if (!checkUserRoleUtil.isAdminUser()) {
            throw new RuntimeException("Not Allowed");
        }

        Position position = new Position();
        position.setName(dto.getName());
        position.setDetail(dto.getDetail());
        position.setStatus(dto.getStatus());
        position.setCity(dto.getCity());
        positionRepository.save(position);
    }
}

package org.atmosfer.seed.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.atmosfer.seed.businessservice.controller.ApplyPositionDto;
import org.atmosfer.seed.businessservice.data.Position;
import org.atmosfer.seed.businessservice.data.PositionApply;
import org.atmosfer.seed.businessservice.dto.PositionDto;
import org.atmosfer.seed.businessservice.repository.PositionApplyRepository;
import org.atmosfer.seed.businessservice.repository.PositionRepository;
import org.atmosfer.seed.businessservice.type.ApprovalStatus;
import org.atmosfer.seed.businessservice.type.PositionStatus;
import org.atmosfer.seed.businessservice.util.CheckUserRoleUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;
    private final CheckUserRoleUtil checkUserRoleUtil;
    private final PositionApplyRepository positionApplyRepository;

    public Position getPositionById(String id) {
        return positionRepository.findById(id).orElseThrow(() -> new RuntimeException("Position not found"));
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

    public void applyPosition(String positionId, ApplyPositionDto applyPositionDto) {
        String email = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Optional<Position> positionOpt = positionRepository.findById(positionId);
        if(positionOpt.isPresent()) {
            PositionApply fromRedis = positionApplyRepository.findByEmail(applyPositionDto.getEmail());
            if(fromRedis != null) {
                throw new RuntimeException("You have already applied this position");
            }
            PositionApply positionApply = new PositionApply();
            positionApply.setCity(applyPositionDto.getCity());
            positionApply.setName(applyPositionDto.getName());
            positionApply.setPhone(applyPositionDto.getPhone());
            positionApply.setSurname(applyPositionDto.getSurname());
            positionApply.setTckn(applyPositionDto.getTckn());

            positionApply.setPositionId(positionId);
            positionApply.setEmail(email);
            positionApply.setFinanceApprovalStatus(ApprovalStatus.WAITING);
            positionApply.setHrApprovalStatus(ApprovalStatus.WAITING);
            positionApply.setTechinalApprovalStatus(ApprovalStatus.WAITING);
            positionApplyRepository.save(positionApply);
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
        position.setWorkType(dto.getWorkType());
        positionRepository.save(position);
    }

    public Integer getApplicantCount(String positionId) {
        List<PositionApply> applies = positionApplyRepository.findAllByPositionId(positionId);
        if (applies != null) {
            return applies.size();
        }
        return 0;
    }

    public List<PositionApply> getPositionApplies(String positionId) {
        return positionApplyRepository.findAllByPositionId(positionId);
    }
}

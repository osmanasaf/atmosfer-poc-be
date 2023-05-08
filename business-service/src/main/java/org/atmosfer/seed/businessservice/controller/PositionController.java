package org.atmosfer.seed.businessservice.controller;

import lombok.AllArgsConstructor;
import org.atmosfer.seed.businessservice.data.Position;
import org.atmosfer.seed.businessservice.data.PositionApply;
import org.atmosfer.seed.businessservice.dto.ApproveDto;
import org.atmosfer.seed.businessservice.dto.PositionDto;
import org.atmosfer.seed.businessservice.service.ApproveService;
import org.atmosfer.seed.businessservice.service.KafkaService;
import org.atmosfer.seed.businessservice.service.PositionService;
import org.atmosfer.seed.businessservice.type.PositionStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/position")
@AllArgsConstructor
public class PositionController {

    private final PositionService positionService;
    private final ApproveService approveService;
    private final KafkaService kafkaService;

    /***/@PostMapping()
    public void createPosition(@RequestBody PositionDto dto)  {
        String currUser = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        positionService.create(dto);
        kafkaService.sendMessage("create-position", currUser, dto.toString());
    }
    /***/@GetMapping("/all-positions")
    public List<PositionDto> allPositions() {
        return positionService.allPositions().stream().map(position -> {
            PositionDto dto = new PositionDto();
            dto.setCity(position.getCity());
            dto.setName(position.getName());
            dto.setDetail(position.getDetail());
            dto.setWorkType(position.getWorkType());
            dto.setStatus(position.getStatus());
            dto.setId(position.getId());
            dto.setApplicantCount(positionService.getApplicantCount(position.getId()));
            return dto;
        }).collect(Collectors.toList());
    }
    /***/@GetMapping("/all-open-positions")
    public List<PositionDto> allOpenPositions() {
        return positionService.allOpenPositions().stream().map(position -> {
            PositionDto dto = new PositionDto();
            dto.setCity(position.getCity());
            dto.setName(position.getName());
            dto.setDetail(position.getDetail());
            dto.setId(position.getId());
            dto.setWorkType(position.getWorkType());
            dto.setStatus(position.getStatus());
            dto.setApplicantCount(positionService.getApplicantCount(position.getId()));
            return dto;
        }).collect(Collectors.toList());
    }
    /***/@PutMapping("/id/{id}/status/{status}")
    public void changePositionStatus(@PathVariable String id, @PathVariable PositionStatus status){
        positionService.changePositionStatus(id, status);
        String currUser = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        kafkaService.sendMessage("change-position-status", currUser, id + " : " + status);
    }
    /***/@PostMapping("/id/{id}/apply")
    public void applyPosition(@PathVariable String id, @RequestBody ApplyPositionDto applyPositionDto) {
        positionService.applyPosition(id, applyPositionDto);
        String currUser = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        kafkaService.sendMessage("apply-position", currUser, applyPositionDto.toString());
    }
    /***/@GetMapping("/id/{id}/applies")
    public List<PositionApply> getPositionApplies(@PathVariable String id) {
        return positionService.getPositionApplies(id);
    }
    /***/@GetMapping("/id/{id}/applies/technical")
    public List<PositionApply> getPositionAppliesForTechnical(@PathVariable String id) {
        return positionService.getPositionAppliesTechnical(id);
    }
    /***/@GetMapping("/id/{id}/applies/financial")
    public List<PositionApply> getPositionAppliesForFinance(@PathVariable String id) {
        return positionService.getPositionAppliesFinance(id);
    }
    /***/@PutMapping("/position-apply/id/{id}/hr-approve")
    public void hrApprove(@PathVariable("id") String id, @RequestBody ApproveDto dto) {
        approveService.hrApproval(id, dto);
        String currUser = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        kafkaService.sendMessage("hr-approve", currUser, id + " : " + dto);
    }
    /***/@PutMapping("/position-apply/id/{id}/tech-approve")
    public void technicalApprove(@PathVariable("id") String id, @RequestBody ApproveDto dto) {
        approveService.technicalApproval(id, dto);
        String currUser = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        kafkaService.sendMessage("hr-approve", currUser, id + " : " + dto);
    }
    /***/@PutMapping("/position-apply/id/{id}/finance-approve")
    public void financeApprove(@PathVariable("id") String id, @RequestBody ApproveDto dto) {
        approveService.pricingApproval(id, dto);
        String currUser = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        kafkaService.sendMessage("hr-approve", currUser, id + " : " + dto);
    }

    @GetMapping("/get/{id}")
    public Position getPosition(@PathVariable("id") String id) {
        return positionService.getPositionById(id);
    }

}

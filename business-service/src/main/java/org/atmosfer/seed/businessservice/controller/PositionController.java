package org.atmosfer.seed.businessservice.controller;

import lombok.AllArgsConstructor;
import org.atmosfer.seed.businessservice.data.Position;
import org.atmosfer.seed.businessservice.data.PositionApply;
import org.atmosfer.seed.businessservice.dto.PositionDto;
import org.atmosfer.seed.businessservice.type.ApprovalStatus;
import org.atmosfer.seed.businessservice.service.PositionService;
import org.atmosfer.seed.businessservice.type.PositionStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/position")
@AllArgsConstructor
public class PositionController {

    private final PositionService positionService;

    /***/@PostMapping()
    public void createPosition(@RequestBody PositionDto dto)  {
        positionService.create(dto);
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
    }
    /***/@PostMapping("/id/{id}/apply")
    public void applyPosition(@PathVariable String id, @RequestBody ApplyPositionDto applyPositionDto) {
        positionService.applyPosition(id, applyPositionDto);
    }
    /***/@GetMapping("/id/{id}/applies")
    public List<PositionApply> getPositionApplies(@PathVariable String id) {
        return positionService.getPositionApplies(id);
    }
    @GetMapping("/get/{id}")
    public Position getPosition(@PathVariable("id") String id) {
        return positionService.getPositionById(id);
    }

    @GetMapping("/get-available-positions")
    public List<Position> getAvailablePositions() {
        //return positionService.getAvailablePositions();
        return null;
    }

    @PutMapping("/hr-approve/{id}")
    public void hrApprove(@PathVariable("id") String id, @RequestParam("hrApprovalStatus") ApprovalStatus approvalStatus) {
        //positionService.hrApprove(id, approvalStatus);
    }

    @PutMapping("/technical-approve/{id}")
    public void technicalApprove(@PathVariable("id") String id, @RequestParam("technicalApprovalStatus")ApprovalStatus approvalStatus) {
        //positionService.technicalApprove(id, approvalStatus);
    }

    @PutMapping("/payment-approve/{id}")
    public void paymentApprove(@PathVariable("id") String id, @RequestParam("paymentApprovalStatus")ApprovalStatus approvalStatus) {
        //positionService.paymentApprove(id, approvalStatus);
    }
}

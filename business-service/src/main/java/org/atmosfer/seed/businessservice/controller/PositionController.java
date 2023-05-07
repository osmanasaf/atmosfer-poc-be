package org.atmosfer.seed.businessservice.controller;

import lombok.AllArgsConstructor;
import org.atmosfer.seed.businessservice.data.Position;
import org.atmosfer.seed.businessservice.dto.PositionDto;
import org.atmosfer.seed.businessservice.type.ApprovalStatus;
import org.atmosfer.seed.businessservice.service.PositionService;
import org.atmosfer.seed.businessservice.type.PositionStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Position> allPositions() {
        return positionService.allPositions();
    }
    /***/@GetMapping("/all-open-positions")
    public List<Position> allOpenPositions() {
        return positionService.allOpenPositions();
    }
    /***/@PutMapping("/id/{id}/status/{status}")
    public void changePositionStatus(@PathVariable String id, @PathVariable PositionStatus status){
        positionService.changePositionStatus(id, status);
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

    @PostMapping("/apply/{id}")
    public void applyPosition(@PathVariable("id") String id, @RequestBody ApplyPositionDto applyPositionDto) {
        positionService.applyPosition(id, applyPositionDto);
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

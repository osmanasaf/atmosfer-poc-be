package org.atmosfer.seed.businessservice.controller;

import lombok.AllArgsConstructor;
import org.atmosfer.seed.businessservice.data.Position;
import org.atmosfer.seed.businessservice.type.ApprovalStatus;
import org.atmosfer.seed.businessservice.service.PositionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/position")
@AllArgsConstructor
public class PositionController {

    private final PositionService positionService;
    //endpoint to create position

    @GetMapping("/get/{id}")
    public Position getPosition(@PathVariable("id") String id) {
        return positionService.getPositionById(id);
    }

    @GetMapping("/get-available-positions")
    public List<Position> getAvailablePositions() {
        return positionService.getAvailablePositions();
    }

    @PostMapping("/apply/{id}")
    public void applyPosition(@PathVariable("id") String id, @RequestBody ApplyPositionDto applyPositionDto) {
        positionService.applyPosition(id, applyPositionDto);
    }

    @GetMapping("/all-positions")
    public List<Position> allPositions() {
        return positionService.allPositions();
    }

    @GetMapping("/all-open-positions")
    public List<Position> allOpenPositions() {
        return positionService.allOpenPositions();
    }

    @PutMapping("/hr-approve/{id}")
    public void hrApprove(@PathVariable("id") String id, @RequestParam("hrApprovalStatus") ApprovalStatus approvalStatus) {
        positionService.hrApprove(id, approvalStatus);
    }

    @PutMapping("/technical-approve/{id}")
    public void technicalApprove(@PathVariable("id") String id, @RequestParam("technicalApprovalStatus")ApprovalStatus approvalStatus) {
        positionService.technicalApprove(id, approvalStatus);
    }

    @PutMapping("/payment-approve/{id}")
    public void paymentApprove(@PathVariable("id") String id, @RequestParam("paymentApprovalStatus")ApprovalStatus approvalStatus) {
        positionService.paymentApprove(id, approvalStatus);
    }

}

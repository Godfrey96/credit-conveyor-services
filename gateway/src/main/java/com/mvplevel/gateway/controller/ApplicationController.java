package com.mvplevel.gateway.controller;

import com.mvplevel.conveyorservice.dto.LoanApplicationRequestDTO;
import com.mvplevel.conveyorservice.dto.LoanOfferDTO;
import com.mvplevel.conveyorservice.dto.ScoringDataDTO;
import com.mvplevel.dealservice.model.Application;
import com.mvplevel.gateway.service.ApplicationService;
import com.mvplevel.gateway.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final DealService dealService;

    @PostMapping("/application")
    public ResponseEntity<List<LoanOfferDTO>> createApplication(@RequestBody LoanApplicationRequestDTO loanApplicationRequest){
        return ResponseEntity.ok(applicationService.createLoanApplication(loanApplicationRequest));
    }

    @PutMapping("/application/offer")
    public ResponseEntity<?> applyOffer(@RequestBody LoanOfferDTO loanOffer){
        applicationService.applyOffer(loanOffer);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/calculate/{applicationId}")
    public ResponseEntity<?> calculateCredit(@PathVariable Long applicationId, @RequestBody ScoringDataDTO scoringData){
        dealService.calculateCredit(applicationId, scoringData);
        return ResponseEntity.ok().build();
    }
}

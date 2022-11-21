package com.mvplevel.dealservice.controller;

import com.mvplevel.conveyorservice.dto.LoanApplicationRequestDTO;
import com.mvplevel.conveyorservice.dto.LoanOfferDTO;
import com.mvplevel.conveyorservice.dto.ScoringDataDTO;
import com.mvplevel.dealservice.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
public class DealController {

    @Autowired
    private final DealService dealService;

    @PostMapping("/application")
    public ResponseEntity<List<LoanOfferDTO>> createApplication(@RequestBody LoanApplicationRequestDTO applicationRequest){
        return new ResponseEntity<>(dealService.createApplication(applicationRequest), HttpStatus.OK);
    }

    @PutMapping("/offer")
    public ResponseEntity<Void> applyOffer(@RequestBody LoanOfferDTO loanOffer){
        dealService.applyOffer(loanOffer);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/calculate/{applicationId}")
    public ResponseEntity<Void> calculateCredit(@RequestBody ScoringDataDTO scoringData, @PathVariable Long applicationId){
        dealService.calculateCredit(scoringData, applicationId);
        return ResponseEntity.ok().build();
    }

}

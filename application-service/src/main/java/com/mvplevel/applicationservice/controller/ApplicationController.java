package com.mvplevel.applicationservice.controller;

import com.mvplevel.applicationservice.service.ApplicationService;
import com.mvplevel.conveyorservice.dto.LoanApplicationRequestDTO;
import com.mvplevel.conveyorservice.dto.LoanOfferDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<List<LoanOfferDTO>> createApplication(@RequestBody LoanApplicationRequestDTO loanApplicationRequest){
        return new ResponseEntity<>(applicationService.loanApplication(loanApplicationRequest), HttpStatus.OK);
    }

    @PutMapping("/offer")
    public ResponseEntity<Void> applyOffer(@RequestBody LoanOfferDTO loanOffer){
        applicationService.applyOffer(loanOffer);
        return ResponseEntity.ok().build();
    }


}

package com.mvplevel.applicationservice.feign;

import com.mvplevel.conveyorservice.dto.LoanApplicationRequestDTO;
import com.mvplevel.conveyorservice.dto.LoanOfferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(url = "http://localhost:8082/deal", name = "DEAL-FEINT-CLIENT")
public interface DealFeignClient {

    @PostMapping("/application")
    ResponseEntity<List<LoanOfferDTO>> createApplication(@RequestBody LoanApplicationRequestDTO loanApplicationRequest);

    @PutMapping("/offer")
    ResponseEntity<Void> applyOffer(@RequestBody LoanOfferDTO loanOffer);
}

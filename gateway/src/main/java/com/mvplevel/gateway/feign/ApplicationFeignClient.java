package com.mvplevel.gateway.feign;

import com.mvplevel.conveyorservice.dto.LoanApplicationRequestDTO;
import com.mvplevel.conveyorservice.dto.LoanOfferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(url = "http://localhost:8083/application", name = "APPLICATION-FEIGN-CLIENT")
public interface ApplicationFeignClient {

    @PostMapping
    ResponseEntity<List<LoanOfferDTO>> createApplication(@RequestBody LoanApplicationRequestDTO loanApplicationRequest);

    @PutMapping("/offer")
    ResponseEntity<?> applyOffer(@RequestBody LoanOfferDTO loanOffer);
}

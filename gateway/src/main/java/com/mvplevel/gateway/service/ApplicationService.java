package com.mvplevel.gateway.service;

import com.mvplevel.conveyorservice.dto.LoanApplicationRequestDTO;
import com.mvplevel.conveyorservice.dto.LoanOfferDTO;
import com.mvplevel.gateway.feign.ApplicationFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationFeignClient applicationFeignClient;

    public List<LoanOfferDTO> createLoanApplication(LoanApplicationRequestDTO applicationRequest){
        return applicationFeignClient.createApplication(applicationRequest).getBody();
    }

    public void applyOffer(LoanOfferDTO loanOffer){
        applicationFeignClient.applyOffer(loanOffer);
    }

}

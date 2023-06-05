package com.mvplevel.applicationservice.service;

import com.mvplevel.applicationservice.exception.InvalidDataException;
import com.mvplevel.applicationservice.feign.DealFeignClient;
import com.mvplevel.conveyorservice.dto.LoanApplicationRequestDTO;
import com.mvplevel.conveyorservice.dto.LoanOfferDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {

    private final DealFeignClient dealFeignClient;

    public List<LoanOfferDTO> loanApplication(LoanApplicationRequestDTO loanApplication){
        // based on LoanApplicationRequestDTO pre-scoring
        preScoring(loanApplication);
        // Send request to /deal/application via deal feign client
        List<LoanOfferDTO> loanOffers = dealFeignClient.createApplication(loanApplication).getBody();
        log.info("Receive offers: {} ", loanOffers);
        // return the list of 4 loan offers
        return loanOffers;
    }

    public void applyOffer(LoanOfferDTO loanOfferDTO){
        log.info("select an offer = {}", loanOfferDTO);
        // send selected offer via deal feign client
        dealFeignClient.applyOffer(loanOfferDTO);
    }

    private void preScoring(LoanApplicationRequestDTO loanApplication){

        log.info("----------------Scoring for client {} {} {} ---", loanApplication.getFirstName(), loanApplication.getMiddleName(), loanApplication.getLastName());

        if (!loanApplication.getFirstName().matches("[A-Za-z\\-]{2,30}")){
            throw new InvalidDataException("Entered first name is invalid");
        }
        if (!loanApplication.getLastName().matches("[A-Za-z\\-]{2,30}")){
            throw new InvalidDataException("Entered last name is invalid");
        }
        if (loanApplication.getMiddleName() != null && !loanApplication.getMiddleName().matches("[A-Za-z\\-]{2,30}")){
            throw new InvalidDataException("Entered middle name invalid");
        }
        if (!loanApplication.getEmail().matches("[\\w\\.]{2,50}@[\\w\\.]{2,20}")){
            throw new InvalidDataException("Entered email has invalid format");
        }
        if (!loanApplication.getPassportSeries().matches("\\d{4}")){
            throw new InvalidDataException("Passport series should be 4 digits");
        }
        if (!loanApplication.getPassportNumber().matches("\\d{6}")){
            throw new InvalidDataException("Passport number should be 6 digits");
        }
        if (loanApplication.getAmount().compareTo(BigDecimal.valueOf(10000)) < 0){
            throw new InvalidDataException("Loan amount cannot be less than 10000");
        }
        if (loanApplication.getTerm() < 6){
            throw new InvalidDataException("Loan term cannot be less than 6");
        }
        if (Period.between(loanApplication.getBirthDate(), LocalDate.now()).getYears() < 18){
            throw new InvalidDataException("Age must be 18 years or above");
        }


    }


}

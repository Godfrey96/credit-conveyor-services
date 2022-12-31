package com.mvplevel.dealservice.service;

import com.mvplevel.conveyorservice.dto.CreditDTO;
import com.mvplevel.conveyorservice.dto.LoanApplicationRequestDTO;
import com.mvplevel.conveyorservice.dto.LoanOfferDTO;
import com.mvplevel.conveyorservice.dto.ScoringDataDTO;
import com.mvplevel.dealservice.constants.ApplicationStatus;
import com.mvplevel.dealservice.constants.CreditStatus;
import com.mvplevel.dealservice.exception.ApplicationNotFoundException;
import com.mvplevel.dealservice.feign.ConveyorFeignClient;
import com.mvplevel.dealservice.model.Application;
import com.mvplevel.dealservice.model.Client;
import com.mvplevel.dealservice.model.Credit;
import com.mvplevel.dealservice.repository.ApplicationRepository;
import com.mvplevel.dealservice.repository.ClientRepository;
import com.mvplevel.dealservice.repository.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DealService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private ConveyorFeignClient conveyorFeignClient;


    // create a new loan application
    public List<LoanOfferDTO> createApplication(LoanApplicationRequestDTO applicationRequest){

        // create a new client
        Client newClient = createClient(applicationRequest);

        // save the new client into the database
        Client saveClient = clientRepository.save(newClient);

        // create an application
        Application application = new Application();
        application.setClient(saveClient);
        application.setCreationDate(LocalDate.now());
        
        // save the new application to the database
        applicationRepository.save(application);

        // send request to conveyor/offers via feign client
        List<LoanOfferDTO> loanOffersDTOs = conveyorFeignClient.loanOffers(applicationRequest).getBody();

        // assigned id of each loan offer dto created in the application
        loanOffersDTOs.forEach(loanOfferDTO -> loanOfferDTO.setApplicationId(application.getId()));

        return loanOffersDTOs;
    }

    // get application by id
    public Application getApplicationById(Long id){
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("No such application found"));
    }

    // get all applications
    public List<Application> getAllApplications(){
        return applicationRepository.findAll();
    }

    public void applyOffer(LoanOfferDTO loanOfferDTO){

        // retrieve application from the database by applicationID
        Application application = applicationRepository.findById(loanOfferDTO.getApplicationId())
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found"));

        // update the status of the application to APPROVED
        application.setStatus(ApplicationStatus.APPROVED);

        // set the accepted offer LoanOfferDTO to the appliedOfferField
        application.setAppliedOffer(loanOfferDTO);

        // save the application to the database
        applicationRepository.save(application);
    }

    public void calculateCredit(ScoringDataDTO scoringData, Long applicationId){

        // retrieve application from the database by applicationID
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found"));

        // fill scoringData with additional from client which is stored in Application
        Client client = application.getClient();
        LoanOfferDTO appliedOffer = application.getAppliedOffer();

        scoringData.setAmount(appliedOffer.getRequestedAmount());
        scoringData.setTerm(appliedOffer.getTerm());
        scoringData.setFirstName(client.getFirstName());
        scoringData.setMiddleName(client.getMiddleName());
        scoringData.setLastName(client.getLastName());
        scoringData.setBirthDate(client.getBirthDate());
        scoringData.setPassportSeries(client.getPassportSeries());
        scoringData.setPassportNumber(client.getPassportNumber());
        scoringData.setIsInsuranceEnabled(appliedOffer.getIsInsuranceEnabled());
        scoringData.setIsSalaryClient(appliedOffer.getIsSalaryClient());

        // send request to /conveyor/calculation via feign client
        CreditDTO creditDTO = conveyorFeignClient.calculateCredit(scoringData).getBody();

        // based on CreditDTO received, create Credit with CALCULATED status and save to database
        Credit credit = new Credit();

        credit.setAmount(creditDTO.getAmount());
        credit.setTerm(creditDTO.getTerm());
        credit.setMonthlyPayment(creditDTO.getMonthlyPayment());
        credit.setRate(creditDTO.getRate());
        credit.setFlc(creditDTO.getPsk());
        credit.setIsInsuranceEnabled(creditDTO.getIsInsuranceEnabled());
        credit.setIsSalaryClient(creditDTO.getIsSalaryClient());
        credit.setCreditStatus(CreditStatus.CALCULATED);
        creditRepository.save(credit);

        // status is updated in the application
        application.setStatus(ApplicationStatus.APPROVED);

        // save application to database
        applicationRepository.save(application);
    }

    private Client createClient(LoanApplicationRequestDTO applicationRequest) {
        Client client = new Client();

        client.setFirstName(applicationRequest.getFirstName());
        client.setMiddleName(applicationRequest.getMiddleName());
        client.setLastName(applicationRequest.getLastName());
        client.setEmail(applicationRequest.getEmail());
        client.setBirthDate(applicationRequest.getBirthDate());
        client.setPassportSeries(applicationRequest.getPassportSeries());
        client.setPassportNumber(applicationRequest.getPassportNumber());

        return client;
    }

}

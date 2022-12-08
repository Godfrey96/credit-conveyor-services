package com.mvplevel.dossierservice.service;

import com.mvplevel.conveyorservice.dto.PaymentScheduleElement;
import com.mvplevel.dealservice.model.Application;
import com.mvplevel.dealservice.model.Client;
import com.mvplevel.dealservice.model.Credit;
import com.mvplevel.dossierservice.feign.DealFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {

    public File createCreditApplicationDocument(Application application, Long applicationId){

        Client client = application.getClient();

        StringBuilder sb = new StringBuilder("Loan Application" + applicationId)
                .append("\n-----------------")
                .append("\nFull name").append(client.getFirstName() + " " + client.getLastName())
                .append("\nDate of Birth: ").append(client.getBirthDate())
                .append("\nGender: ").append(client.getGender())
                .append("\nEmail").append(client.getEmail())
                .append("\nMarriage Status: ").append(client.getMaritalStatus())
                .append("\nDependent Amount: ").append(client.getDependentNumber())
                .append("\nEmployment Information: ")
                .append("\nEmployment Status: ").append(client.getEmployment().getEmploymentStatus())
                .append("\nSalary: ").append(client.getEmployment().getSalary())
                .append("\nPosition: ").append(client.getEmployment().getPosition())
                .append("\nTotal Work Experience: ").append(client.getEmployment().getWorkExperienceTotal())
                .append("\nCurrent Work Experience: ").append(client.getEmployment().getWorkExperienceCurrent());

        File file = null;

        try{
            file = File.createTempFile("Loan Application", null);
        }catch (IOException e){
            e.printStackTrace();
        }

        try(FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write(sb.toString());
        }catch (IOException e){
            e.printStackTrace();
        }
        return file;
    }

    public File createCreditContractDocument(Application application, Long applicationId){

        Client client = application.getClient();
        Credit credit = application.getCredit();

        StringBuilder sb = new StringBuilder("Credit Contract" + applicationId)
                .append("\n-----------------")
                .append("\nFull name").append(client.getFirstName() + " " + client.getLastName())
                .append("\nPassport: ").append(client.getPassportNumber())
                .append("\nCredit Information: ")
                .append("\nAmount: ").append(credit.getAmount())
                .append("\nTerm").append(credit.getTerm())
                .append("\nMonthly Payment: ").append(credit.getMonthlyPayment())
                .append("\nRate: ").append(credit.getRate())
                .append("\nPSK: ").append(credit.getFlc())
                .append("\nServices Information")
                .append("\nInsurance: ").append(credit.getIsInsuranceEnabled())
                .append("\nSalary client: ").append(credit.getIsSalaryClient());

        File file = null;

        try{
            file = File.createTempFile("Credit Contract", null);
        }catch (IOException e){
            e.printStackTrace();
        }

        try(FileWriter fileWriter = new FileWriter(file)){
            file.createNewFile();
            fileWriter.write(sb.toString());
        }catch (IOException e){
            e.printStackTrace();
        }
        return file;
    }

    public File createCreditPaymentScheduleDocument(Application application, Long applicationId){
        Client client = application.getClient();
        Credit credit = application.getCredit();

        StringBuilder sb = new StringBuilder("Payment Schedule" + applicationId)
                .append("\n-----------------");
                for (PaymentScheduleElement paymentSchedule : credit.getPaymentSchedule()){
                    sb.append("\nMonth of payment").append(paymentSchedule.getNumber())
                            .append("\nPayment Date").append(paymentSchedule.getDate())
                            .append("\nInterest Payment").append(paymentSchedule.getInterestPayment())
                            .append("\nTotal Payment").append(paymentSchedule.getTotalPayment())
                            .append("\nDebt Payment").append(paymentSchedule.getDebtPayment())
                            .append("\nRemaining Debt").append(paymentSchedule.getRemainingDebt());
                }

        File file = null;

        try{
            file = File.createTempFile("Payment Schedule", null);
        }catch (IOException e){
            e.printStackTrace();
        }

        try(FileWriter fileWriter = new FileWriter(file)){
            file.createNewFile();
            fileWriter.write(sb.toString());
        }catch (IOException e){
            e.printStackTrace();
        }
        return file;
    }

}

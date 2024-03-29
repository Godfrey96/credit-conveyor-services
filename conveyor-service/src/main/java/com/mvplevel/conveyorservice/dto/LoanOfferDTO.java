package com.mvplevel.conveyorservice.dto;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@Builder
public class LoanOfferDTO {
    private Long applicationId;
    private BigDecimal requestedAmount;
    private BigDecimal totalAmount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;
}

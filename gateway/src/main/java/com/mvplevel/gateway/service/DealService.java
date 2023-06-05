package com.mvplevel.gateway.service;

import com.mvplevel.conveyorservice.dto.ScoringDataDTO;
import com.mvplevel.gateway.feign.DealFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DealService {

    private final DealFeignClient dealFeignClient;

    public void calculateCredit(Long applicationId, ScoringDataDTO scoringData){
        dealFeignClient.calculateCredit(applicationId, scoringData);
    }
}

package com.mvplevel.gateway.feign;

import com.mvplevel.conveyorservice.dto.ScoringDataDTO;
import com.mvplevel.dealservice.model.Application;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(url = "http://localhost:8082/deal", name = "DEAL-FEIGN-CLIENT")
public interface DealFeignClient {

    @GetMapping("/admin/application/{applicationId}")
    Application getApplicationById(@PathVariable Long applicationId);

    @PostMapping("/admin/application/{applicationId)/status")
    void updateApplicationStatusById(@PathVariable Long applicationId, @RequestParam String status);

    @PutMapping("/calculate/{applicationId}")
    ResponseEntity<Void> calculateCredit(@PathVariable Long applicationId, @RequestBody ScoringDataDTO scoringData);

}

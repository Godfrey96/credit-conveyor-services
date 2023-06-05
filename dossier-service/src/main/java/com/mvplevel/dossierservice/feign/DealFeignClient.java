package com.mvplevel.dossierservice.feign;

import com.mvplevel.dealservice.model.Application;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://localhost:8082/deal", name = "DEAL-FEIGN-CLIENT")
public interface DealFeignClient {

    @GetMapping("/application/{applicationId}")
    Application getApplicationById(@PathVariable Long applicationId);

    @PostMapping("/application/{applicationId)/status")
    void updateApplicationStatusById(@PathVariable Long applicationId, @RequestParam String status);
}

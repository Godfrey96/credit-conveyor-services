package com.mvplevel.gateway.feign;

import com.mvplevel.dealservice.model.Application;
import com.mvplevel.dealservice.model.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8082/deal/admin", name = "ADMIN-FEIGN-CLIENT")
public interface AdminFeignClient {

    @GetMapping("/application/{applicationId}")
    Application getApplicationById(@PathVariable Long applicationId);

    @GetMapping("/client/{firstName}/{lastname}")
    Client getCustomerByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName);
}

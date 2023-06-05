package com.mvplevel.gateway.service;

import com.mvplevel.dealservice.model.Application;
import com.mvplevel.dealservice.model.Client;
import com.mvplevel.gateway.feign.AdminFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminFeignClient adminFeignClient;

    public Application getApplicationById(Long applicationId){
        return adminFeignClient.getApplicationById(applicationId);
    }

    public Client getCustomerByFirstNameAndLastName(String firstName, String lastName){
        return adminFeignClient.getCustomerByFirstNameAndLastName(firstName, lastName);
    }
}

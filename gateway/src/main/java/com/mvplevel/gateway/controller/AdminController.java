package com.mvplevel.gateway.controller;

import com.mvplevel.dealservice.model.Application;
import com.mvplevel.dealservice.model.Client;
import com.mvplevel.gateway.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deal/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long applicationId){
        return ResponseEntity.ok(adminService.getApplicationById(applicationId));
    }

    @GetMapping("/client/{firstName}/{lastName}")
    public ResponseEntity<Client> getClientByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName){
        return ResponseEntity.ok(adminService.getCustomerByFirstNameAndLastName(firstName, lastName));
    }
}

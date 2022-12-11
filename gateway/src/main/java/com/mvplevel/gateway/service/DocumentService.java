package com.mvplevel.gateway.service;

import com.mvplevel.gateway.feign.DocumentFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentFeignClient documentFeignClient;

    public void sendDocuments(Long applicationId){
        documentFeignClient.sendDocuments(applicationId);
    }

    public void signDocuments(Long applicationId){
        documentFeignClient.signDocuments(applicationId);
    }

    public void sendSesCode(Long applicationId, int sesCode){
        documentFeignClient.confirmCode(applicationId, sesCode);
    }

}

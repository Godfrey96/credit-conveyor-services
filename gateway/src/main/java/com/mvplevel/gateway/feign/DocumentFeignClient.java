package com.mvplevel.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:8082/deal", name = "DOCUMENT-FEIGN-CLIENT")
public interface DocumentFeignClient {

    @PostMapping("/document/{applicationId}/send")
    ResponseEntity<?> sendDocuments(@PathVariable Long applicationId);

    @PostMapping("/document/{applicationId}/sign")
    ResponseEntity<?> signDocuments(@PathVariable Long applicationId);

    @PostMapping("/document/{applicationId}/code")
    ResponseEntity<?> confirmCode(@PathVariable Long applicationId, @RequestBody Integer sesCode);
}

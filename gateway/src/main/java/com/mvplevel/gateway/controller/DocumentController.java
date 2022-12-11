package com.mvplevel.gateway.controller;

import com.mvplevel.gateway.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/document/{applicationId}/send")
    public ResponseEntity<?> sendDocuments(@PathVariable Long applicationId){
        documentService.sendDocuments(applicationId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/document/{applicationId}/sign")
    public ResponseEntity<?> signDocuments(@PathVariable Long applicationId){
        documentService.signDocuments(applicationId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/document/{applicationId}/code")
    public ResponseEntity<?> confirmCode(@PathVariable Long applicationId, @RequestBody Integer sesCode){
        documentService.sendSesCode(applicationId, sesCode);
        return ResponseEntity.ok().build();
    }

}

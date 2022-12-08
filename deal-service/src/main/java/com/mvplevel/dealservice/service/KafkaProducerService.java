package com.mvplevel.dealservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvplevel.applicationservice.service.ApplicationService;
import com.mvplevel.dealservice.constants.Theme;
import com.mvplevel.dealservice.dto.EmailMessage;
import com.mvplevel.dealservice.model.Application;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private KafkaTemplate<String, String> kafkaTemplate;
    private DealService dealService;
    private ObjectMapper objectMapper;

    private void sendMessage(String topic, Theme theme, Long id) throws JsonProcessingException {

        // get application id first
        Application application = dealService.getApplicationById(id);

        EmailMessage emailMessage = EmailMessage.builder()
                .address(application.getClient().getEmail())
                .theme(theme)
                .applicationId(application.getId())
                .build();

        String jsonMessage = objectMapper.writeValueAsString(emailMessage);

        log.info("Message sent -> {}", jsonMessage);
        kafkaTemplate.send(topic, jsonMessage);

    }


}

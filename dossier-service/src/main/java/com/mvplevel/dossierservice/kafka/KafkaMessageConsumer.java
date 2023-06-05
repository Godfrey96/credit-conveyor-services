package com.mvplevel.dossierservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvplevel.dealservice.constants.ApplicationStatus;
import com.mvplevel.dealservice.constants.Theme;
import com.mvplevel.dealservice.model.Application;
import com.mvplevel.dossierservice.dto.EmailMessage;
import com.mvplevel.dossierservice.feign.DealFeignClient;
import com.mvplevel.dossierservice.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageConsumer {

    private final EmailSenderService emailSender;
    private final DealFeignClient dealFeignClient;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "finish-registration", groupId = "myDossier")
    public void consumeFinishRegistration(String message) throws JsonProcessingException {
        EmailMessage emailMessage = objectMapper.readValue(message, EmailMessage.class);
        Theme theme = emailMessage.getTheme();
        Application application = dealFeignClient.getApplicationById(emailMessage.getApplicationId());
        String link = "http://localhost:9000/finish-registration/{applicationId}";
        String body = String.format("Hi, your loan application {} %s", emailMessage.getApplicationId(), "approved. " +
                "\nPlease finish your registration by clicking the link {} %s", link);

        emailSender.sendEmailMessage(emailMessage.getAddress(), body, theme.toString());
    }

    @KafkaListener(topics = "create-documents", groupId = "myDossier")
    public void consumeCreateDocuments(String message) throws JsonProcessingException {
        EmailMessage emailMessage = objectMapper.readValue(message, EmailMessage.class);
        Theme theme = emailMessage.getTheme();
        Application application = dealFeignClient.getApplicationById(emailMessage.getApplicationId());
        String link = "http://localhost:9000/create-documents/{applicationId}";
        String body = String.format("Hi, your loan application {} %s", emailMessage.getApplicationId(), "met all the requirements. " +
                "\nPlease click the link to upload the supporting documents to process your application {} %s", link);

        emailSender.sendEmailMessage(emailMessage.getAddress(), body, theme.toString());
    }

    @KafkaListener(topics = "send-documents", groupId = "myDossier")
    public void consumeSendDocuments(String message) throws JsonProcessingException, MessagingException {
        EmailMessage emailMessage = objectMapper.readValue(message, EmailMessage.class);
        Theme theme = emailMessage.getTheme();
        Application application = dealFeignClient.getApplicationById(emailMessage.getApplicationId());
        String link = "http://localhost:9000/sign-documents/{applicationId}";
        String body = String.format("Hi, please find your documents for loan application {} %s", emailMessage.getApplicationId(), " " +
                "\nPlease follow the link to read and accept our T&C's and sign the documents and send them back {} %s", link);

        dealFeignClient.updateApplicationStatusById(emailMessage.getApplicationId(), ApplicationStatus.DOCUMENT_CREATED.name());

        emailSender.sendEmailWithAttachment(emailMessage.getAddress(), body, theme.toString(), application, emailMessage.getApplicationId());
    }

    @KafkaListener(topics = "send-ses", groupId = "myDossier")
    public void consumeSendSes(String message) throws JsonProcessingException {
        EmailMessage emailMessage = objectMapper.readValue(message, EmailMessage.class);
        Theme theme = emailMessage.getTheme();
        Application application = dealFeignClient.getApplicationById(emailMessage.getApplicationId());
        String link = "http://localhost:9000/send-ses/{applicationId}";
        int sesCode = dealFeignClient.getApplicationById(emailMessage.getApplicationId()).getSesCode();
        String body = String.format("Hi, this is your SES code {} %s", sesCode, "for your loan application {} %s", emailMessage.getApplicationId(), "" +
                "\nPlease use the link to send this code {} %s");

        emailSender.sendEmailMessage(emailMessage.getAddress(), body, theme.toString());
    }

    @KafkaListener(topics = "send-ses", groupId = "myDossier")
    public void consumeCreditIssue(String message) throws JsonProcessingException {
        EmailMessage emailMessage = objectMapper.readValue(message, EmailMessage.class);
        Theme theme = emailMessage.getTheme();
        String body = String.format("Hi, credit for your loan application {} %s", emailMessage.getApplicationId(), "is issued." +
                "\nThe money will be in your account soon");

        emailSender.sendEmailMessage(emailMessage.getAddress(), body, theme.toString());
    }

    @KafkaListener(topics = "application-denied", groupId = "myDossier")
    public void consumeApplicationDenied(String message) throws JsonProcessingException {
        EmailMessage emailMessage = objectMapper.readValue(message, EmailMessage.class);
        Theme theme = emailMessage.getTheme();
        String body = String.format("Hi, your loan application {} %s", emailMessage.getApplicationId(), "is denied.");

        emailSender.sendEmailMessage(emailMessage.getAddress(), body, theme.toString());
    }


}

package com.mvplevel.dossierservice.service;

import com.mvplevel.dealservice.model.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
@Slf4j
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;
    private DocumentService documentService;

    public void sendEmailMessage(String toEmail, String body, String subject){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ngwatlegodfrey@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
    }

    public void sendEmailWithAttachment(String toEmail, String body, String subject, Application application, Long id) throws MessagingException {

        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom("ngwatlegodfrey@gmail.com");
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setText(body);
            mimeMessageHelper.setSubject(subject);

            File file1 = documentService.createCreditApplicationDocument(application, id);
            File file2 = documentService.createCreditContractDocument(application, id);
            File file3 = documentService.createCreditPaymentScheduleDocument(application, id);

            mimeMessageHelper.addAttachment(file1.getName(), file1);
            mimeMessageHelper.addAttachment(file2.getName(), file2);
            mimeMessageHelper.addAttachment(file3.getName(), file3);

            mailSender.send(mimeMessage);
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }

}

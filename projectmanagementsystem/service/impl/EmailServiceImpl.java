package com.shobhit.projectmanagementsystem.service.impl;

import com.shobhit.projectmanagementsystem.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender javaMailSender ;
    @Override
    public void sendEmailWithToken(String userEmail, String link) throws MessagingException {
        MimeMessage mimeMailMessage=javaMailSender.createMimeMessage() ;
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMailMessage,"utf-8") ;

        String subject="Join Project Team Invitation" ;
        String text="Click the link to join the project:"+link ;

        mimeMessageHelper.setSubject(subject) ;
        mimeMessageHelper.setText(text); ;
        mimeMessageHelper.setTo(userEmail) ;

        try{
            javaMailSender.send(mimeMailMessage) ;
        }catch(MailSendException e){
            throw new MailSendException("failed to send email") ;
        }


    }
}

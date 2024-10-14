package com.shobhit.projectmanagementsystem.service.impl;

import com.shobhit.projectmanagementsystem.model.Invitation;
import com.shobhit.projectmanagementsystem.repository.InvitationRepository;
import com.shobhit.projectmanagementsystem.service.EmailService;
import com.shobhit.projectmanagementsystem.service.InvitationService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService {

    @Autowired
    private EmailService emailService ;

    @Autowired
    private InvitationRepository invitationRepository ;

    @Override
    public void sendInvitation(String email, long projectId) throws MessagingException {

        String invitationToken= UUID.randomUUID().toString() ;
        Invitation invitation=new Invitation() ;
        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.setToken(invitationToken) ;
        invitationRepository.save(invitation) ;

        String invitationLink="http://localhost:3030/accept_invitation?token="+invitationToken ;
        emailService.sendEmailWithToken(email,invitationLink) ;


    }

    @Override
    public Invitation acceptInvitation(String token, long userId) throws Exception {
        Invitation invitation=invitationRepository.findByToken(token) ;
        if(invitation==null){
            throw new Exception("invalid token") ;
        }
        return invitation;
    }

    @Override
    public String getTokenByUserMail(String userEmail) {
        Invitation invitation=invitationRepository.findByEmail(userEmail) ;
        return invitation.getToken() ;

    }

    @Override
    public void deleteToken(String token) {
        Invitation invitation=invitationRepository.findByToken(token) ;
        invitationRepository.delete(invitation) ;
    }
}

package com.shobhit.projectmanagementsystem.service;

import com.shobhit.projectmanagementsystem.model.Invitation;
import jakarta.mail.MessagingException;

public interface InvitationService {
    public void sendInvitation(String email, long projectId) throws MessagingException;
    public Invitation acceptInvitation(String token, long userId) throws Exception;

    public String getTokenByUserMail(String userEmail) ;

    void deleteToken(String token) ;
}

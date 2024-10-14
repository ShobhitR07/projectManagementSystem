package com.shobhit.projectmanagementsystem.repository;

import com.shobhit.projectmanagementsystem.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation,Long> {
    Invitation findByToken(String token) ;
    Invitation findByEmail(String userEmail) ;
}

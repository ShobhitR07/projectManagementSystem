package com.shobhit.projectmanagementsystem.repository;

import com.shobhit.projectmanagementsystem.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRepository extends JpaRepository<Chat,Long> {

}

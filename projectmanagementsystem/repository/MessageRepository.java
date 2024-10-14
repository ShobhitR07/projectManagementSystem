package com.shobhit.projectmanagementsystem.repository;

import com.shobhit.projectmanagementsystem.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findByChatIdOrderByCreatedAtAsc(Long chatId) ;

}
package com.shobhit.projectmanagementsystem.service.impl;

import com.shobhit.projectmanagementsystem.model.Chat;
import com.shobhit.projectmanagementsystem.repository.ChatRepository;
import com.shobhit.projectmanagementsystem.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository ;
    @Override
    public Chat createChat(Chat chat) {
        Chat newChat=new Chat() ;
        newChat.setProject(chat.getProject());
        newChat.setName(chat.getName());
        newChat.setUsers(chat.getUsers());
        newChat.setMessageList(chat.getMessageList());
        chatRepository.save(newChat) ;
        return newChat ;


    }
}

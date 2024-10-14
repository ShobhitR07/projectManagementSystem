package com.shobhit.projectmanagementsystem.service.impl;

import com.shobhit.projectmanagementsystem.model.Chat;
import com.shobhit.projectmanagementsystem.model.Message;
import com.shobhit.projectmanagementsystem.model.Project;
import com.shobhit.projectmanagementsystem.model.User;
import com.shobhit.projectmanagementsystem.repository.ChatRepository;
import com.shobhit.projectmanagementsystem.repository.MessageRepository;
import com.shobhit.projectmanagementsystem.repository.UserRepository;
import com.shobhit.projectmanagementsystem.service.MessageService;
import com.shobhit.projectmanagementsystem.service.ProjectService;
import com.shobhit.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository ;

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private ProjectService projectService ;

    @Autowired
    private ChatRepository chatRepository ;


    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
        Optional<User> optionalUser=userRepository.findById(senderId) ;
        if(optionalUser.isEmpty())throw new Exception("user not found") ;
        User user=optionalUser.get() ;
        Chat chat=projectService.getProjectById(projectId).getChat() ;
        Message message=new Message() ;
        message.setSender(user) ;
        message.setChat(chat);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());
        Message savedMessage=messageRepository.save(message) ;

        chat.getMessageList().add(savedMessage) ;
        chatRepository.save(chat) ;

        return savedMessage;
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) throws Exception {
        Chat chat=projectService.getProjectById(projectId).getChat() ;
        return messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
    }
}

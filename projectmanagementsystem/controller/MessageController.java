package com.shobhit.projectmanagementsystem.controller;

import com.shobhit.projectmanagementsystem.model.Message;
import com.shobhit.projectmanagementsystem.model.Project;
import com.shobhit.projectmanagementsystem.model.User;
import com.shobhit.projectmanagementsystem.request.CreateMessageRequest;
import com.shobhit.projectmanagementsystem.service.MessageService;
import com.shobhit.projectmanagementsystem.service.ProjectService;
import com.shobhit.projectmanagementsystem.service.UserService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService ;
    @Autowired
    private UserService userService ;
    @Autowired
    private ProjectService projectService ;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(
            @RequestBody CreateMessageRequest createMessageRequest
    ) throws Exception {
        User user=userService.findUserById(createMessageRequest.getSenderId()) ;
        Project project=projectService.getProjectById(createMessageRequest.getProjectId()) ;
        Message message=messageService.sendMessage(user.getId(), project.getId(), createMessageRequest.getContent()) ;
        return new ResponseEntity<>(message, HttpStatus.OK) ;
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByProjectId(@PathVariable long projectId) throws Exception {
        return new ResponseEntity<> (messageService.getMessagesByProjectId(projectId),HttpStatus.OK) ;
    }

}

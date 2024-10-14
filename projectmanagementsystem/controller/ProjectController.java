package com.shobhit.projectmanagementsystem.controller;

import com.shobhit.projectmanagementsystem.model.Chat;
import com.shobhit.projectmanagementsystem.model.Invitation;
import com.shobhit.projectmanagementsystem.model.Project;
import com.shobhit.projectmanagementsystem.model.User;
import com.shobhit.projectmanagementsystem.request.InvitationRequest;
import com.shobhit.projectmanagementsystem.response.MessageResponse;
import com.shobhit.projectmanagementsystem.service.InvitationService;
import com.shobhit.projectmanagementsystem.service.ProjectService;
import com.shobhit.projectmanagementsystem.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService ;

    @Autowired
    private UserService userService ;

    @Autowired
    private InvitationService invitationService ;

    @GetMapping
    public ResponseEntity<List<Project>> getProject(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tags,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt) ;
        List<Project> projects=projectService.getProjectByTeam(user,category,tags) ;
        return new ResponseEntity<>(projects, HttpStatus.OK) ;
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(
            @PathVariable long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt) ;
        Project project=projectService.getProjectById(projectId) ;
        return new ResponseEntity<>(project, HttpStatus.OK) ;
    }

    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt) ;
        Project savedProject=projectService.createProject(project,user) ;
        return new ResponseEntity<>(savedProject, HttpStatus.OK) ;
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(
            @PathVariable long projectId,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt) ;
        Project updatedProject=projectService.updateProject(project,projectId) ;
        return new ResponseEntity<>(updatedProject, HttpStatus.OK) ;
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(
            @PathVariable long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt) ;
        projectService.deleteProject(projectId, user.getId()); ;
        return new ResponseEntity<>(new MessageResponse("project deleted!"), HttpStatus.OK) ;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProject(
            @RequestParam(required = true)String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt) ;
        List<Project> projects=projectService.searchProject(keyword,user) ;
        return new ResponseEntity<>(projects, HttpStatus.OK) ;
    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat> getChatByProjectId(
            @PathVariable long projectId,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt) ;
        Chat chat=projectService.getChatByProjectId(projectId) ;
        return new ResponseEntity<>(chat,HttpStatus.OK) ;
    }

    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> inviteProject(
            @RequestBody InvitationRequest invitationRequest,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws MessagingException {
        invitationService.sendInvitation(invitationRequest.getEmail(), invitationRequest.getProjectId());
        return new ResponseEntity<>(new MessageResponse("user invited successfully"),HttpStatus.OK) ;
    }

    @GetMapping("/accept_invitation")
    public ResponseEntity<Invitation> acceptInviteProject(
            @RequestParam String token,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt) ;
        Invitation invitation=invitationService.acceptInvitation(token, user.getId()) ;
        projectService.addUserToProject(invitation.getProjectId(), user.getId());
        return new ResponseEntity<>(invitation,HttpStatus.OK) ;
    }









}

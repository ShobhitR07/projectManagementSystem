package com.shobhit.projectmanagementsystem.controller;

import com.shobhit.projectmanagementsystem.dto.IssueDTO;
import com.shobhit.projectmanagementsystem.model.Issue;
import com.shobhit.projectmanagementsystem.model.User;
import com.shobhit.projectmanagementsystem.request.IssueRequest;
import com.shobhit.projectmanagementsystem.response.MessageResponse;
import com.shobhit.projectmanagementsystem.service.IssueService;
import com.shobhit.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
    @Autowired
    private IssueService issueService ;

    @Autowired
    private UserService userService ;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable long issueId) throws Exception {
        return new ResponseEntity<>(issueService.getIssueById(issueId), HttpStatus.OK) ;

    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable long projectId) throws Exception {
        return new ResponseEntity<>(issueService.getIssueByProjectId(projectId), HttpStatus.OK) ;
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(
            @RequestBody IssueRequest issue,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User tokenUser=userService.findUserProfileByJwt(jwt) ;
        User user=userService.findUserById(tokenUser.getId()) ;

            Issue createdIssue=issueService.createIssue(issue,user) ;
            IssueDTO issueDTO=new IssueDTO() ;
            issueDTO.setDescription(createdIssue.getDescription());
            issueDTO.setTitle(createdIssue.getTitle());
            issueDTO.setStatus(createdIssue.getStatus());
            issueDTO.setDueDate(createdIssue.getDueDate());
            issueDTO.setId(createdIssue.getId());
            issueDTO.setTags(createdIssue.getTags());
            issueDTO.setAssignee(createdIssue.getAssignee());
            issueDTO.setProjectId(createdIssue.getProject().getId());
            issueDTO.setProject(createdIssue.getProject());
            issueDTO.setPriority(createdIssue.getPriority());

            return new ResponseEntity<>(issueDTO,HttpStatus.OK) ;


    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(
            @PathVariable long issueId,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt) ;
        issueService.deleteIssue(issueId,user.getId()) ;
        return new ResponseEntity<>(new MessageResponse("deleted succesffuly"),HttpStatus.OK) ;
    }

    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(
            @PathVariable long issueId,
            @PathVariable long userId
    ) throws Exception {
        Issue issue=issueService.addUserToIssue(issueId,userId) ;
        return new ResponseEntity<>(issue,HttpStatus.OK) ;
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(
            @PathVariable long issueId,
            @PathVariable String status
    ) throws Exception {
        Issue issue=issueService.updateStatus(issueId,status) ;
        return new ResponseEntity<>(issue,HttpStatus.OK) ;
    }




}

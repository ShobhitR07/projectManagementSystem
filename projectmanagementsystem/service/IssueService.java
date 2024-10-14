package com.shobhit.projectmanagementsystem.service;

import com.shobhit.projectmanagementsystem.model.Issue;
import com.shobhit.projectmanagementsystem.model.User;
import com.shobhit.projectmanagementsystem.request.IssueRequest;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface IssueService {
    Issue getIssueById(long id) throws Exception ;
    List<Issue> getIssueByProjectId(long projectId) throws Exception ;

    Issue createIssue(IssueRequest issueRequest,User user) throws Exception ;
//    Optional<Issue> updateIssue(IssueRequest updatedIssue,Long issueId,Long userId) throws Exception ;

    void deleteIssue(long issueId,Long userId) throws Exception ;

//    List<Issue> searchIssue(String title,String status,String priority,Long assigneeId) throws  Exception ;
//
//    List<User> getAssigneeForIssue(long issueId) throws Exception ;

    Issue addUserToIssue(long issueId,long userId) throws Exception ;

    Issue updateStatus(long issueId,String status) throws Exception ;

}

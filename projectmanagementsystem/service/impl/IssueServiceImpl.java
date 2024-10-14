package com.shobhit.projectmanagementsystem.service.impl;

import com.shobhit.projectmanagementsystem.model.Issue;
import com.shobhit.projectmanagementsystem.model.Project;
import com.shobhit.projectmanagementsystem.model.User;
import com.shobhit.projectmanagementsystem.repository.IssueRepository;
import com.shobhit.projectmanagementsystem.request.IssueRequest;
import com.shobhit.projectmanagementsystem.service.IssueService;
import com.shobhit.projectmanagementsystem.service.ProjectService;
import com.shobhit.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueRepository issueRepository ;

    @Autowired
    private ProjectService projectService ;

    @Autowired
    private UserService userService ;

    @Override
    public Issue getIssueById(long id) throws Exception {
        Optional<Issue> optionalIssue=issueRepository.findById(id) ;
        if(optionalIssue.isEmpty())throw new Exception("issue not found") ;
        return optionalIssue.get() ;
    }

    @Override
    public List<Issue> getIssueByProjectId(long projectId) throws Exception {
        return issueRepository.findByProjectId(projectId);
    }

    @Override
    public Issue createIssue(IssueRequest issueRequest, User user) throws Exception {
        Project project=projectService.getProjectById(issueRequest.getProjectId()) ;
        Issue issue=new Issue() ;

        issue.setTitle(issueRequest.getTitle());
        issue.setDescription(issueRequest.getDescription());
        issue.setStatus(issueRequest.getStatus());
        issue.setProject(project);
        issue.setPriority(issueRequest.getPriority());
        issue.setDueDate(issueRequest.getDueDate());
//        issue.setProjectId(project.getId());

        return issueRepository.save(issue);
    }

    @Override
    public void deleteIssue(long issueId, Long userId) throws Exception {
        Optional<Issue> issue=issueRepository.findById(issueId) ;
        if(issue.isEmpty())throw new Exception("issue not found") ;
        issueRepository.deleteById(issueId);
    }

    @Override
    public Issue addUserToIssue(long issueId, long userId) throws Exception {
        Optional<Issue> optionalIssue=issueRepository.findById(issueId) ;
        if(optionalIssue.isEmpty())throw new Exception("issue not found") ;
        User user=userService.findUserById(userId) ;
        Issue issue=optionalIssue.get() ;
        issue.setAssignee(user);
        return issueRepository.save(issue) ;

    }

    @Override
    public Issue updateStatus(long issueId, String status) throws Exception {
        Optional<Issue> optionalIssue=issueRepository.findById(issueId) ;
        if(optionalIssue.isEmpty())throw new Exception("issue not found") ;
        Issue issue=optionalIssue.get() ;
        issue.setStatus(status);
        return issueRepository.save(issue);
    }
}

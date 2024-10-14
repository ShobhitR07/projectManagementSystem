package com.shobhit.projectmanagementsystem.service.impl;

import com.shobhit.projectmanagementsystem.model.Comment;
import com.shobhit.projectmanagementsystem.model.Issue;
import com.shobhit.projectmanagementsystem.model.User;
import com.shobhit.projectmanagementsystem.repository.CommentRepository;
import com.shobhit.projectmanagementsystem.repository.IssueRepository;
import com.shobhit.projectmanagementsystem.repository.UserRepository;
import com.shobhit.projectmanagementsystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository ;

    @Autowired
    private IssueRepository issueRepository ;

    @Autowired
    private UserRepository userRepository ;

    @Override
    public Comment createComment(Long issueId, long userId, String comment) throws Exception {

        Optional<Issue> optionalIssue=issueRepository.findById(issueId) ;
        Optional<User> optionalUser=userRepository.findById(userId) ;
        if(optionalUser.isEmpty()){
            throw new Exception("user is not present"+userId);
        }
        if(optionalIssue.isEmpty()){
            throw new Exception("issue is not present"+issueId);
        }

        User user=optionalUser.get() ;
        Issue issue=optionalIssue.get() ;
        Comment comment1=new Comment() ;
        comment1.setContent(comment);
        comment1.setCreatedAt(LocalDateTime.now());
        comment1.setUser(user);
        comment1.setIssue(issue);

        Comment savedComment=commentRepository.save(comment1) ;

        issue.getComments().add(savedComment) ;

        issueRepository.save(issue) ;

        return savedComment;
    }

    @Override
    public void deleteComment(long commentId, long userId) throws Exception {

        Optional<Comment> optionalComment=commentRepository.findById(commentId) ;
        Optional<User> optionalUser=userRepository.findById(userId) ;
        if(optionalUser.isEmpty()){
            throw new Exception("user is not present"+userId);
        }
        if(optionalComment.isEmpty()){
            throw new Exception("issue is not present"+commentId);
        }

        User user=optionalUser.get() ;
        Comment comment=optionalComment.get() ;

        if(comment.getUser().equals(user)){
            commentRepository.delete(comment);
        }else{
            throw new Exception("user does not have permission to delete this comment") ;
        }

    }

    @Override
    public List<Comment> findCommentByIssueId(long issueId) {

        return commentRepository.findByIssueId(issueId);
    }
}

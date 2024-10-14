package com.shobhit.projectmanagementsystem.controller;

import com.shobhit.projectmanagementsystem.model.Comment;
import com.shobhit.projectmanagementsystem.model.Issue;
import com.shobhit.projectmanagementsystem.model.User;
import com.shobhit.projectmanagementsystem.request.CreateCommentRequest;
import com.shobhit.projectmanagementsystem.response.MessageResponse;
import com.shobhit.projectmanagementsystem.service.CommentService;
import com.shobhit.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService ;

    @Autowired
    private UserService userService ;

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestBody CreateCommentRequest createCommentRequest,
            @RequestHeader("Authorization") String jwt
            )throws Exception{
        User user=userService.findUserProfileByJwt(jwt) ;
        Comment comment=commentService.createComment(createCommentRequest.getIssueId(), user.getId(), createCommentRequest.getContent()) ;
        return new ResponseEntity<>(comment, HttpStatus.CREATED) ;
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(
            @PathVariable long commentId,
            @RequestHeader("Authorization") String jwt
    )throws Exception{
        User user=userService.findUserProfileByJwt(jwt) ;
        commentService.deleteComment(commentId, user.getId());
        return new ResponseEntity<>(new MessageResponse("comment deleted successfully"),HttpStatus.OK) ;
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>>  getCommentsByIssueId(@PathVariable long issueId){

        List<Comment> comments=commentService.findCommentByIssueId(issueId) ;
        return new ResponseEntity<>(comments,HttpStatus.OK) ;

    }
}

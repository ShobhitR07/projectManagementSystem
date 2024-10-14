package com.shobhit.projectmanagementsystem.service;

import com.shobhit.projectmanagementsystem.model.Comment;

import java.util.List;
import java.util.concurrent.CompletionException;

public interface CommentService {
    Comment createComment(Long issueId,long userId ,String comment) throws Exception;
    void deleteComment(long commentId,long userId) throws Exception;

    List<Comment> findCommentByIssueId(long issueId) ;
}

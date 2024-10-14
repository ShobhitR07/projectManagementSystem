package com.shobhit.projectmanagementsystem.repository;

import com.shobhit.projectmanagementsystem.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByIssueId(long issueId) ;

}

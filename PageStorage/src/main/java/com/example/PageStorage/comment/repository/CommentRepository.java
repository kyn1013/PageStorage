package com.example.PageStorage.comment.repository;

import com.example.PageStorage.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMemberMemberSeq(Long memberSeq);
    List<Comment> findByHistoryHistorySeq(Long historySeq);

}

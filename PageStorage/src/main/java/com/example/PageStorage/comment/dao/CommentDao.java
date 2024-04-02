package com.example.PageStorage.comment.dao;

import com.example.PageStorage.comment.repository.CommentRepository;
import com.example.PageStorage.common.exception.DataNotFoundException;
import com.example.PageStorage.entity.Comment;
import com.example.PageStorage.entity.History;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommentDao {

    private final CommentRepository commentRepository;

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    //댓글 번호로 댓글 조회
    public Comment find(Long commentSeq) {
        Comment comment = commentRepository.findById(commentSeq).orElseThrow(() -> new DataNotFoundException("찾을 수 없습니다."));
        return comment;
    }

    //회원 번호로 회원이 쓴 댓글 조회
    public List<Comment> findByMemberSeq(Long memberSeq) {
        List<Comment> comments = commentRepository.findByMemberMemberSeq(memberSeq);
        return comments;
    }

    //게시글 번호로 해당 게시글 번호 조회
    public List<Comment> findByHistorySeq(Long historySeq) {
        List<Comment> comments = commentRepository.findByHistoryHistorySeq(historySeq);
        return comments;
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    //댓글 번호로 삭제
    public void delete(Long commentSeq) {
        Comment comment = commentRepository.findById(commentSeq).orElseThrow(() -> new DataNotFoundException("찾을 수 없습니다."));
        commentRepository.delete(comment);
    }


}

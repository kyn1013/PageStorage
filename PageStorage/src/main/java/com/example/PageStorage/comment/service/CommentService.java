package com.example.PageStorage.comment.service;

import com.example.PageStorage.comment.dao.CommentDao;
import com.example.PageStorage.comment.dto.CommentRequestDto;
import com.example.PageStorage.comment.repository.CommentRepository;
import com.example.PageStorage.entity.Comment;
import com.example.PageStorage.entity.History;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.history.dao.HistoryDao;
import com.example.PageStorage.history.dto.HistoryRequestDto;
import com.example.PageStorage.member.dao.MemberDao;
import com.example.PageStorage.member.dto.MemberSaveRequestDto;
import com.example.PageStorage.security.login.dao.LoginDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentService {

    private final CommentDao commentDao;
    private final LoginDao loginDao;
    private final HistoryDao historyDao;

    public Comment saveComment(Long id, CommentRequestDto commentRequestDto) {
        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .build();

        Member member = loginDao.findByUserLoginId(commentRequestDto.getUserLoginId());
        comment.addMember(member);

        History history = historyDao.find(id);
        comment.addHistory(history);

        return commentDao.save(comment);
    }

    public Comment find(Long commentSeq) {
        return commentDao.find(commentSeq);
    }

    public List<Comment> findByHistory(Long historySeq) {
        return commentDao.findByHistorySeq(historySeq);
    }


    public Comment update(CommentRequestDto commentRequestDto) {
        Comment comment = commentDao.find(commentRequestDto.getHistorySeq());
        comment.changeInfo(commentRequestDto);
        return comment;
    }

    public void delete(Long commentSeq) {
        commentDao.delete(commentSeq);
    }


}

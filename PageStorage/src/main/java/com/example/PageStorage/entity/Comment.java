package com.example.PageStorage.entity;

import com.example.PageStorage.comment.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_seq")
    private Long commentSeq;

    @Column(columnDefinition = "TEXT", name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_seq")
    private History history;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_seq")
    private Member member;

    @Builder
    public Comment(String content) {
        this.content = content;
    }

    public void addMember(Member member) {
        this.member = member;
    }

    public void addHistory(History history) {
        this.history = history;
    }

    public void changeInfo (CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }


}

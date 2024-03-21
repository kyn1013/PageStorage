package com.example.PageStorage.gpt2;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatGptMessage {
    private String role; //역할?

    private String content; //우리가 보낼 질문 메시지 내용

    @Builder
    public ChatGptMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }
}

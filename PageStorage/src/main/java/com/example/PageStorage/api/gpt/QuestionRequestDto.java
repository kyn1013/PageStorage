package com.example.PageStorage.api.gpt;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor

//Front단에서 요청하는 DTO
public class QuestionRequestDto implements Serializable {
    private String question;
}


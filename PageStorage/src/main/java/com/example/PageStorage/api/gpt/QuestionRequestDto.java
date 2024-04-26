package com.example.PageStorage.api.gpt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor

//Front단에서 요청하는 DTO
public class QuestionRequestDto implements Serializable {
    private String question;
}


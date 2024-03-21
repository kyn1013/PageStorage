package com.example.PageStorage.gpt2;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClientResponse {

//    private List<ChatGptResponse.Choice> choices;

    private List<String> contents;

//    private ChatGptMessage message;

    @Builder
    public ClientResponse(List<String> contents){
        this.contents = contents;
    }
}

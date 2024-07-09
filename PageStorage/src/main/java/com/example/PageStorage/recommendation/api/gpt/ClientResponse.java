package com.example.PageStorage.recommendation.api.gpt;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClientResponse {

    private List<String> contents;

    @Builder
    public ClientResponse(List<String> contents){
        this.contents = contents;
    }
}

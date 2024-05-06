package com.example.PageStorage.api.gpt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatGptService {
    private final RestTemplate restTemplate;

    @Value("${api-key.chat-gpt}")
    private String apiKey;

    public HttpEntity<ChatGptRequest> buildHttpEntity(ChatGptRequest chatGptRequest){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        httpHeaders.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + apiKey);
        return new HttpEntity<>(chatGptRequest, httpHeaders);
    }

    public ChatGptResponse getResponse(HttpEntity<ChatGptRequest> chatGptRequestHttpEntity){

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60000);
        //답변이 길어질 경우 TimeOut Error가 발생하므로 1분정도 설정
        requestFactory.setReadTimeout(60 * 1000);   //  1min = 60 sec * 1,000ms
        restTemplate.setRequestFactory(requestFactory);

        ResponseEntity<ChatGptResponse> responseEntity = restTemplate.postForEntity(
                ChatGptConfig.CHAT_URL,
                chatGptRequestHttpEntity,
                ChatGptResponse.class);

        return responseEntity.getBody();
    }
    public ChatGptResponse askQuestion(QuestionRequestDto questionRequest){
        List<ChatGptMessage> messages = new ArrayList<>();
        messages.add(ChatGptMessage.builder()
                .role(ChatGptConfig.ROLE)
                .content(questionRequest.getQuestion() + "책 추천해서 [ \"도서명\" ] 형식으로 리스트로 알려줘")
                .build());
        return this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequest(
                                ChatGptConfig.CHAT_MODEL,
                                ChatGptConfig.MAX_TOKEN,
                                ChatGptConfig.TEMPERATURE,
                                ChatGptConfig.STREAM,
                                messages
                                //ChatGptConfig.TOP_P
                        )
                )
        );

    }

    public ChatGptResponse askKeywordQuestion(QuestionRequestDto questionRequest){
        List<ChatGptMessage> messages = new ArrayList<>();
        messages.add(ChatGptMessage.builder()
                .role(ChatGptConfig.ROLE)
                .content(questionRequest.getQuestion() + "이 키워드를 기반으로 책 추천해서 [ \"도서명\" ] 형식으로 리스트로 알려줘 ")
                .build());
        return this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequest(
                                ChatGptConfig.CHAT_MODEL,
                                ChatGptConfig.MAX_TOKEN,
                                ChatGptConfig.TEMPERATURE,
                                ChatGptConfig.STREAM,
                                messages
                                //ChatGptConfig.TOP_P
                        )
                )
        );

    }
}


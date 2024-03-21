//package com.example.PageStorage.gpt.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.web.client.RestTemplate;
//
///**
// * ChatGPT에서 사용하는 환경 구성
// *
// * @author : lee
// * @fileName : RestTemplate
// * @since : 12/29/23
// */
////동기식 HTTPS 통신을 위한 클래스를 관리하는 디렉터리
//@Configuration
//public class ChatGPTConfig {
//    public static final String CHAT_MODEL = "gpt-3.5-turbo";
//    public static final String ROLE = "user";
//    public static final String CHAT_URL = "https://api.openai.com/v1/chat/completions";
//    //
//
//    @Value("${openai.secret-key}")
//    private String secretKey;
//
//    @Bean
//    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//        return restTemplate;
//    }
//
//
//    @Bean //헤더에 키랑 미디어 타입을 담아서 보냄
//    public HttpHeaders httpHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(secretKey);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        return headers;
//    }
//}

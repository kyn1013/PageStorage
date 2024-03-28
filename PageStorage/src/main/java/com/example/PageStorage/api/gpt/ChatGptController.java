package com.example.PageStorage.api.gpt;

import com.example.PageStorage.api.naverbook.BookApiClient;
import com.example.PageStorage.api.naverbook.BooksResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@RequestMapping("/chat-gpt")
@RestController
public class ChatGptController {

    private final ChatGptService chatGptService;
    private final BookApiClient bookApiClient;

    @PostMapping("/question")
    public List<BooksResponseDto> sendQuestion(
            Locale locale,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody QuestionRequestDto questionRequest) {

        ChatGptResponse chatGptResponse = null;
//
        chatGptResponse = chatGptService.askQuestion(questionRequest);
//
//        ClientResponse clientResponse = ClientResponse.builder().choices(chatGptResponse.getChoices()).build();

        // ChatGptResponse 객체에서 choices를 가져옴
        List<ChatGptResponse.Choice> choices = chatGptResponse.getChoices();

        // 가져온 choices를 순회하면서 content를 추출하여 리스트에 담기
        List<String> contents = new ArrayList<>();
        for (ChatGptResponse.Choice choice : choices) {
            String content = choice.getMessage().getContent();
            contents.add(content);
        }

        // 추출한 content를 사용하여 ClientResponse 객체를 생성
        ClientResponse clientResponse = ClientResponse.builder().contents(contents).build();


        //return 부분은 자유롭게 수정하시면됩니다. ex)return chatGptResponse;
//        return clientResponse;
//        return contents;

        String content = chatGptResponse.getChoices().get(0).getMessage().getContent();


        List<String> bookTitles = new ArrayList<>();
        // 정규 표현식 패턴 설정
        String patternString = "\"(.*?)\"";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(content);

        // 매칭된 각 책 제목을 리스트에 추가
        while (matcher.find()) {
            bookTitles.add(matcher.group(1));
        }


        List<BooksResponseDto> booksResponseDtos = new ArrayList<>();
        for (String bookTitle : bookTitles) {
            booksResponseDtos.add(bookApiClient.requestBook(bookTitle));
        }
        return booksResponseDtos;
    }





}

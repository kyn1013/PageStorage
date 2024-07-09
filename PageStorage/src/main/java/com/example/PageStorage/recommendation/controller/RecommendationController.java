package com.example.PageStorage.recommendation.controller;

import com.example.PageStorage.recommendation.api.gpt.ChatGptResponse;
import com.example.PageStorage.recommendation.api.gpt.ChatGptService;
import com.example.PageStorage.recommendation.api.gpt.ClientResponse;
import com.example.PageStorage.recommendation.api.gpt.QuestionRequestDto;
import com.example.PageStorage.recommendation.api.naverbook.BookApiClient;
import com.example.PageStorage.recommendation.api.naverbook.BooksResponseDto;
import com.example.PageStorage.recommendation.api.naverbook.ResultResponseDto;
import com.example.PageStorage.recommendation.service.KeywordService;
import com.example.PageStorage.common.exception.recommendation.BookItemsNotFoundException;
import com.example.PageStorage.recommendation.service.RecommendationService;
import com.example.PageStorage.security.login.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@RequestMapping("/books")
@Controller
@Slf4j
public class RecommendationController {

    private final ChatGptService chatGptService;
    private final BookApiClient bookApiClient;
    private final KeywordService keywordService;
    private final RecommendationService recommendationService;

    @GetMapping("/recommendations")
    public String createHistoryForm(Model model) {

        model.addAttribute("questionRequestDto", new QuestionRequestDto());
        return "recommendationForm";
    }

    @PostMapping("/recommendations")
    public String sendQuestion(
            @ModelAttribute("questionRequestDto") QuestionRequestDto questionRequest, Model model) {

        ChatGptResponse chatGptResponse = chatGptService.askQuestion(questionRequest);

        List<ChatGptResponse.Choice> choices = chatGptResponse.getChoices();

        List<String> contents = new ArrayList<>();
        for (ChatGptResponse.Choice choice : choices) {
            String content = choice.getMessage().getContent();
            contents.add(content);
        }

        ClientResponse clientResponse = ClientResponse.builder().contents(contents).build();

        String content = chatGptResponse.getChoices().get(0).getMessage().getContent();

        List<String> bookTitles = new ArrayList<>();
        // 정규 표현식 패턴 설정
        String patternString = "\"(.*?)\"";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(content);

        //매칭된 각 책 제목을 리스트에 추가
        while (matcher.find()) {
            bookTitles.add(matcher.group(1));
        }

        List<BooksResponseDto> booksResponseDtos = new ArrayList<>();
        for (String bookTitle : bookTitles) {
            booksResponseDtos.add(bookApiClient.requestBook(bookTitle));
        }

        List<ResultResponseDto> resultResponseDtos = new ArrayList<>();
        for (BooksResponseDto booksResponseDto : booksResponseDtos){
            BooksResponseDto.Item firstItem = booksResponseDto.getItems()[0];

            ResultResponseDto resultResponseDto = ResultResponseDto.builder()
                    .title(firstItem.getTitle())
                    .author(firstItem.getAuthor())
                    .image(firstItem.getImage())
                    .publisher(firstItem.getPublisher())
                    .description(firstItem.getDescription())
                    .build();

            resultResponseDtos.add(resultResponseDto);
        }

        model.addAttribute("books", resultResponseDtos);
        return "recommendationForm";
    }

    @GetMapping("/keyword")
    public String keyword(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        String mail = userDetails.getMail();
        String userNickName = userDetails.getNickname();

        Set<String> keywordList = keywordService.getKeywordsForMember(mail);
        model.addAttribute("keywords", keywordList);
        model.addAttribute("nickName", userNickName);

        QuestionRequestDto questionRequestDto = new QuestionRequestDto();
        questionRequestDto.setQuestion(keywordList.toString());
        ChatGptResponse chatGptResponse = chatGptService.askKeywordQuestion(questionRequestDto);

        List<ChatGptResponse.Choice> choices = chatGptResponse.getChoices();
        List<String> contents = new ArrayList<>();
        for (ChatGptResponse.Choice choice : choices) {
            String content = choice.getMessage().getContent();
            contents.add(content);
        }

        ClientResponse clientResponse = ClientResponse.builder().contents(contents).build();

        String content = chatGptResponse.getChoices().get(0).getMessage().getContent();

        List<String> bookTitles = new ArrayList<>();
        String patternString = "\"(.*?)\"";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            bookTitles.add(matcher.group(1));
        }

        List<BooksResponseDto> booksResponseDtos = new ArrayList<>();
        for (String bookTitle : bookTitles) {
            booksResponseDtos.add(recommendationService.requestBookWithCacheAndRetry(bookTitle));
        }

        List<ResultResponseDto> resultResponseDtos = new ArrayList<>();
        for (BooksResponseDto booksResponseDto : booksResponseDtos) {
            if (booksResponseDto.getItems() != null && booksResponseDto.getItems().length > 0) {
                BooksResponseDto.Item firstItem = booksResponseDto.getItems()[0];

                ResultResponseDto resultResponseDto = ResultResponseDto.builder()
                        .title(firstItem.getTitle())
                        .author(firstItem.getAuthor())
                        .image(firstItem.getImage())
                        .publisher(firstItem.getPublisher())
                        .description(firstItem.getDescription())
                        .build();

                resultResponseDtos.add(resultResponseDto);
            } else {
                throw new BookItemsNotFoundException("책 응답 데이터가 없습니다.");
            }
        }

        model.addAttribute("books", resultResponseDtos);
        return "keyword_view";
    }

}

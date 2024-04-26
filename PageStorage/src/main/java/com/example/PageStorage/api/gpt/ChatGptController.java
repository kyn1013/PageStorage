package com.example.PageStorage.api.gpt;

import com.example.PageStorage.api.naverbook.BookApiClient;
import com.example.PageStorage.api.naverbook.BooksResponseDto;
import com.example.PageStorage.api.naverbook.ResultResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@RequestMapping("/books")
@Controller
//@RestController
@Slf4j
public class ChatGptController {

    private final ChatGptService chatGptService;
    private final BookApiClient bookApiClient;

    @GetMapping("/recommendations")
    public String createHistoryForm(Model model) {

        model.addAttribute("questionRequestDto", new QuestionRequestDto());
        return "recommendationForm";
    }

    @PostMapping("/recommendations")
    public String sendQuestion(
            @ModelAttribute("questionRequestDto") QuestionRequestDto questionRequest, Model model) {

        ChatGptResponse chatGptResponse = null;
//

        log.info("질문 ={}", questionRequest.getQuestion());
        chatGptResponse = chatGptService.askQuestion(questionRequest);

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

        List<ResultResponseDto> resultResponseDtos = new ArrayList<>();
        for (BooksResponseDto booksResponseDto : booksResponseDtos){
            log.info("답변={}", booksResponseDto.getItems()[0]);
            BooksResponseDto.Item firstItem = booksResponseDto.getItems()[0];

            // ResultResponseDto의 빌더를 사용하여 객체 생성
            ResultResponseDto resultResponseDto = ResultResponseDto.builder()
                    .title(firstItem.getTitle())
                    .author(firstItem.getAuthor())
                    .image(firstItem.getImage())
                    .publisher(firstItem.getPublisher())
                    .description(firstItem.getDescription())
                    .build();

            resultResponseDtos.add(resultResponseDto);
        }

        log.info("여기도 실행 안됨?");
        model.addAttribute("books", resultResponseDtos);  // 책 정보를 모델에 추가
        return "recommendationForm";  // 같은 페이지로 리디렉트하지 않고, 뷰 이름 반환
    }

//    @GetMapping("/recommendations/result")
//    public String showRecommendations(Model model, @ModelAttribute("books") List<BooksResponseDto> booksResponseDtos) {
//        model.addAttribute("books", booksResponseDtos);
//        return "bookRecommendations";
//    }

    @PostMapping("/question")
    public List<ResultResponseDto> sendQuestion(
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

        List<ResultResponseDto> resultResponseDtos = new ArrayList<>();
        for (BooksResponseDto booksResponseDto : booksResponseDtos){
            log.info("답변={}", booksResponseDto.getItems()[0]);
            BooksResponseDto.Item firstItem = booksResponseDto.getItems()[0];

            // ResultResponseDto의 빌더를 사용하여 객체 생성
            ResultResponseDto resultResponseDto = ResultResponseDto.builder()
                    .title(firstItem.getTitle())
                    .author(firstItem.getAuthor())
                    .image(firstItem.getImage())
                    .publisher(firstItem.getPublisher())
                    .description(firstItem.getDescription())
                    .build();

            resultResponseDtos.add(resultResponseDto);
        }

        return resultResponseDtos;
    }





}

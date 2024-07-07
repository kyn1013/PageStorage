package com.example.PageStorage.history.controller;

import com.example.PageStorage.api.naverbook.BookApiClient;
import com.example.PageStorage.api.naverbook.BooksResponseDto;
import com.example.PageStorage.api.naverbook.ResultResponseDto;
import com.example.PageStorage.comment.dto.CommentRequestDto;
import com.example.PageStorage.comment.dto.response.CommentResponseDto;
import com.example.PageStorage.comment.service.CommentService;
import com.example.PageStorage.common.PsResponse;
import com.example.PageStorage.common.code.SuccessCode;
import com.example.PageStorage.common.model.ResBodyModel;
import com.example.PageStorage.entity.Comment;
import com.example.PageStorage.entity.History;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.history.dto.HistoryDeleteDto;
import com.example.PageStorage.history.dto.HistoryRequestDto;
import com.example.PageStorage.history.dto.response.HistoryAllResponseDto;
import com.example.PageStorage.history.dto.response.HistoryDetailResponseDto;
import com.example.PageStorage.history.dto.response.HistoryResponseDto;
import com.example.PageStorage.history.dto.response.RankingResponseDto;
import com.example.PageStorage.history.service.HistoryService;
import com.example.PageStorage.member.service.MemberService;
import com.example.PageStorage.security.login.dto.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Slice;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/histories")
@Slf4j
public class HistoryController {

    private final MemberService memberService;
    private final HistoryService historyService;
    private final CommentService commentService;
    private final BookApiClient bookApiClient;

    /*
    히스토리 랭킹
     */
    @GetMapping("/ranking")
    public String getTopBookNamesLast24Hours(Model model) {
        List<String> bookTitles = historyService.readBookRanking();
        List<BooksResponseDto> booksResponseDtos = new ArrayList<>();
        for (String bookTitle : bookTitles) {
            booksResponseDtos.add(bookApiClient.requestBook(bookTitle));
        }

        List<RankingResponseDto> rankingResponseDtos = new ArrayList<>();
        for (BooksResponseDto booksResponseDto : booksResponseDtos){
            log.info("답변={}", booksResponseDto.getItems()[0]);
            BooksResponseDto.Item firstItem = booksResponseDto.getItems()[0];

            // ResultResponseDto의 빌더를 사용하여 객체 생성
            RankingResponseDto resultResponseDto = RankingResponseDto.builder()
                    .title(firstItem.getTitle())
                    .image(firstItem.getImage())
                    .build();

            rankingResponseDtos.add(resultResponseDto);
        }

        model.addAttribute("books", rankingResponseDtos);  // 책 정보를 모델에 추가
        return "ranking_view";  // 같은 페이지로 리디렉트하지 않고, 뷰 이름 반환
//        return "a";
    }

    @GetMapping("/top3")
    public List<String> getTop3Books() {
        return historyService.getTop3Books();
    }

    /*
    히스토리 수정
     */
    @GetMapping("/update/{id}")
    public String updateHistory(@ModelAttribute("historyForm") HistoryRequestDto historyRequestDto
    ,@PathVariable("id") Long id, Principal principal ){
        History history = historyService.find(id);
        HistoryResponseDto historyResponseDto = HistoryResponseDto.buildDto(history);
        log.info("name={}", principal.getName());
//        if(!history.getMember().getLogin().getUserLoginId().equals(principal.getName())) { //login id?
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//        }
        historyRequestDto.setBookName(history.getBookName());
        historyRequestDto.setHistoryContent(history.getHistoryContent());
        historyRequestDto.setPhrase(history.getPhrase());
        historyRequestDto.setDifficulty(history.getDifficulty());
        historyRequestDto.setBookRecommender(history.getBookRecommender());
        historyRequestDto.setApplicationToLife(history.getApplicationToLife());
        historyRequestDto.setUpdateSeq(history.getHistorySeq());

        // 태그 리스트 가져오기
        Set<String> tags = historyResponseDto.getTagNames();

        // StringBuilder를 사용하여 문자열 생성
        StringBuilder result = new StringBuilder();
        for (String tag : tags) {
            result.append("#").append(tag);
        }

        // 최종 결과 문자열
        String finalTags = result.toString();

        historyRequestDto.setTagNames(finalTags);
        historyRequestDto.setFileName(history.getHistoryImage().getOriginFilename());

        return "history_modify_view";

//        History history = historyService.update(historyRequestDto);
//        HistoryResponseDto historyResponseDto = HistoryResponseDto.buildDto(history);
//        return PsResponse.toResponse(SuccessCode.SUCCES,historyResponseDto);
    }

    @PostMapping("/update/{id}")
    public String updateToHistory(@PathVariable Long id, @ModelAttribute("historyForm") @Valid HistoryRequestDto historyRequestDto,
                                BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        if (bindingResult.hasErrors()) {
            return "history_modify_view";
        }

        historyRequestDto.setUserLoginId( userDetails.getUsername());
//        historyService.saveHistory(historyRequestDto).getHistorySeq();
        History savedHistory = historyService.update(historyRequestDto, id);
        Long historySeq = savedHistory.getHistorySeq();

        // 여기에서 sendText 메소드 호출
        sendTextUpdate(historyRequestDto.getHistoryContent(), historySeq); // 실제 전송할 텍스트는 적절히 조정 필요

        return "redirect:/histories/all";
    }



    /*
    히스토리 추가
     */
    @GetMapping("/new")
    public String createHistoryForm(Model model) {

        model.addAttribute("historyForm", new HistoryRequestDto());
        return "createHistoryForm";
    }

    @PostMapping("/new")
    public String createHistory(@ModelAttribute("historyForm") @Valid HistoryRequestDto historyRequestDto,
                                BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        if (bindingResult.hasErrors()) {
            return "createHistoryForm";
        }

        historyRequestDto.setUserLoginId(userDetails.getUsername());
//        historyService.saveHistory(historyRequestDto).getHistorySeq();
        History savedHistory = historyService.saveHistory(historyRequestDto);
        Long historySeq = savedHistory.getHistorySeq();

        // 여기에서 sendText 메소드 호출
        sendText(historyRequestDto.getHistoryContent(), historySeq); // 실제 전송할 텍스트는 적절히 조정 필요

        return "redirect:/histories/all";
    }

    //장고 서버로 보냄
    @PostMapping("/sendText")
    public ResponseEntity<String> sendText(String text, Long historySeq) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8000/analyze"; // 장고 서버 URL

        // HttpHeaders 객체 생성 및 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // JSONObject를 사용하여 JSON 형식의 데이터 생성
        JSONObject json = new JSONObject();
        json.put("text", text); // 'text'라는 키에 text 변수의 값을 할당

        // JSON 문자열로 변환
        String jsonString = json.toString();

        // HttpEntity에 JSON 문자열과 헤더를 포함
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);

        // RestTemplate을 사용하여 POST 요청 전송 및 응답 수신
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        String keywordData = response.getBody();

        JSONObject jsonObject = new JSONObject(keywordData);
        log.info("응답내용 = {}", keywordData);
        // Set 집합 생성
        Set<String> keysSet = new HashSet<>();

        // JSON 객체의 키 추출 및 Set에 추가
        jsonObject.keys().forEachRemaining(keysSet::add);

        // 추출된 키 출력
        System.out.println("Extracted Keys: " + keysSet);

        Long seq = historySeq;

        historyService.saveKeyword(keysSet, seq);
        return response;
    }

    @PostMapping("/sendTextUpdate")
    public ResponseEntity<String> sendTextUpdate(String text, Long historySeq) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8000/analyze"; // 장고 서버 URL

        // HttpHeaders 객체 생성 및 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // JSONObject를 사용하여 JSON 형식의 데이터 생성
        JSONObject json = new JSONObject();
        json.put("text", text); // 'text'라는 키에 text 변수의 값을 할당

        // JSON 문자열로 변환
        String jsonString = json.toString();

        // HttpEntity에 JSON 문자열과 헤더를 포함
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);

        // RestTemplate을 사용하여 POST 요청 전송 및 응답 수신
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        String keywordData = response.getBody();

        JSONObject jsonObject = new JSONObject(keywordData);
        log.info("응답내용 = {}", keywordData);
        // Set 집합 생성
        Set<String> keysSet = new HashSet<>();

        // JSON 객체의 키 추출 및 Set에 추가
        jsonObject.keys().forEachRemaining(keysSet::add);

        // 추출된 키 출력
        System.out.println("Extracted Keys: " + keysSet);

        Long seq = historySeq;

        historyService.updateKeyword(keysSet, seq);
        return response;
    }


    /*
    히스토리 조회
     */
//    @GetMapping("/read/{historySeq}")
//    public ResponseEntity<ResBodyModel> findBySeq(@PathVariable Long historySeq) {
//        History history = historyService.find(historySeq);
//        HistoryResponseDto historyResponseDto = HistoryResponseDto.buildDto(history);
//        return PsResponse.toResponse(SuccessCode.SUCCES,historyResponseDto);
//    }

    @GetMapping("/myPage")
    public String myPage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        String id = userDetails.getUsername();
        String mail = userDetails.getMail();
        String userNickName = userDetails.getNickname();

        List<History> histories = historyService.findByMail(mail);
        List<HistoryResponseDto> historyResponseDtos = HistoryResponseDto.buildDtoList(histories);

        Member member = memberService.find(id);
        model.addAttribute("history", historyResponseDtos);
        model.addAttribute("nickName", member.getNickName());
        model.addAttribute("member", member);

        return "my_history_view";
    }

    @GetMapping("/bookName/{bookName}")
    public ResponseEntity<ResBodyModel> findByBookName(@PathVariable String bookName) {
        List<History> histories = historyService.findHistoriesByBookName(bookName);
        List<HistoryResponseDto> historyResponseDtos = HistoryResponseDto.buildDtoList(histories);
        return PsResponse.toResponse(SuccessCode.SUCCES,historyResponseDtos);
    }

//    @GetMapping("/all")
//    public ResponseEntity<ResBodyModel> findAll () {
//        List<History> histories = historyService.findAll();
//        List<HistoryResponseDto> historyResponseDtos = HistoryResponseDto.buildDtoList(histories);
//        return PsResponse.toResponse(SuccessCode.SUCCES,historyResponseDtos);
//    }

//    @GetMapping("/all")
//    public String findAll (@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
//        List<History> histories = historyService.findAll();
//        List<HistoryResponseDto> historyResponseDtos = HistoryResponseDto.buildDtoList(histories);
//
//        model.addAttribute("history", historyResponseDtos);
//
//        String id = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
//        GrantedAuthority auth = iter.next();
//        String role = auth.getAuthority();
//
//        model.addAttribute("id", id);
//        model.addAttribute("role", role);
//
//        String nickname = userDetails.getNickname();
//        model.addAttribute("nickName", nickname);
//
//        Member member = memberService.find(id);
//        model.addAttribute("member", member);
//
//        return "history_view"; // Thymeleaf 템플릿 파일 이름 반환
//    }

//    @GetMapping("/a")
//    public String findA(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
//        // 초기 페이지는 0번째 페이지로 설정하고, 크기를 10으로 설정
//        Page<History> historyPage = historyService.findAll(0, 10);
//        List<HistoryResponseDto> historyResponseDtos = new ArrayList<>();
//
//        for (History history : historyPage.getContent()) {
//            historyResponseDtos.add(HistoryResponseDto.buildDto(history));
//        }
//
//        model.addAttribute("history", historyResponseDtos);
//        model.addAttribute("currentPage", historyPage.getNumber());
//        model.addAttribute("totalPages", historyPage.getTotalPages());
//
//        String id = SecurityContextHolder.getContext().getAuthentication().getName();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
//        GrantedAuthority auth = iter.next();
//        String role = auth.getAuthority();
//
//        model.addAttribute("id", id);
//        model.addAttribute("role", role);
//
//        String nickname = userDetails.getNickname();
//        model.addAttribute("nickName", nickname);
//
//        Member member = memberService.find(id);
//        model.addAttribute("member", member);
//
//        return "a"; // Thymeleaf 템플릿 파일 이름 반환
//    }
//
//    @GetMapping("/b")
//    @ResponseBody
//    public Page<HistoryResponseDto> getHistories(@RequestParam int page, @RequestParam int size) {
//        Page<History> historyPage = historyService.findAll(page, size);
//
////        List<History> histories = historyService.findAll();
////        List<HistoryResponseDto> historyResponseDtos = HistoryResponseDto.buildDtoList(histories);
//
//        List<HistoryResponseDto> historyResponseDtos = new ArrayList<>();
//
//        for (History history : historyPage.getContent()) {
//            historyResponseDtos.add(HistoryResponseDto.buildDto(history));
//        }
//
//        return new PageImpl<>(historyResponseDtos, historyPage.getPageable(), historyPage.getTotalElements());
//    }
//
//    @GetMapping("/c")
//    public String findAllc(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
//        List<History> histories = historyService.findAlll(0L, 10);
//        List<HistoryResponseDto> historyResponseDtos = HistoryResponseDto.buildDtoList(histories);
//
//
//        model.addAttribute("history", historyResponseDtos);
//
//        String id = SecurityContextHolder.getContext().getAuthentication().getName();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
//        GrantedAuthority auth = iter.next();
//        String role = auth.getAuthority();
//
//        model.addAttribute("id", id);
//        model.addAttribute("role", role);
//
//        String nickname = userDetails.getNickname();
//        model.addAttribute("nickName", nickname);
//
//        Member member = memberService.find(id);
//        model.addAttribute("member", member);
//
//        return "c"; // Thymeleaf 템플릿 파일 이름 반환
//    }
//
//    @GetMapping("/d")
//    @ResponseBody
//    public List<HistoryResponseDto> getsHistories(@RequestParam Long cursor, @RequestParam int size) {
//        List<History> histories = historyService.findAlll(cursor, size);
//
//        List<HistoryResponseDto> historyResponseDtos = HistoryResponseDto.buildDtoList(histories);
//
//
//        return historyResponseDtos;
//    }
//
//    @GetMapping("/pp")
//    public String findAgll(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
//        List<History> histories = historyService.findAlll(0L, 10); // 초기 로드는 cursor를 0으로 설정
//        List<HistoryResponseDto> historyResponseDtos = new ArrayList<>();
//
//        for (History history : histories) {
//            historyResponseDtos.add(HistoryResponseDto.buildDto(history));
//        }
//
//        model.addAttribute("history", historyResponseDtos);
//
//        String id = SecurityContextHolder.getContext().getAuthentication().getName();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
//        GrantedAuthority auth = iter.next();
//        String role = auth.getAuthority();
//
//        model.addAttribute("id", id);
//        model.addAttribute("role", role);
//
//        String nickname = userDetails.getNickname();
//        model.addAttribute("nickName", nickname);
//
//        Member member = memberService.find(id);
//        model.addAttribute("member", member);
//
//        return "d"; // Thymeleaf 템플릿 파일 이름 반환
//    }
//
//    @GetMapping("/histories")
//    @ResponseBody
//    public List<HistoryResponseDto> getHistoㅑries(@RequestParam Long cursor, @RequestParam int size) {
//        List<History> histories = historyService.findByCursor(cursor, size);
//        List<HistoryResponseDto> historyResponseDtos = new ArrayList<>();
//
//        for (History history : histories) {
//            historyResponseDtos.add(HistoryResponseDto.buildDto(history));
//            System.out.println(history.getBookName());
//        }
//
//
//        return historyResponseDtos;
//    }

    @GetMapping("/all")
    public String findAll(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        HistoryAllResponseDto historyResponseDto = historyService.findAllByIdCursorBased(0L, 10); // 초기 로드는 cursor를 0으로 설정

        model.addAttribute("history", historyResponseDto.getHistoryResponseDtos());

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        model.addAttribute("id", id);
        model.addAttribute("role", role);

        String nickname = userDetails.getNickname();
        model.addAttribute("nickName", nickname);

        Member member = memberService.find(id);
        model.addAttribute("member", member);

        return "d"; // Thymeleaf 템플릿 파일 이름 반환
    }

    @GetMapping("/hhh")
    @ResponseBody
    public HistoryAllResponseDto getHistories(@RequestParam Long cursor, @RequestParam int size) {
        HistoryAllResponseDto historyResponseDt = historyService.findAllByIdCursorBased(cursor, size);
        return historyResponseDt;
    }


//    @GetMapping("/test")
//    public String find (@AuthenticationPrincipal CustomUserDetails userDetails, Model model, @RequestParam(value = "cursor") Long cursor) {
//        List<History> histories = historyService.test(cursor);
//        List<HistoryResponseDto> historyResponseDtos = HistoryResponseDto.buildDtoList(histories);
//
//        model.addAttribute("history", historyResponseDtos);
//
//        String id = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
//        GrantedAuthority auth = iter.next();
//        String role = auth.getAuthority();
//
//        model.addAttribute("id", id);
//        model.addAttribute("role", role);
//
//        String nickname = userDetails.getNickname();
//        model.addAttribute("nickName", nickname);
//
//        Member member = memberService.find(id);
//        model.addAttribute("member", member);
//
//        return "history_view"; // Thymeleaf 템플릿 파일 이름 반환
//    }



    @GetMapping(value = "/read/{historySeq}")
    public String findBySeq(Model model, @PathVariable Long historySeq, CommentRequestDto commentRequestDto,@AuthenticationPrincipal CustomUserDetails userDetails) {
        String loginId = userDetails.getUserLoginId();
        Member member = memberService.find(loginId);
//        String userNickName = "mm";

        History history = historyService.find(historySeq);
        List<Comment> comments = commentService.findByHistory(historySeq);
        List<CommentResponseDto> commentResponseDtos = CommentResponseDto.buildDtoList(comments);
        HistoryDetailResponseDto historyDetailResponseDto = HistoryDetailResponseDto.buildDto(history);

        model.addAttribute("loginMember", member);
        model.addAttribute("history", historyDetailResponseDto);
        model.addAttribute("realComment", commentResponseDtos);
        model.addAttribute("commentForm", commentRequestDto);
        return "history_detail_view";
    }



    /*
    히스토리 삭제
     */
    @GetMapping("/delete/{historySeq}")
    public String deleteBySeq(@PathVariable Long historySeq) {
        historyService.delete(historySeq);
        return "redirect:/histories/all";
    }

    @DeleteMapping()
    public ResponseEntity<ResBodyModel> delete(@RequestBody HistoryDeleteDto historyDeleteDto) {
        historyService.delete(historyDeleteDto);
        return PsResponse.toResponse(SuccessCode.SUCCES);
    }

    /*
    이미지 삭제
     */
    @DeleteMapping("/image/{imageSeq}")
    public ResponseEntity<ResBodyModel> deleteByImageSeq(@PathVariable Long imageSeq) {
        historyService.deleteImage(imageSeq);
        return PsResponse.toResponse(SuccessCode.SUCCES);
    }

    @DeleteMapping("/delete/keyword")
    public ResponseEntity<ResBodyModel> deleteKeyword() {
        historyService.deleteKeyword();
        return PsResponse.toResponse(SuccessCode.SUCCES);
    }
}

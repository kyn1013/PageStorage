package com.example.PageStorage.history.service;

import com.example.PageStorage.entity.*;
import com.example.PageStorage.genre.dto.GenreUpdateRequestDto;
import com.example.PageStorage.history.dao.HistoryDao;
import com.example.PageStorage.history.dao.HistoryImageDao;
import com.example.PageStorage.history.dao.HistoryKeywordDao;
import com.example.PageStorage.history.dao.KeywordDao;
import com.example.PageStorage.history.dto.HistoryDeleteDto;
import com.example.PageStorage.history.dto.HistoryRequestDto;
import com.example.PageStorage.historytag.dao.HistoryTagDao;
import com.example.PageStorage.member.dao.MemberDao;
import com.example.PageStorage.security.login.dao.LoginDao;
import com.example.PageStorage.tag.dao.TagDao;
import com.example.PageStorage.tag.dto.TagRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class HistoryService {

    private final HistoryDao historyDao;
    private final HistoryImageDao historyImageDao;
    private final HistoryTagDao historyTagDao;
    private final MemberDao memberDao;
    private final LoginDao loginDao;
    private final TagDao tagDao;

    private final KeywordDao keywordDao;
    private final HistoryKeywordDao historyKeywordDao;

    private final String FOLDER_PATH="/Users/gim-yena/Desktop/history/";



    public History update(HistoryRequestDto historyRequestDto, Long historySeq) throws IOException {
        History history = find(historySeq);
        history.changeInfo(historyRequestDto);

        /*
        태그 지우기
         */
        historyTagDao.deleteHistorySeq(history.getHistorySeq());
        // 기존 태그 관계 모두 삭제 (단순 예제, 실제로는 보다 세심한 접근 방식이 필요할 수 있음)
        history.getHistoryTags().clear();
//        historyTagDao.deleteHistorySeq(history.getHistorySeq());

        /*
        태그 다시 만들기
         */
        String tagString = historyRequestDto.getTagNames();
        Set<String> tagsSet = Arrays.stream(tagString.split("#"))
                .filter(tag -> !tag.isEmpty()) // 빈 태그 제거
                .collect(Collectors.toSet());
        for (String tag : tagsSet) {
            Tag savedTag = tagDao.findOrCreate(tag);

            HistoryTag historyTag = HistoryTag.builder()
                    .history(history)
                    .tag(savedTag)
                    .build();

            history.getHistoryTags().add(historyTag);
        }

        //저장된 이미지 삭제
        Files.deleteIfExists(Paths.get(history.getHistoryImage().getFilePath()));
//        historyDao.delete(historySeq);


        String originalFilename = historyRequestDto.getImageFile().getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);
        String filePath = createPath(storeFilename);

        HistoryImage historyImagee = historyImageDao.findByHistorySeq(historySeq);
        historyImagee.changeInfo(originalFilename, storeFilename, historyRequestDto.getImageFile().getContentType(), filePath);

//        HistoryImage historyImage = HistoryImage.builder()
//                .originFilename(originalFilename)
//                .storeFilename(storeFilename)
//                .type(historyRequestDto.getImageFile().getContentType())
//                .filePath(filePath)
//                .history(history)
//                .build();

        // 새 이미지 정보 추가 및 파일 저장
        history.addHistoryImage(historyImagee);
        historyImageDao.save(historyImagee);
        historyRequestDto.getImageFile().transferTo(new File(filePath));

        return history;
    }

    public History saveHistory(HistoryRequestDto historyRequestDto) throws IOException {
        History history = History.builder()
                .bookName(historyRequestDto.getBookName())
                .historyContent(historyRequestDto.getHistoryContent())
                .phrase(historyRequestDto.getPhrase())
                .difficulty(historyRequestDto.getDifficulty())
                .applicationToLife(historyRequestDto.getApplicationToLife())
                .bookRecommender(historyRequestDto.getBookRecommender())
                .build();
        Member member = loginDao.findByUserLoginId(historyRequestDto.getUserLoginId());
        history.addMember(member);

        // History 객체 먼저 저장
        history = historyDao.save(history);

        String tagString = historyRequestDto.getTagNames();
        Set<String> tagsSet = Arrays.stream(tagString.split("#"))
                .filter(tag -> !tag.isEmpty()) // 빈 태그 제거
                .collect(Collectors.toSet());
        for (String tag : tagsSet) {
            Tag savedTag = tagDao.findOrCreate(tag);

//            HistoryTag historyTag = HistoryTag.builder()
//                    .history(history)
//                    .tag(savedTag)
//                    .build();

            HistoryTag historyTag = HistoryTag.builder()
                    .history(history)
                    .tag(savedTag)
                    .build();

            historyTagDao.save(historyTag);

            history.getHistoryTags().add(historyTag);
        }


        //사진 저장 로직
        String originalFilename = historyRequestDto.getImageFile().getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);
        String filePath = createPath(storeFilename);

        HistoryImage historyImage = HistoryImage.builder()
                .originFilename(originalFilename)
                .storeFilename(storeFilename)
                .type(historyRequestDto.getImageFile().getContentType())
                .filePath(filePath)
                .history(history)
                .build();

        //히스토리에 이미지 정보 추가
        history.addHistoryImage(historyImage);
        historyImageDao.save(historyImage);

        historyRequestDto.getImageFile().transferTo(new File(filePath));

        return history;
    }

    public void saveKeyword(Set<String> keywordSet, Long historySeq) {
        History history = find(historySeq);

        for (String keyword : keywordSet) {
//            Keyword savedKeyword = Keyword.builder()
//                    .keyword(keyword)
//                    .build();
//
//            keywordDao.save(savedKeyword);
            Keyword savedKeyword = keywordDao.findOrCreate(keyword);

            HistoryKeyword historyKeyword = HistoryKeyword.builder()
                    .history(history)
                    .keyword(savedKeyword)
                    .build();

            historyKeywordDao.save(historyKeyword);

            history.getHistoryKeywords().add(historyKeyword);
        }

    }

    public void updateKeyword(Set<String> keywordSet, Long historySeq) {
        History history = find(historySeq);

        /*
        태그 지우기
         */
        historyKeywordDao.deleteHistorySeq(history.getHistorySeq());
        // 기존 태그 관계 모두 삭제 (단순 예제, 실제로는 보다 세심한 접근 방식이 필요할 수 있음)
        history.getHistoryKeywords().clear();

        for (String keyword : keywordSet) {
            Keyword savedKeyword = Keyword.builder()
                    .keyword(keyword)
                    .build();

            keywordDao.save(savedKeyword);

            HistoryKeyword historyKeyword = HistoryKeyword.builder()
                    .history(history)
                    .keyword(savedKeyword)
                    .build();

            historyKeywordDao.save(historyKeyword);

            history.getHistoryKeywords().add(historyKeyword);
        }

    }

    public String createPath(String storeFilename) {
        return FOLDER_PATH+storeFilename;
    }

    private String createStoreFilename(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        String storeFilename = uuid + ext;

        return storeFilename;
    }

    private String extractExt(String originalFilename) {
        int idx = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(idx);
        return ext;
    }

    public History find(Long historySeq) {
        return historyDao.find(historySeq);
    }

    public List<History> findHistoriesByMemberName(String memberName) {
        Member member = memberDao.findName(memberName);
        return historyDao.findByMember(member);
    }

    public List<History> findHistoriesByMemberNickName(String nickName) {
        Member member = memberDao.findNickName(nickName);
        return historyDao.findByMember(member);
    }

    public List<History> findHistoriesByBookName(String bookName) {
        return historyDao.find(bookName);
    }

    public List<History> findByMail(String mail) {
        Member member = memberDao.findMail(mail);
        return historyDao.findByMember(member);
    }


    public List<History> findAll() {
        return historyDao.findAllByCreatedDate();
    }

//    public History update(HistoryRequestDto historyRequestDto) {
//        History history = historyDao.findByMemberNameAndBookName(
//                historyRequestDto.getUserLoginId(), historyRequestDto.getBookName());
//        history.changeInfo(historyRequestDto);
//
//
//        historyTagDao.deleteHistorySeq(history.getHistorySeq());
//        // 기존 태그 관계 모두 삭제 (단순 예제, 실제로는 보다 세심한 접근 방식이 필요할 수 있음)
//        history.getHistoryTags().clear();
////        historyTagDao.deleteHistorySeq(history.getHistorySeq());
//
//        String tagString = historyRequestDto.getTagNames();
//        Set<String> tagsSet = Arrays.stream(tagString.split("#"))
//                .filter(tag -> !tag.isEmpty()) // 빈 태그 제거
//                .collect(Collectors.toSet());
//        for (String tag : tagsSet) {
//            Tag savedTag = tagDao.findOrCreate(tag);
//
//            HistoryTag historyTag = HistoryTag.builder()
//                    .history(history)
//                    .tag(savedTag)
//                    .build();
//
//            history.getHistoryTags().add(historyTag);
//        }
//
//        return history;
//    }



    public void delete(HistoryDeleteDto historyDeleteDto) {
        History history = historyDao.findByMemberNameAndBookName(
                historyDeleteDto.getMemberName(), historyDeleteDto.getBookName());
        historyDao.delete(history);
    }

    public void delete(Long historySeq) {
        historyDao.delete(historySeq);
    }

    public void deleteImage(Long imageSeq) {
        historyImageDao.delete(imageSeq);
    }

    public void deleteKeyword() {
        keywordDao.deleteAll();
    }

    public List<String> readBookRanking() {
        // 시간 범위 설정
        LocalDateTime startDate24Hours = LocalDateTime.now().minusHours(24);
        LocalDateTime startDate168Hours = LocalDateTime.now().minusHours(168);
        LocalDateTime startDate720Hours = LocalDateTime.now().minusHours(720);

        // 각 시간 범위별로 책 제목 조회
        List<String> bookRankList720Hours = historyDao.findTopBookNamesLast24Hours(startDate720Hours);
        List<String> bookRankList168Hours = historyDao.findTopBookNamesLast24Hours(startDate168Hours);
        List<String> bookRankList24Hours = historyDao.findTopBookNamesLast24Hours(startDate24Hours);

        if (bookRankList24Hours.isEmpty()) {
            bookRankList24Hours = bookRankList168Hours;
        }

        // 하나의 리스트에 모든 결과 합치기
        List<String> bookRankList = new ArrayList<>();
        bookRankList.addAll(bookRankList24Hours);
        bookRankList.addAll(bookRankList168Hours);
        bookRankList.addAll(bookRankList720Hours);

        System.out.println(bookRankList);
        return bookRankList;
    }

    public List<String> getTopBookNamesLast168Hours() {
        LocalDateTime startDate = LocalDateTime.now().minusHours(168);
        return historyDao.findTopBookNamesLast24Hours(startDate);
    }

    public List<String> getTopBookNamesLast720Hours() {
        LocalDateTime startDate = LocalDateTime.now().minusHours(720);
        return historyDao.findTopBookNamesLast24Hours(startDate);
    }

    public List<String> getTopBookNamesLast24Hours() {
        LocalDateTime startDate = LocalDateTime.now().minusHours(24);
        return historyDao.findTopBookNamesLast24Hours(startDate);
    }

    public List<String> getTop3Books() {
        return historyDao.getTop3Books();
    }

}

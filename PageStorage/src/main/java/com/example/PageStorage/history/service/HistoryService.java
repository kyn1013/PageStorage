package com.example.PageStorage.history.service;

import com.example.PageStorage.entity.*;
import com.example.PageStorage.history.dao.HistoryDao;
import com.example.PageStorage.history.dao.HistoryImageDao;
import com.example.PageStorage.history.dao.HistoryKeywordDao;
import com.example.PageStorage.history.dao.KeywordDao;
import com.example.PageStorage.history.dto.HistoryRequestDto;
import com.example.PageStorage.history.dto.response.HistoryAllResponseDto;
import com.example.PageStorage.history.dto.response.HistoryResponseDto;
import com.example.PageStorage.historytag.dao.HistoryTagDao;
import com.example.PageStorage.member.dao.MemberDao;
import com.example.PageStorage.security.login.dao.LoginDao;
import com.example.PageStorage.tag.dao.TagDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        //기존 태그 삭제
        historyTagDao.deleteHistorySeq(history.getHistorySeq());
        history.getHistoryTags().clear();

        //태그 재생성
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

        String originalFilename = historyRequestDto.getImageFile().getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);
        String filePath = createPath(storeFilename);

        HistoryImage historyImage = historyImageDao.findByHistorySeq(historySeq);
        historyImage.changeInfo(originalFilename, storeFilename, historyRequestDto.getImageFile().getContentType(), filePath);

        // 새 이미지 정보 추가 및 파일 저장
        history.addHistoryImage(historyImage);
        historyImageDao.save(historyImage);
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

        // History 객체 저장
        history = historyDao.save(history);

        String tagString = historyRequestDto.getTagNames();
        Set<String> tagsSet = Arrays.stream(tagString.split("#"))
                .filter(tag -> !tag.isEmpty())
                .collect(Collectors.toSet());
        for (String tag : tagsSet) {
            Tag savedTag = tagDao.findOrCreate(tag);

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

        //기존 태그 제거
        historyKeywordDao.deleteHistorySeq(history.getHistorySeq());
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


    public History find(Long historySeq) {
        return historyDao.find(historySeq);
    }

    public List<History> findByMail(String mail) {
        Member member = memberDao.findMail(mail);
        return historyDao.findByMember(member);
    }


    /*
    커서기반 페이징 히스토리 조회
     */
    public HistoryAllResponseDto findAllByIdCursorBased(Long cursorId, int pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize + 1);
        List<History> histories = findAllByCursorIdCheckExistsCursor(cursorId, pageable);
        boolean hasNext = hasNext(histories.size(), pageSize);

//        List<PostResponse> postResponses = new ArrayList<>();
//        List<Post> subList = toSubListIfHasNext(hasNext, pageSize, posts);
//        for (Post post : subList) {
//            postResponses.add(new PostResponse(post));
//        }
//
//        return new PostResponses(postResponses, cursorId, hasNext);

        List<History> subList = toSubListIfHasNext(hasNext, pageSize, histories);
        List<HistoryResponseDto> responseDtos = HistoryResponseDto.buildDtoAllList(subList);

//        for (History history : subList) {
//            historyAllResponseDtos.add(HistoryResponseDto.buildDto(history));
//        }

        return new HistoryAllResponseDto(responseDtos, cursorId, hasNext);
    }

    private List<History> findAllByCursorIdCheckExistsCursor(Long cursorId, Pageable pageable) {
        return cursorId == null ? historyDao.findAllByOrderByHistorySeqDescCreatedDateDesc(pageable)
                : historyDao.findByHistorySeqLessThanOrderByHistorySeqDescCreatedDateDesc(cursorId, pageable);
    }

    private boolean hasNext(int postsSize, int pageSize) {
//        if (postsSize == 0) {
//            throw new EntityNotFoundException(Post.class);
//        }
        return postsSize > pageSize;
    }


    private List<History> toSubListIfHasNext(boolean hasNext, int pageSize, List<History> histories) {
        return hasNext ? histories.subList(0, pageSize) : histories;
    }

    /*
    히스토리 제거
     */
    public void delete(Long historySeq) {
        historyDao.delete(historySeq);
    }

    /*
    책 랭킹 조회
     */
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

    /*
    사진 저장 관련 메서드
     */
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

}

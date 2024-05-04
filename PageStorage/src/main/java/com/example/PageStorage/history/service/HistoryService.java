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
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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

        return historyDao.save(history);
    }

    public void saveKeyword(Set<String> keywordSet, Long historySeq) {
        History history = find(historySeq);

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

    public History update(HistoryRequestDto historyRequestDto) {
        History history = historyDao.findByMemberNameAndBookName(
                historyRequestDto.getUserLoginId(), historyRequestDto.getBookName());
        history.changeInfo(historyRequestDto);


        historyTagDao.deleteHistorySeq(history.getHistorySeq());
        // 기존 태그 관계 모두 삭제 (단순 예제, 실제로는 보다 세심한 접근 방식이 필요할 수 있음)
        history.getHistoryTags().clear();
//        historyTagDao.deleteHistorySeq(history.getHistorySeq());

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

        return history;
    }



    public void delete(HistoryDeleteDto historyDeleteDto) {
        History history = historyDao.findByMemberNameAndBookName(
                historyDeleteDto.getMemberName(), historyDeleteDto.getBookName());
        historyDao.delete(history);
    }

    public void delete(Long historySeq) {
        historyDao.delete(historySeq);
    }


}

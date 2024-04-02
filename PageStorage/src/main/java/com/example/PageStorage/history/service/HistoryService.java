package com.example.PageStorage.history.service;

import com.example.PageStorage.entity.*;
import com.example.PageStorage.genre.dto.GenreUpdateRequestDto;
import com.example.PageStorage.history.dao.HistoryDao;
import com.example.PageStorage.history.dto.HistoryDeleteDto;
import com.example.PageStorage.history.dto.HistoryRequestDto;
import com.example.PageStorage.historytag.dao.HistoryTagDao;
import com.example.PageStorage.member.dao.MemberDao;
import com.example.PageStorage.tag.dao.TagDao;
import com.example.PageStorage.tag.dto.TagRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class HistoryService {

    private final HistoryDao historyDao;
    private final HistoryTagDao historyTagDao;
    private final MemberDao memberDao;
    private final TagDao tagDao;

    public History saveHistory(HistoryRequestDto historyRequestDto) {
        History history = History.builder()
                .bookName(historyRequestDto.getBookName())
                .historyContent(historyRequestDto.getHistoryContent())
                .phrase(historyRequestDto.getPhrase())
                .difficulty(historyRequestDto.getDifficulty())
                .applicationToLife(historyRequestDto.getApplicationToLife())
                .bookRecommender(historyRequestDto.getBookRecommender())
                .build();
        Member member = memberDao.findName(historyRequestDto.getMemberName());
        history.addMember(member);

        Set<TagRequestDto> tagRequestDtos = historyRequestDto.getTagRequestDtos();
        for (TagRequestDto tagRequestDto : tagRequestDtos) {
            Tag tag = tagDao.findOrCreate(tagRequestDto.getTagName());

            HistoryTag historyTag = HistoryTag.builder()
                    .history(history)
                    .tag(tag)
                    .build();

            history.getHistoryTags().add(historyTag);
        }

        return historyDao.save(history);
    }

    public History find(Long historySeq) {
        return historyDao.find(historySeq);
    }

    public List<History> findHistoriesByMemberName(String memberName) {
        Member member = memberDao.findName(memberName);
        return historyDao.findByMember(member);
    }

    public List<History> findHistoriesByBookName(String bookName) {
        return historyDao.find(bookName);
    }

    public List<History> findAll() {
        return historyDao.findAll();
    }

    public History update(HistoryRequestDto historyRequestDto) {
        History history = historyDao.findByMemberNameAndBookName(
                historyRequestDto.getMemberName(), historyRequestDto.getBookName());
        history.changeInfo(historyRequestDto);


        historyTagDao.deleteHistorySeq(history.getHistorySeq());
        // 기존 태그 관계 모두 삭제 (단순 예제, 실제로는 보다 세심한 접근 방식이 필요할 수 있음)
        history.getHistoryTags().clear();
//        historyTagDao.deleteHistorySeq(history.getHistorySeq());

        // 새로운 태그 관계 추가
        Set<TagRequestDto> tagRequestDtos = historyRequestDto.getTagRequestDtos();
        for (TagRequestDto tagRequestDto : tagRequestDtos) {
            Tag tag = tagDao.findOrCreate(tagRequestDto.getTagName());
            HistoryTag historyTag = HistoryTag.builder()
                    .history(history)
                    .tag(tag)
                    .build();

            // 연관 관계 설정
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

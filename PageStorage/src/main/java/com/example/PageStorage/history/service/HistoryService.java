package com.example.PageStorage.history.service;

import com.example.PageStorage.entity.Genre;
import com.example.PageStorage.entity.History;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.genre.dto.GenreUpdateRequestDto;
import com.example.PageStorage.history.dao.HistoryDao;
import com.example.PageStorage.history.dto.HistoryDeleteDto;
import com.example.PageStorage.history.dto.HistoryRequestDto;
import com.example.PageStorage.member.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class HistoryService {

    private final HistoryDao historyDao;
    private final MemberDao memberDao;

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

        return historyDao.save(history);
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

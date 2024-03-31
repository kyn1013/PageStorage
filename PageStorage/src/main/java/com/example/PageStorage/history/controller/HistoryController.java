package com.example.PageStorage.history.controller;

import com.example.PageStorage.common.PsResponse;
import com.example.PageStorage.common.code.SuccessCode;
import com.example.PageStorage.common.model.ResBodyModel;
import com.example.PageStorage.entity.History;
import com.example.PageStorage.history.dto.HistoryDeleteDto;
import com.example.PageStorage.history.dto.HistoryRequestDto;
import com.example.PageStorage.history.dto.response.HistoryResponseDto;
import com.example.PageStorage.history.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/histories")
public class HistoryController {

    private final HistoryService historyService;

    /*
    히스토리 추가
     */
    @PostMapping()
    public ResponseEntity<ResBodyModel> historySave(@RequestBody HistoryRequestDto historyRequestDto) {

        History history = historyService.saveHistory(historyRequestDto);
        HistoryResponseDto historyResponseDto = HistoryResponseDto.buildDto(history);
        return PsResponse.toResponse(SuccessCode.SUCCES,historyResponseDto);
    }

    /*
    히스토리 조회
     */
    @GetMapping("/{memberName}")
    public ResponseEntity<ResBodyModel> findByMemberName(@PathVariable String memberName) {
        List<History> histories = historyService.findHistoriesByMemberName(memberName);
        List<HistoryResponseDto> historyResponseDtos = HistoryResponseDto.buildDtoList(histories);
        return PsResponse.toResponse(SuccessCode.SUCCES,historyResponseDtos);
    }

    @GetMapping("/bookName/{bookName}")
    public ResponseEntity<ResBodyModel> findByBookName(@PathVariable String bookName) {
        List<History> histories = historyService.findHistoriesByBookName(bookName);
        List<HistoryResponseDto> historyResponseDtos = HistoryResponseDto.buildDtoList(histories);
        return PsResponse.toResponse(SuccessCode.SUCCES,historyResponseDtos);
    }

    @GetMapping("/all")
    public ResponseEntity<ResBodyModel> findAll () {
        List<History> histories = historyService.findAll();
        List<HistoryResponseDto> historyResponseDtos = HistoryResponseDto.buildDtoList(histories);
        return PsResponse.toResponse(SuccessCode.SUCCES,historyResponseDtos);
    }

    /*
    히스토리 수정
     */
    @PatchMapping("/update")
    public ResponseEntity<ResBodyModel> updateHistory(@RequestBody HistoryRequestDto historyRequestDto){
        History history = historyService.update(historyRequestDto);
        HistoryResponseDto historyResponseDto = HistoryResponseDto.buildDto(history);
        return PsResponse.toResponse(SuccessCode.SUCCES,historyResponseDto);
    }

    /*
    히스토리 삭제
     */
    @DeleteMapping("/{historySeq}")
    public ResponseEntity<ResBodyModel> deleteBySeq(@PathVariable Long historySeq) {
        historyService.delete(historySeq);
        return PsResponse.toResponse(SuccessCode.SUCCES);
    }

    @DeleteMapping()
    public ResponseEntity<ResBodyModel> delete(@RequestBody HistoryDeleteDto historyDeleteDto) {
        historyService.delete(historyDeleteDto);
        return PsResponse.toResponse(SuccessCode.SUCCES);
    }

}

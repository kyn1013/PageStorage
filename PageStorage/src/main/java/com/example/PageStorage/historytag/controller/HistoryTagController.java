package com.example.PageStorage.historytag.controller;

import com.example.PageStorage.common.PsResponse;
import com.example.PageStorage.common.code.SuccessCode;
import com.example.PageStorage.common.model.ResBodyModel;
import com.example.PageStorage.historytag.service.HistoryTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/historytags")
public class HistoryTagController {
    private final HistoryTagService historyTagService;

    @DeleteMapping("/{historytags}")
    public ResponseEntity<ResBodyModel> deleteBySeq(@PathVariable Long historytags) {
        historyTagService.delete(historytags);
        return PsResponse.toResponse(SuccessCode.SUCCES);
    }

}

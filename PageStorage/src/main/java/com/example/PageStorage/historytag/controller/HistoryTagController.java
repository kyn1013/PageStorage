package com.example.PageStorage.historytag.controller;

import com.example.PageStorage.common.PsResponse;
import com.example.PageStorage.common.code.SuccessCode;
import com.example.PageStorage.common.model.ResBodyModel;
import com.example.PageStorage.historytag.service.HistoryTagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/historytags")
@Slf4j
public class HistoryTagController {
    private final HistoryTagService historyTagService;

    @DeleteMapping("/{historytags}")
    public ResponseEntity<ResBodyModel> deleteBySeq(@PathVariable Long historytags) {
        historyTagService.delete(historytags);
        return PsResponse.toResponse(SuccessCode.SUCCES);
    }

}

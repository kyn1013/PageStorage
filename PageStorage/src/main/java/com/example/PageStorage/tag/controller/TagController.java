package com.example.PageStorage.tag.controller;

import com.example.PageStorage.common.PsResponse;
import com.example.PageStorage.common.code.SuccessCode;
import com.example.PageStorage.common.model.ResBodyModel;
import com.example.PageStorage.entity.Tag;
import com.example.PageStorage.tag.dto.TagRequestDto;
import com.example.PageStorage.tag.dto.TagUpdateRequestDto;
import com.example.PageStorage.tag.dto.response.TagResponseDto;
import com.example.PageStorage.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    /*
    태크 추가
     */
    @PostMapping()
    public ResponseEntity<ResBodyModel> tagSave(@RequestBody TagRequestDto tagRequestDto) {

        Tag tag = tagService.saveTag(tagRequestDto);
        TagResponseDto tagResponseDto = TagResponseDto.buildDto(tag);
        return PsResponse.toResponse(SuccessCode.SUCCES,tagResponseDto);
    }

    /*
    태그 조회
     */
    @GetMapping("/name/{tagName}")
    public ResponseEntity<ResBodyModel> findByTagName(@PathVariable String tagName) {
        Tag tag = tagService.findByTagName(tagName);
        TagResponseDto tagResponseDto = TagResponseDto.buildDto(tag);
        return PsResponse.toResponse(SuccessCode.SUCCES,tagResponseDto);
    }

    @GetMapping("/{tagSeq}")
    public ResponseEntity<ResBodyModel> findByTagSeq(@PathVariable Long tagSeq) {
        Tag tag = tagService.findBySeq(tagSeq);
        TagResponseDto tagResponseDto = TagResponseDto.buildDto(tag);
        return PsResponse.toResponse(SuccessCode.SUCCES,tagResponseDto);
    }

    @GetMapping("/all")
    public ResponseEntity<ResBodyModel> findAll() {
        List<Tag> tags = tagService.findAll();
        List<TagResponseDto> tagResponseDto = TagResponseDto.buildDtotoList(tags);
        return PsResponse.toResponse(SuccessCode.SUCCES,tagResponseDto);
    }

    /*
    태그 수정
     */
    @PatchMapping()
    public ResponseEntity<ResBodyModel> update(@RequestBody TagUpdateRequestDto tagUpdateRequestDto) {

        Tag tag = tagService.update(tagUpdateRequestDto);
        TagResponseDto tagResponseDto = TagResponseDto.buildDto(tag);
        return PsResponse.toResponse(SuccessCode.SUCCES,tagResponseDto);
    }

    /*
    태그 삭제
     */
    @DeleteMapping ("/name/{tagName}")
    public ResponseEntity<ResBodyModel> deleteTagName(@PathVariable String tagName) {
        tagService.delete(tagName);
        return PsResponse.toResponse(SuccessCode.SUCCES);
    }

    @DeleteMapping ("/{tagSeq}")
    public ResponseEntity<ResBodyModel> deleteTagSeq(@PathVariable Long tagSeq) {
        tagService.delete(tagSeq);
        return PsResponse.toResponse(SuccessCode.SUCCES);
    }
}

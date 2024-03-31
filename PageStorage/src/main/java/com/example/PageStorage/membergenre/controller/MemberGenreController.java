package com.example.PageStorage.membergenre.controller;

import com.example.PageStorage.common.code.SuccessCode;
import com.example.PageStorage.common.PsResponse;
import com.example.PageStorage.common.model.ResBodyModel;
import com.example.PageStorage.entity.Genre;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.entity.MemberGenre;
import com.example.PageStorage.genre.dto.response.GenreResponseDto;
import com.example.PageStorage.membergenre.dto.MemberGenreRequestDto;
import com.example.PageStorage.membergenre.dto.response.MemberGenreResponseDto;
import com.example.PageStorage.membergenre.dto.response.MemberResponseDto;
import com.example.PageStorage.membergenre.service.MemberGenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/membergenres")
public class MemberGenreController {

    private final MemberGenreService memberGenreService;

    /*
    선호 장르 추가
     */
    @PostMapping()
    public ResponseEntity<ResBodyModel> memberGenreSave(@RequestBody MemberGenreRequestDto memberGenreRequestDto) {

        MemberGenre memberGenre = memberGenreService.saveMemberGenre(memberGenreRequestDto);
        MemberGenreResponseDto memberGenreResponseDto = MemberGenreResponseDto.buildDto(memberGenre);
        return PsResponse.toResponse(SuccessCode.SUCCES,memberGenreResponseDto);
    }

    /*
    회원으로 선호 장르 조회
     */
    @GetMapping("/{memberName}")
    public ResponseEntity<ResBodyModel> readGenresByMemberName(@PathVariable String memberName) {
        List<Genre> genres = memberGenreService.findGenresByMemberName(memberName);
        List<GenreResponseDto> GenreResponseDtoList = GenreResponseDto.buildGenreResponseDtotoList(genres);
        return PsResponse.toResponse(SuccessCode.SUCCES, GenreResponseDtoList);
    }

    /*
    장르로 선호 회원 조회
     */
    @GetMapping("/read/{genreName}")
    public ResponseEntity<ResBodyModel> readMembersByGenre(@PathVariable String genreName) {
        List<Member> members = memberGenreService.findMembersByGenreName(genreName);
        List<MemberResponseDto> memberResponseDtos = MemberResponseDto.buildResponseDtoList(members);
        return PsResponse.toResponse(SuccessCode.SUCCES, memberResponseDtos);
    }

    /*
    전체 조회
     */
    @GetMapping("/all")
    public ResponseEntity<ResBodyModel> readAll() {
        List<MemberGenre> memberGenres = memberGenreService.findAll();
        List<MemberGenreResponseDto> memberGenreResponseDtos = MemberGenreResponseDto.buildDtoToList(memberGenres);
        return PsResponse.toResponse(SuccessCode.SUCCES,memberGenreResponseDtos);
    }

    /*
    삭제
     */
    @DeleteMapping()
    public ResponseEntity<ResBodyModel> delete(@RequestBody MemberGenreRequestDto memberGenreRequestDto) {
        memberGenreService.delete(memberGenreRequestDto);
        return PsResponse.toResponse(SuccessCode.SUCCES);
    }


}

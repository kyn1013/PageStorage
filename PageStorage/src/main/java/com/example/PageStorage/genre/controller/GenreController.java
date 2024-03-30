package com.example.PageStorage.genre.controller;

import com.example.PageStorage.code.SuccessCode;
import com.example.PageStorage.common.PsResponse;
import com.example.PageStorage.common.model.ResBodyModel;
import com.example.PageStorage.entity.Genre;
import com.example.PageStorage.genre.dto.GenreRequestDto;
import com.example.PageStorage.genre.dto.GenreUpdateRequestDto;
import com.example.PageStorage.genre.dto.response.GenreResponseDto;
import com.example.PageStorage.genre.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    /*
    장르 추가
     */
    @PostMapping()
    public ResponseEntity<ResBodyModel> genreSave(@RequestBody GenreRequestDto genreRequestDto) {

        Genre genre = genreService.saveGenre(genreRequestDto);
        GenreResponseDto genreResponseDto = GenreResponseDto.buildDto(genre);
        return PsResponse.toResponse(SuccessCode.SUCCES,genreResponseDto);

    }

    /*
    장르 조회
     */
    @GetMapping("/{genreName}")
    public ResponseEntity<ResBodyModel> findByGenreName(@PathVariable String genreName) {
        Genre genre = genreService.find(genreName);
        GenreResponseDto genreResponseDto = GenreResponseDto.buildDto(genre);
        return PsResponse.toResponse(SuccessCode.SUCCES, genreResponseDto);
    }


    @GetMapping("/{genreSeq}")
    public ResponseEntity<ResBodyModel> findGenreByName(@PathVariable Long genreSeq) {
        Genre genre = genreService.find(genreSeq);
        GenreResponseDto genreResponseDto = GenreResponseDto.buildDto(genre);
        return PsResponse.toResponse(SuccessCode.SUCCES, genreResponseDto);
    }

    @GetMapping("/all")
    public ResponseEntity<ResBodyModel> readAll() {
        List<Genre> genres = genreService.findAll();
        List<GenreResponseDto> GenreResponseDtoList = GenreResponseDto.buildGenreResponseDtotoList(genres);
        return PsResponse.toResponse(SuccessCode.SUCCES, GenreResponseDtoList);
    }

    /*
    장르 수정
     */
    @PatchMapping("/update")
    public ResponseEntity<ResBodyModel> updateMember(@RequestBody GenreUpdateRequestDto genreUpdateRequestDto){
        Genre genre = genreService.update(genreUpdateRequestDto);
        GenreResponseDto genreResponseDto = GenreResponseDto.buildDto(genre);
        return PsResponse.toResponse(SuccessCode.SUCCES, genreResponseDto);
    }

    /*
    장르 삭제
     */
    @DeleteMapping("/{genreName}")
    public ResponseEntity<ResBodyModel> delete(@PathVariable String genreName) {
        genreService.delete(genreName);
        return PsResponse.toResponse(SuccessCode.SUCCES);
    }

}

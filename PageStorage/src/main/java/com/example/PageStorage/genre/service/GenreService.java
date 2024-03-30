package com.example.PageStorage.genre.service;

import com.example.PageStorage.entity.Genre;
import com.example.PageStorage.genre.dto.GenreRequestDto;
import com.example.PageStorage.genre.dto.GenreUpdateRequestDto;

import java.util.List;

public interface GenreService {

    Genre saveGenre(GenreRequestDto genreRequestDto);

    Genre find(String genreName);

    Genre find(Long genreSeq);

    List<Genre> findAll();

    Genre update(GenreUpdateRequestDto genreUpdateRequestDto);

    void delete(String genreName);

    void delete(Long genreSeq);
}

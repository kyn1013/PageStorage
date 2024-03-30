package com.example.PageStorage.genre.service;

import com.example.PageStorage.entity.Genre;
import com.example.PageStorage.genre.dao.GenreDao;
import com.example.PageStorage.genre.dto.GenreRequestDto;
import com.example.PageStorage.genre.dto.GenreUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GenreServiceImpl implements GenreService{

    private final GenreDao genreDao;

    @Override
    public Genre saveGenre(GenreRequestDto genreRequestDto) {
        Genre genre = Genre.builder()
                .genreName(genreRequestDto.getGenreName())
                .build();

        return genreDao.save(genre);
    }

    @Override
    public Genre find(String genreName) {
        return genreDao.find(genreName);
    }

    @Override
    public Genre find(Long genreSeq) {
        return genreDao.find(genreSeq);
    }

    @Override
    public List<Genre> findAll() {
        List<Genre> genres = genreDao.findAll();
        return genres;
    }

    @Override
    public Genre update(GenreUpdateRequestDto genreUpdateRequestDto) {
        Genre genre = genreDao.find(genreUpdateRequestDto.getGenreSeq());
        genre.changeInfo(genreUpdateRequestDto);
        return genre;
    }

    @Override
    public void delete(String genreName) {
        genreDao.delete(genreName);
    }

    @Override
    public void delete(Long genreSeq) {
        genreDao.delete(genreSeq);
    }





}

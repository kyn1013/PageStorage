package com.example.PageStorage.genre.dao;

import com.example.PageStorage.entity.Genre;
import com.example.PageStorage.exception.DataNotFoundException;
import com.example.PageStorage.genre.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GenreDao {

    private final GenreRepository genreRepository;

    public Genre save(Genre genre) {
        Genre savedGenre = genreRepository.save(genre);
        return savedGenre;
    }

    public Genre find(Long genreSeq) {
        Genre genre = genreRepository.findById(genreSeq).orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));
        return genre;
    }

    public Genre find(String genreName) {
        Genre genre = genreRepository.findByGenreName(genreName).orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));
        return genre;
    }

    public List<Genre> findAll() {
        List<Genre> genres = genreRepository.findAll();
        return genres;
    }

    public void delete(String genreName) {
        Genre genre = genreRepository.findByGenreName(genreName).orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));
        genreRepository.delete(genre);
    }

    public void delete(Long genreSeq) {
        Genre genre = genreRepository.findById(genreSeq).orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));
        genreRepository.delete(genre);
    }

}

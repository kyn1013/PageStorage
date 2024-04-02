package com.example.PageStorage.tag.service;

import com.example.PageStorage.entity.Genre;
import com.example.PageStorage.entity.Tag;
import com.example.PageStorage.genre.dto.GenreRequestDto;
import com.example.PageStorage.genre.dto.GenreUpdateRequestDto;
import com.example.PageStorage.tag.dao.TagDao;
import com.example.PageStorage.tag.dto.TagRequestDto;
import com.example.PageStorage.tag.dto.TagUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TagService {
    private final TagDao tagDao;

    public Tag saveTag(TagRequestDto tagRequestDto) {
        Tag tag = Tag.builder()
                .tagName(tagRequestDto.getTagName())
                .build();

        return tagDao.save(tag);
    }

    public Tag findByTagName(String tagName) {
        return tagDao.find(tagName);
    }

    public Tag findBySeq(Long tagSeq) {
        return tagDao.find(tagSeq);
    }

    public List<Tag> findAll() {
        List<Tag> tags = tagDao.findAll();
        return tags;
    }

    public Tag update(TagUpdateRequestDto tagUpdateRequestDto) {
        Tag tag = tagDao.find(tagUpdateRequestDto.getTagSeq());
        tag.changeInfo(tagUpdateRequestDto);
        return tag;
    }

    public void delete(String tagName) {
        tagDao.delete(tagName);
    }

    public void delete(Long tagSeq) {
        tagDao.delete(tagSeq);
    }
}

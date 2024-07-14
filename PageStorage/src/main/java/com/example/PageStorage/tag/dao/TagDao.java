package com.example.PageStorage.tag.dao;

import com.example.PageStorage.common.exception.DataNotFoundException;
import com.example.PageStorage.entity.Tag;
import com.example.PageStorage.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TagDao {
    private final TagRepository tagRepository;

    public Tag save(Tag tag) {
        Tag savedTag = tagRepository.save(tag);
        return savedTag;
    }

    public Tag find(Long tagSeq) {
        Tag tag = tagRepository.findById(tagSeq).orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));
        return tag;
    }

    public Tag find(String tagName) {
        Tag tag = tagRepository.findByTagName(tagName).orElseThrow(() -> new DataNotFoundException("존재하지 않는 태그입니다"));
        return tag;
    }

    public Tag findOrCreate(String tagName) {
        Tag tag;
        try {
            // 태그가 존재하면 반환, 존재하지 않으면 예외 던짐
            tag = tagRepository.findByTagName(tagName).orElseThrow(() ->
                    new DataNotFoundException("존재하지 않는 태그입니다."));
        } catch (DataNotFoundException e) {
            // 태그가 존재하지 않을 경우, 새로운 태그를 생성하고 저장함
            tag = new Tag(tagName);
            tagRepository.save(tag);
        }
        return tag;
    }

    public List<Tag> findAll() {
        List<Tag> tags = tagRepository.findAll();
        return tags;
    }

    public void delete(Long tagSeq) {
        Tag tag = tagRepository.findById(tagSeq).orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));
        tagRepository.delete(tag);
    }

    public void delete(String tagName) {
        Tag tag = tagRepository.findByTagName(tagName).orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));
        tagRepository.delete(tag);
    }


}

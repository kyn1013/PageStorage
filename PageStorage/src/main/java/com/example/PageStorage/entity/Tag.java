package com.example.PageStorage.entity;

import com.example.PageStorage.tag.dto.TagRequestDto;
import com.example.PageStorage.tag.dto.TagUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tag")
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_seq")
    private Long tagSeq;

    @Column(name = "tag_name")
    private String tagName;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private Set<HistoryTag> historyTags = new HashSet<>();

    @Builder
    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public void changeInfo (TagUpdateRequestDto tagUpdateRequestDto) {
        this.tagName = tagUpdateRequestDto.getTagName();
    }

}

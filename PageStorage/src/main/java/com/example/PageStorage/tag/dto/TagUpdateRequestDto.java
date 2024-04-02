package com.example.PageStorage.tag.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagUpdateRequestDto {
    private String tagName;
    private Long tagSeq;
}

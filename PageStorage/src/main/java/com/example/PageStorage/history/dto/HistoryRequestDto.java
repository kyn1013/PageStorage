package com.example.PageStorage.history.dto;

import com.example.PageStorage.tag.dto.TagRequestDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class HistoryRequestDto {

    @NotEmpty(message="책 제목은 필수항목입니다.")
    @Size(max=200)
    private String bookName;
    @NotEmpty(message="내용은 필수항목입니다.")
    private String historyContent;
    private String phrase;
    private String difficulty;
    private String applicationToLife;
    private String bookRecommender;
    private MultipartFile imageFile;
//    private Set<TagRequestDto> tagRequestDtos;
    private String tagNames;
    private String userLoginId;

}

package com.example.PageStorage.history.dto;

import com.example.PageStorage.common.validation.ValidFile;
import com.example.PageStorage.tag.dto.TagRequestDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class HistoryRequestDto {

    @NotEmpty(message="책 제목은 필수 항목입니다.")
    @Size(max=200)
    private String bookName;
    @NotEmpty(message="내용은 필수 항목입니다.")
    private String historyContent;
    @Size(max=65)
    private String phrase;
    @Size(max=35)
    private String difficulty;
    private String applicationToLife;
    @Size(max=35)
    private String bookRecommender;
    @ValidFile(message = "이미지 파일은 필수 항목입니다.")
    private MultipartFile imageFile;
    private String fileName;
//    private Set<TagRequestDto> tagRequestDtos;
    private String tagNames;
    private String userLoginId;
    private Long updateSeq;

}

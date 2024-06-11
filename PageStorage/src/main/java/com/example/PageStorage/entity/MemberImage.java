package com.example.PageStorage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member_image")
@Entity
public class MemberImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_image_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String originFilename; //원본 이름
    private String storeFilename; //파일을 저장한 이름, 원본 이름에서 중복이 날 수 있기 때문에 생성
    private String type; //타입
    private String filePath; //경로

    @Builder
    public MemberImage(String originFilename, String storeFilename, String type, String filePath, Member member) { //이게 맞나..?ㅜ
        this.originFilename = originFilename;
        this.storeFilename = storeFilename;
        this.type = type;
        this.filePath = filePath;
        this.member = member;
    }

    public void changeInfo(String originFilename, String storeFilename, String type, String filePath) {
        this.originFilename = originFilename;
        this.storeFilename = storeFilename;
        this.type = type;
        this.filePath = filePath;
    }


}

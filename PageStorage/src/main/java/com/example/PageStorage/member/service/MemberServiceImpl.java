package com.example.PageStorage.member.service;

import com.example.PageStorage.entity.HistoryImage;
import com.example.PageStorage.entity.Login;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.entity.MemberImage;
import com.example.PageStorage.member.dao.MemberImageDao;
import com.example.PageStorage.member.dto.MemberUpdateRequestDto;
import com.example.PageStorage.security.login.dao.LoginDao;
import com.example.PageStorage.member.dao.MemberDao;
import com.example.PageStorage.member.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberServiceImpl implements MemberService{

    private final PasswordEncoder passwordEncoder;

    private final MemberDao memberDao;
    private final LoginDao loginDao;
    private final MemberImageDao memberImageDao;

    private final String FOLDER_PATH="/Users/gim-yena/Desktop/profile/";

    @Override
    public Member saveMember(MemberSaveRequestDto memberSaveRequestDto) {
        Member member = Member.builder()
                .name(memberSaveRequestDto.getName())
                .nickName(memberSaveRequestDto.getNickName())
                .phoneNumber(memberSaveRequestDto.getPhoneNumber())
                .mail(memberSaveRequestDto.getMail())
                .build();

        Member savedMember = memberDao.save(member); //회원 객체 반환

        Login login = Login.builder()
                .userLoginId(memberSaveRequestDto.getUserLoginId())
                .userLoginPassword(passwordEncoder.encode((memberSaveRequestDto.getUserLoginPassword())))
                .role("ROLE_USER")
                .build();

        login.addMember(savedMember);
        loginDao.save(login); //로그인, 비밀번호 저장
        return savedMember;
    }

    @Override
    public Member find(String userLoginId) {
        Member member = loginDao.findByUserLoginId(userLoginId);
        return member;
    }

    @Override
    public Member find(Long memberSeq) {
        return memberDao.find(memberSeq);
    }

    @Override
    public Member findNickName(String nickName) {
        return memberDao.findNickName(nickName);
    }

    @Override
    public Member findName(String name) {
        return memberDao.findName(name);
    }

    @Override
    public List<Member> findAll() {
        return memberDao.findAll();
    }

    @Override
    public Member update(MemberSaveRequestDto memberSaveRequestDto) {
        Member member = memberDao.findName(memberSaveRequestDto.getName());
        member.changeInfo(memberSaveRequestDto);
        return member;
    }

    @Override
    public Member updateProfile(MemberUpdateRequestDto memberUpdateRequestDto) throws IOException {
        Member member = find(memberUpdateRequestDto.getUserLoginId());

        String originalFilename = memberUpdateRequestDto.getImageFile().getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);
        String filePath = createPath(storeFilename);

        MemberImage memberImage = MemberImage.builder()
                .originFilename(originalFilename)
                .storeFilename(storeFilename)
                .type(memberUpdateRequestDto.getImageFile().getContentType())
                .filePath(filePath)
                .member(member)
                .build();

        //멤버에 이미지 정보 추가
        member.changeProfile(memberUpdateRequestDto, memberImage);
        memberImageDao.save(memberImage);

        memberUpdateRequestDto.getImageFile().transferTo(new File(filePath));

        return member;
    }

    public String createPath(String storeFilename) {
        return FOLDER_PATH+storeFilename;
    }

    private String createStoreFilename(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        String storeFilename = uuid + ext;

        return storeFilename;
    }

    private String extractExt(String originalFilename) {
        int idx = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(idx);
        return ext;
    }

    @Override
    public Login delete(String userLoginId) {
        return loginDao.delete(userLoginId);
    }

    @Override
    public void delete(Long memberSeq) {
        memberDao.delete(memberSeq);
    }
}

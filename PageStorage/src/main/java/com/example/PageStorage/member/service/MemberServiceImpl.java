package com.example.PageStorage.member.service;

import com.example.PageStorage.common.exception.member.BothLoginIdAndMailExistsException;
import com.example.PageStorage.common.exception.member.LoginIdAlreadyExistsException;
import com.example.PageStorage.common.exception.member.MailAlreadyExistsException;
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
import java.nio.file.Files;
import java.nio.file.Paths;
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
        boolean loginIdExists = loginDao.existsByUserLoginId(memberSaveRequestDto.getUserLoginId());
        boolean mailExists = memberDao.existsByMail(memberSaveRequestDto.getMail());

        if (loginIdExists && mailExists) {
            throw new BothLoginIdAndMailExistsException("이미 존재하는 아이디와 메일입니다.");
        } else if (loginIdExists) {
            throw new LoginIdAlreadyExistsException("이미 존재하는 아이디입니다.");
        } else if (mailExists) {
            throw new MailAlreadyExistsException("이미 존재하는 메일입니다.");
        }

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

//        existsByUserLoginId(login.getUserLoginId());
//        existsByMail(member.getMail());

        loginDao.save(login); //로그인, 비밀번호 저장
        return savedMember;
    }

    /*
    로그인 아이디 중복 검사
     */
    private void existsByUserLoginId(String loginId) {
        Boolean result = loginDao.existsByUserLoginId(loginId);
        if (result) {
            throw new LoginIdAlreadyExistsException("이미 존재하는 아이디입니다.");
        }
    }

    /*
    회원 이메일 중복 검사
     */
    private void existsByMail(String mail) {
        Boolean result = memberDao.existsByMail(mail);
        if (result) {
            throw new MailAlreadyExistsException("이미 존재하는 메일입니다.");
        }
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
//        member.changeInfo(memberSaveRequestDto);
        return member;
    }

    @Override
    public Member updateProfile(MemberUpdateRequestDto memberUpdateRequestDto) throws IOException {
        Member member = find(memberUpdateRequestDto.getUserLoginId());
        System.out.println(member);

        member.changeInfo(memberUpdateRequestDto);

        //사진을 등록하지 않았다면 닉네임만 바꾸고 종료
        if (memberUpdateRequestDto.getImageFile().isEmpty()){
            return member;
        }

            if (member.getMemberImage() != null && member.getMemberImage().getFilePath() != null) {
                //저장된 이미지 삭제
                Files.deleteIfExists(Paths.get(member.getMemberImage().getFilePath()));
            }

            String originalFilename = memberUpdateRequestDto.getImageFile().getOriginalFilename();
            String storeFilename = createStoreFilename(originalFilename);
            String filePath = createPath(storeFilename);

            MemberImage memberImage;

            //이미지가 없었는데 새로 등록할 떄
            if (member.getMemberImage() == null) {
                memberImage = MemberImage.builder()
                        .originFilename(originalFilename)
                        .storeFilename(storeFilename)
                        .type(memberUpdateRequestDto.getImageFile().getContentType())
                        .filePath(filePath)
                        .member(member)
                        .build();
            } else {
                //기존 이미지에서 새로운 이미지로 변경할 때
                memberImage = memberImageDao.findByMemberSeq(member.getMemberSeq());
                memberImage.changeInfo(originalFilename, storeFilename, memberUpdateRequestDto.getImageFile().getContentType(), filePath);
            }

            member.addMemberImage(memberImage);
            memberImageDao.save(memberImage);

            // 멤버에 이미지 정보 추가
            // member.changeProfile(memberUpdateRequestDto, memberImage);

            // memberImageDao.save(memberImage);
            // memberDao.save(member);

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

package com.nft.store.service;

import com.nft.store.dto.AUCTION;
import com.nft.store.dto.MEMBER;
import com.nft.store.dto.PAGE;
import com.nft.store.dto.T_DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.UUID;


@Service
public class MService {
    @Autowired
    private com.nft.store.dao.MDAO MDAO;

    @Autowired
    private HttpSession session;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder pwEnc;


    private ModelAndView mav = new ModelAndView();


    // 회원가입 메소드
    public ModelAndView mJoin(MEMBER MDTO) throws IOException {
        // 생년월일 합치기
        MDTO.setMBirth(MDTO.getYy()+ MDTO.getMm() + MDTO.getDd());

        // (1) 파일 불러오기
        MultipartFile mProfile = MDTO.getMProfile();

        // (2) 파일이름 설정하기
        String originalFileName = mProfile.getOriginalFilename();

        // 스프링 파일 업로드 할 때 문제점! + 파일 이름이 같을 경우!

        // (3) 난수 생성하기
        String uuid = UUID.randomUUID().toString().substring(1, 7);

        // (4) 난수와 파일이름 합치기 : d8nd01_inchoriya.png
        String mProfileName = uuid + "_" + originalFileName;

        // (5) 파일 저장위치
        String savePath = "C:/Boot/store/src/main/resources/static/profile/" + mProfileName;
        System.out.println("mProfileName : " + mProfileName);
        // (6) 파일 선택여부
        if (!mProfile.isEmpty()) {
            MDTO.setMProfileName(mProfileName);
            mProfile.transferTo(new File(savePath));
        } else {
            MDTO.setMProfileName("default.png");
        }

        // 아이디, 비밀번호, 이메일
        System.out.println("암호화 전 비밀번호 확인 : " + MDTO.getMPw());
        // [1] 우리가 입력한 패스워드
        // [2] 암호화
        // [3] 암호화 된 패스워드를 SecuDTO에 다시 저장
        MDTO.setMPw(pwEnc.encode(MDTO.getMPw()));

        // Q. 어떤 작업? 가입(입력)
        // Q. 입력, 수정, 삭제 시 int result 사용!
        int result = MDAO.mJoin(MDTO);


        if (result > 0) {
            // 성공
            mav.setViewName("redirect:/");
        } else {
            // 실패
            mav.setViewName("M_joinForm");
        }

        return mav;
    }

    // 로그인 메소드
    public ModelAndView mLogin(MEMBER MDTO) {

        MEMBER MDTO1 = MDAO.mLogin(MDTO);

        if (pwEnc.matches(MDTO.getMPw(), MDTO1.getMPw())) {
            System.out.println("비밀번호 일치!");


            session.setAttribute("loginId", MDTO1.getMId());
            session.setAttribute("loginNick", MDTO1.getMNick());
            session.setAttribute("loginState", MDTO1.getMState());
            session.setAttribute("loginProfile", MDTO1.getMProfileName());
            mav.setViewName("redirect:/");


        } else {
            System.out.println("비밀번호 불일치!");
            mav.setViewName("M_loginForm");
        }
        return mav;
    }

    private static final int PAGE_LIMIT = 5; // 한 페이지당 글 개수
    private static final int BLOCK_LIMIT = 5; // 한 페이지당 페이지 개수

    // 회원목록보기 메소드
    public ModelAndView mList(int page) {

        PAGE paging = new PAGE();

        int listCount = MDAO.mListCount();

        int startRow = (page - 1) * PAGE_LIMIT + 1;
        int endRow = page * PAGE_LIMIT;

        int maxPage = (int) (Math.ceil((double) listCount / PAGE_LIMIT));
        int startPage = (((int) (Math.ceil((double) page / BLOCK_LIMIT))) - 1) * BLOCK_LIMIT + 1;
        int endPage = startPage + BLOCK_LIMIT - 1;

        if (endPage > maxPage) {
            endPage = maxPage;
        }

        paging.setPage(page);
        paging.setStartRow(startRow);
        paging.setEndRow(endRow);
        paging.setMaxPage(maxPage);
        paging.setStartPage(startPage);
        paging.setEndPage(endPage);

        List<MEMBER> memberList = MDAO.mList(paging);

        mav.setViewName("M_list");
        mav.addObject("memberList", memberList);
        mav.addObject("paging", paging);

        return mav;
    }

    // 회원 상세보기
    public ModelAndView mView(String mId) {
        MEMBER member = MDAO.mView(mId);
        member.setYy(member.getMBirth().substring(0,4));
        member.setMm(member.getMBirth().substring(4,6));
        member.setDd(member.getMBirth().substring(6,8));

        if (member != null) {
            // 검색 한 회원의 정보가 존재할 때 (not null일때)
            mav.addObject("member", member);
            mav.setViewName("M_view");
        } else {
            // 검색 한 회원의 정보가 존재하지 않을 때 -> 리스트로 돌아가기
            // html파일이 아닌 controller의 주소로 값을 보낼 때 redirect:/주소
            mav.setViewName("redirect:/mList");
        }

        return mav;
    }

    public ModelAndView mModiForm(String mId) {

        MEMBER member = MDAO.mView(mId);
        member.setYy(member.getMBirth().substring(0,4));
        member.setMm(member.getMBirth().substring(4,6));
        member.setDd(member.getMBirth().substring(6,8));

        if (member != null) {
            // 검색 한 회원의 정보가 존재할 때 (not null일때)
            mav.addObject("member", member);
            mav.setViewName("M_modify");
        } else {
            // 검색 한 회원의 정보가 존재하지 않을 때 -> 리스트로 돌아가기
            // html파일이 아닌 controller의 주소로 값을 보낼 때 redirect:/주소
            mav.setViewName("redirect:/M_ist");
        }

        return mav;
    }

    // 회원 수정
    public ModelAndView mModify(MEMBER MDTO) throws IOException {
        // 생년월일 합치기
        MDTO.setMBirth(MDTO.getYy()+ MDTO.getMm() + MDTO.getDd());

        // (1) 파일 불러오기
        MultipartFile mProfile = MDTO.getMProfile();

        // (2) 파일이름 설정하기
        String originalFileName = mProfile.getOriginalFilename();

        // 스프링 파일 업로드 할 때 문제점! + 파일 이름이 같을 경우!

        // (3) 난수 생성하기
        String uuid = UUID.randomUUID().toString().substring(1, 7);

        // (4) 난수와 파일이름 합치기 : d8nd01_inchoriya.png
        String mProfileName = uuid + "_" + originalFileName;

        // (5) 파일 저장위치

        String savePath = "C:/Boot/store/src/main/resources/static/profile/" + mProfileName;

        // (6) 파일 선택여부
        if (!mProfile.isEmpty()) {
            MDTO.setMProfileName(mProfileName);
            mProfile.transferTo(new File(savePath));
        } else {
            MDTO.setMProfileName("default.png");
        }

        MDTO.setMPw(pwEnc.encode(MDTO.getMPw()));

        int result = MDAO.mModify(MDTO);


        if (result > 0) {
            mav.setViewName("M_loginForm");
        } else {
            mav.setViewName("redirect:/M_modiForm?mId=" + MDTO.getMId());
        }

        return mav;
    }

    // 회원 삭제
    public ModelAndView mDelete(String mId, int mState) {
        int result = MDAO.mDelete(mId);

        if (result > 0) {
            if(mState==1){
                mav.setViewName("redirect:/M_list");
            }
            else{
                mav.setViewName("redirect:/M_logout");
            }

        } else {
            mav.setViewName("redirect:/");
        }

        return mav;
    }


    // 회원 중복검사
    public String idoverlap(String mId) {

        String idCheck = MDAO.idoverlap(mId);
        String result = null;

        if (idCheck == null) {
            result = "OK";
        } else {
            result = "NO";
        }

        return result;
    }


    // 회원 상태
    public String mState(String mId) {

        int State = MDAO.mState(mId);
        String result = null;
        System.out.println("State :" + State);
        if (State < 3) {
            result = "OK";
        } else {
            result = "NO";
        }
        System.out.println("result :" + result);


        return result;
    }

    // 닉네임 중복검사
    public String Nickoverlap(String mNick) {
        String NickCheck = MDAO.Nickoverlap(mNick);
        String result = null;

        if (NickCheck == null) {
            result = "OK";
        } else {
            result = "NO";
        }

        return result;
    }

    public String mFindId(String mName, String mBirth) {

        String FindId = MDAO.mFindId(mName,mBirth);
        String result = null;

        if(FindId!=null){
            result = FindId;
        } else {
            result = "NO";
        }

        return result;
    }

    public String mFindPw(String mId, String mName, String mBirth) {
        String FindPw = MDAO.mFindPw(mId,mName,mBirth);
        String result = null;
        String str = "";
        String Title = "NFT Store";
        String pw = "1111";
        if(FindPw!=null){
            String mPw=pwEnc.encode(pw);
            MDAO.ChangePw(mId,mPw);

            result = "OK";
            Title = "NFT Store - 비밀번호";
            str = "<h2>안녕하세요, NFT Store 입니다.</h2>" +
                    "<p>회원님의 비밀번호는 "+ 1111+ " 로 초기화되었습니다.</p>" +
                    "<p>접속 후 비밀번호를 변경해주세요</p>";
            MimeMessage mail = mailSender.createMimeMessage();
            try {
                mail.setSubject(Title);
                mail.setText(str, "UTF-8","html");
                mail.addRecipient(Message.RecipientType.TO, new InternetAddress(mId));

                mailSender.send(mail);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } else {
            result = "NO";
        }

        return result;
    }


    public ModelAndView index() {
        List<AUCTION> auctions = MDAO.aList();
        List<T_DTO> market = MDAO.tList();

        mav.addObject("aList", auctions);
        mav.addObject("tList", market);
        mav.setViewName("index");
        return mav;
    }
}

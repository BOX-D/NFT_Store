package com.nft.store.controller;

import com.nft.store.dto.MEMBER;
import com.nft.store.service.MService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
public class MController {

    @Autowired
    private MService msvc;

    @Autowired
    private HttpSession session;


    private ModelAndView mav = new ModelAndView();

    // 처음화면 실행
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        mav = msvc.index();
        return mav;
    }

    // 메인화면으로 이동
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index1() {
        return "redirect:/";
    }

    // 어바웃 페이지로 이동
    @RequestMapping(value = "about", method = RequestMethod.GET)
    public String about() {
        return "About";
    }
    // M_joinForm : 회원가입 페이지로 이동
    @RequestMapping(value = "M_joinForm", method = RequestMethod.GET)
    public String joinForm() {
        return "M_joinForm";
    }

    // M_loginForm : 로그인 페이지로 이동
    @RequestMapping(value = "M_loginForm", method = RequestMethod.GET)
    public String loginForm() {
        return "M_loginForm";
    }

    // M_findIdForm : 아이디 찾기로 이동
    @RequestMapping(value = "M_findIdForm", method = RequestMethod.GET)
    public String findId() {
        return "M_findIdForm";
    }

    // M_findIdForm : 비밀번호 찾기로 이동
    @RequestMapping(value = "M_findPwForm", method = RequestMethod.GET)
    public String findPw() {
        return "M_findPwForm";
    }

    // M_join : 회원가입
    @RequestMapping(value = "M_join", method = RequestMethod.POST)
    public ModelAndView mJoin(@ModelAttribute MEMBER MDTO) throws IOException {
        mav = msvc.mJoin(MDTO);
        return mav;
    }

    // M_login : 로그인
    @RequestMapping(value = "M_login", method = RequestMethod.POST)
    public ModelAndView mLogin(@ModelAttribute MEMBER MDTO) {
        mav = msvc.mLogin(MDTO);
        return mav;
    }

    // M_logout : 로그아웃
    @RequestMapping(value = "M_logout", method = RequestMethod.GET)
    public String mLogout() {

        session.invalidate();   // session을 초기화 하는 작업
        return "index";
    }

    // M_list : 회원목록 보기
    @RequestMapping(value = "M_list", method = RequestMethod.GET)
    public ModelAndView mList(@RequestParam(value="page", required= false, defaultValue="1") int page) {

        mav = msvc.mList(page);

        return mav;
    }

    // M_view : 회원 상세보기
    @RequestMapping(value = "M_view", method = RequestMethod.GET)
    public ModelAndView mView(@RequestParam("mId") String mId) {
        mav = msvc.mView(mId);
        return mav;
    }

    // M_modiForm : 회원 수정 페이지로 이동
    @RequestMapping(value = "M_modiForm", method = RequestMethod.GET)
    public ModelAndView mModiForm(@RequestParam("mId") String mId) {
        mav = msvc.mModiForm(mId);
        return mav;
    }

    // M_modify : 회원 수정
    @RequestMapping(value = "M_modify", method = RequestMethod.POST)
    public ModelAndView mModify(@ModelAttribute MEMBER MDTO) throws IOException {

        mav = msvc.mModify(MDTO);


        return mav;
    }

    // M_delete : 회원 삭제(GET방식)
    @RequestMapping(value = "M_delete", method = RequestMethod.GET)
    public ModelAndView mDelete(@RequestParam("mId") String mId,@RequestParam("mState") int mState) {
        mav = msvc.mDelete(mId,mState);
        return mav;
    }

    // A_idoverlap : 아이디 중복검사
    @RequestMapping(value = "A_idoverlap", method = RequestMethod.GET)
    public @ResponseBody
    String A_idoverlap(@RequestParam("mId") String mId) {

        String result = msvc.idoverlap(mId);

        return result;
    }

    // A_Nickoverlap : 아이디 중복검사
    @RequestMapping(value = "A_Nickoverlap", method = RequestMethod.GET)
    public @ResponseBody
    String A_Nickoverlap(@RequestParam("mNick") String mNick) {

        String result = msvc.Nickoverlap(mNick);

        return result;
    }

    // M_state : 회원 상태 검사
    @RequestMapping(value = "M_state", method = RequestMethod.GET)
    public @ResponseBody
    String mState(@RequestParam("mId") String mId) {

        String result = msvc.mState(mId);

        return result;
    }

    // mFindId : 아이디 찾기
    @RequestMapping(value = "mFindId", method = RequestMethod.GET)
    public @ResponseBody
    String mFindId(@RequestParam("mName") String mName,@RequestParam("mBirth") String mBirth) {

        String result = msvc.mFindId(mName,mBirth);

        return result;
    }

    // mFindPw : 비밀번호 찾기
    @RequestMapping(value = "mFindPw", method = RequestMethod.GET)
    public @ResponseBody
    String mFindPw(@RequestParam("mId") String mId,@RequestParam("mName") String mName,@RequestParam("mBirth") String mBirth) {

        String result = msvc.mFindPw(mId,mName,mBirth);

        return result;
    }

}

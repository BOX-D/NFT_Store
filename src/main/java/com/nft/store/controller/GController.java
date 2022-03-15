package com.nft.store.controller;

import com.nft.store.dto.COMMENTS;
import com.nft.store.dto.GALLERY;
import com.nft.store.dto.LIKE;
import com.nft.store.dto.MEMBER;
import com.nft.store.service.GService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class GController {
    @Autowired
    private GService gsvc;

    @Autowired
    private HttpSession session;

    private ModelAndView mav = new ModelAndView();


    // gView : 글 상세보기
    @RequestMapping("/gView")
    public ModelAndView gView(@RequestParam(value = "gNo")int gNo){
        mav = gsvc.gView(gNo);
        return mav;
    }
    // gProfile : 프로필 페이지로
    @RequestMapping("/gProfile")
    public ModelAndView gProfile(@RequestParam(value = "gWriter")String gWriter){
        mav = gsvc.gProfile(gWriter);
        return mav;
    }
    // gLike : 좋아요 버튼 눌렀을 때
    @RequestMapping("/gLike")
    public ModelAndView gLike(@ModelAttribute LIKE like, @RequestParam(value = "iLike")boolean iLike){
        mav = gsvc.gLike(like, iLike);
        return mav;
    }
    // gModiform : 글 수정 페이지로
    @RequestMapping("/gModiform")
    public ModelAndView gModiform(@RequestParam(value = "gNo")int gNo){
        mav = gsvc.gModiform(gNo);
        return mav;
    }
    // gModify : 글 수정하기
    @RequestMapping( "/gModify")
    public ModelAndView gModify(@ModelAttribute GALLERY gallery){
        mav = gsvc.gModify(gallery);
        return mav;
    }
    // gDelete : 글 삭제하기
    @RequestMapping( "/gDelete")
    public ModelAndView gDelete(@RequestParam(value = "gNo")int gNo){
        mav = gsvc.gDelete(gNo);
        return mav;
    }
    // cModify : 댓글 수정하기
    @RequestMapping("/cModify")
    public ModelAndView cModify(@ModelAttribute COMMENTS comment){
        mav = gsvc.cModify(comment);
        return mav;
    }
    // cDelete : 댓글 삭제하기
    @RequestMapping("/cDelete")
    public ModelAndView cModify(@RequestParam(value = "cNo")int cNo,@RequestParam(value = "cgNo")int cgNo){
        mav = gsvc.cDelete(cNo, cgNo);
        return mav;
    }
    // cWrite : 댓글 작성하기
    @RequestMapping("/cWrite")
    public ModelAndView cWrite(@ModelAttribute COMMENTS comment){
        mav = gsvc.cWrite(comment);
        return mav;
    }
    // iLike _ajax : 좋아요 눌렀었는지 확인하기
    @RequestMapping("/iLike")
    public @ResponseBody String iLike(@ModelAttribute LIKE like){
        String data = "o";
        boolean iLike = gsvc.iLike(like);
        if(!iLike){
            data = "x";
        }
        return data;
    }

    // writeForm : 게시글 작성 페이지 이동
    @RequestMapping(value = "/writeForm", method = RequestMethod.GET)
    public ModelAndView writeForm(@RequestParam("bCode") int bCode){

        // 구매 리스트에서 선택한 상품 코드 받기
        mav = gsvc.writeForm(bCode);
        return mav;
    }

    // gWrite : 글작성
    @RequestMapping(value = "/gWrite", method = RequestMethod.POST)
    public ModelAndView gWrite(@ModelAttribute GALLERY gallery) throws IOException {
        mav = gsvc.gWrite(gallery);
        return mav;
    }

    // bList : 구매 리스트
    @RequestMapping(value = "/bList", method = RequestMethod.GET)
    public ModelAndView bList(@RequestParam("bId") String bId){

        // 구매자 id 가져가기
        mav = gsvc.bList(bId);
        return mav;
    }

    // gList : 갤러리 메인 목록 부분
    @RequestMapping(value = "/gList", method = RequestMethod.GET)
    public ModelAndView gList(){
        mav = gsvc.gList();
        return mav;
    }

    // gSearch : 게시글 검색
    @RequestMapping(value = "gSearch", method = RequestMethod.GET)
    public ModelAndView bSearch(@RequestParam("selectVal") String selectVal, @RequestParam("keyword") String keyword) {
        System.out.println("selectVal : " + selectVal);
        System.out.println("keyword : " + keyword);

        mav = gsvc.gSearch(selectVal, keyword);
        return mav;
    }
}
